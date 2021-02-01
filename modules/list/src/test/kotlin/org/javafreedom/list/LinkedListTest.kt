/*
 * This Groovy source file was generated by the Gradle "init" task.
 */
package org.javafreedom.list

import assertk.assertThat
import assertk.assertions.*
import kotlin.test.Test

class LinkedListTest {

    @Test fun testConstructor() {
        val list = LinkedList()
        assertThat(list.size()).isEqualTo(0)
    }

    @Test fun testAdd() {
        val list = LinkedList()

        list.add("one")
        assertThat(list.size()).isEqualTo(1)
        assertThat(list.get(0)).isEqualTo("one")

        list.add("two")
        assertThat(list.size()).isEqualTo(2)
        assertThat(list.get(1)).isEqualTo("two")
    }

    @Test fun testRemove() {
        val list = LinkedList()

        list.add("one")
        list.add("two")
        assertThat(list.remove("one")).isTrue()

        assertThat(list.size()).isEqualTo(1)
        assertThat(list.get(0)).isEqualTo("two")

        assertThat(list.remove("two")).isTrue()
        assertThat(list.size()).isEqualTo(0)
    }

    @Test fun testRemoveMissing() {
        val list = LinkedList()

        list.add("one")
        list.add("two")
        assertThat(list.size()).isEqualTo(2)
        assertThat(list.remove("three")).isFalse()
        assertThat(list.size()).isEqualTo(2)
    }
}