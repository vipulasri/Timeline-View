package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.widgets.BorderedCircle
import com.github.vipulasri.timelineview.sample.widgets.RoundedCornerBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.thebluealliance.spectrum.SpectrumDialog
import com.github.vipulasri.timelineview.sample.databinding.BottomSheetOptionsBinding
import com.github.vipulasri.timelineview.sample.databinding.ItemBottomSheetLineBinding
import com.github.vipulasri.timelineview.sample.databinding.ItemBottomSheetMarkerBinding
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar

class TimelineAttributesBottomSheet : RoundedCornerBottomSheet() {

    interface Callbacks {
        fun onAttributesChanged(attributes: TimelineAttributes)
    }

    companion object {
        private const val EXTRA_ATTRIBUTES = "EXTRA_ATTRIBUTES"

        fun showDialog(
            fragmentManager: FragmentManager,
            attributes: TimelineAttributes,
            callbacks: Callbacks
        ) {
            val dialog = TimelineAttributesBottomSheet()
            dialog.arguments = bundleOf(EXTRA_ATTRIBUTES to attributes)
            dialog.setCallback(callbacks)
            dialog.show(fragmentManager, "[TIMELINE_ATTRIBUTES_BOTTOM_SHEET]")
        }
    }

    private var mCallbacks: Callbacks? = null
    private lateinit var mAttributes: TimelineAttributes
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    // View Binding
    private var _binding: BottomSheetOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let { bottomSheet ->
                bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }

        view?.post {
            val parent = view?.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            mBottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            mBottomSheetBehavior?.peekHeight = view?.measuredHeight!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        _binding = BottomSheetOptionsBinding.inflate(inflater.cloneInContext(contextThemeWrapper), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<TimelineAttributes>(EXTRA_ATTRIBUTES)?.let { attributes ->
            mAttributes = attributes.copy()
        } ?: run {
            // Handle the case where EXTRA_ATTRIBUTES is missing or null
            dismiss() // Or any other fallback action
        }

        binding.textAttributesHeading.setOnClickListener { dismiss() }

        // Orientation
        binding.rgOrientation.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_horizontal -> {
                    mAttributes.orientation = Orientation.HORIZONTAL
                }
                R.id.rb_vertical -> {
                    mAttributes.orientation = Orientation.VERTICAL
                }
            }
        }
        binding.rgOrientation.check(
            if (mAttributes.orientation == Orientation.VERTICAL)
                R.id.rb_vertical else R.id.rb_horizontal
        )

        // Marker
        val markerBinding = ItemBottomSheetMarkerBinding.bind(binding.layoutMarker.root)
        markerBinding.seekMarkerSize.progress = mAttributes.markerSize
        markerBinding.imageMarkerColor.mFillColor = mAttributes.markerColor
        markerBinding.checkboxMarkerInCenter.isChecked = mAttributes.markerInCenter
        markerBinding.seekMarkerLeftPadding.progress = mAttributes.markerLeftPadding
        markerBinding.seekMarkerTopPadding.progress = mAttributes.markerTopPadding
        markerBinding.seekMarkerRightPadding.progress = mAttributes.markerRightPadding
        markerBinding.seekMarkerBottomPadding.progress = mAttributes.markerBottomPadding
        markerBinding.seekMarkerLinePadding.progress = mAttributes.linePadding

        markerBinding.checkboxMarkerInCenter.setOnCheckedChangeListener { _, isChecked ->
            mAttributes.markerInCenter = isChecked
        }

        markerBinding.imageMarkerColor.setOnClickListener {
            showColorPicker(mAttributes.markerColor, markerBinding.imageMarkerColor)
        }

        markerBinding.seekMarkerSize.setOnProgressChangeListener(progressChangeListener)
        markerBinding.seekMarkerLeftPadding.setOnProgressChangeListener(progressChangeListener)
        markerBinding.seekMarkerTopPadding.setOnProgressChangeListener(progressChangeListener)
        markerBinding.seekMarkerRightPadding.setOnProgressChangeListener(progressChangeListener)
        markerBinding.seekMarkerBottomPadding.setOnProgressChangeListener(progressChangeListener)
        markerBinding.seekMarkerLinePadding.setOnProgressChangeListener(progressChangeListener)

