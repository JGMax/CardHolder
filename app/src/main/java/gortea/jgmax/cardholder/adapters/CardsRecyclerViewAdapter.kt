package gortea.jgmax.cardholder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.items.CardItemData


class CardsRecyclerViewAdapter(
    private val context : Context?,
    private val values: List<CardItemData>
) : RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cards, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.apply {
            name.text = "${item.name} ${item.surname}"
            birthDate.text = item.dateOfBirth
            age.text = item.age
            additionalInfo.text = item.additionalInfo
            if (item.phoneNumbers.size == 1) {
                phoneNumbersButton.text = item.phoneNumbers[0]
                val img = context?.getDrawable(R.drawable.ic_call)
                phoneNumbersButton.setCompoundDrawablesRelative(null, null, img, null)
            }
            //todo social network listener
            //todo phone numbers listener
            //todo phone call
            //todo open social networks
            //todo animation of opening menu
            //todo add popup menu
        }
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
    }
}