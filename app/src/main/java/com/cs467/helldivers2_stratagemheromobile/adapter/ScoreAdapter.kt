package com.cs467.helldivers2_stratagemheromobile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cs467.helldivers2_stratagemheromobile.Screens.Score
import com.cs467.helldivers2_stratagemheromobile.UpdateActivity
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreEntity
import com.cs467.helldivers2_stratagemheromobile.databinding.ItemScoreBinding

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    private lateinit var binding: ItemScoreBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemScoreBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ScoreAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: HighScoreEntity) {
            //InitView
            binding.apply {
                //Set text
                tvName.text = item.playerName
                tvScore.text= item.playerScore.toString()

            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<HighScoreEntity>() {
        override fun areItemsTheSame(oldItem: HighScoreEntity, newItem: HighScoreEntity): Boolean {
            return oldItem.scoreId == newItem.scoreId
        }

        override fun areContentsTheSame(oldItem: HighScoreEntity, newItem: HighScoreEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}