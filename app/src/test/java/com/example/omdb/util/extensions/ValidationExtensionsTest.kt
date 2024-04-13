package com.example.omdb.util.extensions

import kotlinx.coroutines.test.runTest
import org.junit.Test

class ValidationExtensionsTest {

    @Test
    fun `test email validation for valid email`() = runTest {
        val email = "abc@xyz.com"
        val isValid = email.isValidEmail()
        assert(isValid)
    }

    @Test
    fun `test email validation for invalid email`() = runTest {
        val email = "abc!xyz.com"
        val isValid = email.isValidEmail()
        assert(!isValid)
    }
}