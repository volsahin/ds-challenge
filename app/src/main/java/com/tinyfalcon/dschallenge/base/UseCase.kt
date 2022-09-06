package com.tinyfalcon.dschallenge.base

abstract class UseCase<R, P> {
    abstract suspend fun execute(params: P): R
}
