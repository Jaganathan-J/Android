package com.example.fitlife.ui.feature.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.fitlife.data.WorkoutRepositoryImpl
import com.example.fitlife.domain.ExerciseCategory
import com.example.fitlife.ui.navigation.Screen
import com.example.fitlife.ui.theme.BrandLavender
import com.example.fitlife.ui.theme.BrandPurple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor() : ViewModel() {
    // Direct instantiation for demo simplicity vs injecting repo interface
    val categories = WorkoutRepositoryImpl().getCategories()
}

@Composable
fun CategoriesScreen(navController: NavController, viewModel: CategoriesViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
        }
        Text(
            "Exercises", 
            fontSize = 32.sp, 
            fontWeight = FontWeight.Bold, 
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 24.dp)
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.categories.size) { index ->
                CategoryCard(viewModel.categories[index]) {
                    navController.navigate(Screen.WorkoutList.createRoute(it.id))
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: ExerciseCategory, onClick: (ExerciseCategory) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(BrandLavender)
            .clickable { onClick(category) }
            .padding(24.dp)
    ) {
        Icon(
            Icons.Default.FitnessCenter, 
            contentDescription = null, 
            tint = BrandPurple, 
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(category.name, fontWeight = FontWeight.Bold, color = BrandPurple)
    }
}