package com.example.pruebat.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RecompensasScreen() {
	Column(
		verticalArrangement = Arrangement.Center,
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colors.background),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Recompensas")
	}
}