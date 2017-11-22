package ahmed.com.mvp_rx_viewmodel.injection.question;

import ahmed.com.mvp_rx_viewmodel.injection.rx.SchedulerModule;
import ahmed.com.mvp_rx_viewmodel.presentation.base.ActivityScope;
import ahmed.com.mvp_rx_viewmodel.presentation.questions.QuestionsActivity;
import dagger.Component;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@ActivityScope
@Component(modules = {QuestionsPresenterModule.class , SchedulerModule.class},
        dependencies = {QuestionInteractorComponent.class})
public interface QuestionsComponent
{
    void inject(QuestionsActivity questionsActivity);
}
