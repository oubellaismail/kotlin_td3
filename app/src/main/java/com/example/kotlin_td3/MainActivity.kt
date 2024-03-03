package com.example.kotlin_td3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_td3.ui.theme.Kotlin_td3Theme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kotlin_td3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Layout()
                }
            }
        }
    }
}

@Composable
fun Layout(){
    var priceInput by remember {
        mutableStateOf("")
    }
    val price = priceInput.toDoubleOrNull() ?: 0.0

    var quantityInput by remember {
        mutableStateOf("")
    }
    val quantity = quantityInput.toIntOrNull() ?: 0

    var tvaState by remember {
        mutableStateOf(false)
    }

    val totalPrice = calcPrice(price, quantity, tvaState)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start),
            text = "Welcome"
        )
        EditNumberField(
            label = R.string.price,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType =  KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = priceInput,
            onValueChanged = {priceInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.quantity,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType =  KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = quantityInput,
            onValueChanged = {quantityInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        TaxSwitch(
            switch = tvaState,
            onSwitchChanged = {tvaState = it},
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(id = R.string.total_price, totalPrice),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

fun calcPrice (price : Double, quantity : Int, TVA : Boolean) : Double {
    val totalPrice = price * quantity
    return if (TVA) totalPrice + (totalPrice * 0.2) else totalPrice

}

@Composable
fun EditNumberField(value : String,
                    onValueChanged : (String) -> Unit,
                    @StringRes label : Int,
                    keyboardOptions : KeyboardOptions,
                    modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        modifier = modifier,
        label = {Text(stringResource(id = label))},
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun TaxSwitch(switch : Boolean,onSwitchChanged : (Boolean) -> Unit, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.add_tva)
        )
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked =  switch ,
            onCheckedChange = onSwitchChanged
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Kotlin_td3Theme {
        Layout()
    }
}