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

## Basic Usage

### Simple Timeline
```kotlin
Timeline(
    modifier = Modifier.height(100.dp),
    lineType = LineType.MIDDLE,
    lineStyle = LineStyle.dashed(
        color = colorResource(R.color.colorAccent),
        width = 2.dp
    ),
    marker = {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = colorResource(R.color.colorAccent),
                    CircleShape
                )
        )
    }
)
```
Output

![Compose Simple Timeline](../assets/compose-simple-timeline.png){ width="100" }

### Timeline with Content

```kotlin
Row(
    modifier = Modifier.height(IntrinsicSize.Min)
        .padding(start = 16.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Timeline(
        modifier = Modifier.fillMaxHeight(),
        lineType = LineType.MIDDLE,
        lineStyle = LineStyle.solid(
            color = MaterialTheme.colorScheme.primary,
            width = 2.dp
        ),
        marker = {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .padding(4.dp),
                tint = Color.White
            )
        }
    )

    Card(
        Modifier.padding(16.dp).weight(1f)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Order Placed",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Your order has been placed",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
```

Output

![Compose Timeline with Content](../assets/compose-timeline-with-content.png){ width="500" }

### Timeline in LazyColumn

```kotlin
LazyColumn {
    items(timelineItems.size) { index ->
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Timeline(
                lineType = getLineType(index, timelineItems.size),
                lineStyle = LineStyle.dashed(
                    color = MaterialTheme.colorScheme.primary,
                    width = 2.dp,
                    dashLength = 8.dp,
                    dashGap = 4.dp
                ),
                marker = {
                    // Your marker composable
                }
            )
            
            // Your content composable
        }
    }
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