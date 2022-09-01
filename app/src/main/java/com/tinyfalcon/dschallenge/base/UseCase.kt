package com.tinyfalcon.dschallenge.base

abstract class UseCase<R> {
    abstract suspend fun execute(): R
}