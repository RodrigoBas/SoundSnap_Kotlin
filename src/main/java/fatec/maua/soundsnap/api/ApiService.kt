package fatec.maua.soundsnap.api

import fatec.maua.soundsnap.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users") // Altere para o endpoint correto de criação de usuário
    fun createUser(@Body user: User): Call<User>
}
