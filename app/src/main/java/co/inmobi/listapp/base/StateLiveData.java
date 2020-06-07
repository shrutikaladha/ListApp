package co.inmobi.listapp.base;

import androidx.lifecycle.MutableLiveData;

public class StateLiveData<T> extends MutableLiveData<StateData<T>> {

    /**
     * This is used to put the Data on a LOADING Status
     */
    public void postLoading() {
        postValue(new StateData<T>().loading());
    }

    /**
     * This is used to put the Data on a ERROR DataStatus
     *
     * @param throwable the error to be handled
     */
    public void postError(Throwable throwable) {
        postValue(new StateData<T>().error(throwable));
    }

    /**
     * This is used to put the Data on a SUCCESS DataStatus
     *
     * @param data
     */
    public void postSuccess(T data) {
        postValue(new StateData<T>().success(data));
    }

    /**
     * This is used to put the Data on a COMPLETE DataStatus
     */
    public void postComplete() {
        postValue(new StateData<T>().complete());
    }

}
