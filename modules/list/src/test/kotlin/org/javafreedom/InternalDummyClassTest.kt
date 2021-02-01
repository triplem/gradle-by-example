package org.javafreedom

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class InternalDummyClassTest {

    @Test
    fun testInternalDummyClass() {
        val dummy = InternalDummyClass("name")

        assertThat(dummy.name).isEqualTo("name")
    }

}