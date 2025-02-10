# Timeline View Compose

A Jetpack Compose implementation of Timeline View for creating visually appealing timelines.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/com.github.vipulasri/timelineview.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.vipulasri/timelineview-compose/overview)

Add the code below to your root build.gradle file (not your module-level build.gradle file):

```kotlin

allprojects {
    repositories {
        mavenCentral()
    }
}
```

Then, add the dependency to your app's `build.gradle`:

```kotlin

dependencies {
    implementation("com.github.vipulasri:timelineview-compose:${latest_version}")
}
```

## Line Types

Timeline supports different line types based on the item's position:

- `LineType.START`: First item in the timeline
- `LineType.MIDDLE`: Items between first and last
- `LineType.END`: Last item in the timeline
- `LineType.SINGLE`: Single item timeline

## Line Styles

Timeline supports two line styles:

### Solid Line

```kotlin
LineStyle.solid(
    color = Color.Blue,
    width = 2.dp
)
```

### Dashed Line

```kotlin
LineStyle.dashed(
    color = Color.Blue,
    width = 2.dp,
    dashLength = 8.dp,
    dashGap = 4.dp
)
```

!!! tip "Best Practice"
    When using Timeline in a list, make sure to properly handle the line types based on item position for a consistent appearance.

!!! note "Marker Customization"
    The marker composable gives you complete freedom to design your timeline markers. You can use icons, shapes, images, or any other composable.