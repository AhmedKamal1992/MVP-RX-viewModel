package ahmed.com.mvp_rx_viewmodel.injection.question;

import javax.inject.Singleton;

import ahmed.com.mvp_rx_viewmodel.usecase.QuestionDataSource;
import ahmed.com.mvp_rx_viewmodel.usecase.local.Local;
import ahmed.com.mvp_rx_viewmodel.usecase.local.QuestionLocalDataSource;
import ahmed.com.mvp_rx_viewmodel.usecase.remote.QuestionRemoteDataSource;
import ahmed.com.mvp_rx_viewmodel.usecase.remote.Remote;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Module
public class QuestionInteractorModule
{
    @Provides
    @Local
    @Singleton
    public QuestionDataSource provideLocalDataSource(QuestionLocalDataSource questionLocalDataSource) {
        return questionLocalDataSource;
    }

    @Provides
    @Remote
    @Singleton
    public QuestionDataSource provideRemoteDataSource(QuestionRemoteDataSource questionRemoteDataSource) {
        return questionRemoteDataSource;
    }
}
