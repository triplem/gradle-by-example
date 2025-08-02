# detekt

## Metrics

* 33 number of properties

* 26 number of functions

* 9 number of classes

* 4 number of packages

* 13 number of kt files

## Complexity Report

* 359 lines of code (loc)

* 249 source lines of code (sloc)

* 165 logical lines of code (lloc)

* 28 comment lines of code (cloc)

* 42 cyclomatic complexity (mcc)

* 20 cognitive complexity

* 4 number of total code smells

* 11% comment source ratio

* 254 mcc per 1,000 lloc

* 24 code smells per 1,000 lloc

## Findings (4)

### naming, FunctionNaming (2)

Function names should follow the naming convention set in the configuration.

[Documentation](https://detekt.dev/docs/rules/naming#functionnaming)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/integrationTest/kotlin/org/javafreedom/InternalDummyClassTest.kt:10:9
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

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/integrationTest/kotlin/org/javafreedom/InternalDummyClassTest.kt:18:9
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

### style, NewLineAtEndOfFile (2)

Checks whether files end with a line separator.

[Documentation](https://detekt.dev/docs/rules/style#newlineatendoffile)

* /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/integrationTest/kotlin/org/javafreedom/InternalDummyClassTest.kt:25:2
```
The file /home/runner/work/gradle-by-example/gradle-by-example/modules/list/src/integrationTest/kotlin/org/javafreedom/InternalDummyClassTest.kt is not ending with a new line.
```
```kotlin
22         assertThat(dummy.nick).isEqualTo("")
23     }
24 
25 }
!!  ^ error

```

* /home/runner/work/gradle-by-example/gradle-by-example/modules/utilities/src/test/kotlin/org/javafreedom/utilities/JoinUtilsTest.kt:41:2
```
The file /home/runner/work/gradle-by-example/gradle-by-example/modules/utilities/src/test/kotlin/org/javafreedom/utilities/JoinUtilsTest.kt is not ending with a new line.
```
```kotlin
38         list.add("abc")
39         assertEquals("@#$ 123 abc", JoinUtils.join(list))
40     }
41 }
!!  ^ error

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2025-08-02 15:58:53 UTC
