package io.github.yusufahmadi.labelcalculator.ui.taffeta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaffetaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TaffetaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is taffeta fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}