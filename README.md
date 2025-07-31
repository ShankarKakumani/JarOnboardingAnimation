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

This approach provides flexibility for different use cases - Ktor for lightweight requests, Retrofit for complex API interactions, and Room for local caching. The repository also handles **animation configuration** from the API, enabling server-controlled timing adjustments.

## 🎬 Animation Flow

The animation system orchestrates three cards in sequence with **API-driven** timing configurations and **dynamic rotation durations**. Each card follows a specific sequence with hybrid timing - some values from API, others optimized for user experience.

```
Card 1: ┌─────────────────────────────────────────────────────────────────────────────┐
        │ 1. Slide Up │ 2. Wait/Expand │ 3. Collapse  │ 4. Tilt (-6°) │ 5. Straighten │
        │  (500ms*)   │    (2000ms)    │  (instant)   │  (instant)    │  (2000ms)     │
        └─────────────────────────────────────────────────────────────────────────────┘

Card 2:                               ┌─────────────────────────────────────────────┐
                                      │ 1. Slide Up │ 2. Wait/Pos  │ 3. Tilt (+6°)  │
                                      │   (100ms)   │   (2000ms)   │   (2000ms)     │
                                      └─────────────────────────────────────────────┘

Card 3:                                              ┌─────────────────────────────┐
                                                     │ 1. Slide Up │ 2. Final Wait │
                                                     │   (100ms)   │   (2500ms)    │
                                                     └─────────────────────────────┘

API Config: ┌────────────────────────────────────────────────────────────────────┐
            │ • collapseExpandIntroInterval: 500ms* (initial slide timing)       │
            │ • collapseCardTiltInterval: 2000ms* (rotation duration)            │
            │ • Fixed delays: 2000ms/2500ms (optimized for UX flow)              │
            └────────────────────────────────────────────────────────────────────┘
```

**Key Features:**
- **Dynamic rotation durations** sourced from `collapseCardTiltInterval` API value
- **Initial slide timing** uses `collapseExpandIntroInterval` from API  
- **Fixed wait times** optimized for smooth user experience flow
- **Custom transform origins** for realistic rotation effects
- **Background gradients** synchronized with card transitions

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

## 🔮 Future Enhancements

Due to time constraints, the following features were identified but not implemented:

### **1. Enhanced Card Transitions**
- **Smooth expand-to-collapse animation**: Currently cards instantly switch states
- **Proposed solution**: Use `AnimatedContent` with custom `ContentTransform` for size transitions
- **Technical approach**: Implement height animation with `animateContentSize()` modifier

### **2. Interactive Card Expansion**
- **Click-to-expand functionality**: Allow users to re-expand collapsed cards  
- **Proposed solution**: Add click handlers to collapsed cards with state management
- **Technical approach**: 
  ```kotlin
  // Add to AnimatedCardState
  val isExpandedByUser: Boolean = false
  val allowInteraction: Boolean = true
  
  // Click handler in CollapsedCardContent  
  .clickable { onCardClick(cardState.card.id) }
  ```

### **3. Additional Improvements**
- **Staggered entry animations** for multiple cards
- **Haptic feedback** on card interactions
- **Accessibility improvements** with semantic descriptions
- **A/B testing framework** for different animation timings

These enhancements would improve user engagement and interaction patterns while maintaining the current animation architecture.

## 🎨 Implementation Details

- **Figma-accurate animations** with pixel-perfect positioning
- **Responsive layouts** that adapt to screen dimensions
- **Dynamic color theming** based on API data

---

*Built with modern Android development best practices for production-ready applications.*