        // Line
        Log.e(" mAttributes.lineWidth", "${mAttributes.lineWidth}")

        val lineBinding = ItemBottomSheetLineBinding.bind(binding.layoutLine.root)
        lineBinding.seekLineWidth.progress = mAttributes.lineWidth
        lineBinding.imageStartLineColor.mFillColor = mAttributes.startLineColor
        lineBinding.imageEndLineColor.mFillColor = mAttributes.endLineColor

        lineBinding.imageStartLineColor.setOnClickListener {
            showColorPicker(mAttributes.startLineColor, lineBinding.imageStartLineColor)
        }
        lineBinding.imageEndLineColor.setOnClickListener {
            showColorPicker(mAttributes.endLineColor, lineBinding.imageEndLineColor)
        }

        when (mAttributes.lineStyle) {
            TimelineView.LineStyle.NORMAL -> lineBinding.spinnerLineType.setSelection(0)
            TimelineView.LineStyle.DASHED -> lineBinding.spinnerLineType.setSelection(1)
        }

        lineBinding.spinnerLineType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                mAttributes.lineStyle = when (selectedItem) {
                    "Normal" -> TimelineView.LineStyle.NORMAL
                    "Dashed" -> TimelineView.LineStyle.DASHED
                    else -> TimelineView.LineStyle.NORMAL
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        lineBinding.seekLineDashWidth.progress = mAttributes.lineDashWidth
        lineBinding.seekLineDashGap.progress = mAttributes.lineDashGap

        lineBinding.seekLineWidth.setOnProgressChangeListener(progressChangeListener)
        lineBinding.seekLineDashWidth.setOnProgressChangeListener(progressChangeListener)
        lineBinding.seekLineDashGap.setOnProgressChangeListener(progressChangeListener)

        binding.buttonApply.setOnClickListener {
            mCallbacks?.onAttributesChanged(mAttributes)
            dismiss()
        }
    }

    private fun showColorPicker(selectedColor: Int, colorView: BorderedCircle) {
        SpectrumDialog.Builder(requireContext())
            .setColors(R.array.colors)
            .setSelectedColor(selectedColor)
            .setDismissOnColorSelected(true)
            .setOutlineWidth(1)
            .setOnColorSelectedListener { positiveResult, color ->
                if (positiveResult) {
                    colorView.mFillColor = color
                    when (colorView.id) {
                        R.id.image_marker_color -> mAttributes.markerColor = color
                        R.id.image_start_line_color -> mAttributes.startLineColor = color
                        R.id.image_end_line_color -> mAttributes.endLineColor = color
                    }
                }
            }.build().show(childFragmentManager, "ColorPicker")
    }

    private var progressChangeListener: DiscreteSeekBar.OnProgressChangeListener =
        object : DiscreteSeekBar.OnProgressChangeListener {

            override fun onProgressChanged(discreteSeekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {
                when (discreteSeekBar.id) {
                    R.id.seek_marker_size -> mAttributes.markerSize = value
                    R.id.seek_marker_left_padding -> mAttributes.markerLeftPadding = value
                    R.id.seek_marker_top_padding -> mAttributes.markerTopPadding = value
                    R.id.seek_marker_right_padding -> mAttributes.markerRightPadding = value
                    R.id.seek_marker_bottom_padding -> mAttributes.markerBottomPadding = value
                    R.id.seek_marker_line_padding -> mAttributes.linePadding = value
                    R.id.seek_line_width -> mAttributes.lineWidth = value
                    R.id.seek_line_dash_width -> mAttributes.lineDashWidth = value
                    R.id.seek_line_dash_gap -> mAttributes.lineDashGap = value
                }
            }

            override fun onStartTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onStopTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
        }

    private fun setCallback(callbacks: Callbacks) {
        mCallbacks = callbacks
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
