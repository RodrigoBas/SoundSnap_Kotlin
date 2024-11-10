package fatec.maua.soundsnap.models

data class SearchResponse(
    val albums: AlbumItems
)

data class AlbumItems(
    val items: List<Album>
)