package me.miladjalali.commonutils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class LocalHelper
{

    public static final String TAG = "LocalHelper";

    private static final Set<String> RTL;

    static
    {
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


    public static Locale getDefaultLocale()
    {
        return Locale.getDefault();
    }


    public static String convertEnNumbersToLocal(String text, String lang)
    {
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        char[] farsiChars = {'۰','١','٢','٣','۴','۵','۶','٧','٨','٩'};

        StringBuilder builder = new StringBuilder();

        for(int i =0;i<text.length();i++)
        {
            Character c = text.charAt(i);
            if(Character.isDigit(c))
            {
                if(lang.startsWith("ar"))
                    builder.append(arabicChars[ ((int) c) -48 ]);
                else if(lang.startsWith("fa"))
                    builder.append(farsiChars[ ((int)c) -48 ]);
                else
                    builder.append(c);
            }
            else if (c == '٫' && lang.startsWith("fa") && lang.startsWith("ar"))
            {
                builder.append('،');
            }
            else
            {
                builder.append(text.charAt(i));
            }
        }

        return builder.toString();
    }

    public static String convertNumbersToLocal(String text,String forceLang)
    {
        if(text == null)
            return "";

        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        char[] farsiChars = {'۰','١','٢','۳','۴','۵','۶','٧','٨','٩'};
        char[] englishChars = {'0','1','2','3','4','5','6','7','8','9'};

        boolean rtlDir = false;

        StringBuilder builder = new StringBuilder();
        Character c;
        int idx;

        if(forceLang.startsWith("ar") || forceLang.startsWith("fa"))
            rtlDir = true;

        for(int i =0; i<text.length(); i++)
        {
            c = text.charAt(i);

            if(Character.isDigit(c))
            {
                idx = -2;

                for(int k=0; k<10; k++)
                    if(arabicChars[k] == c)
                    {
                        idx = k;
                        break;
                    }

                for(int k=0; k<10; k++)
                    if(farsiChars[k] == c)
                    {
                        idx = k;
                        break;
                    }

                for(int k=0; k<10; k++)
                    if(englishChars[k] == c)
                    {
                        idx = k;
                        break;
                    }

                if(forceLang.startsWith("ar"))
                    builder.append(arabicChars[ idx ]);
                else if(forceLang.startsWith("fa"))
                    builder.append(farsiChars[ idx ]);
                else if(forceLang.startsWith("en"))
                    builder.append(englishChars[ idx ]);
            }
            else
            {
                builder.append(text.charAt(i));
            }
        }

        return builder.toString();
    }

}

