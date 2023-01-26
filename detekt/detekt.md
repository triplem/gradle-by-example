# detekt

## Metrics

* 28 number of properties

* 22 number of functions

* 12 number of classes

* 4 number of packages

* 12 number of kt files

## Complexity Report

* 321 lines of code (loc)

* 221 source lines of code (sloc)

* 144 logical lines of code (lloc)

* 25 comment lines of code (cloc)

* 38 cyclomatic complexity (mcc)

* 20 cognitive complexity

* 10 number of total code smells

* 11% comment source ratio

* 263 mcc per 1,000 lloc

* 69 code smells per 1,000 lloc

## Findings (10)

### naming, FunctionNaming (2)

Function names should follow the naming convention set in the configuration.

[Documentation](https://detekt.dev/docs/rules/naming#functionnaming)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/testIntegration/kotlin/org/javafreedom/InternalDummyClassTest.kt:10:9
```
Function names should match the pattern: [a-z][a-zA-Z0-9]*
```
```kotlin
7  class InternalDummyClassTest {
8  
9      @Test
10     fun testInternalDummyClass_AllProperties() {
!!         ^ error
11         val dummy = InternalDummyClass(name = "name", nick = "nick")
12 
13         assertThat(dummy.name).isEqualTo("name")

```

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/testIntegration/kotlin/org/javafreedom/InternalDummyClassTest.kt:18:9
```
Function names should match the pattern: [a-z][a-zA-Z0-9]*
```
```kotlin
15     }
16 
17     @Test
18     fun testInternalDummyClass_DefaultProperties() {
!!         ^ error
19         val dummy = InternalDummyClass(name = "name")
20 
21         assertThat(dummy.name).isEqualTo("name")

```

### style, FunctionOnlyReturningConstant (1)

A function that only returns a constant is misleading. Consider declaring a constant instead.

[Documentation](https://detekt.dev/docs/rules/style#functiononlyreturningconstant)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/app/src/main/kotlin/org/javafreedom/app/MessageUtils.kt:8:13
```
getMessage is returning a constant. Prefer declaring a constant instead.
```
```kotlin
5  
6  class MessageUtils {
7      companion object {
8          fun getMessage(): String = "Hello      World!"
!              ^ error
9      }
10 }
11 

```

### style, NewLineAtEndOfFile (2)

Checks whether files end with a line separator.

[Documentation](https://detekt.dev/docs/rules/style#newlineatendoffile)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/test/kotlin/org/javafreedom/AnotherInternalDummyClassTest.kt:16:2
```
The file /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/test/kotlin/org/javafreedom/AnotherInternalDummyClassTest.kt is not ending with a new line.
```
```kotlin
13         assertThat(dummy.name).isEqualTo("name")
14     }
15 
16 }
!!  ^ error

```

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/testIntegration/kotlin/org/javafreedom/InternalDummyClassTest.kt:25:2
```
The file /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/testIntegration/kotlin/org/javafreedom/InternalDummyClassTest.kt is not ending with a new line.
```
```kotlin
22         assertThat(dummy.nick).isEqualTo("")
23     }
24 
25 }
!!  ^ error

```

### style, UtilityClassWithPublicConstructor (4)

The class declaration is unnecessary because it only contains utility functions. An object declaration should be used instead.

[Documentation](https://detekt.dev/docs/rules/style#utilityclasswithpublicconstructor)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/app/src/main/kotlin/org/javafreedom/app/MessageUtils.kt:6:1
```
The class MessageUtils only contains utility functions. Consider defining it as an object.
```
```kotlin
3   */
4  package org.javafreedom.app
5  
6  class MessageUtils {
!  ^ error
7      companion object {
8          fun getMessage(): String = "Hello      World!"
9      }

```

* /home/runner/work/gradle-by-example/gradle-by-example/modules/utilities/src/main/kotlin/org/javafreedom/utilities/JoinUtils.kt:8:1
```
The class JoinUtils only contains utility functions. Consider defining it as an object.
```
```kotlin
5  
6  import org.javafreedom.list.LinkedList
7  
8  class JoinUtils {
!  ^ error
9      companion object {
10         fun join(source: LinkedList): String {
11             val result = StringBuilder()

```

* /home/runner/work/gradle-by-example/gradle-by-example/modules/utilities/src/main/kotlin/org/javafreedom/utilities/SplitUtils.kt:8:1
```
The class SplitUtils only contains utility functions. Consider defining it as an object.
```
```kotlin
5  
6  import org.javafreedom.list.LinkedList
7  
8  class SplitUtils {
!  ^ error
9      companion object {
10         fun split(source: String): LinkedList {
11             var lastFind = 0

```

* /home/runner/work/gradle-by-example/gradle-by-example/modules/utilities/src/main/kotlin/org/javafreedom/utilities/StringUtils.kt:8:1
```
The class StringUtils only contains utility functions. Consider defining it as an object.
```
```kotlin
5  
6  import org.javafreedom.list.LinkedList
7  
8  class StringUtils {
!  ^ error
9      companion object {
10         fun join(source: LinkedList): String {
11             return JoinUtils.join(source)

```

### style, WildcardImport (1)

Wildcard imports should be replaced with imports using fully qualified class names. Wildcard imports can lead to naming conflicts. A library update can introduce naming clashes with your classes which results in compilation errors.

[Documentation](https://detekt.dev/docs/rules/style#wildcardimport)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/test/kotlin/org/javafreedom/list/LinkedListTest.kt:7:1
```
assertk.assertions.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
4  package org.javafreedom.list
5  
6  import assertk.assertThat
7  import assertk.assertions.*
!  ^ error
8  import mu.KotlinLogging
9  import kotlin.test.Test
10 

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-01-26 11:18:45 UTC
