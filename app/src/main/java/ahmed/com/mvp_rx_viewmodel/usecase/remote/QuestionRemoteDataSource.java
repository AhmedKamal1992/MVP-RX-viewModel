package ahmed.com.mvp_rx_viewmodel.usecase.remote;

import java.util.List;

import javax.inject.Inject;

import ahmed.com.mvp_rx_viewmodel.data.api.QuestionResponse;
import ahmed.com.mvp_rx_viewmodel.data.api.QuestionService;
import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.usecase.QuestionDataSource;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import static ahmed.com.mvp_rx_viewmodel.utils.Constants.UNSUPPORTED_OPERATION;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class QuestionRemoteDataSource implements QuestionDataSource
{
    private QuestionService service;

    @Inject
    public QuestionRemoteDataSource(QuestionService service)
    {
        this.service = service;
    }

    @Override
    public Flowable<List<Question>> loadQuestions(boolean forceRemote)
    {
        return service.loadQuestionsByTag("android").map(QuestionResponse::getQuestions);
    }

    @Override
    public void addQuestion(Question question) {
        //Its not needed for remote source.
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }

    @Override
    public void clearData() {
        //Its not needed for remote source.
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }
}
