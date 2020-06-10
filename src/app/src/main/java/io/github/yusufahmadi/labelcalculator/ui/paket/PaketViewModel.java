package io.github.yusufahmadi.labelcalculator.ui.paket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaketViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PaketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is paket fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}