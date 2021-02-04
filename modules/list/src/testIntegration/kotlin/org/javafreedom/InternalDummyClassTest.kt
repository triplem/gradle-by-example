package org.javafreedom

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class InternalDummyClassTest {

    @Test
    fun testInternalDummyClass_AllProperties() {
        val dummy = InternalDummyClass(name = "name", nick = "nick")

        assertThat(dummy.name).isEqualTo("name")
        assertThat(dummy.nick).isEqualTo("nick")
    }

    @Test
    fun testInternalDummyClass_DefaultProperties() {
        val dummy = InternalDummyClass(name = "name")

        assertThat(dummy.name).isEqualTo("name")
        assertThat(dummy.nick).isEqualTo("")
    }

}