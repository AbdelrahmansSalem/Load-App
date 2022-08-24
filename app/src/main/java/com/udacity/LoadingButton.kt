package com.udacity

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var valueAnimator: ValueAnimator = ValueAnimator()
    private var buttonColor: Int
    private var textColor: Int
    private var progress: Double = 0.0
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    init {

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0,
            0
        )

        buttonColor = attr.getColor(
            R.styleable.LoadingButton_buttonColor,
            ContextCompat.getColor(context, R.color.colorPrimary)
        )

        textColor = attr.getColor(
            R.styleable.LoadingButton_textColor,
            ContextCompat.getColor(context, android.R.color.background_dark)
        )
        valueAnimator = AnimatorInflater.loadAnimator(
            context, R.animator.animation
        ) as ValueAnimator

        valueAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener() {
            progress = (it.animatedValue as Float).toDouble()

            invalidate()

        })



    }

    private val paint = Paint().apply {
        isAntiAlias=true
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
    }


    override fun performClick(): Boolean {
        super.performClick()
        if (buttonState == ButtonState.Completed)
            buttonState = ButtonState.Loading
        else{

        }
        valueAnimator.start()
        return true
    }

    private val rect = RectF(
        740f,
        50f,
        810f,
        110f
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }
    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeWidth = 0f
        paint.color = buttonColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)


        if (buttonState == ButtonState.Loading) {
            paint.color = resources.getColor(R.color.downloadingcolor)
            canvas.drawRect(
                0f, 0f,
                (width * (progress / 100)).toFloat(), height.toFloat(), paint
            )
            paint.color =resources.getColor(R.color.circlecoloe)
            canvas.drawArc(rect, 0f, (360 * (progress / 100)).toFloat(), true, paint)
        }

        if (buttonState == ButtonState.Loading){
            var buttonText =  resources.getString(R.string.loading)
            paint.color = textColor
            canvas.drawText(
                buttonText, (width / 2).toFloat(), ((height + 30) / 2).toFloat(),
                paint
            )
            var CountDownTimer=object : CountDownTimer(1000,1){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    buttonState = ButtonState.Completed
                    invalidate()
                }

            }.start()
        }
        else{

            var buttonText = resources.getString(R.string.download)
            paint.color = textColor
            canvas.drawText(
                buttonText, (width / 2).toFloat(), ((height + 30) / 2).toFloat(),
                paint)
        }

    }
}