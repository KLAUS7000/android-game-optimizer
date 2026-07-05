# 🎮 Android Game Optimizer

Um aplicativo Android poderoso projetado para otimizar jogos móveis, reduzindo lag, aumentando FPS e melhorando a performance geral do dispositivo.

## 🚀 Funcionalidades Principais

### 📊 Monitor de FPS em Tempo Real
- Acompanhamento contínuo de FPS
- Monitoramento de uso de memória
- Detecção de picos de lag
- Análise de temperatura do dispositivo
- Monitoramento de bateria

### 🎨 Redução de Qualidade Gráfica
O app oferece 5 níveis de qualidade gráfica, cada um com otimizações específicas:

#### **Ultra (Máxima qualidade)**
- Renderização completa em 4K
- Efeitos visuais avançados
- Ray tracing parcial habilitado
- **Aumento de FPS: 0**

#### **High (Qualidade Alta)**
- Renderização em Full HD
- Efeitos visuais moderados
- Bloom e reflexos habilitados
- **Aumento de FPS: +15**

#### **Medium (Qualidade Média)**
- Renderização reduzida a 1280x720
- Efeitos visuais limitados
- Bloom desabilitado
- **Aumento de FPS: +30**

#### **Low (Qualidade Baixa)**
- Renderização em 960x540
- Efeitos mínimos
- Sombras dinâmicas desabilitadas
- LOD agressivo ativado
- **Aumento de FPS: +50**

#### **Ultra Low (Mínima qualidade)**
- Renderização em 720x400
- Sem efeitos visuais
- Todas as sombras desabilitadas
- Geometria simplificada
- **Aumento de FPS: +80**

### 🧠 Otimizações de Memória
- Limpeza automática de cache
- Garbage collection otimizado
- Liberação agressiva de memória
- Monitoramento de heap
- Aviso de memória baixa

### ⚙️ Otimizações do Sistema
- **GPU**: Redução de draw calls, desabilitação de efeitos
- **CPU**: Limite de threads, redução de processamento de física
- **Memória**: Limite de cache, compressão de texturas

### 🤖 Recomendação Automática
O app analisa o dispositivo e recomenda a melhor qualidade gráfica baseado em:
- RAM disponível
- Número de núcleos CPU
- Performance histórica

## 📱 Requisitos

- **Android**: 7.0+ (API 24)
- **RAM**: 2GB mínimo recomendado
- **Armazenamento**: 50MB

## 🛠️ Tecnologias Utilizadas

- **Kotlin**: Linguagem principal
- **Jetpack Compose**: Interface moderna e responsiva
- **MVVM Architecture**: Padrão de arquitetura limpa
- **Coroutines**: Processamento assíncrono
- **Flow**: Reatividade e observação de dados

## 📦 Estrutura do Projeto

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/gameoptimizer/app/
│   │       ├── data/
│   │       │   └── model/
│   │       │       └── GraphicsQuality.kt
│   │       ├── domain/
│   │       │   └── usecase/
│   │       │       ├── GraphicsOptimizationUseCase.kt
│   │       │       ├── FpsMonitorUseCase.kt
│   │       │       └── MemoryOptimizationUseCase.kt
│   │       └── presentation/
│   │           ├── ui/
│   │           │   └── MainActivity.kt
│   │           └── viewmodel/
│   │               └── GameOptimizerViewModel.kt
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## 🎯 Como Compilar

### Windows
```cmd
build.bat
```

### Linux/Mac
```bash
chmod +x build.sh
./build.sh
```

### Ou via Gradle direto
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

## 🎮 Como Usar

1. **Instalar o app** no seu dispositivo Android
2. **Abrir o aplicativo**
3. **Escolher o nível de qualidade gráfica** desejado
4. **Iniciar o monitoramento de FPS** para acompanhar a performance
5. **Otimizar memória** quando necessário

## 📊 Dados Monitorados

- **FPS**: Frames por segundo em tempo real
- **Memória**: Uso total, nativa e Java heap
- **CPU**: Porcentagem de uso dos processadores
- **Temperatura**: Temperatura térmica do dispositivo
- **Bateria**: Nível percentual da bateria
- **Lag Spikes**: Detecção e severidade de quedas de FPS

## 🔧 Configuração de Desenvolvimento

```bash
# Clone o repositório
git clone https://github.com/KLAUS7000/android-game-optimizer.git

# Abra no Android Studio
# Sincronize as dependências
# Execute no dispositivo/emulador
```

## 📝 Licença

Este projeto está sob a licença MIT.

## 👨‍💻 Autor

**KLAUS7000**

## 🤝 Contribuições

Contribuições são bem-vindas! Por favor:
1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature')`
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📞 Suporte

Se encontrar problemas, por favor abra uma issue no GitHub.

---

**Made with ❤️ for mobile gamers**