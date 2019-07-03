package com.example.omdb.network.ApiFunctions

import rx.functions.Action1

abstract class ApiSuccess<T> : Action1<T>
