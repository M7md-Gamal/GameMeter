# GameMeter

**GameMeter** is a modern **Kotlin Multiplatform** application (Android + iOS) for browsing video games using the RAWG API. The UI is built with **Compose Multiplatform** and shared across platforms; it demonstrates Clean Architecture, the MVI (Model-View-Intent) pattern, and modern multiplatform development practices.

## 📱 Features

*   **Cross-Platform:** One shared Compose Multiplatform UI running on Android and iOS.
*   **Browse Games:** View a paginated list of video games fetched from the RAWG API.
*   **Genre Filtering:** Filter games by category using an interactive chip selector.
*   **Game Details:** Access detailed information about specific games, including release dates, ratings, and descriptions.
*   **Local Search:** Filter currently loaded games instantly using a local search mechanism.
*   **Offline Support:** Robust offline capabilities using Room database as the single source of truth.
*   **State Handling:** distinct UI states for Loading, Error (with retry), and Empty results.
*   **Dark/Light Mode:** Fully supports system theme settings.

## 🛠 Tech Stack

The application is built using the following modern multiplatform technologies:

*   **Language:** [Kotlin](https://kotlinlang.org/) 2.4 (Kotlin Multiplatform)
*   **UI:** [Compose Multiplatform](https://www.jetbrains.com/lifecycle/compose-multiplatform/) (Material 3) — shared across Android & iOS
*   **Architecture:** Clean Architecture + MVI (Model-View-Intent)
*   **Asynchrony:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
*   **Networking:** [Ktor Client](https://ktor.io/) (OkHttp on Android, Darwin on iOS)
    *   Content Negotiation
    *   Logging
    *   Serialization (Kotlinx Serialization)
*   **Dependency Injection:** [Koin](https://insert-koin.io/) (KMP, with Compose Multiplatform ViewModel integration)
*   **Image Loading:** [Coil 3](https://coil-kt.github.io/coil/) (multiplatform)
*   **Local Storage:** [Room](https://developer.android.com/training/data-storage/room) (KMP, bundled SQLite driver on all platforms)
*   **Pagination:** [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3) (multiplatform, with RemoteMediator)
*   **Navigation:** Jetpack Navigation 2 (JetBrains multiplatform artifact, type-safe routes)

## 🏗 Project Structure

```
core/
├── domain/            # Result, error types — pure Kotlin
├── data/              # Ktor client factory, platform engines (expect/actual)
├── presentation/      # UiText + shared string resources (Compose Resources)
└── ui/                # Material 3 theme
feature/games/
├── domain/            # Models, GamesRepo interface — pure Kotlin
├── api/               # Public contract: type-safe navigation routes
├── data/              # Room (KMP), DTOs, mappers, Ktor data source,
│                      #   RemoteMediator, repository impl, DI
└── presentation/      # MVI ViewModels, Compose screens, DI
composeApp/            # Shared app shell: AppRoot, navigation graph, initKoin,
                       #   iOS MainViewController + exported GameMeter framework
androidApp/            # Android entry point (MainActivity, Application, manifest)
iosApp/                # iOS Xcode project (SwiftUI host)
```

## 🏗 Architecture & Rationale

This project adheres to **Clean Architecture** principles to ensure separation of concerns, testability, and maintainability.

### Layers
1.  **Domain Layer:** Contains business logic and UseCases. It is purely Kotlin and independent of any framework.
2.  **Data Layer:** Handles data retrieval from Remote (API) and Local (Room) sources. It implements the repositories defined in the Domain layer.
3.  **Presentation Layer:** Contains UI components (Composables) and ViewModels. It observes data from the Domain layer and maps it to UI states.

### Key Decisions
*   **MVI & UDF:** This project uses the **Model-View-Intent (MVI)** pattern along with **Unidirectional Data Flow (UDF)**.
    *   **Model (State)**: A single, immutable source of truth for the UI state.
    *   **View**: Compose functions that observe the state and render the UI.
    *   **Intent (Action)**: User actions (e.g., search query updates, category selection) are sent as intents to the ViewModel, ensuring a predictable and traceable state management flow.
*   **Ktor vs Retrofit:** Ktor was chosen for its lightweight nature, native Kotlin support, and first-class Kotlin Multiplatform support (OkHttp engine on Android, Darwin engine on iOS).
*   **Koin vs Hilt:** Koin is used for Dependency Injection due to its simplicity, lack of code generation (annotation processing), and pure-Kotlin/KMP support.
*   **Single Source of Truth (SSOT):** The repository coordinates data. When data is fetched from the network, it is persisted in the Room database. The UI always observes the database, ensuring that the user sees consistent data even when offline. `RemoteMediator` handles this synchronization automatically with Paging 3.
*   **Platform specifics via expect/actual:** HTTP engines, database builders, logging, and network-error classification are isolated behind expect/actual declarations; all feature code is 100% shared.

## 📝 Assumptions & Shortcuts

*   **Genre Selection:** For the scope of this assignment, the game list fetches a default set of popular games or a specific genre if hardcoded, rather than a full dynamic genre selector UI, focusing on the core list browsing experience.

*   **API Key:** The project assumes a valid RAWG API key is configured.

## 🚀 Setup & Build Instructions

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/M7md-Gamal/GameMeter
    ```
2.  **Open in Android Studio (or Fleet / Xcode for iOS):**
    Open the project folder in the latest version of Android Studio.
3.  **API Key Configuration:**
    *   Obtain an API key from [RAWG.io](https://rawg.io/apidocs).
    *   Add your API key to `local.properties` (quotes are stripped automatically):
        ```properties
        API_KEY=your_api_key_here
        ```
    *   The key is injected into shared code via `initKoin(apiKey)` — Android reads it from `BuildConfig`, iOS reads it from `Info.plist` (`API_KEY` ← `RAWG_API_KEY` in `iosApp/Config.xcconfig`).
4.  **Build and Run (Android):**
    *   Sync Gradle files, then run the `androidApp` configuration on an Emulator or Physical device, or:
    ```bash
    ./gradlew :androidApp:installDebug
    ```
5.  **Build and Run (iOS):**
    *   Set your `TEAM_ID` and `RAWG_API_KEY` in `iosApp/Config.xcconfig`.
    *   Open `iosApp/iosApp.xcodeproj` in Xcode and press Cmd+R (requires macOS; the Gradle build phase links the `GameMeter` framework automatically).

## 📦 Deliverables Checklist

*   [x] **Tech Stack:** Kotlin, Compose Multiplatform, Ktor, Koin, Room (all KMP-ready).
*   [x] **Architecture:** MVI with Clean Architecture in a multi-module KMP project.
*   [x] **Pagination:** Paging 3 with RemoteMediator, shared on Android & iOS.
*   [x] **Offline Support:** Room caching with bundled SQLite driver on all platforms.
*   [x] **UI:** Material 3 design with handling for various states.
*   [x] **iOS target:** Compose Multiplatform UI + Xcode project wired to the shared framework.

