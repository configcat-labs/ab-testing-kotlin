package com.example.abtestingkotlin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.configcat.ConfigCatClient
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.events.Identify


class MainActivity : AppCompatActivity() {
    var client = ConfigCatClient.get("YOUR-CONFIGCAT-SDK-KEY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amplitude = Amplitude(
            Configuration(
                apiKey = "YOUR-AMPLITUDE-SDK-KEY",
                context = applicationContext
            )
        )

        val priceShown = client.getValue(Boolean::class.java, "priceShown", false)

        amplitude.setUserId("myuser1@awesomeapp.com")

        val identify = Identify()
        identify.set("flag", priceShown)
        amplitude.identify(identify)

        val myButton: Button = findViewById(R.id.myButton)

        if(priceShown){
            myButton.text="Subscribe to Premium! $4.99"
        }
        else {
            myButton.text="Subscribe to Premium!"

        }
        
        myButton.setOnClickListener {
            amplitude.track("Subscribe Button Pressed")
        }
    }
}