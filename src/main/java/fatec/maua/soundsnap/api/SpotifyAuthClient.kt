package fatec.maua.soundsnap.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.util.Base64

object SpotifyAuthClient {

    private const val CLIENT_ID = "5f8af35190404b008dee31cfac33fae7"       // Substitua pelo Client ID do Spotify
    private const val CLIENT_SECRET = "a258112f7f1a4a8dafcf114d63070849"  // Substitua pelo Client Secret do Spotify
    private const val TOKEN_URL = "https://accounts.spotify.com/api/token"

    private var accessToken: String? = null

    fun getAccessToken(): String? {
        if (accessToken.isNullOrEmpty()) {
            accessToken = fetchAccessToken()
        }
        return accessToken
    }

    private fun fetchAccessToken(): String? {
        val client = OkHttpClient()

        val credentials = "$CLIENT_ID:$CLIENT_SECRET"
        val encodedCredentials = Base64.getEncoder().encodeToString(credentials.toByteArray())

        // Usa 'toMediaTypeOrNull()' para evitar erros de tipo
        val requestBody = "grant_type=client_credentials".toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(TOKEN_URL)
            .post(requestBody)
            .addHeader("Authorization", "Basic $encodedCredentials")
            .build()

        // Trata exceções ao fazer a requisição
        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseJson = JSONObject(response.body?.string() ?: "")
                responseJson.getString("access_token")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
