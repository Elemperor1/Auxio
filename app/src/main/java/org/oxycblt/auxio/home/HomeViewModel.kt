/*
 * Copyright 2024 The Auxio Project
 *
 * This file is part of Auxio.
 *
 * Auxio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Auxio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.oxycblt.auxio.home

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import org.oxycblt.auxio.home.tabs.Tab
import org.oxycblt.auxio.list.ListSettings
import org.oxycblt.auxio.list.adapter.UpdateInstructions
import org.oxycblt.auxio.list.ui.Sort
import org.oxycblt.auxio.list.update.Event
import org.oxycblt.auxio.list.update.MutableEvent

@Parcelize
sealed interface HomeTab : Tab, Parcelable {
    @Parcelize
    data object Songs : HomeTab

    @Parcelize
    data object Albums : HomeTab

    @Parcelize
    data object Artists : HomeTab

    @Parcelize
    data object Playlists : HomeTab
}

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    val listSettings: ListSettings,
) : ViewModel() {
    private val _tab = MutableStateFlow<HomeTab>(HomeTab.Songs)
    val tab: StateFlow<HomeTab>
        get() = _tab

    private val _empty = MutableEvent<UpdateInstructions>()
    val empty: Event<UpdateInstructions>
        get() = _empty

    private val _playlistInstructions = MutableEvent<UpdateInstructions>()
    /** Instructions for how to update [playlistList] in the UI. */
    val playlistInstructions: Event<UpdateInstructions>
        get() = _playlistInstructions

    /** The current [Sort] used for [playlistList]. */
    val playlistSort: Sort
        get() = listSettings.playlistSort
}
