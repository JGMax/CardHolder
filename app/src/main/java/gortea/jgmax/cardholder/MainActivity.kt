package gortea.jgmax.cardholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gortea.jgmax.cardholder.fragments.CardsFragment

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