package com.gameoptimizer.app.domain.usecase

import android.app.ActivityManager
import android.content.Context
import javax.inject.Inject

class MemoryOptimizationUseCase @Inject constructor(
    private val context: Context
) {

    fun optimizeMemory(): MemoryOptimizationResult {
        val beforeMemory = getMemoryInfo()

        clearApplicationCache()

        System.gc()
        System.runFinalization()

        val afterMemory = getMemoryInfo()

        return MemoryOptimizationResult(
            freedMemoryMB = beforeMemory.usedMemoryMB - afterMemory.usedMemoryMB,
            beforeUsageMB = beforeMemory.usedMemoryMB,
            afterUsageMB = afterMemory.usedMemoryMB,
            successRate = calculateSuccessRate(beforeMemory, afterMemory)
        )
    }

    fun getMemoryInfo(): MemoryInfo {
        val runtime = Runtime.getRuntime()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        val javaHeap = runtime.totalMemory() - runtime.freeMemory()
        val totalMemory = runtime.maxMemory()
        val usedMemory = javaHeap

        return MemoryInfo(
            totalMemoryMB = totalMemory / (1024 * 1024),
            usedMemoryMB = usedMemory / (1024 * 1024),
            freeMemoryMB = runtime.freeMemory() / (1024 * 1024),
            nativeHeapMB = 0,
            javaHeapMB = javaHeap / (1024 * 1024),
            deviceTotalMemoryMB = memInfo.totalMemory / (1024 * 1024),
            deviceAvailableMemoryMB = memInfo.availableMemory / (1024 * 1024),
            isLowMemory = memInfo.lowMemory
        )
    }

    private fun clearApplicationCache() {
        try {
            val cacheDir = context.cacheDir
            if (cacheDir.isDirectory) {
                deleteDir(cacheDir)
            }

            val externalCacheDir = context.externalCacheDir
            if (externalCacheDir != null && externalCacheDir.isDirectory) {
                deleteDir(externalCacheDir)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: java.io.File): Boolean {
        return if (dir.isDirectory) {
            val children = dir.listFiles() ?: return true
            for (child in children) {
                deleteDir(child)
            }
            dir.delete()
        } else {
            dir.delete()
        }
    }

    private fun calculateSuccessRate(before: MemoryInfo, after: MemoryInfo): Float {
        val freed = before.usedMemoryMB - after.usedMemoryMB
        return if (before.usedMemoryMB > 0) {
            (freed.toFloat() / before.usedMemoryMB.toFloat()) * 100f
        } else {
            0f
        }
    }

    fun isMemoryWarning(): Boolean {
        val memInfo = getMemoryInfo()
        val usagePercent = (memInfo.usedMemoryMB.toFloat() / memInfo.totalMemoryMB.toFloat()) * 100f
        return usagePercent > 80f
    }

    fun isCriticalMemory(): Boolean {
        val memInfo = getMemoryInfo()
        val usagePercent = (memInfo.usedMemoryMB.toFloat() / memInfo.totalMemoryMB.toFloat()) * 100f
        return usagePercent > 95f
    }
}

data class MemoryInfo(
    val totalMemoryMB: Long,
    val usedMemoryMB: Long,
    val freeMemoryMB: Long,
    val nativeHeapMB: Long,
    val javaHeapMB: Long,
    val deviceTotalMemoryMB: Long,
    val deviceAvailableMemoryMB: Long,
    val isLowMemory: Boolean
)

data class MemoryOptimizationResult(
    val freedMemoryMB: Long,
    val beforeUsageMB: Long,
    val afterUsageMB: Long,
    val successRate: Float
)