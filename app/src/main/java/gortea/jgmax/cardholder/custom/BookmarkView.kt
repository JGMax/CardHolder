package gortea.jgmax.cardholder.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.custom.model.BookmarkViewModel
import gortea.jgmax.cardholder.custom.params.Orientation
import gortea.jgmax.cardholder.utils.toPx
import kotlin.math.abs

class BookmarkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // view state
    private val model = BookmarkViewModel(this)
    var opened: Boolean
        set(value) {
            model.opened = value
        }
        get() = model.opened

    // listener init
    private var stateListener: StateChangeListener? = null

    // Size constants
    private var desiredWidth: Int = 104.toPx(context)
    private var desiredHeight: Int = 40.toPx(context)
    private val defaultOffsetFromTriangle = 4.toPx(context)

    // Paints init
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Background path init
    private val path = Path()

    // Current length of bookmark (0-1)
    private var currentLength = 0.5f

    // Orientation constant
    private var orientation: Orientation = Orientation.HORIZONTAL

    // Animation constants
    private val animationDuration = 200L

    // Customizable fields
    private var triangleHeight = -1
    private var closeLength = -1
    private var openLength = -1
    private var icLeftTransition = 0.toPx(context)
    private var icRightTransition = 0.toPx(context)
    private var icTopTransition = 0.toPx(context)
    private var icBottomTransition = 0.toPx(context)
    private var icHeight = -1
    private var icWidth = -1
    private var drawable: Drawable? = null
    private var drawableColor = Color.BLACK

    init {
        bgPaint.style = Paint.Style.FILL
        if (attrs != null) {
            val styledAttrs = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BookmarkView,
                defStyleAttr,
                0
            )
            triangleHeight =
                styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_triangle_height, triangleHeight)
            closeLength = styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_close_length, closeLength)
            openLength = styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_open_length, openLength)
            opened = styledAttrs.getBoolean(R.styleable.BookmarkView_opened, false)
            currentLength = if (opened) 1f else 0f

            icTopTransition =
                styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_top_transition, 0)
            icLeftTransition =
                styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_left_transition, 0)
            icRightTransition =
                styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_right_transition, 0)
            icBottomTransition =
                styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_bottom_transition, 0)

            val drawableId = styledAttrs.getResourceId(R.styleable.BookmarkView_ic_drawable, -1)
            drawable = if (drawableId != -1) {
                VectorDrawableCompat.create(resources, drawableId, null)
            } else {
                null
            }

            if (drawable != null) {
                icHeight =
                    styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_height, icHeight)
                icWidth =
                    styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_width, icWidth)
                drawableColor = styledAttrs.getColor(R.styleable.BookmarkView_ic_color, Color.BLACK)
            }

            bgPaint.color =
                styledAttrs.getColor(R.styleable.BookmarkView_bookmark_color, Color.GRAY)

            val orientationValue =
                styledAttrs.getInt(R.styleable.BookmarkView_bookmark_orientation, 0)
            orientation = when (orientationValue) {
                1 -> {
                    desiredHeight = 104.toPx(context)
                    desiredWidth = 40.toPx(context)
                    Orientation.VERTICAL
                }
                else -> {
                    Orientation.HORIZONTAL
                }
            }
            styledAttrs.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> widthSize.coerceAtMost(desiredWidth)
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> desiredWidth
        }

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> heightSize.coerceAtMost(desiredHeight)
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> desiredHeight
        }
        setMeasuredDimension(width, height)
    }

    private fun getTargets(): Pair<Int, Int> {
        val targetViewLength: Int
        val targetViewWidth: Int
        if (orientation == Orientation.HORIZONTAL) {
            targetViewWidth = height
            targetViewLength = width
        } else {
            targetViewWidth = width
            targetViewLength = height
        }
        return targetViewLength to targetViewWidth
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val targetViewLength = getTargets().first

        if (openLength == -1)
            openLength = targetViewLength
        if (closeLength == -1)
            closeLength = targetViewLength / 2

        if (triangleHeight == -1)
            triangleHeight = openLength.coerceAtMost(closeLength) / 3
    }

    private fun setDrawableDimensions(drawable: Drawable) {
        val length = abs(openLength - closeLength) *
                currentLength +
                openLength.coerceAtMost(closeLength)
        val x: Int
        val y: Int

        if (icHeight == -1) {
            icHeight = drawable.intrinsicHeight
        }
        if (icWidth == -1) {
            icWidth = drawable.intrinsicWidth
        }
        if (orientation == Orientation.HORIZONTAL) {
            x =
                (width - length + icLeftTransition + triangleHeight + defaultOffsetFromTriangle).toInt()
            y = (height / 2f - icHeight / 2 + icTopTransition).toInt()
        } else {
            x = (width / 2f - icWidth / 2f + icLeftTransition).toInt()
            y =
                (length - triangleHeight - icHeight - defaultOffsetFromTriangle + icTopTransition).toInt()
        }

        val rightBorder = x + icWidth - icRightTransition
        val bottomBorder = y + icHeight - icBottomTransition

        drawable.setBounds(x, y, rightBorder, bottomBorder)
    }

    private fun drawPath(path: Path) {
        val (targetViewLength, targetViewWidth) = getTargets()
        if (currentLength !in 0f..1f) return
        val length = abs(openLength - closeLength) *
                currentLength +
                openLength.coerceAtMost(closeLength)

        path.apply {
            reset()
            if (orientation == Orientation.HORIZONTAL) {
                moveTo(targetViewLength.toFloat(), 0f)
                lineTo(targetViewLength - length, 0f)
                lineTo(targetViewLength - length + triangleHeight, targetViewWidth / 2f)
                lineTo(targetViewLength - length, targetViewWidth.toFloat())
                lineTo(targetViewLength.toFloat(), targetViewWidth.toFloat())
            } else {
                moveTo(0f, 0f)
                lineTo(0f, targetViewLength - length)
                lineTo(targetViewWidth / 2f, targetViewLength - length - triangleHeight)
                lineTo(targetViewWidth.toFloat(), targetViewLength - length)
                lineTo(targetViewWidth.toFloat(), 0f)
            }
            close()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        drawPath(path)
        canvas.drawPath(path, bgPaint)
        drawable?.let {
            it.setTint(drawableColor)
            setDrawableDimensions(it)
            it.draw(canvas)
        }
    }

    fun setTriangleHeight(height: Int) {
        if (height < closeLength.coerceAtMost(openLength)) {
            triangleHeight = height
            invalidate()
        }
    }

    fun setCloseLength(length: Int) {
        val targetLength = getTargets().first
        if (length !in 0..targetLength) return

        val proportion = (openLength - length) / (openLength - closeLength)
        currentLength *= proportion
        closeLength = length
        invalidate()
    }

    fun setOpenLength(length: Int) {
        val targetLength = getTargets().first
        if (length !in 0..targetLength) return

        val proportion = (length - closeLength) / (openLength - closeLength)
        currentLength *= proportion
        openLength = length
        invalidate()
    }

    fun setIconSize(width: Int, height: Int) {
        if (icWidth == width && icHeight == height) return
        icHeight = height
        icWidth = width
        invalidate()
    }

    fun setIconTransitions(top: Int, left: Int, right: Int, bottom: Int) {
        if (top == icTopTransition
            && left == icLeftTransition
            && right == icRightTransition
            && bottom == icBottomTransition
        ) return

        icTopTransition = top
        icLeftTransition = left
        icRightTransition = right
        icBottomTransition = bottom
        invalidate()
    }

    fun setOpened(opened: Boolean, withAnimation: Boolean = true) {
        if (opened && currentLength == 1f
            || !opened && currentLength == 0f) return

        val startValue: Float
        val endValue: Float
        if (opened) {
            startValue = 0f
            endValue = 1f
        } else {
            startValue = 1f
            endValue = 0f
        }

        if (withAnimation) {
            val valueAnimator = ValueAnimator.ofFloat(startValue, endValue)
            valueAnimator.duration = animationDuration
            valueAnimator.addUpdateListener {
                currentLength = it.animatedValue as Float
                invalidate()
            }
            valueAnimator.start()
        } else if (!withAnimation) {
            currentLength = endValue
        }
        invalidate()
        this.opened = opened
        stateListener?.onStateChange(this.opened)
    }

    override fun performClick(): Boolean {
        setOpened(!opened, true)
        return super.performClick()
    }

    fun setStateChangeListener(listener: StateChangeListener) {
        this.stateListener = listener
    }

    interface StateChangeListener {
        fun onStateChange(isOpened: Boolean)
    }

    companion object {
        @BindingAdapter("opened")
        @JvmStatic fun setOpened(view: BookmarkView, newValue: Boolean) {
            if (view.opened != newValue) {
                view.opened = newValue
            }
        }

        @InverseBindingAdapter(attribute = "opened")
        @JvmStatic
        fun getOpened(view: BookmarkView) = view.opened

        @BindingAdapter("openedAttrChanged")
        @JvmStatic
        fun setListeners(
            view: BookmarkView,
            attrChange: InverseBindingListener
        ) {
            view.setOnClickListener { attrChange.onChange() }
        }
    }
}
