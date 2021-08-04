package gortea.jgmax.cardholder.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.adapters.CardsListAdapter
import gortea.jgmax.cardholder.decorators.HorizontalItemDecorator
import gortea.jgmax.cardholder.decorators.VerticalItemDecorator
import gortea.jgmax.cardholder.decorators.params.*
import gortea.jgmax.cardholder.dummy.DummyContent
import gortea.jgmax.cardholder.utils.toPx
import gortea.jgmax.cardholder.viewmodels.CardsListViewModel

class CardsFragment : Fragment() {
    private val viewModel: CardsListViewModel by activityViewModels()
    private val adapter by lazy {
        CardsListAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rw = view.findViewById<RecyclerView>(R.id.card_list)

        if (rw is RecyclerView) {
            with(rw) {
                layoutManager = LinearLayoutManager(context)
                adapter = this@CardsFragment.adapter
                addItemDecoration(
                    HorizontalItemDecorator(
                        leftDivider = ITEM_LEFT_MARGIN_DP.toPx(context),
                        rightDivider = ITEM_RIGHT_MARGIN_DP.toPx(context)
                    )
                )
                addItemDecoration(
                    VerticalItemDecorator(
                        topDivider = ITEM_TOP_MARGIN_DP.toPx(context),
                        mediumDivider = ITEM_MEDIUM_VERTICAL_MARGIN_DP.toPx(context),
                        bottomDivider = ITEM_BOTTOM_MARGIN_DP.toPx(context)
                    )
                )
            }
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.let {
            it.getCards().observe(viewLifecycleOwner,
                Observer {
                    adapter.submitList(DummyContent.getItems())
                })

            viewModel.getOpenIntent().observe(viewLifecycleOwner, Observer { intent ->
                intent?.resolveActivity(requireActivity().packageManager)?.apply {
                    startActivity(intent)
                }
            })
        }
    }
}