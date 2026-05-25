package com.example.ar.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val LightBg = Color(0xFFFFFFFF)
val SoftFieldBg = Color(0xFFF5F7FA)
val MainBlack = Color(0xFF1A1A1A)
val AccentBlue = Color(0xFF3D5AFE)
val GrayText = Color(0xFF717171)


@Composable
fun AppHeader(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row {
            Text(
                text = title + " ",
                color = AccentBlue,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "Vous",
                color = MainBlack,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Bienvenue dans SmartHome",
            color = GrayText,
            fontSize = 18.sp
        )
    }
}


@Composable
fun CustomCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(28.dp))
    ) {
        Column(modifier = Modifier.padding(28.dp)) {
            content()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column {
        Text(
            text = label,
            color = MainBlack,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SoftFieldBg,
                unfocusedContainerColor = SoftFieldBg,
                focusedIndicatorColor = AccentBlue,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MainBlack,
                unfocusedTextColor = MainBlack
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}


@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MainBlack),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun NavigationLink(
    text1: String,
    text2: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text1,
            color = GrayText,
            fontSize = 14.sp
        )

        Text(
            text = text2,
            color = AccentBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onClick() }
        )
    }
}