package ahmed.com.mvp_rx_viewmodel.data.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static ahmed.com.mvp_rx_viewmodel.utils.Constants.QUESTIONS_END_POINT;
import static ahmed.com.mvp_rx_viewmodel.utils.Constants.QUESTIONS_END_POINT_QUERY;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public interface QuestionService
{
    @GET(QUESTIONS_END_POINT)
    Flowable<QuestionResponse> loadQuestionsByTag(@Query(QUESTIONS_END_POINT_QUERY) String tag);
}
