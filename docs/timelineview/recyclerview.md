# Timeline View in RecyclerView

Timeline View can be easily integrated with RecyclerView by following these steps:

### Adapter

The adapter needs to override `getItemViewType` to provide the correct timeline type:

```kotlin
class TimelineAdapter(
    private val items: List<TimelineItem>
) : RecyclerView.Adapter<TimelineViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timeline, parent, false)
        return TimelineViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
```

!!! note "Important"
Make sure to pass the `viewType` from `onCreateViewHolder` to the ViewHolder constructor. This is crucial for proper timeline initialization.

### ViewHolder

The ViewHolder needs to initialize the timeline with the correct view type:

```kotlin
class TimelineViewHolder(
    itemView: View, 
    viewType: Int // Required for timeline initialization
) : RecyclerView.ViewHolder(itemView) {
    
    private val timelineView: TimelineView = itemView.findViewById<TimelineView>(R.id.timeline).apply {
        initLine(viewType) // Initialize timeline with view type
    }
    
    fun bind(item: TimelineItem) {
        // Bind your data here
    }
}
```

#### Layout

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.github.vipulasri.timelineview.TimelineView
        android:id="@+id/timeline"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:markerSize="20dp"
        app:lineWidth="2dp"
        app:lineStyle="normal"/>

    <!-- Your content layout here -->

</LinearLayout>
```
