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

package cz.lastaapps.menza.api.agata.data

import cz.lastaapps.core.domain.Outcome
import cz.lastaapps.menza.api.agata.api.DishApi
import cz.lastaapps.menza.api.agata.domain.SyncProcessor
import cz.lastaapps.menza.api.agata.domain.model.SyncJobNoCache
import cz.lastaapps.menza.api.agata.domain.model.common.DishCategory
import cz.lastaapps.menza.api.agata.domain.model.mapers.toDomain
import cz.lastaapps.menza.api.agata.domain.repo.DishListRepo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class DishListRepoStrahovImpl(
    private val dishApi: DishApi,
    private val processor: SyncProcessor,
) : DishListRepo {

    private val dishList = MutableStateFlow<ImmutableList<DishCategory>>(persistentListOf())

    override fun getData(): Flow<ImmutableList<DishCategory>> = dishList

    private val job = SyncJobNoCache(
        fetchApi = { dishApi.getStrahov() },
        store = { data ->
            dishList.value = data.toDomain().toImmutableList()
        }
    )

    override suspend fun sync(): Outcome<Unit> = processor.run(job)
}