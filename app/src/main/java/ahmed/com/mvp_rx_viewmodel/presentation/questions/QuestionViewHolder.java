package ahmed.com.mvp_rx_viewmodel.presentation.questions;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ahmed.com.mvp_rx_viewmodel.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class QuestionViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.user_text)
    TextView userText;
    @BindView(R.id.created_time_text)
    TextView createdTimeText;

    public QuestionViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this , itemView);
    }
}
