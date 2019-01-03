/*
 * Copyright (c) 2019 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.customchart.pointsgenerator.points

class ChartPointYComparator : Comparator<ChartPoint> {

    override fun compare(o1: ChartPoint, o2: ChartPoint): Int {
        return when {
            o1.getY() > o2.getY() -> 1
            o1.getY() < o2.getY() -> -1
            else -> 0
        }
    }
}
