/*
 *    Copyright 2023, Petr Laštovička as Lasta apps, All rights reserved
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

package cz.lastaapps.menza.features.settings.domain.usecase.initialmenza

import cz.lastaapps.core.domain.UCContext
import cz.lastaapps.core.domain.UseCase
import cz.lastaapps.menza.features.settings.data.SettingsStore
import cz.lastaapps.menza.features.settings.data.initialMenza
import cz.lastaapps.menza.features.settings.data.latestMenza
import cz.lastaapps.menza.features.settings.data.preferredMenza
import cz.lastaapps.menza.features.settings.domain.model.InitialMenza.Ask
import cz.lastaapps.menza.features.settings.domain.model.InitialMenza.Remember
import cz.lastaapps.menza.features.settings.domain.model.InitialMenza.Specific
import kotlinx.coroutines.flow.combine

class GetInitialMenzaUC internal constructor(
    context: UCContext,
    private val store: SettingsStore,
) : UseCase(context) {
    suspend operator fun invoke() = launch {
        combine(
            store.initialMenza,
            store.preferredMenza,
            store.latestMenza,
        ) { mode, preferred, latest ->
            when (mode) {
                Ask -> null
                Remember -> latest
                Specific -> preferred
            }
        }
    }
}