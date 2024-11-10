package fatec.maua.soundsnap

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fatec.maua.soundsnap.api.SpotifyAuthClient
import fatec.maua.soundsnap.api.SpotifyRetrofitClient
import fatec.maua.soundsnap.models.Album
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() {

    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private val albumList = ArrayList<Album>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        albumRecyclerView = findViewById(R.id.albumRecyclerView)
        albumRecyclerView.layoutManager = LinearLayoutManager(this)
        albumAdapter = AlbumAdapter(albumList) { album ->
            // Lógica de clique no item do álbum
        }
        albumRecyclerView.adapter = albumAdapter

        fetchSpotifyAlbums()
    }

    private fun fetchSpotifyAlbums() {
        SpotifyAuthClient.getAccessToken { token ->
            token?.let {
                SpotifyRetrofitClient.instance.getNewReleases("Bearer $token")
                    .enqueue(object : Callback<AlbumResponse> {
                        override fun onResponse(
                            call: Call<AlbumResponse>,
                            response: Response<AlbumResponse>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.let { albumResponse ->
                                    albumList.clear()
                                    albumList.addAll(albumResponse.albums.items)
                                    albumAdapter.notifyDataSetChanged()
                                }
                            } else {
                                Toast.makeText(this@FeedActivity, "Erro ao carregar álbuns", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                            Log.e("FeedActivity", "Erro: ${t.message}")
                            Toast.makeText(this@FeedActivity, "Erro na conexão", Toast.LENGTH_SHORT).show()
                        }
                    })
            } ?: Toast.makeText(this, "Erro ao obter token", Toast.LENGTH_SHORT).show()
        }
    }
}
