package ahmed.com.mvp_rx_viewmodel.presentation.questions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.InvalidParameterException;
import java.util.List;

import ahmed.com.mvp_rx_viewmodel.R;
import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.presentation.base.BaseRecyclerViewAdapter;
import ahmed.com.mvp_rx_viewmodel.utils.DateTimeUtils;
import io.reactivex.annotations.NonNull;

import static ahmed.com.mvp_rx_viewmodel.utils.Constants.INVALID_INDEX;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class QuestionAdapter extends BaseRecyclerViewAdapter<QuestionViewHolder>
{
    private List<Question> questions;

    public QuestionAdapter(@NonNull List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.question_list_raw, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);
        QuestionViewHolder questionViewHolder = (QuestionViewHolder) holder; //safe cast
        Question question = questions.get(position);
        questionViewHolder.titleText.setText(question.getTitle());
        questionViewHolder.userText.setText(question.getUser().getName());
        questionViewHolder.createdTimeText.setText(DateTimeUtils.
                formatRelativeTime(question.getCreationDate()));
    }

    @Override
    public int getItemCount()
    {
        return questions.size();
    }

    public void replaceData(List<Question> questions)
    {
        this.questions.clear();
        this.questions.addAll(questions);
        notifyDataSetChanged();
    }

    public Question getItem(int position)
    {
        if (position < 0 || position >= questions.size())
        {
            throw new InvalidParameterException(INVALID_INDEX);
        }
        return questions.get(position);
    }

    public void clearData()
    {
        questions.clear();
        notifyDataSetChanged();
    }
}
