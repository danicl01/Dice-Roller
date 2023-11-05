package com.example.diceroller.ui

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.R
import androidx.compose.ui.platform.LocalContext


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun DiceRollerScreen(viewModel: DiceRollerViewModel = remember { DiceRollerViewModel() }) {
    val isDarkMode by viewModel.isDarkModeEnabled.collectAsState(initial = false)

    Surface(modifier = Modifier.fillMaxSize(),
        color = if (isDarkMode) Color.Black else Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DiceWithButtonAndImage(Modifier)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.setDarkModeEnabled(!isDarkMode)
            }) {
                Text(text = if (isDarkMode) "Desactivar Dark Mode" else "Activar Dark Mode")
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var result2 by remember { mutableStateOf(1) }
    var diceNumber by remember { mutableStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    val imageResource2 = when (result2) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    // Obtener el contexto utilizando CompositionLocal
    val context = LocalContext.current

    // Inicializar el MediaPlayer con el archivo de audio dice.mp3
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.dice) }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mostrar una o dos imágenes de dados según el número seleccionado
        if (diceNumber == 1) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = result.toString()
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = result.toString()
                )
                Image(
                    painter = painterResource(imageResource2),
                    contentDescription = result2.toString()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            diceNumber = if (diceNumber == 1) 2 else 1
        }) {
            Text(stringResource(R.string.dice_number)+": $diceNumber")
        }
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = {
            result = (1..6).random()
            if(diceNumber == 2) result2 = (1..6).random()
            // Reproducir el audio cuando se pulsa el botón
            mediaPlayer.start()
        },
            modifier = Modifier
                .height(64.dp) // Modifica esta línea para ajustar la altura del botón
                .width(120.dp)
        ) {
            Text(stringResource(
                R.string.roll),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}
