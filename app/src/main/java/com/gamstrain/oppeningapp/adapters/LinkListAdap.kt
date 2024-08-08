package com.gamstrain.oppeningapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gamstrain.oppeningapp.databinding.LayLinkBinding
import com.gamstrain.oppeningapp.models.LinkD


class LinkListAdap(private val links: List<LinkD>) :
    RecyclerView.Adapter<LinkListAdap.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val binding = LayLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = links[position]
        holder.bind(item)
    }

    inner class MyViewHolder(private val binding: LayLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LinkD) {

            Glide.with(binding.root.context).load(item.thumb)
                .into(binding.icon)

            binding.title.text = item.title
            binding.description.text = item.des
            binding.clicks.text = item.clicks
            binding.link.text = item.link
            binding.link.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(item.link) // Replace with your URL
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return links.size
    }
}
