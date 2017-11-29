package ahmed.com.mvp_rx_viewmodel.usecase;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.usecase.local.Local;
import ahmed.com.mvp_rx_viewmodel.usecase.remote.Remote;
import io.reactivex.Flowable;

import static ahmed.com.mvp_rx_viewmodel.utils.Constants.UNSUPPORTED_OPERATION;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class QuestionUseCase implements QuestionDataSource
{
    private QuestionDataSource remoteDataSource;
    private QuestionDataSource localDataSource;

    @VisibleForTesting List<Question> caches;

    @Inject public QuestionUseCase(@Remote QuestionDataSource remoteDataSource, @Local QuestionDataSource localDataSource)
    {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        caches = new ArrayList<>();
    }

    @Override public Flowable<List<Question>> loadQuestions(boolean isRemote) {
        if (isRemote) {
            return refreshData();
        } else {
            if (caches.size() > 0) {
                // if cache is available, return it immediately
                return Flowable.just(caches);
            } else {
                // else return data from local storage
                return localDataSource.loadQuestions(false)
                        .take(1)
                        .flatMap(Flowable::fromIterable)
                        .doOnNext(question -> caches.add(question))
                        .toList()
                        .toFlowable()
                        .filter(list -> !list.isEmpty())
                        .switchIfEmpty(
                                refreshData()); // If local data is empty, fetch from remote source instead.
            }
        }
    }

    @Override
    public void addQuestion(Question question)
    {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }

    @Override
    public void clearData()
    {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }

    public Flowable<Question> getQuestion(long questionId) {
        return Flowable.fromIterable(caches).filter(question -> question.getId() == questionId);
    }

    /**
     * Fetches data from remote source.
     * Save it into both local database and cache.
     *
     * @return the Flowable of newly fetched data.
     */
    private Flowable<List<Question>> refreshData() {
        return remoteDataSource.loadQuestions(true).doOnNext(list -> {
            // Clear cache
            caches.clear();
            // Clear data in local storage
            localDataSource.clearData();
        }).flatMap(Flowable::fromIterable).doOnNext(question -> {
            caches.add(question);
            localDataSource.addQuestion(question);
        }).toList().toFlowable();
    }
}
