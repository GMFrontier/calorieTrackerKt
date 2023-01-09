package com.example.tracker_presentation.search

import com.example.tracker_domain.model.TrackableFood

data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = true,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = emptyList()
) {
}