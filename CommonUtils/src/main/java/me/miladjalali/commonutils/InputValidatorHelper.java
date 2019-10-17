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
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (text.length() > 40) {
            error++;
            editText.setError("حداکثر 40 کاراکتر مجاز است");
            if(error==0)
                editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }


    public boolean ValidateInputDescription(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.length() < 10) {
            error++;
            editText.setError("حداقل 10 کاراکتر وارد کنید");
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (text.length() > 750) {
            error++;
            editText.setError("حداکثر 750 کاراکتر مجاز است");
            if(error==0)
                editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }


    public boolean ValidateInputPrice(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.substring(0,1).equals("0")) {
            error++;
            editText.setError("مبلغ وارد شده صحیح نیست");
            if(error==0)
                editText.requestFocus();
            return false;
        }else if (text.length()<3) {
            error++;
            editText.setError("مبلغ وارد شده نمی تواند کمتر از 100 تومان باشد");
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (text.length()>10) {
            error++;
            editText.setError("مبلغ وارد شده نمی تواند بیشتر از 1000000000 تومان باشد");
            if(error==0)
                editText.requestFocus();
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }

    public boolean ValidateInputHasAtLeastOneChar(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.length() < 1) {
            error++;
            editText.setError("تعداد را وارد کنید");
            if(error==0)
                editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }


    public boolean ValidateInputPassword(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.length() < 6) {
            error++;
            editText.setError("حداقل 6 کاراکتر وارد کنید");
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (text.length() > 16) {
            error++;
            editText.setError("حداکثر 16 کاراکتر مجاز است");
            if(error==0)
                editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean ValidateInputMobile(EditText editText) {
        String text = GetTextOfEditText(editText);
        if (text.length() != 11) {
            error++;
            editText.setError("شماره موبایل باید 11 رقم باشد");
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (!text.substring(0, 2).equals("09")) {
            error++;
            editText.setError("شماره موبایل وارد شده صحیح نیست");
            if(error==0)
                editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean ValidateInputConfirmPassword(EditText editText, EditText editTextConfirm) {
        String text = GetTextOfEditText(editText);
        String textConfirm = GetTextOfEditText(editTextConfirm);

        if (textConfirm.length() < 6) {
            error++;
            editTextConfirm.setError("حداقل 6 کاراکتر وارد کنید");
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (textConfirm.length() > 40) {
            error++;
            editTextConfirm.setError("حداکثر 16 کاراکتر مجاز است");
            if(error==0)
                editText.requestFocus();
            return false;
        } else if (!text.equals(textConfirm)) {
            error++;
            editText.setError("رمز عبور و تکرار آن یکسان نیست");
            if(error==0)
                editText.requestFocus();
            return false;
        } else {
            editTextConfirm.setError(null);
            return true;
        }
    }

    public boolean ValidateInputSmsCode(EditText editText, String smsCode) {
        return true;
    }

    public String GetTextOfEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public boolean InputsHasError() {
        return error > 0;
    }
}
