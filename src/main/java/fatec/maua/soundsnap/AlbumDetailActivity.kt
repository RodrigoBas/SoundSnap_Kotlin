package fatec.maua.soundsnap

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fatec.maua.soundsnap.api.RetrofitClient
import fatec.maua.soundsnap.models.Album
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var albumName: TextView
    private lateinit var albumArtist: TextView
    private lateinit var albumReleaseDate: TextView
    private lateinit var albumTracks: TextView
    private lateinit var likeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        albumName = findViewById(R.id.albumName)
        albumArtist = findViewById(R.id.albumArtist)
        albumReleaseDate = findViewById(R.id.albumReleaseDate)
        albumTracks = findViewById(R.id.albumTracks)
        likeButton = findViewById(R.id.likeButton)

        val albumId = intent.getStringExtra("album_id") ?: return
        fetchAlbumDetails(albumId)

        likeButton.setOnClickListener {
            Toast.makeText(this, "Álbum curtido!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchAlbumDetails(albumId: String) {
        val call = RetrofitClient.instance.getAlbum(albumId)
        call.enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful) {
                    response.body()?.let { album ->
                        albumName.text = album.name
                        albumArtist.text = album.artist
                        albumReleaseDate.text = "Lançamento: ${album.release_date}"
                        albumTracks.text = "Total de músicas: ${album.total_tracks}"
                    }
                } else {
                    Toast.makeText(this@AlbumDetailActivity, "Erro ao carregar detalhes do álbum", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                Toast.makeText(this@AlbumDetailActivity, "Erro na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
