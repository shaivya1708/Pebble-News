# PebbleNews 

> A personalized news reader app built from scratch as a learning project to explore modern Android development practices.

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-MIT-orange.svg)](LICENSE)

---


##  Features

### Core Features
-  **User Authentication** - Local registration and login system with per-user data isolation
-  **Personalized News Feed** - "For You" tab curated based on your selected preferences
-  **Top Headlines** - Latest breaking news from trusted sources worldwide
-  **Search** - Find news articles by keyword with real-time results

### Save & Organize
-  **Favorites** - Heart articles you love for quick access
-  **Read Later** - Bookmark articles to read when you have time
- **Categories** - Choose from Technology, Business, World, Economy

### User Experience
-  **Dark Mode** - Seamless light/dark theme that follows system settings
-  **Open in Browser** - Read full articles on the original source
-  **Material Design 3** - Modern, clean UI following Google's design guidelines

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| **Kotlin** | Primary programming language |
| **MVVM Architecture** | Clean separation of concerns |
| **Navigation Component** | Fragment navigation with SafeArgs |
| **ViewBinding** | Type-safe view access |
| **Retrofit 2.9.0** | REST API communication |
| **Glide** | Image loading and caching |
| **Material Design 3** | Modern UI components |
| **SharedPreferences** | Local data persistence |
| **Coroutines** | Asynchronous programming |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Fragments  │  │  Adapters   │  │  ViewModels         │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                              │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │  Repository     │  │  LocalStorageManager            │   │
│  │  (API calls)    │  │  (SharedPreferences)            │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    Network Layer                             │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │  Retrofit       │  │  NewsAPI.org                    │   │
│  │  ApiService     │  │  REST Endpoints                 │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## Project Structure

```
app/src/main/java/com/example/sampleapp/
├── config/
│   └── Config.kt                 # API configuration
├── data/
│   ├── ApiService.kt             # Retrofit API interface
│   ├── LocalStorageManager.kt    # SharedPreferences manager
│   ├── NewsCategory.kt           # News category enum
│   ├── NewsRepository.kt         # Data repository
│   └── RetrofitClient.kt         # Retrofit instance
├── model/
│   ├── Article.kt                # Article data model
│   └── NewsResponse.kt           # API response model
├── ui/
│   ├── auth/
│   │   ├── LoginFragment.kt
│   │   └── RegisterFragment.kt
│   ├── details/
│   │   ├── DetailsFragment.kt
│   │   └── DetailsViewModel.kt
│   ├── favorites/
│   │   ├── FavoritesFragment.kt
│   │   └── FavoritesViewModel.kt
│   ├── home/
│   │   ├── HomeFragment.kt
│   │   └── HomeViewModel.kt
│   ├── readlater/
│   │   ├── ReadLaterFragment.kt
│   │   └── ReadLaterViewModel.kt
│   ├── search/
│   │   ├── SearchFragment.kt
│   │   └── SearchViewModel.kt
│   ├── settings/
│   │   └── UserSettingsDialogFragment.kt
│   ├── ArticleAdapter.kt
│   └── ViewModelFactory.kt
└── MainActivity.kt
```

---

## Setup Instructions

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 17 or higher
- Android SDK (API 24+)
- NewsAPI.org API Key

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd SampleApp
```

### Step 2: Get NewsAPI Key

1. Go to [https://newsapi.org/](https://newsapi.org/)
2. Sign up for a free account
3. Copy your API key from the dashboard

### Step 3: Configure API Key

Open `app/src/main/java/com/example/sampleapp/config/Config.kt` and replace the API key:

```kotlin
object Config {
    const val API_KEY = "your_api_key_here"
    const val BASE_URL = "https://newsapi.org/v2/"
}
```

### Step 4: Build and Run

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Connect an Android device or start an emulator (API 24+)
4. Click **Run**  or press `Shift + F10`

---

## App Guide

### 1. Registration & Login

- Launch the app
- Tap **"Don't have an account? Register"**
- Enter email and password (min 6 characters)
- After registration, login with your credentials

### 2. Home Screen

The home screen has two tabs:
- **Top Headlines** - Breaking news from major sources
- **For You** - Personalized news based on your preferences

### 3. Set Your Preferences

1. Tap the **Settings** icon in the toolbar
2. Select your preferred news categories:
   - Technology
   - Business
   - World
   - Economy
3. Tap **Save**
4. Switch to the "For You" tab to see personalized news

### 4. Search News

1. Tap the **Search** icon in the toolbar
2. Enter keywords and press search
3. Browse results and tap any article for details

### 5. Save Articles

- **Favorites**: Tap the heart icon on any article
- **Read Later**: Tap the bookmark icon on any article

### 6. View Saved Articles

- **Favorites**: Tap the heart icon in the toolbar
- **Read Later**: Go to Settings → Read Later

### 7. Read Full Article

1. Tap any article to view details
2. Scroll down and tap **"Read Full Article"**
3. The article opens in your browser

### 8. Dark Mode

The app automatically follows your system theme. Toggle dark mode in your phone's display settings.

### 9. Logout

1. Tap the **Settings** icon
2. Scroll down and tap **Logout**

---

## Configuration

### API Settings

| Setting | Value |
|---------|-------|
| Base URL | `https://newsapi.org/v2/` |
| Page Size | 50 articles |
| Date Range | Last 7 days |

### Supported Endpoints

- `/top-headlines` - Breaking news
- `/everything` - Search and preference-based news

---

## Minimum Requirements

| Requirement | Value |
|-------------|-------|
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 (Android 14) |
| Compile SDK | API 34 |

---

## Theme Colors

### Light Mode
- Primary: `#6200EE`
- Background: `#FFFFFF`
- Text: `#212121`

### Dark Mode
- Primary: `#BB86FC`
- Background: `#121212`
- Text: `#FFFFFF`

---

## Learnings

Learning outcomes for me were: 
1. **MVVM Pattern** - How ViewModels survive configuration changes and separate UI from business logic
2. **Navigation Component** - Managing fragment transactions and passing data with SafeArgs
3. **Repository Pattern** - Single source of truth for data management
4. **Dark Mode Implementation** - Using theme attributes instead of hardcoded colors
5. **SharedPreferences** - Storing user data locally with key isolation per user
6. **Retrofit + Coroutines** - Making async API calls cleanly
7. **Material Design** - Building consistent, modern UIs

---

---

## Future Improvements

- [ ] Add offline caching with Room database
- [ ] Implement push notifications for breaking news
- [ ] Add article sharing functionality
- [ ] Implement biometric authentication
- [ ] Add news source filtering
- [ ] Create home screen widget
- [ ] Add text-to-speech for articles

---

##  License

This project is for educational purposes.

---

##Acknowledgments

- [NewsAPI.org](https://newsapi.org/) - For providing the news data API
- [Material Design](https://material.io/) - For design guidelines
- [Android Developers](https://developer.android.com/) - For documentation and codelabs

---

##Contact

**Shaivya Yadav**

Feel free to reach out if you have questions about this project!

---

## 📜 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | Jan 2026 | Initial release with core features |

---


