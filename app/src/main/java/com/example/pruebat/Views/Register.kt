package com.example.pruebat.Views

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pruebat.data.User
import com.example.pruebat.data.UserType
import com.example.pruebat.ui.theme.PruebaTTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pruebat.MainViewModel
import com.example.pruebat.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterScreen(
	mainViewModel: MainViewModel = hiltViewModel(),
	viewModel: UsersViewModel,
	navController: NavController
) {
	val context = LocalContext.current
	// State for the username field
	var user: User by remember {
		mutableStateOf(User(
			id = 0,
			nombre = "",
			username = "",
			password = "",
			type = UserType.USUARIO,
			imageUrl = ""
		))
	}

	var showDropdownMenu by remember {
		mutableStateOf(false)
	}

	val userTypes = UserType.values()


	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent(),
		onResult = { uri: Uri? ->
			// Handle the selected image URI here
			println(uri)
			user = user.copy(imageUrl = uri.toString())
		}
	)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colors.background),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		val tipoUsuario = if (mainViewModel.hasTutorRegistered.value) UserType.USUARIO else UserType.TUTOR

		Text(
			text = "Registrarse (${tipoUsuario.name})",
			fontSize = MaterialTheme.typography.h4.fontSize
		)
		// Image picker
		IconButton(
			modifier = Modifier
				.size(height = 96.dp, width = 96.dp)
				.clip(RoundedCornerShape(12.dp)),
			onClick = { launcher.launch("image/*") }
		) {
			if (user.imageUrl == "") {
				Icon(
					Icons.Default.Person,
					contentDescription = "Profile Picture",
					modifier = Modifier.size(96.dp),
				)
			} else {
				AsyncImage(
					model = user.imageUrl,
					contentDescription = "Imagen",
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.clip(RoundedCornerShape(12.dp))
				)
			}

		}

		// Username field
		OutlinedTextField(
			value = user.username,
			onValueChange = { user = user.copy(username = it)},
			label = { Text("Username") },
		)
		// Name field
		OutlinedTextField(
			value = user.nombre,
			onValueChange = { user = user.copy(nombre = it)},
			label = { Text("Nombre") },
		)
		// password field
		if (tipoUsuario == UserType.TUTOR){
			OutlinedTextField(
				value = user.password,
				visualTransformation = PasswordVisualTransformation(),
				onValueChange = { user = user.copy(password = it)},
				label = { Text("Contraseña") },
			)
		}

		// Submit button
		Button(
			onClick = {
				viewModel.saveUser(user.copy(type = tipoUsuario))
				Toast.makeText(context, "Guardado!", Toast.LENGTH_SHORT).show()
				mainViewModel.saveHasTutorRegistered(true)
				navController.navigate(Screen.Login.route)
			},
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			Text("Create Account")
		}
	}
}

@Preview
@Composable
fun RegisterPreview() {
	PruebaTTheme {
		RegisterScreen(
			viewModel = UsersViewModel(LocalContext.current.applicationContext as Application),
			navController = rememberNavController()
		)
	}
}