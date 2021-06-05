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

class MainActivity : AppCompatActivity(), Callbacks {

    //todo add main menu

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

    override suspend fun onLoginClick(login: String, password: String): Boolean {
        if (authorize(login, password)) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, CardsFragment())
            transaction.commit()
            CoroutineScope(Dispatchers.Main).launch {
                supportActionBar?.show()
            }
            return true
        }
        return false
    }

    override suspend fun onRegisterClick(login: String, password: String): Boolean {
        return onLoginClick(login, password)
    }

    private fun authorize(login: String, password: String) : Boolean {
        //todo send data to authorize
        return true
    }
}