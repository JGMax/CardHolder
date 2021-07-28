package gortea.jgmax.cardholder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gortea.jgmax.cardholder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.hide()

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, CardsFragment())
            transaction.commit()
        }
    }
}