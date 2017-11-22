package ahmed.com.mvp_rx_viewmodel.usecase.local;

import java.util.List;

import javax.inject.Inject;

import ahmed.com.mvp_rx_viewmodel.data.database.QuestionDao;
import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.usecase.QuestionDataSource;
import io.reactivex.Flowable;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class QuestionLocalDataSource implements QuestionDataSource
{
    private QuestionDao questionDao;

    @Inject
    public QuestionLocalDataSource(QuestionDao questionDao)
    {
        this.questionDao = questionDao;
    }

    @Override
    public Flowable<List<Question>> loadQuestions(boolean forceRemote)
    {
        return questionDao.getAllQuestions();
    }

    @Override
    public void addQuestion(Question question)
    {
        questionDao.insert(question);
    }

    @Override
    public void clearData()
    {
        questionDao.deleteAll();
    }
}
