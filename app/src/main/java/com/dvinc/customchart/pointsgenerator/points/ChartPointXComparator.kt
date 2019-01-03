/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.pointsgenerator.points

class ChartPointXComparator : Comparator<ChartPoint> {

    override fun compare(o1: ChartPoint, o2: ChartPoint): Int {
        return when {
            o1.getX() > o2.getX() -> 1
            o1.getX() < o2.getX() -> -1
            else -> 0
        }
    }
}
