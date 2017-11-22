package ahmed.com.mvp_rx_viewmodel.presentation.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ahmed.com.mvp_rx_viewmodel.StackApplication;
import ahmed.com.mvp_rx_viewmodel.injection.question.QuestionInteractorComponent;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    protected QuestionInteractorComponent getQuestionRepositoryComponent() {
        return ((StackApplication) getApplication()).getQuestionInteractorComponent();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
