/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.pointsgenerator

import com.dvinc.customchart.pointsgenerator.points.ChartPoint
import com.dvinc.customchart.pointsgenerator.points.SimplePoint
import kotlin.random.Random

class RandomPointsGenerator {

    companion object {

        private const val MIN_X = 0

        private const val MAX_X = 100

        private const val MIN_Y = 30

        private const val MAX_Y = 50

        private const val POINTS_COUNT = 40
    }

    private val random = Random

    fun getRandomPoints(): List<ChartPoint> {
        val points: MutableList<ChartPoint> = mutableListOf()
        (0..POINTS_COUNT).forEach { _ ->
            points.add(getRandomPointInRange(MIN_X, MAX_X, MIN_Y, MAX_Y))
        }
        return points
    }

    private fun getRandomPointInRange(minX: Int, maxX: Int, minY: Int, maxY: Int): ChartPoint {
        val x = random.nextFloat() * (maxX - minX)
        val y = random.nextFloat() * (maxY - minY)
        return SimplePoint(x, y)
    }
}
