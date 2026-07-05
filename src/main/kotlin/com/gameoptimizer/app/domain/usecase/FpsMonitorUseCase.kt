package com.gameoptimizer.app.domain.usecase

import android.app.ActivityManager
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FpsMonitorUseCase @Inject constructor(
    private val context: Context
) {

    fun monitorFpsInRealTime(): Flow<FpsData> = flow {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var previousTime = System.currentTimeMillis()
        var frameCount = 0

        while (true) {
            val currentTime = System.currentTimeMillis()
            val deltaTime = (currentTime - previousTime).toFloat() / 1000f

            if (deltaTime > 0) {
                frameCount++
                val fps = frameCount / deltaTime

                val memInfo = ActivityManager.MemoryInfo()
                activityManager.getMemoryInfo(memInfo)

                val javaHeap = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()

                val fpsData = FpsData(
                    currentFps = fps.toInt(),
                    averageFps = fps.toInt(),
                    maxFps = 120,
                    minFps = fps.toInt(),
                    memoryUsageMB = (javaHeap / (1024 * 1024)).toFloat(),
                    totalMemoryMB = (memInfo.totalMemory / (1024 * 1024)).toFloat(),
                    cpuUsagePercent = getCpuUsage(),
                    temperatureC = getDeviceTemperature(),
                    batteryPercent = getBatteryLevel(),
                    isLagging = fps < 30,
                    timestamp = System.currentTimeMillis()
                )

                emit(fpsData)

                if (deltaTime > 1.0f) {
                    previousTime = currentTime
                    frameCount = 0
                }
            }

            Thread.sleep(100)
        }
    }

    private fun getCpuUsage(): Float {
        return try {
            val reader = java.io.BufferedReader(java.io.FileReader("/proc/stat"))
            val line = reader.readLine()
            reader.close()

            val tokens = line.split(" +".toRegex())
            if (tokens.size >= 5) {
                val user = tokens[1].toLong()
                val nice = tokens[2].toLong()
                val system = tokens[3].toLong()
                val idle = tokens[4].toLong()

                val totalCpu = user + nice + system + idle
                val usedCpu = user + nice + system

                if (totalCpu > 0) {
                    (usedCpu.toFloat() / totalCpu.toFloat()) * 100f
                } else {
                    0f
                }
            } else {
                0f
            }
        } catch (e: Exception) {
            0f
        }
    }

    private fun getDeviceTemperature(): Float {
        return try {
            val reader = java.io.BufferedReader(
                java.io.FileReader("/sys/class/thermal/thermal_zone0/temp")
            )
            val temp = reader.readLine().toFloat() / 1000
            reader.close()
            temp
        } catch (e: Exception) {
            25f
        }
    }

    private fun getBatteryLevel(): Int {
        return try {
            val ifilter = android.content.IntentFilter(
                android.content.Intent.ACTION_BATTERY_CHANGED
            )
            val batteryStatus = context.registerReceiver(null, ifilter)
            val level = batteryStatus?.getIntExtra(
                android.os.BatteryManager.EXTRA_LEVEL,
                -1
            ) ?: -1
            level
        } catch (e: Exception) {
            -1
        }
    }

    fun detectLagSpikes(fpsDataList: List<FpsData>): List<LagSpike> {
        if (fpsDataList.size < 2) return emptyList()

        val lagSpikes = mutableListOf<LagSpike>()
        val avgFps = fpsDataList.map { it.currentFps }.average()

        var spikeStart = 0
        var inSpike = false

        for (i in fpsDataList.indices) {
            val fps = fpsDataList[i].currentFps
            val isLagging = fps < (avgFps * 0.5)

            if (isLagging && !inSpike) {
                spikeStart = i
                inSpike = true
            } else if (!isLagging && inSpike) {
                val duration = fpsDataList[i].timestamp - fpsDataList[spikeStart].timestamp
                lagSpikes.add(
                    LagSpike(
                        startTime = fpsDataList[spikeStart].timestamp,
                        endTime = fpsDataList[i].timestamp,
                        durationMs = duration,
                        severity = calculateSeverity(fpsDataList[spikeStart].currentFps.toFloat(), avgFps.toFloat())
                    )
                )
                inSpike = false
            }
        }

        return lagSpikes
    }

    private fun calculateSeverity(currentFps: Float, avgFps: Float): LagSpikesSeverity {
        val fpsLoss = ((avgFps - currentFps) / avgFps) * 100

        return when {
            fpsLoss > 80 -> LagSpikesSeverity.CRITICAL
            fpsLoss > 60 -> LagSpikesSeverity.SEVERE
            fpsLoss > 40 -> LagSpikesSeverity.MODERATE
            else -> LagSpikesSeverity.MILD
        }
    }
}

data class FpsData(
    val currentFps: Int,
    val averageFps: Int,
    val maxFps: Int,
    val minFps: Int,
    val memoryUsageMB: Float,
    val totalMemoryMB: Float,
    val cpuUsagePercent: Float,
    val temperatureC: Float,
    val batteryPercent: Int,
    val isLagging: Boolean,
    val timestamp: Long
)

data class LagSpike(
    val startTime: Long,
    val endTime: Long,
    val durationMs: Long,
    val severity: LagSpikesSeverity
)

enum class LagSpikesSeverity {
    MILD, MODERATE, SEVERE, CRITICAL
}