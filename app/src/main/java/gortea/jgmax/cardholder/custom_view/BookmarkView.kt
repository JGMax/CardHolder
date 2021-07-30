package gortea.jgmax.cardholder.custom_view

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
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.utils.toPx
import kotlin.math.abs

class BookmarkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

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
    private var triangleHeight: Float = -1f
    private var closeLength = -1f
    private var openLength = -1f
    var isOpened = false
        private set
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
                styledAttrs.getFloat(R.styleable.BookmarkView_triangle_height, triangleHeight)
            closeLength = styledAttrs.getFloat(R.styleable.BookmarkView_close_length, closeLength)
            openLength = styledAttrs.getFloat(R.styleable.BookmarkView_open_length, openLength)
            isOpened = styledAttrs.getBoolean(R.styleable.BookmarkView_opened, false)
            currentLength = if (isOpened) 1f else 0f

            icTopTransition = styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_top_transition, 0)
            icLeftTransition = styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_left_transition, 0)
            icRightTransition = styledAttrs.getDimensionPixelSize(R.styleable.BookmarkView_ic_right_transition, 0)
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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val targetViewLength = getTargets().first

        if (openLength == -1f)
            openLength = targetViewLength
        if (closeLength == -1f)
            closeLength = targetViewLength / 2f

        if (triangleHeight == -1f)
            triangleHeight = openLength.coerceAtMost(closeLength) / 3f
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

    fun setTriangleHeight(height: Float) {
        if (height < closeLength.coerceAtMost(openLength)) {
            triangleHeight = height
            invalidate()
        }
    }

    fun setCloseLength(length: Float) {
        val targetLength = getTargets().first
        if (length !in 0f..targetLength) return

        val proportion = (openLength - length) / (openLength - closeLength)
        currentLength *= proportion
        closeLength = length
        invalidate()
    }

    fun setOpenLength(length: Float) {
        val targetLength = getTargets().first
        if (length !in 0f..targetLength) return

        val proportion = (length - closeLength) / (openLength - closeLength)
        currentLength *= proportion
        openLength = length
        invalidate()
    }

    fun isOpened(opened: Boolean, withAnimation: Boolean = true) {
        if (isOpened == opened) return
        isOpened = opened
        stateListener?.onStateChange(isOpened)

        val startValue: Float
        val endValue: Float
        if (isOpened) {
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
            invalidate()
        }
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
            x = (width - length + icLeftTransition + triangleHeight + defaultOffsetFromTriangle).toInt()
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
                moveTo(targetViewLength, 0f)
                lineTo(targetViewLength - length, 0f)
                lineTo(targetViewLength - length + triangleHeight, targetViewWidth / 2f)
                lineTo(targetViewLength - length, targetViewWidth)
                lineTo(targetViewLength, targetViewWidth)
            } else {
                moveTo(0f, 0f)
                lineTo(0f, targetViewLength - length)
                lineTo(targetViewWidth / 2f, targetViewLength - length - triangleHeight)
                lineTo(targetViewWidth, targetViewLength - length)
                lineTo(targetViewWidth, 0f)
            }
            close()
        }
    }

    private fun getTargets(): Pair<Float, Float> {
        val targetViewLength: Float
        val targetViewWidth: Float
        if (orientation == Orientation.HORIZONTAL) {
            targetViewWidth = height.toFloat()
            targetViewLength = width.toFloat()
        } else {
            targetViewWidth = width.toFloat()
            targetViewLength = height.toFloat()
        }
        return targetViewLength to targetViewWidth
    }

    override fun performClick(): Boolean {
        isOpened(!isOpened)
        return super.performClick()
    }

    fun setStateChangeListener(listener: StateChangeListener) {
        this.stateListener = listener
    }

    interface StateChangeListener {
        fun onStateChange(isOpened: Boolean)
    }
}
