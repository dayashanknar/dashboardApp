package com.gamstrain.oppeningapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gamstrain.oppeningapp.databinding.LayAnalsBinding
import com.gamstrain.oppeningapp.models.HoriModel

class HoriAdapter(
    private var data: List<HoriModel>

) : RecyclerView.Adapter<HoriVh>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoriVh {
        val binding =
            LayAnalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HoriVh(binding)
    }

    override fun onBindViewHolder(holder: HoriVh, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size

//    fun updateSelection(position: Int) {
//        val previousPosition = selectedPosition
//        selectedPosition = position
//        notifyItemChanged(previousPosition) // Notify the previously selected item
//        notifyItemChanged(selectedPosition) // Notify the newly selected item
//    }

}
