package gortea.jgmax.cardholder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.items.CardModel


class CardsListAdapter(
    private val values: List<CardModel>
) : RecyclerView.Adapter<CardsListAdapter.CardViewHolder>() {

    //private val scrollListener = context as? OnScrollListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cards, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun onViewAttachedToWindow(holder: CardViewHolder) {
        super.onViewAttachedToWindow(holder)
        //scrollListener?.onScroll(holder.layoutPosition, itemCount)
    }

    override fun getItemCount(): Int = values.size

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: CardModel) {

        }
    }
}