package ahmed.com.mvp_rx_viewmodel.presentation.questions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import javax.inject.Inject;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.rx.RunOn;
import ahmed.com.mvp_rx_viewmodel.usecase.QuestionUseCase;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.IO;
import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.UI;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class QuestionsPresenter implements QuestionsContract.Presenter , LifecycleObserver
{
    private QuestionUseCase interactor;

    private QuestionsContract.View view;

    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    private CompositeDisposable disposable;

    @Inject
    public QuestionsPresenter(QuestionUseCase interactor, QuestionsContract.View view,
        @RunOn(IO) Scheduler ioScheduler, @RunOn(UI) Scheduler mainScheduler)
    {
        this.interactor = interactor;
        this.view = view;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;

        if(view instanceof LifecycleOwner) {((LifecycleOwner) view).getLifecycle().addObserver(this);}
        disposable = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach()
    {
        loadQuestions(false);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetech()
    {
        disposable.clear();
        disposable.dispose();
    }

    @Override
    public void loadQuestions(boolean onlineRequired)
    {
        // Clear old data on view
        view.clearQuestions();
        addDisposable(interactor.loadQuestions(onlineRequired).
                subscribeOn(ioScheduler).
                observeOn(mainScheduler).
                subscribe(this::handleQuestions , this::handleError, () -> view.hideLoading())
        );

    }

    @Override
    public void getQuestion(long questionId)
    {
        addDisposable(interactor.getQuestion(questionId)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(question -> {
                    if (question != null) {
                        view.showQuestionDetail(question);
                    }
                }));
    }

    @Override
    public void search(String questionTitle)
    {
        addDisposable(interactor.loadQuestions(false)
                .flatMap(Flowable::fromIterable)
                .filter(question -> question.getTitle().toLowerCase().contains(questionTitle.toLowerCase()))
                .toList()
                .toFlowable()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(questions -> {
                    if (questions.isEmpty()) {
                        // Clear old data in view
                        view.clearQuestions();
                        // Show notification
                        view.showEmptySearchResult();
                    } else {
                        // Update filtered data
                        view.showQuestions(questions);
                    }
                }));
    }

    private void addDisposable(Disposable disposable)
    {
        this.disposable.add(disposable);
    }

    private void handleQuestions(List<Question> questionList)
    {
        view.hideLoading();
        if (questionList != null && !questionList.isEmpty()) {
            view.showQuestions(questionList);
        } else {
            view.showNoDataMessage();
        }
    }

    private void handleError(Throwable error)
    {
        view.hideLoading();
        view.showErrorMessage(error.getLocalizedMessage());
    }
}
