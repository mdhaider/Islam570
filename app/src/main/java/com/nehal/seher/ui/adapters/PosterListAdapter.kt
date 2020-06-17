package com.nehal.seher.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nehal.seher.R
import com.nehal.seher.model.Poster
import kotlinx.android.synthetic.main.layout_urdu_poster_list_item.view.*
import kotlin.math.roundToInt

class PosterListAdapter(private val poster: ArrayList<Poster>) :
    RecyclerView.Adapter<PosterListAdapter.DataViewHolder>() {
    private var itemAction: ((Poster) -> Unit)? = null
  //  val randomNumber = listOf(0.0f, 0.1f, 0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f).random()

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(poster: Poster) {
            itemView.apply {
                posterItem.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.4f))
                posterItem.text=poster.description
                Glide.with(context)
                    .load("${poster.imgUrl}?raw=true")
                    .fitCenter()
                    .into(imgPoster)

                itemAction?.let {
                    itemView.setOnClickListener {
                        it(poster)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_urdu_poster_list_item, parent, false)
        )

    override fun getItemCount(): Int = poster.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(poster[position])
    }

    fun setItemAction(action: (Poster) -> Unit) {
        this.itemAction = action
    }

    fun addUrduPosters(poster: List<Poster>) {
        this.poster.apply {
            clear()
            addAll(poster)
        }
    }

    fun getColorWithAlpha(color: Int, ratio: Float): Int {
        var newColor = 0
        val alpha = (Color.alpha(color) * ratio).roundToInt()
        val r: Int = Color.red(color)
        val g: Int = Color.green(color)
        val b: Int = Color.blue(color)
        newColor = Color.argb(alpha, r, g, b)
        return newColor
    }
}