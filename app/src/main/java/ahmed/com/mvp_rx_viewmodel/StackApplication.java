package ahmed.com.mvp_rx_viewmodel;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import ahmed.com.mvp_rx_viewmodel.injection.AppModule;
import ahmed.com.mvp_rx_viewmodel.injection.question.DaggerQuestionInteractorComponent;
import ahmed.com.mvp_rx_viewmodel.injection.question.QuestionInteractorComponent;
import timber.log.Timber;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class StackApplication extends Application
{
    private QuestionInteractorComponent interactorComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        initializeDependencies();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initializeDependencies()
    {
        interactorComponent = DaggerQuestionInteractorComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public QuestionInteractorComponent getQuestionInteractorComponent()
    {
        return interactorComponent;
    }

}
