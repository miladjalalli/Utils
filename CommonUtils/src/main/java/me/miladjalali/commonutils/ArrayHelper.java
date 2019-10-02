package me.miladjalali.commonutils;


import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ArrayHelper {
    // PUBLIC
    public static final String TAG = "ArrayHelper";

    @SuppressWarnings("unchecked")
    public static <E> E[] addToArray(E[] baseArray, E addCase) {
        Object o = Array.newInstance(addCase.getClass(), baseArray.length + 1);
        E[] res = (E[]) o;
        System.arraycopy(baseArray, 0, res, 0, baseArray.length);
        res[baseArray.length] = addCase;

        return res;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removeFromArray(E[] baseArray, int index) {
        int len = baseArray.length;

        if (len == 0 || len < index)
            return baseArray;

        Object o;
        try {
            o = Array.newInstance(baseArray[0].getClass(), len - 1);
        } catch (Exception e) {
            o = Array.newInstance(Object.class, len - 1);
        }

        E[] res = (E[]) o;
        System.arraycopy(baseArray, 0, res, 0, index);
        System.arraycopy(baseArray, index + 1, res, index, len - index - 1);

        return res;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removeFromArray(E[] baseArray, Integer[] indexes) {
        int len = baseArray.length;

        if (len == 0 || 0 >= indexes.length)
            return baseArray;

        Object o;
        try {
            o = Array.newInstance(baseArray[0].getClass(), len - indexes.length);
        } catch (Exception e) {
            o = Array.newInstance(Object.class, len - indexes.length);
        }

        E[] res = (E[]) o;

        for (int idx : indexes) {
            res = removeFromArray(baseArray, idx);
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removeFromArray(E[] baseArray, E k) {
        int len = baseArray.length;

        if (!arrayIsContain(baseArray, k) || len == 0)
            return baseArray;

        int index = Arrays.asList(baseArray).indexOf(k);

        Object o;
        try {
            o = Array.newInstance(baseArray[0].getClass(), len - 1);
        } catch (Exception e) {
            o = Array.newInstance(Object.class, len - 1);
        }

        E[] res = (E[]) o;

        if (index < 0)
            return baseArray;

        System.arraycopy(baseArray, 0, res, 0, index);
        System.arraycopy(baseArray, index + 1, res, index, len - index - 1);

        return res;
    }

    public static <E> Integer[] getIndexOfItems(List baseArray, E[] items) {
        ArrayList<Integer> ind = new ArrayList<>(items.length);

        for (int i = 0; i < items.length; i++) {
            if (baseArray.contains(items[i]))
                ind.add(i);
        }

        if (ind.size() < 1)
            return new Integer[0];
        return ArrayHelper.listToArray(ind);
    }

    @SuppressWarnings("unchecked")
    public static <T> String joinArrayToString(T[] arr, String separate) {
        return TextUtils.join(separate, arr);
    }

    @SuppressWarnings("unchecked")
    public static <T> String joinListToString(List<T> a, String separate) {
        return TextUtils.join(separate, a);
    }

    @SuppressWarnings("unchecked")
    public static String[] splitArrayString(String text, String separate) {
        return TextUtils.split(text, separate);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] joinObjectArrays(T[] a, T[] b) {
        a = (a != null) ? a : (T[]) new Object[0];
        b = (b != null) ? b : (T[]) new Object[0];
        int countBoth = a.length + b.length;
        int countA = a.length;
        T[] temp = (T[]) new Object[countBoth];

        for (int i = 0; i < countA; i++)
            Array.set(temp, i, a[i]);

        for (int i = countA; i < countBoth; i++)
            Array.set(temp, i, b[i - countA]);

        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] joinArrays(E[] a, E[] b) {
        if (a == null && b == null)
            return null;


        E typeHelper = (a != null) ? a[0] : b[0];
        a = (a != null) ? a : (E[]) new Object[0];
        b = (b != null) ? b : (E[]) new Object[0];
        int countBoth = a.length + b.length;
        int countA = a.length;
        E[] temp;

        try {
            Object o = Array.newInstance(typeHelper.getClass(), countBoth);
            temp = (E[]) o;

            for (int i = 0; i < countA; i++)
                Array.set(temp, i, a[i]);

            for (int i = countA; i < countBoth; i++)
                Array.set(temp, i, b[i - countA]);
        } catch (Exception e) {
            return null;
        }

        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] listToArray(List<E> list) {
        int s;
        if (list == null || (s = list.size()) < 1)
            return null;

        E[] temp;
        E typeHelper = list.get(0);

        try {
            Object o = Array.newInstance(typeHelper.getClass(), s);
            temp = (E[]) o;

            for (int i = 0; i < list.size(); i++)
                Array.set(temp, i, list.get(i));
        } catch (Exception e) {

            return null;
        }
        return temp;
    }

    public static <E> List<E> arrayToList(E[] arr) {
        return Arrays.asList(arr);
    }

    public static <E> void joinArrayToList(List<E> list, E[] arr) {
        Collections.addAll(list, arr);
    }

    public static Boolean[] convertPrimitiveBoolArray(boolean[] arr) {
        Boolean[] res = new Boolean[arr.length];

        for (int i = 0; i < arr.length; i++)
            res[i] = arr[i];

        return res;
    }

    public static <T> boolean compareArrays(T[] array1, T[] array2) {
        boolean res = false;
        if (array1 != null && array2 != null) {
            if (array1.length == array2.length) {
                res = true;
                for (int i = 0; i < array1.length; i++) {
                    if (!array1[i].equals(array2[i])) {
                        res = false;
                        break;
                    }
                }
            }
        }

        return res;
    }

    public static <T> boolean arrayIsContain(T[] array, T cas) {
        if (array == null || cas == null || array.length == 0)
            return false;

        if (!array[0].getClass().equals(cas.getClass()))

        for (T o : array) {
            if (cas instanceof Number) {
                if (o == cas)
                    return true;
            } else if (cas instanceof String) {
                if (((String) o).trim().equalsIgnoreCase((String) cas))
                    return true;
            } else if (o.equals(cas))
                return true;
        }
        return false;
    }

    public static <T> int getIndex(T[] array, Object obj) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].equals(obj))
                return i;
        }

        return -1;
    }

    public static <T> boolean hasJoint(T[] array1, T[] array2) {
        boolean res = false;
        for (T o : array1) {
            if (arrayIsContain(array2, o))
                res = true;
            if (res)
                return true;
        }
        return false;
    }

    public static <T> Integer[] getJointIndex(T[] array1, T[] array2) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < array1.length; i++) {
            if (arrayIsContain(array2, array1[i]))
                list.add(i);
        }

        return listToArray(list);
    }

    public static <T> T[] getJointObject(T[] array1, T[] array2) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < array1.length; i++) {
            if (arrayIsContain(array2, array1[i]))
                list.add(array1[i]);
        }

        return listToArray(list);
    }

    @SuppressWarnings("all")
    public static <T> void copyTo(T[] orgArr, T[] newArr) {
        if (orgArr == null || newArr == null)
            return;

        if (!orgArr[0].getClass().equals(newArr[0].getClass()))
            return;

        newArr = Arrays.copyOf(orgArr, orgArr.length);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] getCopyOf(T[] org) {
        int L = org.length;
        T[] arr = (T[]) new Object[L];
        System.arraycopy(org, 0, arr, 0, L);

        return arr;
    }

    public static String[] convertTo(CharSequence[] arr) {
        String[] res = new String[0];

        if (arr == null)
            return res;

        res = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i].toString();
        }

        return res;
    }

    public static void reverse(byte[] array) {
        if (array == null)
            return;

        int i = 0;
        int j = array.length - 1;
        byte tmp;

        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }
}
