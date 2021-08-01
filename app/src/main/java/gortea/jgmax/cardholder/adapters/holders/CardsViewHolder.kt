package gortea.jgmax.cardholder.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.databinding.ItemCardsBinding
import gortea.jgmax.cardholder.models.CardModel

class CardsViewHolder(private val binding: ItemCardsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CardModel) {
        binding.model = item
        binding.executePendingBindings()
    }
}