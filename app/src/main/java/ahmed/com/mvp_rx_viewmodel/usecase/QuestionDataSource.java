package ahmed.com.mvp_rx_viewmodel.usecase;

import java.util.List;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import io.reactivex.Flowable;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public interface QuestionDataSource
{
    Flowable<List<Question>> loadQuestions(boolean forceRemote);
    void addQuestion(Question question);
    void clearData();
}
