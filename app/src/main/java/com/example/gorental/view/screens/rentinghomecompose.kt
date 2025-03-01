package com.example.gorental.view.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.gorental.R
import com.example.gorental.utils.AppConstants.EMPTY_STRING
import com.example.gorental.utils.AppConstants.WEB_VIEW
import com.example.gorental.viewmodel.MainViewModel


@Composable
fun HomeScreen(navController: NavController,viewModel: MainViewModel) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = colorResource(id = R.color.background_white))
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.rental_car),
                    contentDescription = stringResource(
                        id = R.string.car_image_desc
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                RentalEntryCard(viewModel) {
                    navController.navigate("$WEB_VIEW/$it")
                }
            }
        }

    }
}


@Composable
fun RentalEntryCard(viewModel: MainViewModel,webViewNav: (link:String)->Unit){

    val cityStateFrom =  viewModel.cityFromLiveData.observeAsState(EMPTY_STRING)
    val countryStateFrom = viewModel.countryFromLiveData.observeAsState(EMPTY_STRING)
    val cityStateTo =  viewModel.cityToLiveData.observeAsState(EMPTY_STRING)
    val countryStateTo =  viewModel.countryToLiveData.observeAsState(EMPTY_STRING)
    val pickUpState =  viewModel.pickUpDateLiveData.observeAsState(EMPTY_STRING)
    val dropOffState =  viewModel.dropOffDateLiveData.observeAsState(EMPTY_STRING)
    val validInput =  viewModel.validInputFlag.observeAsState(true)

    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.background_white))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ){
          val (fromLocText,toLocText,fromCity,fromCountry,toCity,toCountry,pickUpDate,dropOffDate,searchButton,errorText) = createRefs()

            DatePickerBox(modifier = Modifier.constrainAs(pickUpDate){
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(dropOffDate.start)
            }, label = stringResource(id = R.string.pick_up_text),
                date = pickUpState.value
            ) {
                viewModel.updatePickup(it)
            }
            DatePickerBox(modifier = Modifier.constrainAs(dropOffDate){
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(pickUpDate.end)
                end.linkTo(parent.end)
            }, label =  stringResource(id = R.string.drop_off_text),
                date = dropOffState.value
            ) {
                viewModel.updateDropOff(it)
            }
            Text(text = stringResource(id = R.string.pick_up_loc),
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(fromLocText){
                top.linkTo(pickUpDate.bottom,margin = 15.dp)
                start.linkTo(fromCity.start)
            })
            OutlinedTextField(
                modifier = Modifier.constrainAs(fromCity){
                    top.linkTo(fromLocText.bottom,margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                value = cityStateFrom.value,
                onValueChange = {
                    viewModel.updateCityFrom(it)
                },
                label = { Text(text = stringResource(id = R.string.city_text)) },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_city_text))
                },
            )
            OutlinedTextField(
                modifier = Modifier.constrainAs(fromCountry){
                    top.linkTo(fromCity.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                value = countryStateFrom.value,
                onValueChange = {
                    viewModel.updateCountryFrom(it)
                },
                label = { Text(text = stringResource(id = R.string.state_country_text)) },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_state_country_text))
                },
            )
            Text(text = stringResource(id = R.string.drop_off_loc),
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.constrainAs(toLocText){
                    top.linkTo(fromCountry.bottom, margin = 10.dp)
                    start.linkTo(fromCountry.start)
                }
            )
            OutlinedTextField(
                modifier = Modifier.constrainAs(toCity){
                    top.linkTo(toLocText.bottom,margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                value = cityStateTo.value,
                onValueChange = {
                    viewModel.updateCityTo(it)
                },
                label = { Text(text = stringResource(id = R.string.city_text)) },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_city_text))
                },
            )
            OutlinedTextField(
                modifier = Modifier.constrainAs(toCountry){
                    top.linkTo(toCity.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                value = countryStateTo.value,
                onValueChange = {
                    viewModel.updateCountryTo(it)
                },
                label = { Text(text = stringResource(id = R.string.state_country_text)) },
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_state_country_text))
                },
            )

            if(!validInput.value){
                Text(
                    stringResource(id = R.string.error_message), modifier = Modifier.constrainAs(errorText){
                    top.linkTo(searchButton.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                    color = colorResource(id = R.color.red)
                    )
            }

            Button(onClick = {
                viewModel.updateValidityFlag(viewModel.validateInput(cityStateFrom.value,countryStateFrom.value,pickUpState.value,dropOffState.value))
                if(validInput.value){
                    val link = viewModel.getKayakWebLink()
                    webViewNav.invoke(Uri.encode(link))
                }
            },
                modifier = Modifier.constrainAs(searchButton){
                top.linkTo(toCountry.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = stringResource(id = R.string.search_button_text), style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 18.sp)
            }

        }
    }
}
