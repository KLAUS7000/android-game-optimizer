# 🎮 Game Optimizer - Setup Instructions

## 📦 Conteúdo do Repositório

```
android-game-optimizer/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/gameoptimizer/app/
│   │   │   │   ├── data/model/
│   │   │   │   ├── domain/usecase/
│   │   │   │   └── presentation/
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── mipmap/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew (Linux/Mac)
├── gradlew.bat (Windows)
├── build.sh (Script Linux/Mac)
├── build.bat (Script Windows)
├── proguard-rules.pro
├── .gitignore
├── README.md
├── COMPILE.md
└── QUICK_START.md (este arquivo)
```

---

## ⚡ Quick Start (5 Minutos)

### **1️⃣ Windows**

#### Opção A: Script Automático
```cmd
# Duplo clique em:
build.bat

# Escolha a opção no menu (1-4)
```

#### Opção B: Comando Direto
```cmd
# Abra PowerShell/CMD na pasta do projeto e execute:
gradlew.bat assembleDebug

# ou para Release:
gradlew.bat assembleRelease
```

---

### **2️⃣ Linux/Mac**

#### Opção A: Script Automático
```bash
# Abra terminal na pasta do projeto:
chmod +x build.sh
./build.sh

# Escolha a opção no menu (1-4)
```

#### Opção B: Comando Direto
```bash
# Compile Debug APK:
./gradlew assembleDebug

# ou Release:
./gradlew assembleRelease
```

---

## 📊 O que esperar

### **Build Debug** (Mais rápido ⚡)
- ⏱️ Tempo: 2-5 minutos (primeira vez), 1-2 minutos (próximas)
- 📦 Tamanho: 45-55 MB
- 🎯 Ideal para: Testes e desenvolvimento
- 📁 Localização: `app/build/outputs/apk/debug/app-debug.apk`

### **Build Release** (Otimizado 🚀)
- ⏱️ Tempo: 3-8 minutos (primeira vez), 2-3 minutos (próximas)
- 📦 Tamanho: 15-20 MB (comprimido)
- 🎯 Ideal para: Distribuição na Play Store
- 📁 Localização: `app/build/outputs/apk/release/app-release.apk`

---

## 📱 Instalar no Dispositivo

### **Método 1: Via Script (Automático)**
O script oferecerá a opção automaticamente após compilar.

### **Método 2: ADB (Manual)**
```bash
# Certificar que o dispositivo está conectado:
adb devices

# Instalar Debug APK:
adb install app/build/outputs/apk/debug/app-debug.apk

# ou Release:
adb install app/build/outputs/apk/release/app-release.apk
```

### **Método 3: Android Studio**
1. Abra o projeto em Android Studio
2. Clique em **Run > Run 'app'**
3. Selecione seu dispositivo

---

## 🎯 Estrutura Pronta

Tudo já está configurado:
- ✅ Gradle sincronizado
- ✅ Dependências corretas
- ✅ BuildTypes configurados
- ✅ ProGuard ativo (Release)
- ✅ Kotlin 1.9.10
- ✅ Jetpack Compose 1.5.4

---

## 🔧 Requisitos do Sistema

| Item | Mínimo | Recomendado |
|------|--------|------------|
| **Java** | 11 | 17+ |
| **RAM** | 4 GB | 8 GB+ |
| **Disk** | 2 GB | 10 GB+ |
| **Android SDK** | 24 | 34 |
| **Gradle** | 8.0 | 8.1+ |

---

## ⚠️ Troubleshooting

### ❌ "Gradle sync failed"
```bash
./gradlew --refresh-dependencies
```

### ❌ "SDK version too low"
1. Abra Android Studio
2. Tools > SDK Manager
3. Instale Android SDK 34

### ❌ "Java not found"
```bash
java -version  # Deve retornar Java 11+
```

Se não tiver Java 11+, instale de: https://www.oracle.com/java/technologies/downloads/

### ❌ "Permission denied" (Linux/Mac)
```bash
chmod +x gradlew
chmod +x build.sh
```

---

## 📊 Monitorar Build

Durante a compilação, você verá:

```
> Task :app:mergeDebugResources
> Task :app:compileDebugKotlin
> Task :app:mergeDebugAssets
> Task :app:processDebugManifest
> Task :app:generateDebugBuildConfig
> Task :app:packageDebug
> Task :app:assembleDebug

BUILD SUCCESSFUL in 2m 45s
```

---

## 🎮 Funcionalidades do APK

✨ **Monitor de FPS** - Em tempo real com dados detalhados
✨ **Redução de Gráficos** - 5 níveis (+80 FPS em Ultra Low)
✨ **Otimização de Memória** - Limpeza automática
✨ **IA Recomendadora** - Detecta seu dispositivo
✨ **Dark Mode** - Interface moderna e responsiva

---

## 📥 Próximos Passos

1. ✅ Clone o repositório
2. ✅ Abra em Android Studio ou terminal
3. ✅ Execute `build.sh` (Mac/Linux) ou `build.bat` (Windows)
4. ✅ Escolha a opção (1-4)
5. ✅ Aguarde compilar
6. ✅ Instale no seu dispositivo

---

## 🚀 Você está pronto!

Se tiver dúvidas, consulte:
- 📖 **README.md** - Documentação completa
- 📋 **COMPILE.md** - Guia de compilação detalhado
- 💬 **GitHub Issues** - Reporte problemas

---

**Made with ❤️ for mobile gamers**

Última atualização: 2026-07-05