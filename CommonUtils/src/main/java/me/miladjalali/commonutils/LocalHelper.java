package me.miladjalali.commonutils;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class LocalHelper {

    public static final String TAG = "LocalHelper";

    private static final Set<String> RTL;

    static {
        Set<String> lang = new HashSet<>();
        lang.add("ar"); // Arabic
        lang.add("dv"); // Divehi
        lang.add("fa"); // Persian (Farsi)
        lang.add("pr"); // Persian (Farsi)
        lang.add("ha"); // Hausa
        lang.add("he"); // Hebrew
        lang.add("iw"); // Hebrew (old code)
        lang.add("ji"); // Yiddish (old code)
        lang.add("ps"); // Pashto
        lang.add("ur"); // Urdu
        lang.add("yi"); // Yiddish
        RTL = Collections.unmodifiableSet(lang);
    }


    public static Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    public static String WhatIsLangOfText(String text) {

        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        char[] farsiChars = {'۰', '١', '٢', '٣', '۴', '۵', '۶', '٧', '٨', '٩'};

        for (char arabicChar : arabicChars) {
            if (text.contains(String.valueOf(arabicChar))) {
                return "ar";
            }
        }
        for (char farsiChar : farsiChars) {
            if (text.contains(String.valueOf(farsiChar))) {
                return "fa";
            }
        }
        return "fa";
    }


    public static String convertEnNumbersToFa(String text) {
        String[] faNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        if (text.length() == 0) {
            return "";
        }
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out += faNumbers[number];
            } else if (c == '٫' || c == ',') {
                out += '،';
            } else {
                out += c;
            }
        }
        return out;
    }


    public static String convertEnNumbersToLocal(String text, String lang) {

        Map<Character, Character> arabicCharsDictionary = new HashMap<>();
        arabicCharsDictionary.put('۰', '0');
        arabicCharsDictionary.put('١', '1');
        arabicCharsDictionary.put('٢', '2');
        arabicCharsDictionary.put('٣', '3');
        arabicCharsDictionary.put('٤', '4');
        arabicCharsDictionary.put('٥', '5');
        arabicCharsDictionary.put('٦', '6');
        arabicCharsDictionary.put('٧', '7');
        arabicCharsDictionary.put('٨', '8');
        arabicCharsDictionary.put('٩', '9');


        Map<Character, Character> farsiCharsDictionary = new HashMap<>();
        farsiCharsDictionary.put('۰', '0');
        farsiCharsDictionary.put('١', '1');
        farsiCharsDictionary.put('٢', '2');
        farsiCharsDictionary.put('٣', '3');
        farsiCharsDictionary.put('۴', '4');
        farsiCharsDictionary.put('۵', '5');
        farsiCharsDictionary.put('۶', '6');
        farsiCharsDictionary.put('٧', '7');
        farsiCharsDictionary.put('٨', '8');
        farsiCharsDictionary.put('٩', '9');

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (lang.startsWith("ar"))
                if(arabicCharsDictionary.containsKey(c))
                    text = text.replace(c, arabicCharsDictionary.get(c));

                else if (lang.startsWith("fa"))
                    if(farsiCharsDictionary.containsKey(c))
                        text = text.replace(c, farsiCharsDictionary.get(c));

        }

        return text;
    }


    public static String convertNumbersToLocal(String text, String forceLang) {
        if (text == null)
            return "";

        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        char[] farsiChars = {'۰', '١', '٢', '۳', '۴', '۵', '۶', '٧', '٨', '٩'};
        char[] englishChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        boolean rtlDir = false;

        StringBuilder builder = new StringBuilder();
        Character c;
        int idx;

        if (forceLang.startsWith("ar") || forceLang.startsWith("fa"))
            rtlDir = true;

        for (int i = 0; i < text.length(); i++) {
            c = text.charAt(i);

            if (Character.isDigit(c)) {
                idx = -2;

                for (int k = 0; k < 10; k++)
                    if (arabicChars[k] == c) {
                        idx = k;
                        break;
                    }

                for (int k = 0; k < 10; k++)
                    if (farsiChars[k] == c) {
                        idx = k;
                        break;
                    }

                for (int k = 0; k < 10; k++)
                    if (englishChars[k] == c) {
                        idx = k;
                        break;
                    }

                if (forceLang.startsWith("ar"))
                    builder.append(arabicChars[idx]);
                else if (forceLang.startsWith("fa"))
                    builder.append(farsiChars[idx]);
                else if (forceLang.startsWith("en"))
                    builder.append(englishChars[idx]);
            } else {
                builder.append(text.charAt(i));
            }
        }

        return builder.toString();
    }

}

