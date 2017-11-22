package ahmed.com.mvp_rx_viewmodel.presentation.base;

import android.arch.lifecycle.ViewModel;

/**
 * Created by Ahmed Kamal on 22/11/2017.
 */

public final class BaseViewModel<V ,P extends BasePresenter<V>> extends ViewModel
{

    private P presenter;

    void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }
    }

    P getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //presenter.onPresenterDestroy();
        presenter = null;
    }
}
