# Timeline-View 

Android Timeline View Library (Using RecyclerView) is simple implementation used to display view like Tracking of shipment/order, steppers etc.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Timeline--View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2923)

[![Android Gems](http://www.android-gems.com/badge/vipulasri/Timeline-View.svg)

### Screenshot of Sample Application

[Sample Application Apk](https://github.com/vipulasri/Timeline-View/tree/master/apk)

![Screenshot](https://github.com/vipulasri/Timeline-View/blob/master/Screenshot1.png)

## Quick Setup

### 1. Include library

**Automatically with Gradle**

``` gradle
dependencies {
    compile 'com.github.vipulasri:timelineview:1.0.0'
}
```

#### Manual:
**Manual - Using [Android Studio](https://developer.android.com/sdk/installing/studio.html):**
 * Download the library folder and import to your root application folder. 
You can manually achieve this step with 3 steps: 
    1. Paste the folder library into your application at the same level of your app, build and gradle folder
    2. Add to your settings.gradle file the following code line:
    "include ':app', ':timelineview'"
    3. Rebuild the project
 * File → Project Structure → in Modules section click on "app" → Click on tab "Dependecies" → Click on the green plus → Module Dependecy → Select ":library"
 * Done 
 
 ### 2. Usage
 
 * In XML Layout : 
 
 ``` java
<com.vipul.hp_hp.timelineview.TimelineView
    android:id="@+id/time_marker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:paddingTop="30dp"
    android:paddingBottom="30dp"
    android:paddingLeft="10dp"
    android:paddingRight="10dp"
    app:marker_size="25dp"
    app:line_size="2dp"
    app:line="@color/colorPrimary"/>
```
 
 
 * RecyclerView Holder : 
   Your `RecyclerViewHolder` should have an extra paramenter in constructor i.e viewType from `onCreateViewHolder`. You would also have to call the method `initLine(viewType)` in constructor definition.
 
``` java

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public  TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }
    }

```

 * RecyclerView Adapter : 
   override `getItemViewType` method in Adapter
 
``` java

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
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


For information : checkout [sample app](https://github.com/vipulasri/Timeline-View/tree/master/app) in repository.


## License


    The MIT License (MIT)

    Copyright (c) 2015 Vipul Asri

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
