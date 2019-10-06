package me.miladjalali.commonutils;

import android.widget.EditText;

public class InputValidatorHelper {

    int error;

    public InputValidatorHelper() {
        error = 0;
    }

    public boolean ValidateInputName(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.length() < 3) {
            error++;
            editText.setError("حداقل 3 کاراکتر وارد کنید");
            return false;
        } else if (text.length() > 40) {
            error++;
            editText.setError("حداکثر 40 کاراکتر مجاز است");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean ValidateInputMobile(EditText editText) {
        return true;
    }

    public boolean ValidateInputPassword(EditText editText) {
        return true;
    }


    public String GetTextOfEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public boolean ValidateInputSmsCode(EditText editText,String smsCode){
        return true;
    }
    public boolean InputsHasError(){
        return error>0;
    }
}
