package io.github.yusufahmadi.labelorder.ui.ribbon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RibbonViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RibbonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ribbon fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}