package com.example.mobile_client_app.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun <T> InfiniteAutoScrollLazyRow(
    items: List<T>,
    scrollIntervalMs: Long = 3000L,
    itemCard: @Composable (item: T) -> Unit,
) {
    if (items.isEmpty()) return

    val listState = rememberLazyListState()
    val infiniteItems = remember(items) { items + items + items }
    var currentIndex by remember { mutableIntStateOf(items.size) }

    LaunchedEffect(infiniteItems.size) {
        // Initially scroll to the middle section without animation
        listState.scrollToItem(currentIndex)

        while (true) {
            delay(scrollIntervalMs)
            currentIndex++

            // Animate to next item
            listState.animateScrollToItem(
                index = currentIndex,
                scrollOffset = 0
            )

            // Reset to beginning of middle section when we reach the end
            if (currentIndex >= items.size * 2) {
                delay(500) // Small delay to see the animation complete
                currentIndex = items.size
                listState.scrollToItem(currentIndex) // Jump back without animation
            }
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(infiniteItems) { item ->
            itemCard(item)
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun <T> SmartAutoScrollLazyRow(
    items: List<T>,
    scrollIntervalMs: Long = 3000L,
    itemCard: @Composable (item: T) -> Unit,
) {
    val listState = rememberLazyListState()
    var currentIndex by remember { mutableIntStateOf(0) }
    var isUserScrolling by remember { mutableStateOf(false) }
    var lastUserInteraction by remember { mutableLongStateOf(0L) }

    // Detect user scrolling
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            isUserScrolling = true
            lastUserInteraction = Clock.System.now().epochSeconds
        } else {
            delay(1000) // Wait 1 second after scrolling stops
            isUserScrolling = false
        }
    }

    // Auto-scroll effect
    LaunchedEffect(items.size, isUserScrolling) {
        if (items.isEmpty()) return@LaunchedEffect

        while (true) {
            delay(scrollIntervalMs)

            // Don't auto-scroll if user recently interacted
            if (!isUserScrolling && Clock.System.now().epochSeconds - lastUserInteraction > 2000) {
                currentIndex = (currentIndex + 1) % items.size
                listState.animateScrollToItem(
                    index = currentIndex,
                    scrollOffset = 0
                )
            }
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items) { item ->
            itemCard(item)
        }
    }
}