package ahmed.com.mvp_rx_viewmodel.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.usecase.local.Local;
import ahmed.com.mvp_rx_viewmodel.usecase.remote.Remote;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by Ahmed Kamal on 29-11-2017.
 */
public class QuestionUseCaseTest
{
    private static final Question question1 = new Question();
    private static final Question question2 = new Question();
    private static final Question question3 = new Question();
    private List<Question> questions = Arrays.asList(question1, question2, question3);

    @Mock
    @Local
    private QuestionDataSource localDataSource;

    @Mock
    @Remote
    private QuestionDataSource remoteDataSource;

    private QuestionUseCase questionUseCase;

    private TestSubscriber<List<Question>> questionsTestSubscriber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        questionUseCase = new QuestionUseCase(localDataSource, remoteDataSource);

        questionsTestSubscriber = new TestSubscriber<>();
    }

    @Test
    public void loadQuestions_ShouldReturnCache_IfItIsAvailable() {
        // Given
        questionUseCase.caches = questions;

        // When
        questionUseCase.loadQuestions(false).subscribe(questionsTestSubscriber);

        // Then
        // No interaction with local storage or remote source
        verifyZeroInteractions(localDataSource);
        verifyZeroInteractions(remoteDataSource);

        questionsTestSubscriber.assertValue(questions);
    }

    @Test
    public void loadQuestions_ShouldReturnFromLocal_IfCacheIsNotAvailable() {
        // Given
        // No cache
        questionUseCase.caches.clear();
        doReturn(Flowable.just(questions)).when(localDataSource).loadQuestions(false);
        doReturn(Flowable.just(questions)).when(remoteDataSource).loadQuestions(true);

        // When
        questionUseCase.loadQuestions(false).subscribe(questionsTestSubscriber);

        // Then
        // Loads from local storage
        verify(localDataSource).loadQuestions(false);
        // Will load from remote source if there is no local data available
        verify(remoteDataSource).loadQuestions(true);

        questionsTestSubscriber.assertValue(questions);
    }

    @Test
    public void loadQuestions_ShouldReturnFromRemote_WhenItIsRequired() {
        // Given
        questionUseCase.caches.clear();
        doReturn(Flowable.just(questions)).when(remoteDataSource).loadQuestions(true);

        // When
        questionUseCase.loadQuestions(true).subscribe(questionsTestSubscriber);

        // Then
        // Load from remote not from local storage
        verify(remoteDataSource).loadQuestions(true);
        verify(localDataSource, never()).loadQuestions(true);
        // Cache and local storage data are clear and are filled with new data
        verify(localDataSource).clearData();
        assertEquals(questionUseCase.caches, questions);
        verify(localDataSource).addQuestion(question1);
        verify(localDataSource).addQuestion(question2);
        verify(localDataSource).addQuestion(question3);

        questionsTestSubscriber.assertValue(questions);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addQuestion_ShouldThrowException() {
        questionUseCase.addQuestion(question1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void clearData_ShouldThrowException() {
        questionUseCase.clearData();
    }
}