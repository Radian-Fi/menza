/*
 *    Copyright 2022, Petr Laštovička as Lasta apps, All rights reserved
 *
 *     This file is part of Menza.
 *
 *     Menza is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Menza is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Menza.  If not, see <https://www.gnu.org/licenses/>.
 */

package cz.lastaapps.menza.ui.info

import android.os.Build
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import cz.lastaapps.entity.LocalTime
import kotlinx.datetime.DayOfWeek
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@Composable
fun OpeningHoursUI(
    data: List<OpeningLocation>,
    modifier: Modifier = Modifier,
    @Suppress("DEPRECATION")
    locale: Locale = LocalConfiguration.current.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) it.locales[0] else it.locale
    },
    use24: Boolean = DateFormat.is24HourFormat(LocalContext.current)
) {
    Column(modifier) {
        data.forEach {
            OpeningHoursLocationUI(data = it, locale, use24)
        }
    }
}

@Composable
fun OpeningHoursLocationUI(
    data: OpeningLocation,
    locale: Locale,
    use24: Boolean,
    modifier: Modifier = Modifier,
) {
    val formatter = remember(use24) {
        val patter = if (use24) "H:mm" else "h:mm a"
        DateTimeFormatter.ofPattern(patter)
    }

    Surface(color = MaterialTheme.colorScheme.secondaryContainer, modifier = modifier) {
        Column {
            Text(data.name)
            Row() {
                Column() {
                    data.list.forEach {
                        val start = it.startDay.getDisplayName(TextStyle.SHORT, locale)
                        if (it.startDay != it.endDay) {
                            val end = it.endDay.getDisplayName(TextStyle.SHORT, locale)
                            Text("$start - $end")
                        } else {
                            Text(start)
                        }
                    }
                }
                Column() {
                    data.list.forEach {
                        val start = it.startTime.toJavaLocalDate().format(formatter)
                        val end = it.endTime.toJavaLocalDate().format(formatter)
                        Text("$start - $end")
                    }
                }
                Column() {
                    data.list.forEach {
                        Text(it.comment ?: "")
                    }
                }
            }
        }
    }
}

private fun LocalTime.toJavaLocalDate(): java.time.LocalTime {
    return java.time.LocalTime.of(hours, minutes, seconds)
}

data class OpeningInterval(
    val startDay: DayOfWeek,
    val endDay: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val comment: String?,
)

data class OpeningLocation(val name: String, val list: List<OpeningInterval>)