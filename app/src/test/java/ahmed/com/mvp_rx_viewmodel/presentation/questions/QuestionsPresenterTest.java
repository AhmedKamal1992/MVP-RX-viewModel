package ahmed.com.mvp_rx_viewmodel.presentation.questions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.rx.RunOn;
import ahmed.com.mvp_rx_viewmodel.usecase.QuestionUseCase;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.IO;
import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.UI;
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Ahmed Kamal on 29-11-2017.
 */
public class QuestionsPresenterTest
{
    private static final Question question1 = new Question();
    private static final Question question2 = new Question();
    private static final Question question3 = new Question();
    private List<Question> questions = Arrays.asList(question1, question2, question3);

    @Mock
    private QuestionUseCase repository;

    @Mock
    private QuestionsContract.View view;

    @RunOn(IO)
    private Scheduler ioScheduler;

    @RunOn(UI)
    private Scheduler uiScheduler;

    private TestScheduler testScheduler;

    private QuestionsPresenter presenter;

    @Before
    public void setUp() {
        // Make sure to use TestScheduler for RxJava testing
        testScheduler = new TestScheduler();
        ioScheduler = testScheduler;
        uiScheduler = testScheduler;
        presenter = new QuestionsPresenter(repository, view, ioScheduler, uiScheduler);

        // Update stub
        question1.setTitle("question1");
        question2.setTitle("question2");
        question3.setTitle("question3");
    }

    @Test
    public void loadQuestions_ShouldShowQuestionOnView_WithDataReturned() {
        // Given
        doReturn(Flowable.just(questions)).when(repository).loadQuestions(true);

        // When
        presenter.loadQuestions(true);
        testScheduler.triggerActions();

        // Then
        verify(view).clearQuestions();
        verify(view).showQuestions(questions);
        verify(view, atLeastOnce()).hideLoading();
    }

    @Test
    public void loadQuestions_ShouldShowMessage_WhenNoDataReturned() {
        // Given
        doReturn(Flowable.just(new ArrayList<Question>())).when(repository).loadQuestions(true);

        // When
        presenter.loadQuestions(true);
        testScheduler.triggerActions();

        // Then
        verify(view).clearQuestions();
        verify(view, never()).showQuestions(questions);
        verify(view).showNoDataMessage();
        verify(view, atLeastOnce()).hideLoading();
    }

    @Test
    public void getQuestion_ShouldShowDetailOnView() {
        // Given
        doReturn(Flowable.just(question1)).when(repository).getQuestion(1);

        // When
        presenter.getQuestion(1);
        testScheduler.triggerActions();

        // Then
        verify(view).showQuestionDetail(question1);
    }

    @Test
    public void search_ResultShouldBeShownOnView_WhenFilteredDataIsNotEmpty() {
        // Given
        doReturn(Flowable.just(questions)).when(repository).loadQuestions(false);

        // When
        presenter.search("question1");
        testScheduler.triggerActions();

        // Then
        // Return a list of questions which should contains only question 1.
        verify(view).showQuestions(Collections.singletonList(question1));
        verifyNoMoreInteractions(view);
    }

    @Test
    public void search_EmptyMessageShouldBeShownOnView_WhenFilteredDataIsEmpty() {
        // Given
        doReturn(Flowable.just(questions)).when(repository).loadQuestions(false);

        // When
        presenter.search("invalid question title");
        testScheduler.triggerActions();

        // Then
        verify(view).clearQuestions();
        verify(view).showEmptySearchResult();
        verifyNoMoreInteractions(view);
    }
}