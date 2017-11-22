package ahmed.com.mvp_rx_viewmodel.injection;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Module
public class AppModule
{
    private Context context;

    public AppModule(Application context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }
}
