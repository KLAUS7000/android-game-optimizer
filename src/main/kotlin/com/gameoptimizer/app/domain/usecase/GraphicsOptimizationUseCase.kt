package com.gameoptimizer.app.domain.usecase

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.gameoptimizer.app.data.model.GraphicsQuality
import javax.inject.Inject

class GraphicsOptimizationUseCase @Inject constructor(
    private val context: Context
) {

    fun reduceGraphicsQuality(targetQuality: GraphicsQuality): GraphicsOptimizationResult {
        return GraphicsOptimizationResult(
            quality = targetQuality,
            textureResolution = targetQuality.getTextureResolution(),
            antiAliasingLevel = targetQuality.getAntiAliasingLevel(),
            shadowQuality = targetQuality.getShadowQuality(),
            particleCount = targetQuality.getParticleCount(),
            drawCallReduction = targetQuality.getDrawCallReduction(),
            estimatedFpsIncrease = targetQuality.getEstimatedFpsIncrease(),
            gpuOptimizations = getGPUOptimizations(targetQuality),
            cpuOptimizations = getCPUOptimizations(targetQuality),
            memoryOptimizations = getMemoryOptimizations(targetQuality)
        )
    }

    private fun getGPUOptimizations(quality: GraphicsQuality): List<String> {
        return when (quality) {
            GraphicsQuality.ULTRA -> listOf(
                "Renderização completa em 4K",
                "Efeitos visuais avançados ativados",
                "Ray tracing parcial habilitado"
            )
            GraphicsQuality.HIGH -> listOf(
                "Renderização em Full HD",
                "Efeitos visuais moderados",
                "Bloom e reflexos habilitados"
            )
            GraphicsQuality.MEDIUM -> listOf(
                "Renderização reduzida a 1280x720",
                "Efeitos visuais limitados",
                "Bloom desabilitado"
            )
            GraphicsQuality.LOW -> listOf(
                "Renderização em 960x540",
                "Efeitos mínimos",
                "Sombras dinâmicas desabilitadas",
                "LOD (Level of Detail) agressivo ativado"
            )
            GraphicsQuality.ULTRA_LOW -> listOf(
                "Renderização em 720x400",
                "Sem efeitos visuais",
                "Todas as sombras desabilitadas",
                "Geometria simplificada",
                "Modo compatibilidade ativado"
            )
        }
    }

    private fun getCPUOptimizations(quality: GraphicsQuality): List<String> {
        return when (quality) {
            GraphicsQuality.ULTRA -> listOf(
                "Processamento completo de física",
                "IA em qualidade máxima"
            )
            GraphicsQuality.HIGH -> listOf(
                "Processamento normal de física",
                "IA otimizada"
            )
            GraphicsQuality.MEDIUM -> listOf(
                "Física simplificada",
                "IA reduzida",
                "Threads limitadas a 4"
            )
            GraphicsQuality.LOW -> listOf(
                "Física mínima",
                "IA desabilitada parcialmente",
                "Threads limitadas a 2",
                "Update rate reduzido para 30 Hz"
            )
            GraphicsQuality.ULTRA_LOW -> listOf(
                "Sem cálculos de física",
                "IA desabilitada",
                "Single thread processamento",
                "Update rate fixo em 20 Hz",
                "Culling agressivo ativado"
            )
        }
    }

    private fun getMemoryOptimizations(quality: GraphicsQuality): List<String> {
        return when (quality) {
            GraphicsQuality.ULTRA -> listOf(
                "Cache máximo de assets",
                "Sem limite de memória de texturas"
            )
            GraphicsQuality.HIGH -> listOf(
                "Cache moderado de assets",
                "Limite de memória de texturas: 2GB"
            )
            GraphicsQuality.MEDIUM -> listOf(
                "Cache reduzido de assets",
                "Limite de memória de texturas: 1GB",
                "Limpeza de cache a cada 2 minutos"
            )
            GraphicsQuality.LOW -> listOf(
                "Cache mínimo de assets",
                "Limite de memória de texturas: 512MB",
                "Limpeza agressiva de cache",
                "LOD de texturas ativado"
            )
            GraphicsQuality.ULTRA_LOW -> listOf(
                "Cache desabilitado",
                "Limite de memória de texturas: 256MB",
                "Carregamento dinâmico de assets",
                "Compressão de texturas máxima",
                "Pool de objetos reutilizáveis"
            )
        }
    }

    fun getRecommendedQualityByDevicePerformance(): GraphicsQuality {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        val totalRamGB = memInfo.totalMemory / (1024 * 1024 * 1024)
        val cpuCores = Runtime.getRuntime().availableProcessors()

        return when {
            totalRamGB >= 8 && cpuCores >= 8 -> GraphicsQuality.ULTRA
            totalRamGB >= 6 && cpuCores >= 6 -> GraphicsQuality.HIGH
            totalRamGB >= 4 && cpuCores >= 4 -> GraphicsQuality.MEDIUM
            totalRamGB >= 2 -> GraphicsQuality.LOW
            else -> GraphicsQuality.ULTRA_LOW
        }
    }

    fun getDeviceInfo(): DeviceInfo {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        return DeviceInfo(
            totalRamGB = memInfo.totalMemory / (1024 * 1024 * 1024),
            availableRamGB = memInfo.availableMemory / (1024 * 1024 * 1024),
            cpuCores = Runtime.getRuntime().availableProcessors(),
            androidVersion = Build.VERSION.SDK_INT,
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            isLowMemoryDevice = memInfo.lowMemory
        )
    }
}

data class GraphicsOptimizationResult(
    val quality: GraphicsQuality,
    val textureResolution: String,
    val antiAliasingLevel: Int,
    val shadowQuality: String,
    val particleCount: Float,
    val drawCallReduction: Float,
    val estimatedFpsIncrease: Int,
    val gpuOptimizations: List<String>,
    val cpuOptimizations: List<String>,
    val memoryOptimizations: List<String>
)

data class DeviceInfo(
    val totalRamGB: Long,
    val availableRamGB: Long,
    val cpuCores: Int,
    val androidVersion: Int,
    val manufacturer: String,
    val model: String,
    val isLowMemoryDevice: Boolean
)