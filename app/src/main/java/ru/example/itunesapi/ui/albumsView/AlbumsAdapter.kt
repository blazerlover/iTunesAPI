package ru.example.itunesapi.ui.albumsView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.example.itunesapi.R
import ru.example.itunesapi.viewModel.row.AlbumRow

class AlbumsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    interface Listener {
        fun onAlbumsAdapterItemClicked(albumId: Int)
    }

    private var albumRows = ArrayList<AlbumRow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = albumRows[position].title
        holder.tvArtist.text = albumRows[position].artist

        Picasso.get()
            .load(albumRows[position].albumPictureUrl)
//            .resizeDimen(500, 500)
            .resize(500, 500)
            .centerCrop()
            .into(holder.ivAlbum)
        setItemClickListener(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return albumRows.size
    }

    fun notifyData(albumRows: ArrayList<AlbumRow>) {
        this.albumRows.clear()
        this.albumRows.addAll(albumRows)
        notifyDataSetChanged()
    }

    private fun setItemClickListener(itemView: View, position: Int) {
        itemView.setOnClickListener {
            listener.onAlbumsAdapterItemClicked(
                albumRows[position].albumId
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView
        val tvArtist: TextView
        val ivAlbum: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.album_row__tvTitle)
            tvArtist = itemView.findViewById(R.id.album_row__tvArtist)
            ivAlbum = itemView.findViewById(R.id.album_row__ivAlbum)
        }
    }
}