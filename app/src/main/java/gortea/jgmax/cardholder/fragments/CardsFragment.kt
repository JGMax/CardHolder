package gortea.jgmax.cardholder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.adapters.CardsRecyclerViewAdapter
import gortea.jgmax.cardholder.fragments.dummy.DummyContent
import gortea.jgmax.cardholder.items.CardItemData

class CardsFragment : Fragment() {
    //todo add item
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = CardsRecyclerViewAdapter(context, arrayListOf(CardItemData()))
            }
        }
        return view
    }
}