# Timeline-View 

Android Timeline View Library (Using RecyclerView) is simple implementation used to display view like Tracking of shipment/order, steppers etc.

### Specs
[![Maven Central](https://img.shields.io/maven-central/v/com.github.vipulasri/timelineview.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.vipulasri%22%20AND%20a:%22timelineview%22)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/vipulasri/Timeline-View/blob/master/LICENSE)

### Badges/Featured In
[![Timeline View](https://www.appbrain.com/stats/libraries/shield/timeline_view.svg)](https://www.appbrain.com/stats/libraries/details/timeline_view/timeline-view)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Timeline--View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2923) 
[![AndroidWeekly](https://androidweekly.net/issues/issue-395/badge)](https://androidweekly.net/issues/issue-395) 
[![AndroidDev Digest](https://img.shields.io/badge/AndroidDev%20Digest-%23126-blue.svg)](https://www.androiddevdigest.com/digest-126/) 

![showcase](https://github.com/vipulasri/Timeline-View/blob/master/art/showcase.png)

## Sample Project

For information : checkout [Example Screen Code](https://github.com/vipulasri/Timeline-View/tree/master/app/src/main/java/com/github/vipulasri/timelineview/sample/example) in repository.

### Download

[![TimelineView on Google Play](https://github.com/vipulasri/Timeline-View/blob/master/art/google_play.png)](https://play.google.com/store/apps/details?id=com.github.vipulasri.timelineview.sample)

## Quick Setup

### 1. Include library

**Using Gradle**

``` gradle
dependencies {
    implementation 'com.github.vipulasri:timelineview:1.1.5'
}
```

### What's New

See the project's Releases page for a list of versions with their change logs.

### [View Releases](https://github.com/vipulasri/Timeline-View/releases)

If you Watch this repository, GitHub will send you an email every time I publish an update.

### 2. Usage
 * In XML Layout :

``` java
<com.github.vipulasri.timelineview.TimelineView
    android:id="@+id/timeline"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    app:markerSize="20dp"
    app:lineWidth="2dp"
    app:startLineColor="@color/colorPrimary"
    app:endLineColor="@color/colorPrimary"/>
```

##### Line Padding around marker

``` java
<com.github.vipulasri.timelineview.TimelineView
    android:id="@+id/timeline"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    app:markerSize="20dp"
    app:lineWidth="2dp"
    app:startLineColor="@color/colorPrimary"
    app:endLineColor="@color/colorPrimary"
    app:linePadding="5dp"/>
```

* Configure using xml attributes or setters in code:

    <table>
    <th>Attribute Name</th>
    <th>Default Value</th>
    <th>Description</th>
    <tr>
        <td>app:marker="@drawable/marker"</td>
        <td>Green Colored Oval Drawable</td>
        <td>sets marker drawable</td>
    </tr>
    <tr>
        <td>app:markerSize="25dp"</td>
        <td>25dp</td>
        <td>sets marker size</td>
    </tr>
    <tr>
        <td>app:markerInCenter="false"</td>
        <td>true</td>
        <td>sets the marker in center of line if `true`</td>
    </tr>
    <tr>
        <td>app:markerPaddingLeft="0dp"</td>
        <td>0dp</td>
        <td>sets the marker left padding, applicable only with horizontal orientation</td>
    </tr>
    <tr>
        <td>app:markerPaddingTop="0dp"</td>
        <td>0dp</td>
        <td>sets the marker top padding, applicable only with vertical orientation</td>
    </tr>
    <tr>
        <td>app:markerPaddingRight="0dp"</td>
        <td>0dp</td>
        <td>sets the marker right padding, applicable only with horizontal orientation</td>
    </tr>
    <tr>
        <td>app:markerPaddingBottom="0dp"</td>
        <td>0dp</td>
        <td>sets the marker bottom padding, applicable only with vertical orientation</td>
    </tr>
    <tr>
        <td>app:startLineColor="@color/primarColor"</td>
        <td>Dark Grey Line</td>
        <td>sets start line color</td>
    </tr>
    <tr>
        <td>app:endLineColor="@color/primarColor"</td>
        <td>Dark Grey Line</td>
        <td>sets end line color</td>
    </tr>
    <tr>
        <td>app:lineWidth="2dp"</td>
        <td>2dp</td>
        <td>sets line width</td>
    </tr>
    <tr>
        <td>app:lineOrientation="horizontal"</td>
        <td>vertical</td>
        <td>sets orientation of line ie `horizontal` or `vertical`</td>
    </tr>
    <tr>
        <td>app:linePadding="5dp"</td>
        <td>0dp</td>
        <td>sets line padding around marker</td>
     </tr>
     <tr>
         <td>app:lineStyle="dash"</td>
         <td>normal</td>
         <td>sets line style ie `normal` or `dashed`</td>
     </tr>
     <tr>
         <td>app:lineStyleDashGap="4dp"</td>
         <td>4dp</td>
         <td>sets line dash gap</td>
     </tr>
     <tr>
         <td>app:lineStyleDashLength="8dp"</td>
         <td>8dp</td>
         <td>sets line dash length</td>
     </tr>
    </table>
 
* RecyclerView Holder : 
   Your `RecyclerViewHolder` should have an extra parameter in constructor i.e viewType from `onCreateViewHolder`. You would also have to call the method `initLine(viewType)` in constructor definition.
 
``` java

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public  TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
        }
    }

```

* RecyclerView Adapter : 
   override `getItemViewType` method in Adapter
 
``` java

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

```
   And pass the `viewType` from `onCreateViewHolder` to its Holder.
   
``` java

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType);
    }

```

## Apps that use this library

* [ALL IPO News](https://play.google.com/store/apps/details?id=com.appbootup.ipo.news)

If you're using this library in your app and you'd like to list it here,
Please let me know via [email](mailto:me@vipulasri.com), [pull requests](https://github.com/vipulasri/Timeline-View/pulls) or [issues](https://github.com/vipulasri/Timeline-View/issues).

[Apps using Timeline-View, via AppBrain Stats](https://www.appbrain.com/stats/libraries/details/timeline_view/timeline-view)

## License


    Copyright 2018 Vipul Asri

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
