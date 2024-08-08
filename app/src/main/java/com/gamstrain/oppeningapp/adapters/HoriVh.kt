package com.gamstrain.oppeningapp.adapters

import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gamstrain.oppeningapp.R
import com.gamstrain.oppeningapp.databinding.LayAnalsBinding
import com.gamstrain.oppeningapp.models.HoriModel


class HoriVh(
    private val binding: LayAnalsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feed: HoriModel) {

        binding.apply {
            icon.setImageResource(feed.icon)
            title.text = feed.title
            description.text = feed.des

            // TODO : we can change the background here
//            when (feed.title) {
//                "Title1" -> icon.background = ContextCompat.getDrawable(itemView.context, R.drawable.background1)
//                "Title2" -> icon.background = ContextCompat.getDrawable(itemView.context, R.drawable.background2)
//                // Add more cases as needed
//                else -> icon.background = ContextCompat.getDrawable(itemView.context, R.drawable.default_background)
//            }
        }
    }
}