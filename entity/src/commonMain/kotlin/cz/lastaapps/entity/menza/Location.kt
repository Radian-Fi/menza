/*
 *    Copyright 2021, Petr Laštovička as Lasta apps, All rights reserved
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

package cz.lastaapps.entity.menza

import io.kotest.matchers.string.shouldNotBeBlank

data class Location(val long: String, val lat: String) {

    init {
        long.shouldNotBeBlank()
        lat.shouldNotBeBlank()
    }

    fun saveToString() : String {
        return "$long:$lat"
    }

    companion object {
        fun restoreFromString(str: String): Location {
            val split = str.split(",")
            return Location(split[0], split[1]);
        }
    }
}