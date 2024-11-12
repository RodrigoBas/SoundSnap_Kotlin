package fatec.maua.soundsnap.models

data class User(
    val usuario: String,
    val nome: String,
    val email: String,
    val senha: String,
    val imagem: String,
    val likes: List<String> = listOf(),
    val dislikes: List<String> = listOf()
)
