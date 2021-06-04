package gortea.jgmax.cardholder.interfaces

interface Callbacks {
    suspend fun onLoginClick(login: String, password: String) : Boolean
    suspend fun onRegisterClick(login: String, password: String) : Boolean
}