/*
 * The MIT License
 *
 * Copyright (c) 2018 Niek Haarman
 * Copyright (c) 2007 Mockito contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.nhaarman.mockitokotlin2

import com.nhaarman.mockitokotlin2.internal.createInstance
import org.mockito.ArgumentCaptor
import kotlin.reflect.KClass

/**
 * Creates a [KArgumentCaptor] for given type.
 */
inline fun <reified T : Any> argumentCaptor(): KArgumentCaptor<T> {
    return KArgumentCaptor(ArgumentCaptor.forClass(T::class.java), T::class)
}

/**
 * Creates a [KArgumentCaptor] for given type, taking in a lambda to allow fast verification.
 */
inline fun <reified T : Any> argumentCaptor(f: KArgumentCaptor<T>.() -> Unit): KArgumentCaptor<T> {
    return argumentCaptor<T>().apply(f)
}

/**
 * Creates a [KArgumentCaptor] for given nullable type.
 */
inline fun <reified T : Any> nullableArgumentCaptor(): KArgumentCaptor<T?> {
    return KArgumentCaptor(ArgumentCaptor.forClass(T::class.java), T::class)
}

/**
 * Creates a [KArgumentCaptor] for given nullable type, taking in a lambda to allow fast verification.
 */
inline fun <reified T : Any> nullableArgumentCaptor(f: KArgumentCaptor<T?>.() -> Unit): KArgumentCaptor<T?> {
    return nullableArgumentCaptor<T>().apply(f)
}

/**
 * Alias for [ArgumentCaptor.capture].
 */
inline fun <reified T : Any> capture(captor: ArgumentCaptor<T>): T {
    return captor.capture() ?: createInstance()
}

class KArgumentCaptor<out T : Any?>(
        private val captor: ArgumentCaptor<T>,
        private val tClass: KClass<*>
) {

    /**
     * The first captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val firstValue: T
        get() = captor.firstValue

    /**
     * The second captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val secondValue: T
        get() = captor.secondValue

    /**
     * The third captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val thirdValue: T
        get() = captor.thirdValue

    /**
     * The last captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val lastValue: T
        get() = captor.lastValue

    val allValues: List<T>
        get() = captor.allValues

    @Suppress("UNCHECKED_CAST")
    fun capture(): T {
        return captor.capture() ?: createInstance(tClass) as T
    }
}

val <T> ArgumentCaptor<T>.firstValue: T
    get() = allValues[0]

val <T> ArgumentCaptor<T>.secondValue: T
    get() = allValues[1]

val <T> ArgumentCaptor<T>.thirdValue: T
    get() = allValues[2]

val <T> ArgumentCaptor<T>.lastValue: T
    get() = allValues.last()