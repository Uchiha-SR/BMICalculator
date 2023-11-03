package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMICalculatorSection(onCalculateClicked: (Double,Double)-> Unit) {

    var height by remember {
        mutableStateOf("")

    }

    var weight by remember {
        mutableStateOf("")
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(8.dp)),
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Adult BMI Calculator",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = {
                    Text(
                        text = "Height in cms"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = {
                    Text(
                        text = "Weight in kgs"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                   // if (!validHeight || !validWeight) return@Button
                    onCalculateClicked(weight.toDouble(), height.toDouble())
                }) {
                    Text(text = "Calculate")
                }

                Button(onClick = {
                    weight = ""
                    height = ""
                //    onResetClicked()
                }) {
                    Text(text = "Reset")
                }

            }

        }
    }
}
@Composable
fun MainContent() {
   var showBmiResult by remember {
       mutableStateOf(false)
   }

    var bmiResult by remember {
        mutableStateOf(0.0)
    }

    Surface {
        Column(modifier= Modifier.padding(12.dp)) {
            BMICalculatorSection(
                onCalculateClicked = { weight, height ->
                    bmiResult = calculateBMI(weight, height)
                    showBmiResult = true
                },
            )
        }

    }

}

fun calculateBMI(weight: Double, height: Double): Double {
    if (weight.equals(0.0) || height.equals(0.0)) return 0.0
    return weight/(height/100 * height/100)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        MainContent()
    }
}