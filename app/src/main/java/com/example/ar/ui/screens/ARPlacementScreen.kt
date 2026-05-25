package com.example.ar.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.ar.ui.components.TopBar
import com.example.ar.ui.components.BottomMenu
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import com.google.ar.core.HitResult

@Composable
fun ARPlacementScreen(
    navController: NavController,
    onNavigateHome: () -> Unit
) {
    val context = LocalContext.current
    var isMenuOpen by remember { mutableStateOf(false) }

    var modelNode by remember { mutableStateOf<ArModelNode?>(null) }

    LaunchedEffect(modelNode) {
        modelNode?.loadModelGlb(
            context = context,
            glbFileLocation = "models/sofa.glb",
            autoAnimate = true,
            scaleToUnits = 0.7f
        )
    }

    val gradient = Brush.horizontalGradient(listOf(Color(0xFF3D5AFE), Color(0xFF6C63FF)))

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                ArSceneView(ctx).apply {
                    val node = ArModelNode(engine, PlacementMode.PLANE_HORIZONTAL)
                    addChild(node)
                    modelNode = node

                    onTapAr = { _: HitResult, _ ->
                        node.anchor()
                    }
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            TopBar(
                onMenuClick = { isMenuOpen = !isMenuOpen },
                onLogoClick = { onNavigateHome() }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SmallBtn(text = "Annuler", gradient = gradient) {
                    modelNode?.detachAnchor()
                }

                SmallBtn(text = "Déplacer", gradient = gradient) {

                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "وجه الكاميرا للأرض واضغط لوضع السوفا",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate("projects")
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A)),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text("Valider Placement", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        ARViewfinder(Color(0xFF3D5AFE))

        if (isMenuOpen) {
            ARDrawer(
                onHome = { isMenuOpen = false; onNavigateHome() },
                onCat = { isMenuOpen = false; navController.navigate("catalogue") },
                onProj = { isMenuOpen = false; navController.navigate("projects") },
                onProf = { isMenuOpen = false; navController.navigate("profile") },
                onClose = { isMenuOpen = false }
            )
        }

        BottomMenu(
            navController = navController,
            currentRoute = "ar",
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
        )
    }
}

@Composable
fun SmallBtn(text: String, gradient: Brush, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ARViewfinder(color: Color) {
    Box(
        modifier = Modifier.fillMaxWidth(0.8f).height(200.dp).graphicsLayer(alpha = 0.5f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = color,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
            )
        }
    }
}

@Composable
fun ARDrawer(onHome: () -> Unit, onCat: () -> Unit, onProj: () -> Unit, onProf: () -> Unit, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.5f)).clickable { onClose() }) {
        Column(modifier = Modifier.fillMaxHeight().width(260.dp).background(Color.White).padding(24.dp)) {
            Text("SmartHome", fontWeight = FontWeight.Black, fontSize = 24.sp, color = Color(0xFF3D5AFE))
            Spacer(modifier = Modifier.height(40.dp))
            Text("Accueil", modifier = Modifier.fillMaxWidth().clickable { onHome() }.padding(12.dp))
            Text("Catalogue", modifier = Modifier.fillMaxWidth().clickable { onCat() }.padding(12.dp))
            Text("Projets", modifier = Modifier.fillMaxWidth().clickable { onProj() }.padding(12.dp))
            Text("Profil", modifier = Modifier.fillMaxWidth().clickable { onProf() }.padding(12.dp))
        }
    }
}
