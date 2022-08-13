<h1 align="center">OMDb</h1>

<p align="center">
  <a href="https://developer.android.com/studio/releases/platforms#5.1"><img alt="API 21" src="https://img.shields.io/badge/API-21%2B-brightgreen"/></a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0"><img alt="Apache License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

<br>

<p align="center">
OMDb is a demo app which allows user to search any movie or show. It uses OMDb APIs which is a free and open movie database.
</p>

<br>

<img src="assets/app-demo.gif" align="right" width="40%"/>

## Built with
- Minimum SDK level 21
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) +
  [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous work
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- Android Jetpack
    - LiveData - notify domain layer data to views.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - [Navigation](https://developer.android.com/guide/navigation) - Provides Navigation components
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - abstraction layer
      over SQLite
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository pattern
- [Ktor](https://github.com/ktorio/ktor)
- [GSON](https://github.com/google/gson) - Java serialization/deserialization library to convert
  Java Objects into JSON and back
- [Glide](https://github.com/bumptech/glide) - Image downloading and caching library
- [Material-Components](https://github.com/material-components/material-components-android) -
  Material design components like ripple animation, cardView
- [OMDb](http://www.omdbapi.com/) - An open and free movie database

<br>

# License
```xml
Designed and developed by 2020 pro-55 (Pranit B. Rane)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.