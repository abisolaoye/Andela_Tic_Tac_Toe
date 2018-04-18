package com.askemkay.probono.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.humanPlay)

        button.setOnClickListener {
            val intent = Intent(this, PlayAgainstHuman::class.java)

            startActivity(intent)

        }

        val buttonComputer = findViewById<Button>(R.id.computerPlay)

        buttonComputer.setOnClickListener { Toast.makeText(this, "Yet To Be Implemented", Toast.LENGTH_LONG).show() }
    }
}
