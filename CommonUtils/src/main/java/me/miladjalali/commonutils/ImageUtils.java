package me.miladjalali.commonutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Milad Jalali on 9/16/2019.
 */

public class ImageUtils {


    public static Bitmap LoadBitmapFromUri(Uri uri) {
        BitmapFactory.Options options;

        File file = new File(uri.getPath());
        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            options.outHeight = 768;
            options.outWidth = 480;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            return bitmap;
        } catch (OutOfMemoryError e) {
            try {
                options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                options.outHeight = 768;
                options.outWidth = 480;
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                return bitmap;
            } catch (Exception excepetion) {
            }
        }
        return null;
    }

    public static Bitmap getRawImageThumb(Uri uri) {
        Bitmap b = null;

        int reqHeight = 100, reqWidth = 100;

        File file = new File(uri.getPath());
        String filename = file.getAbsolutePath();


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(filename, options);
        int height = options.outHeight;
        int width = options.outWidth;


        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        b = BitmapFactory.decodeFile(filename, options);

        return b;
    }

    public static Bitmap resizeBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static byte[] BitmapToByteArray(Bitmap bmp) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 80, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        } catch (Exception e) {

        }
        return null;
    }

    public static Bitmap ByteArrayToBitmap(byte[] bytes) {

        try {
            byte[] decodedString = Base64.decode(bytes, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap blur(Context context, Bitmap image) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            float BITMAP_SCALE = 0.25f;
            float BLUR_RADIUS = 25.0f;
            int width = Math.round(image.getWidth() * BITMAP_SCALE);
            int height = Math.round(image.getHeight() * BITMAP_SCALE);

            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

            theIntrinsic.setRadius(BLUR_RADIUS);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);

            tmpOut.copyTo(outputBitmap);

            return outputBitmap;
        }

        return null;
    }

    public static Bitmap getBitmapByFileSize(Context context, Uri uri, int imageMaxSize) {

        ContentResolver contentResolver = context.getContentResolver();
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = imageMaxSize; //1200000 1.2MP
            in = contentResolver.openInputStream(uri);

            // Decode image size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();


            int scale = 1;
            while ((options.outWidth * options.outHeight) * (1 / scale) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("TAG", "scale = " + scale + ", orig-width: " + options.outWidth + ", orig-height: " + options.outHeight);

            Bitmap resultBitmap = null;
            in = contentResolver.openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                resultBitmap = BitmapFactory.decodeStream(in, null, options);

                // resize to desired dimensions
                int height = resultBitmap.getHeight();
                int width = resultBitmap.getWidth();
                Log.d("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(resultBitmap, (int) x,
                        (int) y, true);

//                scaledBitmap.compress(Bitmap.CompressFormat.WEBP, 70, null);


                resultBitmap.recycle();
                resultBitmap = scaledBitmap;

//                System.gc();
            } else {
                resultBitmap = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("TAG", "bitmap size - width: " + resultBitmap.getWidth() + ", height: " +
                    resultBitmap.getHeight());
            return resultBitmap;
        } catch (IOException e) {
            Log.e("TAG", e.getMessage(), e);
            return null;
        }
    }

    public static String GetImageBase64InCurrentOrientationByUri(Context context, Uri uri) throws IOException {

        Bitmap bitmap = new ImageUtils().getDownsampledBitmap(context, uri, 768, 1080);

        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        if (bitmap.getWidth() < 1000)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOS);
        else if (1000 < bitmap.getWidth() && bitmap.getWidth() < 2000)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 45, byteArrayOS);
        else if (bitmap.getWidth() > 2000)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOS);

        bitmap.recycle();

        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);

    }

    public static byte[] GetImageByteAraayInCurrentOrientationByUri(Context context, Uri uri) throws IOException {

        try {
            Bitmap bitmap = new ImageUtils().getDownsampledBitmap(context, uri, 768, 1080);

            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            byte[] out = null;
            if (bitmap.getWidth() <= 1000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOS);
                out = byteArrayOS.toByteArray();
            } else if (1000 < bitmap.getWidth() && bitmap.getWidth() < 2000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 45, byteArrayOS);
                out = byteArrayOS.toByteArray();
            } else if (bitmap.getWidth() >= 2000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOS);
                out = byteArrayOS.toByteArray();
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOS);
                out = byteArrayOS.toByteArray();
            }
            bitmap.recycle();
            return out;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void ResizeImageInCurrentOrientationByUri(Context context, Uri uri) throws IOException {

        try {
            File file = new File(uri.getPath());
            FileOutputStream fOut;

            Bitmap bitmap = new ImageUtils().getDownsampledBitmap(context, uri, 768, 1080);

            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            byte[] out = null;
            if (bitmap.getWidth() <= 1000) {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
            } else if (1000 < bitmap.getWidth() && bitmap.getWidth() < 2000) {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 45, fOut);
            } else if (bitmap.getWidth() >= 2000) {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut);
            } else {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut);
            }

            fOut.flush();
            fOut.close();
            bitmap.recycle();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Bitmap getDownsampledBitmap(Context context, Uri uri, int targetWidth, int targetHeight) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options outDimens = getBitmapDimensions(context, uri);

            int sampleSize = calculateSampleSize(outDimens.outWidth, outDimens.outHeight, targetWidth, targetHeight);

            bitmap = downsampleBitmap(context, uri, sampleSize);

            File file = new File(uri.getPath());
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int rotationInDegrees = 0;
            if (rotation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotationInDegrees = 90;
            } else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotationInDegrees = 180;
            } else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotationInDegrees = 270;
            }

            Matrix matrix = new Matrix();
            if (rotation != 0) {
                matrix.preRotate(rotationInDegrees);
            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private BitmapFactory.Options getBitmapDimensions(Context context, Uri uri) throws IOException {
        BitmapFactory.Options outDimens = new BitmapFactory.Options();
        outDimens.inJustDecodeBounds = true; // the decoder will return null (na bitmap)

        InputStream is = context.getContentResolver().openInputStream(uri);
        // if Options requested only the size will be returned
        BitmapFactory.decodeStream(is, null, outDimens);
        is.close();

        return outDimens;
    }

    private int calculateSampleSize(int width, int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;

//        if (height > targetHeight || width > targetWidth) {
//
//            // Calculate ratios of height and width to requested height and
//            // width
//            final int heightRatio = Math.round((float) height / (float) targetHeight);
//            final int widthRatio = Math.round((float) width / (float) targetWidth);
//
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee
//            // a final image with both dimensions larger than or equal to the
//            // requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;♦
//        }
        inSampleSize = width / targetWidth;
        return inSampleSize;
    }

    private Bitmap downsampleBitmap(Context context, Uri uri, int sampleSize) throws IOException {

        Bitmap resizedBitmap;
        BitmapFactory.Options outBitmap = new BitmapFactory.Options();
        outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
        outBitmap.inSampleSize = sampleSize;

        InputStream is = context.getContentResolver().openInputStream(uri);
        resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
        is.close();

        return resizedBitmap;
    }
}


