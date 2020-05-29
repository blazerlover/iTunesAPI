package ru.example.itunesapi.ui.albumsView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.example.itunesapi.R
import ru.example.itunesapi.databinding.AlbumRowBinding
import ru.example.itunesapi.viewModel.row.AlbumRow

class AlbumsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    interface Listener {
        fun onAlbumsAdapterItemClicked(albumId: Int)
    }

    private var albumRows = ArrayList<AlbumRow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val albumRowItemBinding: AlbumRowBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.album_row,
                parent,
                false
            )
        return ViewHolder(albumRowItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumRows[position])
        holder.albumRowItemBinding.listener = this.listener
    }

    override fun getItemCount(): Int {
        return albumRows.size
    }

    fun notifyData(albumRows: ArrayList<AlbumRow>) {
        this.albumRows.clear()
        this.albumRows.addAll(albumRows)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.albumRows.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(val albumRowItemBinding: AlbumRowBinding) :
        RecyclerView.ViewHolder(albumRowItemBinding.root) {
        fun bind(albumRow: AlbumRow) {
            albumRowItemBinding.albumRow = albumRow
            albumRowItemBinding.executePendingBindings()
        }
    }
}