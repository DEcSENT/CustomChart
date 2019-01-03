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

    private var points: List<ChartPoint> = mutableListOf()

    init {
        pointPaint.color = Color.RED
        pointPaint.strokeWidth = 10f

        pointPaint.color = Color.BLACK
        pointPaint.strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw is called")
        canvas?.let {
            drawPoints(it)
            drawLines(it)
        }
    }

    fun drawPoints(rawPoints: List<ChartPoint>) {
        val convertedPoints: MutableList<ChartPoint> = mutableListOf()
        val maxX = rawPoints.maxWith(ChartPointXComparator())?.getX() ?: width
        val maxY = rawPoints.maxWith(ChartPointYComparator())?.getY() ?: height

        val multiplierX = width / maxX
        val multiplierY = height / maxY

        rawPoints.forEach {
            convertedPoints.add(
                SimplePoint(
                    checkForZeroCoordinate(it.getX()) * multiplierX,
                    checkForZeroCoordinate(it.getY()) * multiplierY
                )
            )
        }

        convertedPoints.sortBy { it.getX() }

        points = convertedPoints
        invalidate()
    }

    private fun drawPoints(canvas: Canvas) {
        points.forEach { point ->
            val x = point.getX().toFloat()
            val y = point.getY().toFloat()
            canvas.drawPoint(x, y, pointPaint)
        }
    }

    private fun drawLines(canvas: Canvas) {
        (0 until points.size - 1).forEach { index ->
            val firstPoint = points[index]
            val secondPoint = points[index + 1]

            canvas.drawLine(
                firstPoint.getX().toFloat(),
                firstPoint.getY().toFloat(),
                secondPoint.getX().toFloat(),
                secondPoint.getY().toFloat(),
                linePaint
            )
        }
    }

    private fun checkForZeroCoordinate(coordinate: Int): Int {
        return if (coordinate <= 0) {
            1
        } else {
            coordinate
        }
    }
}
