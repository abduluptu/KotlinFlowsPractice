package com.abdul.kotlinflowspractice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abdul.kotlinflowspractice.ui.theme.KotlinFlowsPracticeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowsPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    FlowsPractice()
                }
            }
        }
    }
}

val channel = Channel<Int>()

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FlowsPractice() {
    //Consumer (Coroutines)
    /*CoroutineScope(Dispatchers.Main).launch {
        getUserNames().forEach {
            Log.d("soha", it)
        }
    }*/

    //Using channel
   /* producer()
    consumer()*/

    //Using Flows
    //consume flow
    /*val job = GlobalScope.launch {
        val data: Flow<Int> = producer()
        data.collect{
            Log.d("soha", it.toString())
        }
    }*/

    //cancel coroutines, automatically flow also canceled
    /*GlobalScope.launch {
        delay(3500)
        job.cancel()
    }*/

    //multiple consumers
    GlobalScope.launch {
        val data: Flow<Int> = producer()
        data.collect{
            Log.d("soha - 1", it.toString())
        }
    }

    GlobalScope.launch {
        val data: Flow<Int> = producer()
        data.collect{
            delay(2500)
            Log.d("soha - 2", it.toString())
        }
    }

}

//Using Flows
fun producer() = flow<Int> {
    val list = listOf<Int>(1,2,3,4,5)
    list.forEach {
        delay(1000) //1 second
        emit(it)
    }
}

//Using channel
/*fun producer() {
    CoroutineScope(Dispatchers.Main).launch {
        channel.send(1)
        channel.send(2)
        channel.send(3)

    }
}*/

//Using channel
/*fun consumer() {
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("soha", channel.receive().toString())
        Log.d("soha", channel.receive().toString())
        Log.d("soha", channel.receive().toString())
    }
}*/

//Producer (Coroutines)
private suspend fun getUserNames(): List<String> {
    val list = mutableListOf<String>()
    list.add(getUser(1))
    list.add(getUser(2))
    list.add(getUser(3))
    return list
}

private suspend fun getUser(id: Int): String {
    delay(1000)//Assume Network Call
    return "User$id"
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinFlowsPracticeTheme {
        Greeting("Android")
    }
}