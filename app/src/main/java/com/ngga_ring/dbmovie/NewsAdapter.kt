package com.ngga_ring.dbmovie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngga_ring.dbmovie.databinding.ItemLayoutBinding
import com.ngga_ring.dbmovie.models.Articles

class NewsAdapter(
    private var item: MutableList<Articles>,
    private var context: Context
) : RecyclerView.Adapter<NewsAdapter.PageHolder>() {

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLayoutBinding.bind(view)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        with(holder) {

            Glide
                .with(MyApps.context)
                .load(item[position].urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageView)

            binding.textView.text = item[position].title

            binding.view.setOnClickListener {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", item[position].url)
                context.startActivity(intent)
            }
        }
    }

    fun setNews(r: List<Articles>) {
        item.clear()
        item.addAll(r)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount() = item.size
}