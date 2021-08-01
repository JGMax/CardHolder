package gortea.jgmax.cardholder.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.adapters.holders.CardsViewHolder
import gortea.jgmax.cardholder.databinding.ItemCardsBinding
import gortea.jgmax.cardholder.models.CardModel


class CardsListAdapter(
    private val values: List<CardModel>
) : RecyclerView.Adapter<CardsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCardsBinding>(inflater, R.layout.item_cards, parent, false)
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size
}