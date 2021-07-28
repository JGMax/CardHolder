package gortea.jgmax.cardholder.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cardholder.utils.hasNext
import gortea.jgmax.cardholder.utils.hasPrevious

class VerticalItemDecorator(
    private val topDivider: Int,
    private val bottomDivider: Int
) : RecyclerView.ItemDecoration() {
    private val mediumDivider = (topDivider + bottomDivider) / 2
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION } ?: return
        val adapter = parent.adapter ?: return

        outRect.apply {
            top = if(adapter.hasPrevious(position)) mediumDivider else topDivider
            bottom = if(adapter.hasNext(position)) mediumDivider else bottomDivider
        }
    }
}