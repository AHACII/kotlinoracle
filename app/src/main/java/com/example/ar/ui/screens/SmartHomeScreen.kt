package com.example.ar.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.ar.R
import com.example.ar.ui.components.CategoryItem
import com.example.ar.ui.components.SectionTitle
import com.example.ar.ui.components.ProductCard
import com.example.ar.ui.components.BottomMenu
import androidx.compose.ui.draw.scale
import com.example.ar.ui.components.TopBar
import com.example.ar.data.ApiProduct
import com.example.ar.data.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun SmartHomeScreen(
    navController: NavController,
    currentScreen: String,
    onNavigateToScanner: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToCatalogue: (String?) -> Unit,
    onLogout: () -> Unit,
){
    var isMenuOpen by remember { mutableStateOf(false) }
    var products by remember { mutableStateOf<List<ApiProduct>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val defaultProducts = listOf(
        ApiProduct(1, "Chaise Nordique", 1200.0, "chaise", "Salon"),
        ApiProduct(2, "Table Basse", 2500.0, "table1", "Salon"),
        ApiProduct(3, "Sofa Relax", 5000.0, "sofa", "Salon")
    )

    val customBlue = Color(0xFF1A0BE9)
    val customLightBlue = Color(0xFF6C63FF)
    val gradient = Brush.horizontalGradient(listOf(customBlue, customLightBlue))
    val activeColor = Color(0xFF1A0BE9)
    val inactiveColor = Color.Gray

    LaunchedEffect(Unit) {
        try {
            val apiProducts = RetrofitInstance.productApi.getProducts()
            products = if (apiProducts.isEmpty()) defaultProducts else apiProducts
        } catch (e: Exception) {
            products = defaultProducts
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            Box(modifier = Modifier.height(460.dp)) {
                Image(
                    painter = painterResource(R.drawable.sofa),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    alpha = 0.5f
                )

                Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                    TopBar(
                        onMenuClick = { isMenuOpen = !isMenuOpen },
                        onLogoClick = { onNavigateHome() }
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(gradient)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_star),
                                    contentDescription = "AR",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Mode AR Actif", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Visualisez votre", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text(text = "futur intérieur en AR", color = customBlue, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(25.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(gradient)
                                .clickable { onNavigateToScanner() }
                                .padding(horizontal = 32.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(painter = painterResource(R.drawable.ic_camera), contentDescription = "Scanner", tint = Color.White, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Scanner ma pièce", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                                Text("Placez vos meubles en temps réel", color = Color.White, fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            CategoryItem("Salon", R.drawable.sofa, onClick = { onNavigateToCatalogue("Salon") })
                            Box(modifier = Modifier.scale(1.15f)) { 
                                CategoryItem("Chambre", R.drawable.chambre, onClick = { onNavigateToCatalogue("Chambre") }) 
                            }
                            CategoryItem("Bureau", R.drawable.bureau, onClick = { onNavigateToCatalogue("Bureau") })
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(horizontalArrangement = Arrangement.Center) {
                            repeat(5) { index ->
                                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(if (index == 0) customBlue else Color.White))
                                if (index < 4) Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                SectionTitle("Nos recommandations")
                Text(
                    text = "Voir plus",
                    color = customBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToCatalogue(null) }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = customBlue)
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    products.take(5).forEach { product ->
                        val imageRes = when (product.image?.lowercase()) {
                            "chaise" -> R.drawable.chaise
                            "table1" -> R.drawable.table1
                            "table2" -> R.drawable.table2
                            "sofa" -> R.drawable.sofa
                            "salon" -> R.drawable.salon
                            "chambre" -> R.drawable.chambre
                            "bureau" -> R.drawable.bureau
                            else -> R.drawable.sofa
                        }
                        ProductCard(
                            name = product.name,
                            price = "${product.price} DH",
                            image = imageRes,
                            onClickAR = { onNavigateToScanner() }
                        )
                    }
                }
            }
        }


        if (isMenuOpen) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)).clickable { isMenuOpen = false })

            Column(modifier = Modifier.fillMaxHeight().width(220.dp).background(Color.White).padding(20.dp)) {
                Text("SmartHome", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = customBlue)
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Accueil",
                    color = if (currentScreen == "home") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onNavigateHome()
                        }
                        .padding(12.dp)
                )

                Text(
                    "Catalogue",
                    color = if (currentScreen == "catalogue") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().clickable {
                        isMenuOpen = false
                        onNavigateToCatalogue(null)
                    }.padding(12.dp)
                )

                Text(
                    "Mes Projets",
                    color = if (currentScreen == "projects") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().clickable {
                        isMenuOpen = false
                        onNavigateToProjects()
                    }.padding(12.dp)
                )

                Text(
                    "Profil",
                    color = if (currentScreen == "profile") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().clickable {
                        isMenuOpen = false
                        onNavigateToProfile()
                    }.padding(12.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Se déconnecter",
                    color = Color.Red,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onLogout()
                        }
                        .padding(12.dp)
                )
            }
        }

        BottomMenu(
            navController = navController,
            currentRoute = "home",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
        )
    }
}
