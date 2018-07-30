package iii_conventions

import java.util.Calendar

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        this.year > other.year -> 1
        this.year < other.year -> -1
        this.month > other.month -> 1
        this.month < other.month -> -1
        this.dayOfMonth > other.dayOfMonth -> 1
        this.dayOfMonth < other.dayOfMonth -> -1
        else -> 0
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun MyDate.plus(interval: TimeInterval): MyDate {
    return this.addTimeIntervals(interval, 1)
}

data class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)

operator fun TimeInterval.times(rhs: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, rhs)

operator fun MyDate.plus(repeatedInterval: RepeatedTimeInterval): MyDate {
   return this.addTimeIntervals(repeatedInterval.interval, repeatedInterval.times)
}

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current: MyDate = start

        override fun hasNext(): Boolean = current <= endInclusive

        override fun next(): MyDate {
            val result = current
            current = current.nextDay()
            return result
        }
    }

    operator fun contains(d: MyDate) = d >= start && d <= endInclusive
}
