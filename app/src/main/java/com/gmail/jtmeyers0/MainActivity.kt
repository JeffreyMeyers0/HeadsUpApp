package com.gmail.jtmeyers0

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var checkboxRpm: CheckBox
    private lateinit var checkboxTemp: CheckBox
    private lateinit var checkboxThrottle: CheckBox
    private lateinit var buttonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        //supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //button variables
        checkboxRpm = findViewById(R.id.RPM)
        checkboxTemp = findViewById(R.id.temp)
        checkboxThrottle = findViewById(R.id.throttle)
        buttonSend = findViewById(R.id.send)

        //send button logic
        buttonSend.setOnClickListener {
            val selectedMetrics = mutableListOf<String>()

            //if checked
            if (checkboxRpm.isChecked) selectedMetrics.add("RPM")
            if (checkboxTemp.isChecked) selectedMetrics.add("CoolantTemp")
            if (checkboxThrottle.isChecked) selectedMetrics.add("ThrottlePosition")

            //json file creation
            try {
                val json = JSONObject().apply {
                    put("metrics", JSONArray(selectedMetrics))
                }

                openFileOutput("hud_metrics.json", MODE_PRIVATE).use { fos ->
                    OutputStreamWriter(fos).use { writer ->
                        writer.write(json.toString(4))
                    }
                }

                Toast.makeText(this@MainActivity, "Metrics saved!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Error saving metrics", Toast.LENGTH_SHORT).show()

            }
        }
    }
}