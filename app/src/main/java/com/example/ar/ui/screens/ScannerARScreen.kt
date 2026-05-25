package com.example.ar.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.ar.R

import com.example.ar.ui.components.StepCard
import com.example.ar.ui.components.CategoryItem
import com.example.ar.ui.components.BottomMenu
import com.example.ar.ui.components.TopBar

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController

@Composable
fun ScannerARScreen(
    navController: NavController,
    onNavigateToAR: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToCatalogue: () -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    val customBlue = Color(0xFF1A0BE9)
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner

    val gradient = Brush.horizontalGradient(
        listOf(Color(0xFF1A0BE9), Color(0xFF6C63FF))
    )



    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (!hasCameraPermission) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Veuillez autoriser la caméra",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        } else {


            AndroidView(
                factory = { context ->
                    val previewView = androidx.camera.view.PreviewView(context)

                    val cameraProviderFuture = androidx.camera.lifecycle.ProcessCameraProvider.getInstance(context)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        val preview = androidx.camera.core.Preview.Builder().build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)

                        val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }, ContextCompat.getMainExecutor(context))

                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.TopStart)
            ) {

                TopBar(
                    onMenuClick = { isMenuOpen = !isMenuOpen },
                    onLogoClick = { onNavigateHome() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF6C63FF))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Annuler", color = Color.White, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        append("Scanner la pièce et ")
                        withStyle(style = SpanStyle(color = customBlue, fontWeight = FontWeight.Bold)) {
                            append("placez")
                        }
                        append("\nvos meubles en ")
                        withStyle(style = SpanStyle(color = customBlue, fontWeight = FontWeight.Bold)) {
                            append("temps réel")
                        }
                    },
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }


            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 160.dp)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StepCard("1", "Scannez\nlentement", R.drawable.ic_camera)
                    StepCard("2", "Détectez\nle sol", R.drawable.ic_cube)
                    StepCard("3", "Placez\nvotre meuble", R.drawable.ic_heart)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(gradient)
                        .shadow(10.dp, RoundedCornerShape(30.dp))
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        "Sol détecté\nChoisissez un meuble à placer",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(1.dp, Color.Blue.copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Surface détectée avec succès",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .clip(RoundedCornerShape(40.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) { CategoryItem("", R.drawable.salon) }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) { CategoryItem("", R.drawable.chaise) }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) { CategoryItem("", R.drawable.table1) }
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) { CategoryItem("", R.drawable.table2) }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(gradient)
                        .shadow(10.dp, RoundedCornerShape(30.dp))
                        .clickable {
                            onNavigateToAR()
                        }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text("Placez ce meuble en AR →", color = Color.White)
                }
            }

            if (isMenuOpen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable { isMenuOpen = false }
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(220.dp)
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    Text("SmartHome", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Accueil", modifier = Modifier.clickable { isMenuOpen = false; onNavigateHome() })
                    Text("Catalogue", modifier = Modifier.clickable { isMenuOpen = false; onNavigateToCatalogue() })
                    Text("Mes Projets", modifier = Modifier.clickable { isMenuOpen = false; onNavigateToProjects() })
                    Text("Profil", modifier = Modifier.clickable { isMenuOpen = false; onNavigateToProfile() })
                }
            }

            BottomMenu(
                navController = navController,
                currentRoute = "scanner",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.9f)
            )
        }
    }
}