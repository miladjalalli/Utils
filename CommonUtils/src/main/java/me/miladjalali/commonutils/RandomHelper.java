package me.miladjalali.commonutils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomHelper
{
    // PUBLIC
    public static final String TAG = "RandomHelper";


    public static int getRandomNumber()
    {
        SecureRandom r = new SecureRandom();
        return r.nextInt(1001);
    }

    public static int getRandomNumberByRange(int min, int max)
    {
        SecureRandom r = new SecureRandom();
        return r.nextInt(max - min) + min;
    }

    public static String randomString( int len ){
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public static float randFloat(float min, float max) {

        Random rand = new Random();

        float result = rand.nextFloat() * (max - min) + min;

        return result;

    }

}
