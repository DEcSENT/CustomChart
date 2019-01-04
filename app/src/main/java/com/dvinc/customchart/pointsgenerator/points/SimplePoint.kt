/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.pointsgenerator.points

data class SimplePoint(
    val xCoordinate: Float,
    val yCoordinate: Float
) : ChartPoint {

    override fun getX(): Float {
        return xCoordinate
    }

    override fun getY(): Float {
        return yCoordinate
    }
}
