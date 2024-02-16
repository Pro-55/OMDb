package com.example.omdb.framework.navigation

sealed class Route(val name: String) {
    data object Root : Route(name = "route_root")
    data object Router : Route(name = "route_router")
    data object Auth : Route(name = "route_auth")
    data object App : Route(name = "route_app")
}