package io.github.yusufahmadi.labelcalculator.ui.master;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MasterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MasterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}