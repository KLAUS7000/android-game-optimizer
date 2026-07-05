package com.gameoptimizer.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameoptimizer.app.data.model.GraphicsQuality
import com.gameoptimizer.app.domain.usecase.FpsMonitorUseCase
import com.gameoptimizer.app.domain.usecase.FpsData
import com.gameoptimizer.app.domain.usecase.GraphicsOptimizationUseCase
import com.gameoptimizer.app.domain.usecase.MemoryOptimizationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameOptimizerViewModel @Inject constructor(
    private val fpsMonitorUseCase: FpsMonitorUseCase,
    private val graphicsOptimizationUseCase: GraphicsOptimizationUseCase,
    private val memoryOptimizationUseCase: MemoryOptimizationUseCase
) : ViewModel() {

    private val _fpsData = MutableStateFlow<FpsData?>(null)
    val fpsData: StateFlow<FpsData?> = _fpsData.asStateFlow()

    private val _currentGraphicsQuality = MutableStateFlow(GraphicsQuality.MEDIUM)
    val currentGraphicsQuality: StateFlow<GraphicsQuality> = _currentGraphicsQuality.asStateFlow()

    private val _optimizationResult = MutableStateFlow<String>("")
    val optimizationResult: StateFlow<String> = _optimizationResult.asStateFlow()

    private val _isMonitoring = MutableStateFlow(false)
    val isMonitoring: StateFlow<Boolean> = _isMonitoring.asStateFlow()

    private val _deviceInfo = MutableStateFlow("")
    val deviceInfo: StateFlow<String> = _deviceInfo.asStateFlow()

    init {
        loadDeviceInfo()
    }

    fun startFpsMonitoring() {
        _isMonitoring.value = true
        viewModelScope.launch {
            fpsMonitorUseCase.monitorFpsInRealTime().collect { fpsData ->
                _fpsData.value = fpsData
            }
        }
    }

    fun stopFpsMonitoring() {
        _isMonitoring.value = false
    }

    fun reduceGraphicsQuality(quality: GraphicsQuality) {
        _currentGraphicsQuality.value = quality
        val result = graphicsOptimizationUseCase.reduceGraphicsQuality(quality)

        val message = buildString {
            appendLine("🎮 Qualidade Gráfica: \${result.quality.description}")
            appendLine("📊 Resolução de Texturas: \${result.textureResolution}")
            appendLine("✨ Anti-Aliasing: \${result.antiAliasingLevel}x")
            appendLine("🌑 Qualidade de Sombras: \${result.shadowQuality}")
            appendLine("💫 Partículas: \${(result.particleCount * 100).toInt()}%")
            appendLine("📉 Redução de Draw Calls: \${(result.drawCallReduction * 100).toInt()}%")
            appendLine("⚡ Aumento de FPS Estimado: +\${result.estimatedFpsIncrease}")
            appendLine()
            appendLine("🖥️ Otimizações GPU:")
            result.gpuOptimizations.forEach { appendLine("  • \$it") }
            appendLine()
            appendLine("⚙️ Otimizações CPU:")
            result.cpuOptimizations.forEach { appendLine("  • \$it") }
            appendLine()
            appendLine("🧠 Otimizações de Memória:")
            result.memoryOptimizations.forEach { appendLine("  • \$it") }
        }

        _optimizationResult.value = message
    }

    fun optimizeMemoryNow() {
        viewModelScope.launch {
            val result = memoryOptimizationUseCase.optimizeMemory()
            _optimizationResult.value = buildString {
                appendLine("🧹 Limpeza de Memória Concluída!")
                appendLine("📊 Memória Liberada: \${result.freedMemoryMB} MB")
                appendLine("📉 Antes: \${result.beforeUsageMB} MB")
                appendLine("📈 Depois: \${result.afterUsageMB} MB")
                appendLine("✅ Taxa de Sucesso: \${result.successRate.toInt()}%")
            }
        }
    }

    fun getRecommendedQuality() {
        val recommended = graphicsOptimizationUseCase.getRecommendedQualityByDevicePerformance()
        reduceGraphicsQuality(recommended)
    }

    private fun loadDeviceInfo() {
        viewModelScope.launch {
            val info = graphicsOptimizationUseCase.getDeviceInfo()
            val memInfo = memoryOptimizationUseCase.getMemoryInfo()

            _deviceInfo.value = buildString {
                appendLine("📱 Informações do Dispositivo:")
                appendLine("Modelo: \${info.model}")
                appendLine("Fabricante: \${info.manufacturer}")
                appendLine("Android: \${info.androidVersion}")
                appendLine("CPU: \${info.cpuCores} núcleos")
                appendLine("RAM: \${info.totalRamGB} GB")
                appendLine("Memória em Uso: \${memInfo.usedMemoryMB} MB / \${memInfo.totalMemoryMB} MB")
                appendLine("Dispositivo de Baixa Memória: \${if (info.isLowMemoryDevice) "Sim" else "Não"}")
            }
        }
    }
}