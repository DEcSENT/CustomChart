/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
    }

    private var pointPaint: Paint = Paint()

    private var linePaint: Paint = Paint()

    private val chartBorderPaint = Paint()

    private var points: List<ChartPoint> = mutableListOf()

    init {
        pointPaint.color = Color.RED
        pointPaint.strokeWidth = 10f

        pointPaint.color = Color.BLACK
        pointPaint.strokeWidth = 4f

        chartBorderPaint.color = Color.BLACK
        chartBorderPaint.strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw is called")
        canvas?.let {
            drawPoints(it)
            drawLines(it)
            drawChartBorder(it)
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
            canvas.drawPoint(x, y, pointPaint)
        }
    }

    private fun drawLines(canvas: Canvas) {
        (0 until points.size - 1).forEach { index ->
            val firstPoint = points[index]
            val secondPoint = points[index + 1]

            canvas.drawLine(
                firstPoint.getX(),
                firstPoint.getY(),
                secondPoint.getX(),
                secondPoint.getY(),
                linePaint
            )
        }
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
}
