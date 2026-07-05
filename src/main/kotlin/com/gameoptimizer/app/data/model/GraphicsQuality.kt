package com.gameoptimizer.app.data.model

enum class GraphicsQuality(val level: Int, val description: String) {
    ULTRA(4, "Ultra - Máxima qualidade"),
    HIGH(3, "High - Qualidade alta"),
    MEDIUM(2, "Medium - Qualidade média"),
    LOW(1, "Low - Qualidade baixa"),
    ULTRA_LOW(0, "Ultra Low - Mínima qualidade");

    fun getTextureResolution(): String = when (this) {
        ULTRA -> "2048x2048"
        HIGH -> "1024x1024"
        MEDIUM -> "512x512"
        LOW -> "256x256"
        ULTRA_LOW -> "128x128"
    }

    fun getAntiAliasingLevel(): Int = when (this) {
        ULTRA -> 4
        HIGH -> 2
        MEDIUM -> 1
        LOW -> 0
        ULTRA_LOW -> 0
    }

    fun getShadowQuality(): String = when (this) {
        ULTRA -> "Ultra"
        HIGH -> "High"
        MEDIUM -> "Medium"
        LOW -> "Low"
        ULTRA_LOW -> "Disabled"
    }

    fun getParticleCount(): Float = when (this) {
        ULTRA -> 1.0f
        HIGH -> 0.8f
        MEDIUM -> 0.5f
        LOW -> 0.3f
        ULTRA_LOW -> 0.1f
    }

    fun getDrawCallReduction(): Float = when (this) {
        ULTRA -> 0f
        HIGH -> 0.2f
        MEDIUM -> 0.4f
        LOW -> 0.6f
        ULTRA_LOW -> 0.8f
    }

    fun getEstimatedFpsIncrease(): Int = when (this) {
        ULTRA -> 0
        HIGH -> 15
        MEDIUM -> 30
        LOW -> 50
        ULTRA_LOW -> 80
    }
}