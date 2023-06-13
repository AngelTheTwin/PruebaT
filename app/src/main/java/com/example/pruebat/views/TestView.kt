package com.example.pruebat.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pruebat.ui.theme.PruebaTTheme

@Composable
fun TestView(idActivity: Int) {
	Column(
		modifier = Modifier.fillMaxHeight().fillMaxWidth(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = idActivity.toString())
	}

}

@Preview (showSystemUi = true)
@Composable
fun previewTestView () {
	PruebaTTheme {
		TestView(idActivity = 1)
	}
}