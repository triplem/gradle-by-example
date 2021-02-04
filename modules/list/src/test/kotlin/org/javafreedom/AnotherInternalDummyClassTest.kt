package org.javafreedom

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class AnotherInternalDummyClassTest {

    @Test
    fun testInternalDummyClass() {
        val dummy = AnotherInternalDummyClass("name")

        assertThat(dummy.name).isEqualTo("name")
    }

}