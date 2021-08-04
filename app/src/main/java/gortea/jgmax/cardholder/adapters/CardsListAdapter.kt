package gortea.jgmax.cardholder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.adapters.holders.CardsViewHolder
import gortea.jgmax.cardholder.databinding.ItemCardsBinding
import gortea.jgmax.cardholder.models.CardModel
import gortea.jgmax.cardholder.viewmodels.CardsListViewModel

class CardsListAdapter(private val viewModel: CardsListViewModel) : ListAdapter<CardModel, CardsViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCardsBinding>(inflater, R.layout.item_cards, parent, false)
        return CardsViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private companion object {
        private val comparator = object : DiffUtil.ItemCallback<CardModel>() {
            override fun areItemsTheSame(oldItem: CardModel, newItem: CardModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CardModel, newItem: CardModel): Boolean =
                oldItem == newItem
        }
    }
}