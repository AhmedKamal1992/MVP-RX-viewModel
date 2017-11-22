package ahmed.com.mvp_rx_viewmodel.presentation.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private RecyclerViewListener.OnItemClickListener itemClickListener;
    private RecyclerViewListener.OnItemLongClickListener itemLongClickListener;

    public void setOnItemClickListener
            (@NonNull RecyclerViewListener.OnItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener
            (@NonNull RecyclerViewListener.OnItemLongClickListener itemLongClickListener)
    {
        this.itemLongClickListener = itemLongClickListener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(view -> itemClickListener.OnItemClick(view, position));
        }
        if (itemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> {
                itemLongClickListener.OnItemLongClick(view, position);
                return true;
            });
        }
    }
}
