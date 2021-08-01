package gortea.jgmax.cardholder.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.view.fragments.CardsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, CardsFragment())
            transaction.commit()
        }
    }
}