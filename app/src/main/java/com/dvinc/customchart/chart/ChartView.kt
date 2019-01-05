/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.chart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.dvinc.customchart.pointsgenerator.points.ChartPoint
import com.dvinc.customchart.pointsgenerator.points.ChartPointXComparator
import com.dvinc.customchart.pointsgenerator.points.ChartPointYComparator
import com.dvinc.customchart.pointsgenerator.points.SimplePoint

class ChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {

        private const val TAG = "ChartView"

        private const val POINT_RADIUS = 4f

        private const val CORNER_RADIUS = 10f
    }

    private var pointPaint: Paint = Paint()

    private var linePaint: Paint = Paint()

    private val chartBorderPaint = Paint()

    private val gradientPaint = Paint()

    private val gradientPath = Path()

    private val cornerPathEffect = CornerPathEffect(CORNER_RADIUS)

    private var points: List<ChartPoint> = mutableListOf()

    init {
        pointPaint.color = Color.RED
        pointPaint.strokeWidth = 8f
        pointPaint.isAntiAlias = true

        linePaint.color = Color.BLACK
        linePaint.strokeWidth = 4f
        linePaint.isAntiAlias = true
        linePaint.style = Paint.Style.STROKE
        linePaint.pathEffect = cornerPathEffect

        chartBorderPaint.color = Color.BLACK
        chartBorderPaint.strokeWidth = 4f

        gradientPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw is called")
        canvas?.let {
            drawChart(it)
            drawChartBorder(it)
            drawChartGradient(it)
            drawPoints(it)
        }
    }

    fun drawPoints(rawPoints: List<ChartPoint>) {
        val convertedPoints: MutableList<ChartPoint> = mutableListOf()

        val minX = rawPoints.minWith(ChartPointXComparator())?.getX() ?: return
        val maxX = rawPoints.maxWith(ChartPointXComparator())?.getX() ?: return

        val minY = rawPoints.minWith(ChartPointYComparator())?.getY() ?: return
        val maxY = rawPoints.maxWith(ChartPointYComparator())?.getY() ?: return

        val rangeX = maxX - minX
        val rangeY = maxY - minY
        val multiplierX = width.div(rangeX.toDouble())
        val multiplierY = height.div(rangeY.toDouble())

        rawPoints.forEach {
            convertedPoints.add(
                SimplePoint(
                    ((it.getX() - minX) * multiplierX).toFloat(),
                    ((it.getY() - minY) * multiplierY).toFloat()
                )
            )
        }

        convertedPoints.sortBy { it.getX() }

        points = convertedPoints
        invalidate()
    }

    private fun drawPoints(canvas: Canvas) {
        points.forEach { point ->
            val x = point.getX()
            val y = point.getY()
            canvas.drawCircle(x, y, POINT_RADIUS, pointPaint)
        }
    }

    private fun drawChart(canvas: Canvas) {
        val linePath = Path()
        if (points.isNotEmpty()) {
            points.forEachIndexed { index, chartPoint ->
                with(chartPoint) {
                    if (index == 0) linePath.moveTo(getX(), getY())
                    else linePath.lineTo(getX(), getY())
                }
            }
        }
        canvas.drawPath(linePath, linePaint)
    }

    private fun drawChartBorder(canvas: Canvas) {
        val initialX = 0f
        val initialY = 0f
        val maxX = width.toFloat()
        val maxY = height.toFloat()

        canvas.drawLine(initialX, initialY, maxX, initialY, chartBorderPaint)
        canvas.drawLine(maxX, initialY, maxX, maxY, chartBorderPaint)
        canvas.drawLine(maxX, maxY, initialX, maxY, chartBorderPaint)
        canvas.drawLine(initialX, maxY, initialX, initialY, chartBorderPaint)
    }

    private fun drawChartGradient(canvas: Canvas) {
        val initialX = 0f
        val initialY = 0f
        val maxX = width.toFloat()
        val maxY = height.toFloat()

        val gradient = LinearGradient(initialX, initialY, initialX, height.toFloat(), Color.BLUE, Color.WHITE, Shader.TileMode.CLAMP)
        gradientPaint.shader = gradient

        gradientPath.reset()
        gradientPath.moveTo(initialX, maxY)

        points.forEach {
            gradientPath.lineTo(it.getX(), it.getY())
        }

        gradientPath.lineTo(maxX, maxY)
        gradientPath.moveTo(initialX, maxY)

        canvas.drawPath(gradientPath, gradientPaint)
    }
}
