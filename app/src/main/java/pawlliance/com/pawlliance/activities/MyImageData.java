package pawlliance.com.pawlliance.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyImageData {

    private byte[] imageData = null;

    // Bitmap to byte[] to imageData
    public void setImageDataFromBitmap(Bitmap image) {
        if (image != null) {
            //bitmap to byte[]
            imageData = bitmapToByte(image);
        } else {
            imageData = null;
        }
    }

    // Bitmap to byte[]
    public byte[] bitmapToByte(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap to byte[] stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] x = stream.toByteArray();
            //close stream to save memory
            stream.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Convert imageData directly to bitmap
    public Bitmap getImageDataInBitmap() {
        if (imageData != null) {
            //turn byte[] to bitmap
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
        return null;
    }

    public byte[] getImageData() {
        return imageData;
    }

    // DNC - class closing
}
