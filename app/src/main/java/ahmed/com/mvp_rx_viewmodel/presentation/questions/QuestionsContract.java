package ahmed.com.mvp_rx_viewmodel.presentation.questions;

import java.util.List;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.presentation.base.BasePresenter;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public interface QuestionsContract
{
    interface View
    {
        void showQuestions(List<Question> questions);
        void clearQuestions();
        void showNoDataMessage();
        void showErrorMessage(String error);
        void showQuestionDetail(Question question);
        void hideLoading();
        void showEmptySearchResult();
    }

    interface Presenter extends BasePresenter<View>
    {
        void loadQuestions(boolean onlineRequired);
        void getQuestion(long questionId);
        void search(String questionTitle);
    }
}
