package gortea.jgmax.cardholder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.custom_view.BookmarkView

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