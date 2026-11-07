package com.example.samsungtvremote

import com.example.samsungtvremote.data.RemoteKeys
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class RemoteKeysTest {
    @Test
    fun digitBuildsKeyCode() {
        assertEquals("KEY_0", RemoteKeys.digit(0))
        assertEquals("KEY_9", RemoteKeys.digit(9))
    }

    @Test
    fun digitRejectsOutOfRange() {
        assertThrows(IllegalArgumentException::class.java) { RemoteKeys.digit(10) }
    }
}
