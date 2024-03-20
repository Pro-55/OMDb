<h1 align="center">OMDb</h1>

<p align="center">
  <a href="https://developer.android.com/studio/releases/platforms#5.0"><img alt="API 21" src="https://img.shields.io/badge/API-21%2B-brightgreen"/></a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0"><img alt="Apache License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

<br>

<p align="center">
OMDb is a demo app which allows user to search any movie or show. It uses OMDb APIs which is a free and open movie database.
</p>

<br>

| Dark                                       | Light                                        |
| ------------------------------------------ | -------------------------------------------- |
| ![App Demo Dark](assets/app_demo_dark.gif) | ![App Demo Light](assets/app_demo_light.gif) |

<br>

## Built with

- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) +
  [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous work
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- [Android Jetpack](https://developer.android.com/jetpack)
  - [Compose](https://developer.android.com/jetpack/androidx/releases/compose) - Android’s
    recommended modern toolkit for building native UI
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Dispose of
    observing data when lifecycle state changes.
  - LiveData - Notify domain layer data to views.
  - ViewModel - UI related data holder, lifecycle aware.
  - [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) - Provides
    Navigation components
  - [Room](https://developer.android.com/jetpack/androidx/releases/room) - Abstraction layer over
    SQLite
- [Material Design 3](https://m3.material.io) -
  Material design components like ripple animation, cardView
- [Ktor](https://github.com/ktorio/ktor) - Kotlin first networking library
- [GSON](https://github.com/google/gson) - Java serialization/deserialization library to convert
  Java Objects into JSON and back
- [Coil](https://coil-kt.github.io/coil/compose) - Image downloading and caching library
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [OMDb](http://www.omdbapi.com/) - An open and free movie database

<br>

# License

```xml
Designed and developed by 2024 Pro-55 (Pranit B. Rane)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, softwaredistributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.See the License for the specific language governing permissions andlimitations under the License.
```
