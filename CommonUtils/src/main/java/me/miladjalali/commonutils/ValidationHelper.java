package me.miladjalali.commonutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static final String TAG = "ValidationHelper";

    public static boolean isValidWifiName(String name) {
        return name.matches("^[\\u064b-\\u0655\\p{L}\\d\\s._-]+$");
    }

    public static boolean isValidWifiKey(String name) {
        return name.matches("^[\\p{L}\\d\\s._-]+$");
    }

    public static boolean isIp4Pattern(String ip) {
        return ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    }

    public static boolean isValidIp4(String ip) {
        if (isIp4Pattern(ip)) {
            String[] sp = ip.split("\\.");
            for (String s : sp)
                if (!s.matches("^(\\d|[1-9]\\d|1[0-9][0-9]|(2([0-4]\\d|5[0-5])))$"))
                    return false;
        } //(?<!\S)((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\b|\.\b){7}(?!\S)
        else {
            return false;
        }
        return true;
    }

    public static boolean isValidIp6(String ip) {
        return ip.matches("(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:"
                + "|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]"
                + "{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:)"
                + "{1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|"
                + "[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:"
                + "(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]?|::(ffff(:0{1,4})?:)?((25[0-5]|"
                + "(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|"
                + "([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))");
    }

    public static boolean isMacPattern(String mac) {
        return mac.matches("..:..:..:..:..:..");
    }

    public static boolean isValidMac(String mac) {
        return mac.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
    }

    public static boolean isValidEmail(String email) {
        //String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"
        //+ "\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@"
                + "((\\[(\\d|[1-9]\\d|1[0-9][0-9]|(2([0-4]\\d|5[0-5])))\\."
                + "(\\d|[1-9]\\d|1[0-9][0-9]|(2([0-4]\\d|5[0-5])))\\."
                + "(\\d|[1-9]\\d|1[0-9][0-9]|(2([0-4]\\d|5[0-5])))\\."
                + "(\\d|[1-9]\\d|1[0-9][0-9]|(2([0-4]\\d|5[0-5])))\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidMobile(String mob) {
        mob = LocalHelper.convertNumbersToLocal(mob, "en");
        String ePattern = "^09[0-9]{9}$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(mob);
        return m.matches();
    }

    public static boolean isValidNationalCode(String nc) {

        String ePattern = "^[0-9]{10}$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(nc);

        if (!m.matches())
            return false;

        int check = Integer.parseInt(nc.substring(9, 10));

        int sum = 0;
        int i;
        for (i = 0; i < 9; ++i) {
            sum += Integer.parseInt(nc.charAt(i) + "") * (10 - i);
        }
        sum %= 11;

        return (sum < 2 && check == sum) || (sum >= 2 && check + sum == 11);
    }

    public static boolean isJson(String text) {
        if (text == null)
            return false;

        Pattern pat = Pattern.compile("^\\s*\\{.*?\\}\\s*$", Pattern.DOTALL);

        return pat.matcher(text).matches();
    }

    public static boolean isArrayJson(String text) {
        //PatternModel pat = PatternModel.compile("^\\s*\\[\\{.*?\\}\\]\\s*$", PatternModel.DOTALL);
        Pattern pat = Pattern.compile("^\\s*\\[(\\{.*?\\},*)+\\]\\s*$", Pattern.DOTALL);

        return pat.matcher(text).matches();
    }

    public static boolean haveUpperCase(String str) {
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    public static boolean haveLowerCase(String str) {
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isLowerCase(ch)) {
                return true;
            }
        }
        return false;
    }

    public static boolean haveNumber(String str) {
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
    }

}
