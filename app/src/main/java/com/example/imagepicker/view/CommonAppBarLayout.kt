package com.example.imagepicker.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.imagepicker.R
import com.example.imagepicker.databinding.ViewAppBarLayoutBinding
import com.example.imagepicker.util.extension.visible


class CommonAppBarLayout : ConstraintLayout {
    private var binding: ViewAppBarLayoutBinding

    private var isShowLeftButton: Boolean = true
    private var isShowRightButton: Boolean = false
    private var leftClickListener: OnClickListener? = null
    private var rightClickListener: OnClickListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_app_bar_layout, this, true)

        binding.leftButton.visible(isShowLeftButton)
        binding.rightButton.visible(isShowRightButton)

        binding.leftButton.setOnClickListener(leftClickListener?.let { it }
            ?: OnClickListener { (context as Activity).finish() })
        binding.leftButton.setOnClickListener(rightClickListener?.let { it }
            ?: OnClickListener { (context as Activity).finish() })
    }

    fun setLeftClickListener(clickListener: OnClickListener) {
        this.leftClickListener = clickListener
        binding.leftButton.visible(true)
        binding.leftButton.setOnClickListener(clickListener)
    }

    fun setRightClickListener(clickListener: OnClickListener) {
        this.leftClickListener = clickListener
        binding.rightButton.visible(true)
        binding.rightButton.setOnClickListener(clickListener)
    }

    fun setShowLeftButton(isShow: Boolean) {
        this.isShowLeftButton = isShow
    }

    fun setShowRightButton(isShow: Boolean) {
        this.isShowRightButton = isShow
    }

    fun setTitle(title: String) {
        binding.titleTextView.text = title
    }

    fun setRightButtonText(name: String) {
        binding.rightButton.text = name
    }
}