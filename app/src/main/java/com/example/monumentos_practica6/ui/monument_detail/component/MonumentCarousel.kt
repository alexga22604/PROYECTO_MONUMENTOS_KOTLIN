package com.example.monumentos_practica6.ui.monument_detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.theme.CarouselArrowContent
import kotlinx.coroutines.launch

@Composable
fun MonumentCarousel(
    images: List<String>
) {
    if (images.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { images.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (images.size > 1) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp),
                enabled = pagerState.currentPage > 0
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.previous_image),
                    tint = CarouselArrowContent
                )
            }

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < images.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp),
                enabled = pagerState.currentPage < images.size - 1
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(id = R.string.next_image),
                    tint = CarouselArrowContent
                )
            }
        }
    }
}