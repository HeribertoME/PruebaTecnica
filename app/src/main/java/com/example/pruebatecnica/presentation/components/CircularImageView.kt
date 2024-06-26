package com.example.pruebatecnica.presentation.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.ViewCircularImageBinding
import com.example.pruebatecnica.extensions.loadCircularImage
import com.example.pruebatecnica.utils.Utils

class CircularImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var text: String? = null
    private var imageUrl: String? = null
    private var placeholderResId: Int = R.drawable.placeholder_default
    private var initialsBackgroundColor: Int =
        ContextCompat.getColor(context, R.color.initials_background_color)
    private var initialsTextColor: Int =
        ContextCompat.getColor(context, R.color.initials_text_color)
    private var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val binding: ViewCircularImageBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewCircularImageBinding.inflate(inflater, this)

        textPaint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 24f, resources.displayMetrics
        )
        textPaint.color = initialsTextColor
        paint.color = initialsBackgroundColor

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView)
        text = attributes.getString(R.styleable.CircularImageView_text)
        imageUrl = attributes.getString(R.styleable.CircularImageView_imageUrl)
        placeholderResId = attributes.getResourceId(
            R.styleable.CircularImageView_placeholder, R.drawable.placeholder_default
        )
        initialsBackgroundColor = attributes.getColor(
            R.styleable.CircularImageView_initialsBackgroundColor,
            ContextCompat.getColor(context, R.color.initials_background_color)
        )
        initialsTextColor = attributes.getColor(
            R.styleable.CircularImageView_initialsTextColor,
            ContextCompat.getColor(context, R.color.initials_text_color)
        )
        attributes.recycle()

        updateView()
    }

    fun setImageUrl(url: String) {
        imageUrl = url
        updateView()
    }

    fun setText(text: String) {
        this.text = text
        updateView()
    }

    fun setPlaceholder(resId: Int) {
        placeholderResId = resId
        updateView()
    }

    fun setInitialsBackgroundColor(color: Int) {
        initialsBackgroundColor = color
        updateView()
    }

    fun setInitialsTextColor(color: Int) {
        initialsTextColor = color
        updateView()
    }

    private fun updateView() {
        try {
            loadImageOrShowInitials()
        } catch (e: Exception) {
            showInitialsOrPlaceholder()
        }
    }

    private fun loadImageOrShowInitials() {
        if (!imageUrl.isNullOrEmpty()) {
            binding.ivImage.loadCircularImage(context, imageUrl, getPlaceholderDrawable())
        } else {
            showInitialsOrPlaceholder()
        }
    }

    private fun showInitialsOrPlaceholder() {
        binding.ivImage.setImageDrawable(getPlaceholderDrawable())
    }

    private fun getPlaceholderDrawable(): Drawable? {
        if (!text.isNullOrEmpty() && text!!.first().isLetter()) {
            return object : Drawable() {
                override fun draw(canvas: Canvas) {
                    val initials = Utils.extractInitials(text)
                    val centerX = bounds.width() / 2f
                    val centerY = bounds.height() / 2f

                    val textWidth = textPaint.measureText(initials) / 2f
                    val textBaseLineHeight = textPaint.fontMetrics.ascent * -0.4f

                    canvas.drawCircle(
                        centerX, centerY,
                        (bounds.height() / 2f).coerceAtLeast(textWidth / 2f), paint
                    )
                    canvas.drawText(
                        initials,
                        centerX - textWidth,
                        centerY + textBaseLineHeight,
                        textPaint
                    )
                }

                override fun setAlpha(alpha: Int) {
                    paint.alpha = alpha
                }

                override fun setColorFilter(colorFilter: ColorFilter?) {
                    paint.colorFilter = colorFilter
                }

                override fun getOpacity(): Int {
                    return PixelFormat.TRANSLUCENT
                }
            }
        } else {
            return ContextCompat.getDrawable(context, R.drawable.placeholder_default)
        }
    }

}