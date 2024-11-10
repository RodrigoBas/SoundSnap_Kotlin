package fatec.maua.soundsnap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fatec.maua.soundsnap.models.Album
import com.bumptech.glide.Glide

class AlbumAdapter(
    private val albumList: List<Album>,
    private val onItemClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumTitle: TextView = itemView.findViewById(R.id.albumTitle)
        val albumArtist: TextView = itemView.findViewById(R.id.albumArtist)
        val albumImageView: ImageView = itemView.findViewById(R.id.albumImageView)

        fun bind(album: Album) {
            albumTitle.text = album.name
            albumArtist.text = album.artists[0].name // Mostra o primeiro artista da lista
            itemView.setOnClickListener { onItemClick(album) }
            Glide.with(itemView.context).load(album.images[0].url).into(albumImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size
}
