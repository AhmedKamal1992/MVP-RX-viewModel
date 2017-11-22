package ahmed.com.mvp_rx_viewmodel.injection.rx;

import ahmed.com.mvp_rx_viewmodel.rx.RunOn;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.COMPUTATION;
import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.IO;
import static ahmed.com.mvp_rx_viewmodel.rx.SchedulerType.UI;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Module
public class SchedulerModule
{
    @Provides
    @RunOn(IO)
    Scheduler provideIo(){
        return Schedulers.io();
    }

    @Provides
    @RunOn(COMPUTATION)
    Scheduler provideComputation() {
        return Schedulers.computation();
    }

    @Provides
    @RunOn(UI)
    Scheduler provideUi() {
        return AndroidSchedulers.mainThread();
    }
}
