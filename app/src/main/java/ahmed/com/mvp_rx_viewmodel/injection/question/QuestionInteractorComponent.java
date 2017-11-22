package ahmed.com.mvp_rx_viewmodel.injection.question;

import javax.inject.Singleton;

import ahmed.com.mvp_rx_viewmodel.injection.AppModule;
import ahmed.com.mvp_rx_viewmodel.injection.database.DatabaseModule;
import ahmed.com.mvp_rx_viewmodel.injection.service.ApiServiceModule;
import ahmed.com.mvp_rx_viewmodel.usecase.QuestionUseCase;
import dagger.Component;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Singleton
@Component(modules = { QuestionInteractorModule.class, AppModule.class, ApiServiceModule.class,
        DatabaseModule.class})
public interface QuestionInteractorComponent
{
    QuestionUseCase provideQuestionRepository();
}
