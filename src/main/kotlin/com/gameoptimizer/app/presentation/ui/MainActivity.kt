package com.gameoptimizer.app.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gameoptimizer.app.data.model.GraphicsQuality
import com.gameoptimizer.app.domain.usecase.FpsMonitorUseCase
import com.gameoptimizer.app.domain.usecase.GraphicsOptimizationUseCase
import com.gameoptimizer.app.domain.usecase.MemoryOptimizationUseCase
import com.gameoptimizer.app.presentation.viewmodel.GameOptimizerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fpsMonitorUseCase = FpsMonitorUseCase(this)
        val graphicsOptimizationUseCase = GraphicsOptimizationUseCase(this)
        val memoryOptimizationUseCase = MemoryOptimizationUseCase(this)

        val viewModel = GameOptimizerViewModel(
            fpsMonitorUseCase,
            graphicsOptimizationUseCase,
            memoryOptimizationUseCase
        )

        setContent {
            GameOptimizerApp(viewModel = viewModel)
        }
    }
}

@Composable
fun GameOptimizerApp(viewModel: GameOptimizerViewModel) {
    val fpsData by viewModel.fpsData.collectAsState()
    val currentQuality by viewModel.currentGraphicsQuality.collectAsState()
    val optimizationResult by viewModel.optimizationResult.collectAsState()
    val isMonitoring by viewModel.isMonitoring.collectAsState()
    val deviceInfo by viewModel.deviceInfo.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1a1a2e)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎮 Game Optimizer",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00d4ff)
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configurações",
                        tint = Color(0xFF00d4ff)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            FpsMonitorCard(fpsData, isMonitoring, viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            GraphicsQualityCard(currentQuality, viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.optimizeMemoryNow() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00b377)
                )
            ) {
                Text("🧹 Otimizar Memória", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (optimizationResult.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF16213e)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = optimizationResult,
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF00d4ff),
                        fontSize = 12.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (deviceInfo.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF16213e)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = deviceInfo,
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF00d4ff),
                        fontSize = 12.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FpsMonitorCard(
    fpsData: com.gameoptimizer.app.domain.usecase.FpsData?,
    isMonitoring: Boolean,
    viewModel: GameOptimizerViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF16213e)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "📊 Monitor de FPS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00d4ff)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (fpsData != null) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("FPS Atual", color = Color.White, fontSize = 12.sp)
                        Text(
                            "\${fpsData.currentFps}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (fpsData.isLagging) Color(0xFFff6b6b) else Color(0xFF00d4ff)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Memória", color = Color.White, fontSize = 12.sp)
                        Text(
                            "\${fpsData.memoryUsageMB.toInt()} MB",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00d4ff)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("CPU", color = Color.White, fontSize = 12.sp)
                        Text(
                            "\${fpsData.cpuUsagePercent.toInt()}%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00d4ff)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Temp: \${fpsData.temperatureC.toInt()}°C | Bateria: \${fpsData.batteryPercent}%",
                    color = Color(0xFFaaaaaa),
                    fontSize = 11.sp
                )
            } else {
                Text(
                    "Inicie o monitoramento para ver dados",
                    color = Color(0xFFaaaaaa),
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (isMonitoring) {
                        viewModel.stopFpsMonitoring()
                    } else {
                        viewModel.startFpsMonitoring()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isMonitoring) Color(0xFFff6b6b) else Color(0xFF00d4ff)
                )
            ) {
                Text(if (isMonitoring) "⏸ Parar Monitoramento" else "▶ Iniciar Monitoramento")
            }
        }
    }
}

@Composable
fun GraphicsQualityCard(
    currentQuality: GraphicsQuality,
    viewModel: GameOptimizerViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF16213e)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "🎨 Qualidade Gráfica",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00d4ff)
            )

            Spacer(modifier = Modifier.height(12.dp))

            GraphicsQuality.entries.forEach { quality ->
                Button(
                    onClick = { viewModel.reduceGraphicsQuality(quality) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentQuality == quality) Color(0xFF00d4ff) else Color(0xFF0f3460)
                    )
                ) {
                    Text(
                        quality.description,
                        color = if (currentQuality == quality) Color.Black else Color.White,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = { viewModel.getRecommendedQuality() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7700ff)
                )
            ) {
                Text("🤖 Qualidade Recomendada", fontSize = 12.sp)
            }
        }
    }
}