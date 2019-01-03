/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.pointsgenerator.points

data class SimplePoint(
    val xCoordinate: Int,
    val yCoordinate: Int
) : ChartPoint {

    override fun getX(): Int {
        return xCoordinate
    }

    override fun getY(): Int {
        return yCoordinate
    }
}
