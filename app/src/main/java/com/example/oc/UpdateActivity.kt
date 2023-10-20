package com.example.oc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oc.fragment.UpdateFragment

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        if (savedInstanceState == null) {
            val fragment = UpdateFragment()
            fragment.arguments = intent.extras // Pass any data to the Fragment through arguments
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
