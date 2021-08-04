package gortea.jgmax.cardholder.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.databinding.ItemCardsBinding
import gortea.jgmax.cardholder.models.CardModel
import gortea.jgmax.cardholder.viewmodels.CardsListViewModel

class CardsViewHolder(
    private val binding: ItemCardsBinding,
    private val viewModel: CardsListViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CardModel) {
        binding.item = item
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}