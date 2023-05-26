package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemmonApp()
                }
            }
        }
    }

    @Composable
    fun LemmonApp() {
        val currentState = remember { mutableStateOf(LemonadeState.LemonTree) }
        val tapsRequired = remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            when (currentState.value) {
                LemonadeState.LemonTree -> {
                    LemonTreeScreen {
                        currentState.value = LemonadeState.Lemon
                    }
                }
                LemonadeState.Lemon -> {
                    LemonScreen(tapsRequired.value) {
                        if (tapsRequired.value == 0) {
                            tapsRequired.value = (2..4).random()
                        }
                        else {
                            tapsRequired.value--
                            if (tapsRequired.value == 0) {
                                currentState.value = LemonadeState.Lemonade
                            }
                        }
                    }
                }
                LemonadeState.Lemonade -> {
                    LemonadeScreen {
                        currentState.value = LemonadeState.EmptyGlass
                    }
                }
                LemonadeState.EmptyGlass -> {
                    EmptyGlassScreen {
                        currentState.value = LemonadeState.LemonTree
                        tapsRequired.value = 0
                    }
                }
            }
        }
    }

    @Composable
    fun LemonTreeScreen(onTap: () -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.lemon_tree),
                contentDescription = "Lemon Tree",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTap() }
              )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Tap_the_lemon_tree_to_select_a_lemon),
                style = MaterialTheme.typography.h6
            )
        }
    }

    @Composable
    fun LemonScreen(tapsRequired: Int, onSqueeze: () -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.lemon_squeeze),
                contentDescription = "Lemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSqueeze() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Keep_tapping_the_lemon_to_squeeze_it),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Taps Required: $tapsRequired",
                style = MaterialTheme.typography.body1
            )
        }
    }

    @Composable
    fun LemonadeScreen(onDrink: () -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.lemon_drink),
                contentDescription = "Glass of Lemonade",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDrink() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Tap_the_lemonade_to_drink_it),
                style = MaterialTheme.typography.h6
            )
        }
    }

    @Composable
    fun EmptyGlassScreen(onStartAgain: () -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.lemon_restart),
                contentDescription = "Empty Glass",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStartAgain() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Tap_the_empty_glass_to_start_again),
                style = MaterialTheme.typography.h6
            )
        }

    }

    enum class LemonadeState {
        LemonTree,
        Lemon,
        Lemonade,
        EmptyGlass
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        LemonadeTheme {
            LemmonApp()
        }
    }
}

