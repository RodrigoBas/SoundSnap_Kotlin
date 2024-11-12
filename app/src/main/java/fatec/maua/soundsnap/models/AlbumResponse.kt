package fatec.maua.soundsnap.models

data class AlbumResponse(
    val albums: List<Album>
)

data class Album(
    val id: String,
    val name: String,
    val artists: List<Artist>,
    val release_date: String,
    val total_tracks: Int,
    val images: List<Image>,
    val album_id: String,
    val likes: Int = 0,
    val dislikes: Int = 0
)

data class Artist(
    val id: String,
    val name: String
)

data class Image(
    val url: String,
    val height: Int,
    val width: Int
)
