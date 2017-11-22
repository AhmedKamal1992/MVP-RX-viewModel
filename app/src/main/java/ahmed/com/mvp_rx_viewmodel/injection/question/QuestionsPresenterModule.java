package ahmed.com.mvp_rx_viewmodel.injection.question;

import ahmed.com.mvp_rx_viewmodel.presentation.questions.QuestionsContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */
@Module
public class QuestionsPresenterModule
{
    QuestionsContract.View view;

    public QuestionsPresenterModule(QuestionsContract.View view) {
        this.view = view;
    }

    @Provides
    public QuestionsContract.View provideView() {
        return view;
    }
}
