package com.udacity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val bundle = intent.extras
        val value = bundle!!.getString("file_name")
        var filename:TextView=findViewById(R.id.filename)
        filename.text=value

        button.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

}
