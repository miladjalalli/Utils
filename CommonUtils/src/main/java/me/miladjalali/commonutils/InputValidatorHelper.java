package me.miladjalali.commonutils;

import android.widget.EditText;

public class InputValidatorHelper {

    int error;

    public InputValidatorHelper() {

    }

    public boolean ValidateInputName(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.length() < 3) {
            editText.setError("حداقل 3 کاراکتر وارد کنید");
            return false;
        } else if (text.length() > 40) {
            editText.setError("حداکثر 40 کاراکتر مجاز است");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean ValidateInputMobile() {
        return true;
    }

    public boolean ValidateInputPassword() {
        return true;
    }


    public String GetTextOfEditText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
