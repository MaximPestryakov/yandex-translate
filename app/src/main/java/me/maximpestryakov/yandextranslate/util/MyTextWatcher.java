package me.maximpestryakov.yandextranslate.util;

import android.text.Editable;
import android.text.TextWatcher;

public class MyTextWatcher implements TextWatcher {

    private OnTextChanged onTextChanged;

    public MyTextWatcher(OnTextChanged onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged.onChanged(s, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public interface OnTextChanged {
        void onChanged(CharSequence s, int start, int before, int count);
    }
}
