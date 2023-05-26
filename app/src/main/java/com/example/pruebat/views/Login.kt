package com.example.pruebat.views

import android.app.Application
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.pruebat.Screen
import com.example.pruebat.data.User
import com.example.pruebat.data.UserType
import com.example.pruebat.ui.theme.PruebaTTheme

@Composable
@ExperimentalFoundationApi
fun LoginScreen(
	viewModel: UsersViewModel,
	navController: NavHostController
) {
	val users by viewModel.usuarios.collectAsState(initial = listOf())
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = "Quién eres?",
			style = MaterialTheme.typography.h4,
			modifier = Modifier.padding(bottom = 16.dp)
		)
		Row {
			for(user in users) {
				UserCard(user) { user ->
					navController.navigate(Screen.Home.route)
				}
			}
			if (users.size < 2) {
				UserCard(User(0, "", "", UserType.USUARIO, "", "")) {
					navController.navigate(Screen.Register.route)
				}
			}
		}
	}
}




@Composable
fun UserCard(user: User, onClickAction: (User) -> Unit) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.padding(vertical = 16.dp, horizontal = 4.dp)
			.clickable { onClickAction(user) }
	) {
		val imagePainter: Painter? = if (user.imageUrl.isNotBlank()) {
			rememberAsyncImagePainter(user.imageUrl)
		} else {
			null
		}
		Box(
			modifier = Modifier
				.size(120.dp)
				.aspectRatio(1f)
				.padding(4.dp)
				.background(
					color = Color.LightGray,
					shape = RoundedCornerShape(12.dp)
				),
			contentAlignment = Alignment.Center
		) {
			if (user.username.isEmpty()) {
				Icon(
					Icons.Default.AddCircle,
					contentDescription = "Add user icon",
					tint = MaterialTheme.colors.onSurface,
					modifier = Modifier
						.size(64.dp)
				)
			} else if (user.imageUrl.isBlank()) {
				Icon(
					Icons.Default.Person,
					contentDescription = "Default user profile picture",
					tint = MaterialTheme.colors.onSurface,
					modifier = Modifier
						.size(64.dp)
				)
			} else {
				AsyncImage(
					model = user.imageUrl,
					contentDescription = "User image",
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.clip(RoundedCornerShape(12.dp)),
				)
			}
		}
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = if (user.username.isEmpty()) "Add User" else user.username ,
			style = MaterialTheme.typography.h6
		)
	}
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	PruebaTTheme {
		LoginScreen(
			viewModel = UsersViewModel(LocalContext.current.applicationContext as Application),
			navController = rememberNavController()
		)
	}
}


class UsersViewModelFactory(val application: Application): ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return UsersViewModel(application) as T
	}
}