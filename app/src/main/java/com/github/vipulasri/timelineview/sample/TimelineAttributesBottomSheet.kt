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
import com.github.vipulasri.timelineview.sample.databinding.BottomSheetOptionsBinding
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.widgets.BorderedCircle
import com.github.vipulasri.timelineview.sample.widgets.RoundedCornerBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.slider.Slider
import com.thebluealliance.spectrum.SpectrumDialog

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
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
            dialog.arguments = bundleOf(
                EXTRA_ATTRIBUTES to attributes
            )
            dialog.setCallback(callbacks)
            dialog.show(fragmentManager, "[TIMELINE_ATTRIBUTES_BOTTOM_SHEET]")
        }
    }

    private var _binding: BottomSheetOptionsBinding? = null
    private val binding get() = _binding!!

    private var callbacks: Callbacks? = null
    private lateinit var attributes: TimelineAttributes
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onStart() {
        super.onStart()

        if (dialog != null) {
            val bottomSheet =
                dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        view?.post {
            val parent = view?.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior?.peekHeight = view?.measuredHeight!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        _binding = BottomSheetOptionsBinding.inflate(
            inflater.cloneInContext(contextThemeWrapper),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val attributes = (requireArguments().getParcelable<TimelineAttributes>(EXTRA_ATTRIBUTES)!!)
        this.attributes = attributes.copy()

        binding.textAttributesHeading.setOnClickListener { dismiss() }

        //orientation
        binding.rgOrientation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_horizontal -> {
                    this.attributes.orientation = Orientation.HORIZONTAL
                }

                R.id.rb_vertical -> {
                    this.attributes.orientation = Orientation.VERTICAL
                }
            }
        }
        binding.rgOrientation.check(if (this.attributes.orientation == Orientation.VERTICAL) R.id.rb_vertical else R.id.rb_horizontal)

        //marker
        binding.layoutMarker.run {
            seekMarkerSize.value = attributes.markerSize.toFloat()
            imageMarkerColor.fillColor = attributes.markerColor
            checkboxMarkerInCenter.isChecked = attributes.markerInCenter
            seekMarkerLeftPadding.value = attributes.markerLeftPadding.toFloat()
            seekMarkerTopPadding.value = attributes.markerTopPadding.toFloat()
            seekMarkerRightPadding.value = attributes.markerRightPadding.toFloat()
            seekMarkerBottomPadding.value = attributes.markerBottomPadding.toFloat()
            seekMarkerLinePadding.value = attributes.linePadding.toFloat()
        }

        binding.layoutMarker.checkboxMarkerInCenter.setOnCheckedChangeListener { buttonView, isChecked ->
            this.attributes.markerInCenter = isChecked
        }

        binding.layoutMarker.imageMarkerColor.setOnClickListener {
            showColorPicker(
                this.attributes.markerColor,
                binding.layoutMarker.imageMarkerColor
            )
        }

        binding.layoutMarker.run {
            seekMarkerSize.addOnChangeListener(progressChangeListener)
            seekMarkerLeftPadding.addOnChangeListener(progressChangeListener)
            seekMarkerTopPadding.addOnChangeListener(progressChangeListener)
            seekMarkerRightPadding.addOnChangeListener(progressChangeListener)
            seekMarkerBottomPadding.addOnChangeListener(progressChangeListener)
            seekMarkerLinePadding.addOnChangeListener(progressChangeListener)
        }

        //line
        Log.e(" mAttributes.lineWidth", "${this.attributes.lineWidth}")

        binding.layoutLine.run {
            seekLineWidth.value = attributes.lineWidth.toFloat()
            imageStartLineColor.fillColor = attributes.startLineColor
            imageEndLineColor.fillColor = attributes.endLineColor
            spinnerLineType.setSelection(if (attributes.lineStyle == TimelineView.LineStyle.NORMAL) 0 else 1)
            seekLineDashWidth.value = attributes.lineDashWidth.toFloat()
            seekLineDashGap.value = attributes.lineDashGap.toFloat()
        }

        binding.layoutLine.imageStartLineColor.setOnClickListener {
            showColorPicker(
                this.attributes.startLineColor,
                binding.layoutLine.imageStartLineColor
            )
        }

        binding.layoutLine.imageEndLineColor.setOnClickListener {
            showColorPicker(
                this.attributes.endLineColor,
                binding.layoutLine.imageEndLineColor
            )
        }

        binding.layoutLine.spinnerLineType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    when (selectedItem) {
                        "Normal" -> this@TimelineAttributesBottomSheet.attributes.lineStyle =
                            TimelineView.LineStyle.NORMAL

                        "Dashed" -> this@TimelineAttributesBottomSheet.attributes.lineStyle =
                            TimelineView.LineStyle.DASHED

                        else -> {
                            this@TimelineAttributesBottomSheet.attributes.lineStyle =
                                TimelineView.LineStyle.NORMAL
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        binding.layoutLine.run {
            seekLineWidth.addOnChangeListener(progressChangeListener)
            seekLineDashWidth.addOnChangeListener(progressChangeListener)
            seekLineDashGap.addOnChangeListener(progressChangeListener)
        }

        binding.buttonApply.setOnClickListener {
            callbacks?.onAttributesChanged(this.attributes)
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
                    colorView.fillColor = color

                    when (colorView.id) {
                        R.id.image_marker_color -> {
                            attributes.markerColor = color
                        }

                        R.id.image_start_line_color -> {
                            attributes.startLineColor = color
                        }

                        R.id.image_end_line_color -> {
                            attributes.endLineColor = color
                        }

                        else -> {
                            //do nothing
                        }
                    }

                }
            }.build().show(childFragmentManager, "ColorPicker")
    }

    private var progressChangeListener = Slider.OnChangeListener { slider, value, fromUser ->
        Log.d("onProgressChanged", "value->$value")
        when (slider.id) {
            R.id.seek_marker_size -> {
                attributes.markerSize = value.toInt()
            }

            R.id.seek_marker_left_padding -> {
                attributes.markerLeftPadding = value.toInt()
            }

            R.id.seek_marker_top_padding -> {
                attributes.markerTopPadding = value.toInt()
            }

            R.id.seek_marker_right_padding -> {
                attributes.markerRightPadding = value.toInt()
            }

            R.id.seek_marker_bottom_padding -> {
                attributes.markerBottomPadding = value.toInt()
            }

            R.id.seek_marker_line_padding -> {
                attributes.linePadding = value.toInt()
            }

            R.id.seek_line_width -> {
                attributes.lineWidth = value.toInt()
            }

            R.id.seek_line_dash_width -> {
                attributes.lineDashWidth = value.toInt()
            }

            R.id.seek_line_dash_gap -> {
                attributes.lineDashGap = value.toInt()
            }
        }
    }

    private fun setCallback(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}