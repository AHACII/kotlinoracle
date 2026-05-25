package com.example.ar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.lazy.items as rowItems
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ar.R
import com.example.ar.ui.components.BottomMenu
import com.example.ar.ui.components.TopBar
import com.example.ar.ui.components.ProductCard
import com.example.ar.data.ApiProduct
import com.example.ar.data.RetrofitInstance

@Composable
fun CatalogueScreen(
    navController: NavController,
    currentScreen: String,
    initialCategory: String = "Touts",
    onNavigateHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToCatalogue: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToAR: () -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    
    LaunchedEffect(initialCategory) {
        selectedCategory = initialCategory
    }

    val categories = listOf("Touts", "Salon", "Chambre", "Cuisine", "Éclairage", "Table", "Tapis")
    val customBlue = Color(0xFF1A0BE9)
    val activeColor = Color(0xFF1A0BE9)
    val inactiveColor = Color.Gray

    var products by remember { mutableStateOf<List<ApiProduct>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val defaultProducts = listOf(
        ApiProduct(1, "Chaise Nordique", 1200.0, "chaise", "Salon"),
        ApiProduct(2, "Table Basse", 2500.0, "table1", "Salon"),
        ApiProduct(3, "Sofa Relax", 5000.0, "sofa", "Salon"),
        ApiProduct(4, "Lit Double", 4500.0, "chambre", "Chambre"),
        ApiProduct(5, "Table Marbre", 3800.0, "table2", "Table"),
        ApiProduct(6, "Lampe LED", 450.0, "lamp", "Éclairage"),
        ApiProduct(7, "Tapis Laine", 1500.0, "tapis", "Tapis"),
        ApiProduct(8, "Bureau Pro", 2800.0, "bureau", "Chambre")
    )

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
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = customBlue
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Column {
                        TopBar(
                            onMenuClick = { isMenuOpen = !isMenuOpen },
                            onLogoClick = { onNavigateHome() }
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Catalogue AR",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            fontSize = 20.sp,
                            color = Color(0xFF1A0BE9)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Recherche
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Rechercher...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(30.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Catégories
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            rowItems(categories) { category ->
                                val isSelected = selectedCategory == category
                                Surface(
                                    modifier = Modifier.clickable { selectedCategory = category },
                                    shape = RoundedCornerShape(24.dp),
                                    color = if (isSelected) Color(0xFF1A0BE9) else Color(0xFFF5F7FA)
                                ) {
                                    Text(
                                        text = category,
                                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                        color = if (isSelected) Color.White else Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                val filteredProducts = products.filter {
                    it.name.contains(searchQuery, ignoreCase = true) && 
                    (selectedCategory == "Touts" || it.category == selectedCategory)
                }
                
                gridItems(filteredProducts) { product ->
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
                        onClickAR = {
                            onNavigateToAR()
                        }
                    )
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
                        onNavigateToCatalogue()
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
            currentRoute = "catalogue",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
        )
    }
}
