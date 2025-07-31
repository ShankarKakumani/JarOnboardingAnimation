# JAR Onboarding Animation

Android onboarding implementation with card animations, dynamic backgrounds, and clean architecture.

## 🚀 Features

- **Multi-stage Animations**: Card animations with rotation, translation, and scaling
- **Dynamic Gradient Backgrounds**: Real-time color transitions synchronized with animations
- **Clean Architecture**: Domain-driven design with clear dependency boundaries
- **Dual Network Clients**: Both Ktor and Retrofit implementations with seamless switching

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation between presentation, business logic, and data layers. Each layer has distinct responsibilities and depends only on inner layers, ensuring maintainability and testability.

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │     Domain      │    │      Data       │
│                 │    │                 │    │                 │
│  ┌───────────┐  │    │  ┌───────────┐  │    │  ┌───────────┐  │
│  │ViewModel  │  │───▶│  │ Use Cases │  │───▶│  │Repository │  │
│  └───────────┘  │    │  └───────────┘  │    │  └───────────┘  │
│  ┌───────────┐  │    │  ┌───────────┐  │    │  ┌───────────┐  │
│  │ Compose   │  │    │  │  Models   │  │    │  │DataSource │  │
│  │    UI     │  │    │  │ Business  │  │    │  │   API/DB  │  │
│  └───────────┘  │    │  │   Logic   │  │    │  └───────────┘  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

The **Domain layer** contains pure business logic with use cases that orchestrate data flow. The **Data layer** implements the repository pattern with multiple data sources, while the **Presentation layer** handles UI state with MVVM/MVI pattern.

## 🔄 Data Strategy

The data layer supports multiple network clients and caching strategies. The repository abstracts data sources, allowing seamless switching between Ktor and Retrofit clients based on configuration.

```
                           OnboardingRepository
                                    │
                     ┌──────────────┼──────────────┐
                     ▼              ▼              ▼
              ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
              │    Ktor     │ │  Retrofit   │ │    Room     │
              │ DataSource  │ │ DataSource  │ │ DataSource  │
              └─────────────┘ └─────────────┘ └─────────────┘
```

This approach provides flexibility for different use cases - Ktor for lightweight requests, Retrofit for complex API interactions, and Room for local caching.

## 🎬 Animation Flow

The animation system orchestrates three cards in sequence, with each card having distinct timing and transform behaviors. Background gradients transition smoothly based on API-provided colors.

```
Card 1: ┌───────────────────────────────────────────────────────────────────────────┐
        │ 1. Slide Up │ 2. Expand    │ 3. Collapse  │ 4. Tilt (-6°) │ 5. Straighten │
        │   (500ms)   │   (3000ms)   │   (1000ms)   │   (instant)   │   (1000ms)    │
        └───────────────────────────────────────────────────────────────────────────┘

Card 2:                              ┌─────────────────────────────────────────────┐
                                     │ 1. Slide Up │ 2. Expand    │ 3. Tilt (+6°) │
                                     │   (100ms)   │   (3000ms)   │   (1000ms)    │
                                     └─────────────────────────────────────────────┘

Card 3:                                             ┌─────────────────────────────┐
                                                    │ 1. Slide Up │ 2. Final Pos  │
                                                    │   (100ms)   │   (3000ms)    │
                                                    └─────────────────────────────┘
```

Each animation uses custom transform origins for realistic rotation effects, with background gradients synchronized to card transitions.

## 🛠️ Tech Stack

- **UI**: Jetpack Compose, Material 3
- **Architecture**: Clean Architecture, MVVM/MVI, Multi-module
- **Networking**: Ktor & Retrofit with Kotlinx Serialization
- **Database**: Room with entities and DAOs
- **DI**: Hilt
- **Animations**: Compose Animations with custom easing
- **Image Loading**: Coil
- **Lottie**: Airbnb Lottie Compose

## 🚦 Getting Started

### Prerequisites
- Android Studio Hedgehog+ 
- JDK 17+
- Minimum SDK 24

### Setup
```bash
git clone <repository-url>
cd JarOnboardingAnimation
./gradlew build
```

### Run
Open in Android Studio and run on device/emulator. The app will automatically fetch onboarding data from the API and start the animation sequence.

## 🎯 API Integration

Connects to: `https://api.npoint.io/796729cca6c55a7d089e`

**Features:**
- Automatic retry and error handling
- Configurable caching strategies
- Network client abstraction (easily switch between Ktor/Retrofit)

## 🎨 Implementation Details

- **Figma-accurate animations** with pixel-perfect positioning
- **Responsive layouts** that adapt to screen dimensions
- **Dynamic color theming** based on API data

---

*Built with modern Android development best practices for production-ready applications.*