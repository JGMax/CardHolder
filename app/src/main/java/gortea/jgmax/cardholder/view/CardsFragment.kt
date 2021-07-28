package gortea.jgmax.cardholder.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.adapters.CardsListAdapter
import gortea.jgmax.cardholder.constants.ITEM_BOTTOM_MARGIN_DP
import gortea.jgmax.cardholder.constants.ITEM_LEFT_MARGIN_DP
import gortea.jgmax.cardholder.constants.ITEM_RIGHT_MARGIN_DP
import gortea.jgmax.cardholder.constants.ITEM_TOP_MARGIN_DP
import gortea.jgmax.cardholder.decorators.HorizontalItemDecorator
import gortea.jgmax.cardholder.decorators.VerticalItemDecorator
import gortea.jgmax.cardholder.utils.toPx

class CardsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = CardsListAdapter(arrayListOf())
                addItemDecoration(HorizontalItemDecorator(
                    leftDivider = ITEM_LEFT_MARGIN_DP.toPx(context),
                    rightDivider = ITEM_RIGHT_MARGIN_DP.toPx(context)
                ))
                addItemDecoration(VerticalItemDecorator(
                    topDivider = ITEM_TOP_MARGIN_DP.toPx(context),
                    bottomDivider = ITEM_BOTTOM_MARGIN_DP.toPx(context)
                ))
            }
        }
        return view
    }
}