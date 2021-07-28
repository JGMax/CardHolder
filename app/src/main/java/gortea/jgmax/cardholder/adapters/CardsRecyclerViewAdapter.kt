package gortea.jgmax.cardholder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.interfaces.OnScrollListener
import gortea.jgmax.cardholder.items.CardModel


class CardsRecyclerViewAdapter(
    private val values: List<CardModel>
) : RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder>() {

    //private val scrollListener = context as? OnScrollListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cards, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        //scrollListener?.onScroll(holder.layoutPosition, itemCount)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name_text_view)
        val birthDate: TextView = view.findViewById(R.id.birth_date_text_view)
        val age: TextView = view.findViewById(R.id.age_text_view)
        val additionalInfo: TextView = view.findViewById(R.id.additional_info_text)
        val phoneNumbersButton: Button = view.findViewById(R.id.phone_number_btn)
        val socialNetworksButton: Button = view.findViewById(R.id.social_networks_btn)
        val popupMenuView: ImageView = view.findViewById(R.id.popup_menu_image_view)

        fun bind(item: CardModel) {

        }
    }
}