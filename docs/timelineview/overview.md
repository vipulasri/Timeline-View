# Timeline View

A Timeline View library for Android that helps you display chronological events in a vertical or horizontal timeline format.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/com.github.vipulasri/timelineview.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.vipulasri/timelineview/overview)

Add the dependency to your app's `build.gradle`:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.vipulasri:timelineview:${latest_version}")
}
```

## Basic Usage

``` xml
<com.github.vipulasri.timelineview.TimelineView
    android:id="@+id/timeline"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    app:markerSize="20dp"
    app:lineWidth="2dp"
    app:startLineColor="@color/colorPrimary"
    app:endLineColor="@color/colorPrimary"/>
```

## Customization

| Attribute                 | Default Value       | Description                                                 |
|---------------------------|---------------------|-------------------------------------------------------------|
| `app:marker`              | Green Oval Drawable | Sets marker drawable                                        |
| `app:markerSize`          | `25dp`              | Sets marker size                                            |
| `app:markerInCenter`      | `true`              | Sets the marker in center of line if `true`                 |
| `app:markerPaddingLeft`   | `0dp`               | Sets the marker left padding (horizontal orientation only)  |
| `app:markerPaddingTop`    | `0dp`               | Sets the marker top padding (vertical orientation only)     |
| `app:markerPaddingRight`  | `0dp`               | Sets the marker right padding (horizontal orientation only) |
| `app:markerPaddingBottom` | `0dp`               | Sets the marker bottom padding (vertical orientation only)  |
| `app:startLineColor`      | Dark Grey           | Sets start line color                                       |
| `app:endLineColor`        | Dark Grey           | Sets end line color                                         |
| `app:lineWidth`           | `2dp`               | Sets line width                                             |
| `app:lineOrientation`     | `vertical`          | Sets orientation (`horizontal` or `vertical`)               |
| `app:linePadding`         | `0dp`               | Sets line padding around marker                             |
| `app:lineStyle`           | `normal`            | Sets style to both start and end (`normal` or `dashed`)     |
| `app:startLineStyle`      | `normal`            | Sets style to start line (`normal` or `dashed`)             |
| `app:endLineStyle`        | `normal`            | Sets style to end line (`normal` or `dashed`)               |
| `app:lineStyleDashGap`    | `4dp`               | Sets line dash gap                                          |
| `app:lineStyleDashLength` | `8dp`               | Sets line dash length                                       |