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

package cz.lastaapps.menza.ui.dests.settings.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.StateFlow

private val systemThemeKey = booleanPreferencesKey("systemTheme")

val SettingsStore.systemTheme: StateFlow<Boolean>
    get() = data.mapState { it[systemThemeKey] ?: true }

suspend fun SettingsStore.setUseSystemTheme(useSystem: Boolean) {
    edit { it[systemThemeKey] = useSystem }
}
