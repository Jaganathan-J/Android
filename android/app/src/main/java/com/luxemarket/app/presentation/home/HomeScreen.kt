package com.luxemarket.app.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.luxemarket.app.domain.model.Product
import com.luxemarket.app.presentation.common.SkeletonLoader

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("LuxeMarket", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is HomeUiState.Loading -> LoadingState()
                is HomeUiState.Error -> ErrorState(state.message) { viewModel.fetchFeed() }
                is HomeUiState.Success -> ProductGrid(state.products)
            }
        }
    }
}

@Composable
fun ProductGrid(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8f)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
        Text(
            text = product.priceFormatted,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}

@Composable
fun LoadingState() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) { 
            Column {
                SkeletonLoader(modifier = Modifier.fillMaxWidth().aspectRatio(0.8f))
                Spacer(modifier = Modifier.height(8.dp))
                SkeletonLoader(modifier = Modifier.width(100.dp).height(16.dp))
            }
        }
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}