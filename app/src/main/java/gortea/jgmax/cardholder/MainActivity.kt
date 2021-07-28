package gortea.jgmax.cardholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gortea.jgmax.cardholder.databinding.ActivityMainBinding
import gortea.jgmax.cardholder.databinding.ToolbarBinding
import gortea.jgmax.cardholder.fragments.CardsFragment
import gortea.jgmax.cardholder.fragments.LoginFragment
import gortea.jgmax.cardholder.interfaces.Callbacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.hide()

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, LoginFragment())
            transaction.commit()
        }
    }
}