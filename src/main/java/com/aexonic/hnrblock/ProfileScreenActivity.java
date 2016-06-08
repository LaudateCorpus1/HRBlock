package com.aexonic.hnrblock;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Parikshit Patil on 12/15/2015.
 */
public class ProfileScreenActivity extends ActionBarActivity
{
    Toolbar toolbar;
    Button btnprofile;
    CircleImageView profilepic;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    protected static final int CAMERA_REQUEST = 10;
    final int CROP_PIC = 2;
    private static final int PICK_IMAGE_ID = 234;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    Uri selectedImage;

    String picturePath,finalimagename;
    Button create_profile_btn;
    String finaldate,encodedImage;
    String sfullname,sbirthdayday,sbirthdaymonth,sbirthdayyear,semail,smobilenumber;
    EditText etfullname,etbirthdayday,etbirthdaymonth,etbirthdayyear,etemail,etmobileno;
    private Bitmap yourbitmap;
    byte[] image;
    Calendar calendar;// = Calendar.getInstance();
    int currentyear;// = calendar.get(Calendar.YEAR);
    int databasecount;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;//= getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor; //= settings.edit();

    int rotatedimage;
    //database....
    DatabaseHandler databaseHandler;
    Bitmap finalbitmap,anglebitmap,camerabitmap;
    Uri NewImageCrop,FixUriFinal,Forphoto;
    int orientation;
    String compress;
    //String picturePath;

    String android_id;
    ConnectionDetector cd;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    //android_id,sfullname,finaldate,semail,smobilenumber

    String encodesfullname,encodefinaldate,encodeemail,encodemobilenumber;
    //private static byte[] key = {0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79};//"thisIsASecretKey";
    String key="hnrblock123@612#";
    String contentx;
    long totalSize = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screenv1);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        cd = new ConnectionDetector(getApplicationContext());

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        calendar= Calendar.getInstance();
        currentyear=calendar.get(Calendar.YEAR);

        etfullname=(EditText)findViewById(R.id.etfullname);
        etbirthdayday=(EditText)findViewById(R.id.etdate);
        etbirthdayday.setFilters(new InputFilter[]{new InputFilterMinMax("0","31")});

        etbirthdaymonth=(EditText)findViewById(R.id.etmonth);
        etbirthdaymonth.setFilters(new InputFilter[]{new InputFilterMinMax("0","12")});

        etbirthdayyear=(EditText)findViewById(R.id.etyear);
        etbirthdayyear.setFilters(new InputFilter[]{new InputFilterMinMax(0,currentyear)});
       // etbirthdayyear.setFilters(new InputFilter[]{new InputFilterMinMaxDouble("0","2015")});
        etemail=(EditText)findViewById(R.id.etemail);
        etmobileno=(EditText)findViewById(R.id.etmobileno);
        create_profile_btn=(Button)findViewById(R.id.create_profile_btn);
       // etpf.setFilters(new InputFilter[]{new InputFilterMinMax("1", "200000")});
        databaseHandler=new DatabaseHandler(this);
//        databasecount=databaseHandler.getProfileCount();
        databasecount=1;
        if(databasecount>0)
        {
            Intent gotoinvestment=new Intent(ProfileScreenActivity.this,InvestmentScreenActivity.class);
            finish();
            startActivity(gotoinvestment);
        }
       // Toast.makeText(getApplicationContext(),"DB COUNT:"+databasecount,Toast.LENGTH_SHORT).show();

        profilepic=(CircleImageView)findViewById(R.id.profilepic);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(ProfileScreenActivity.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });
        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar.setTitleTextColor(getResources().getColor(R.color.textcolorfaint));

        create_profile_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               sfullname=etfullname.getText().toString().trim();
               sbirthdayday=etbirthdayday.getText().toString().trim();
               sbirthdaymonth=etbirthdaymonth.getText().toString().trim();
               sbirthdayyear=etbirthdayyear.getText().toString().trim();
               semail=etemail.getText().toString().trim();
               smobilenumber=etmobileno.getText().toString().trim();
               //android_id= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                android_id= Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);


                if(sfullname.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                }
                else if (!sfullname.matches("[a-zA-Z ]*"))
                {
                    Toast.makeText(getApplicationContext(),"Please enter valid name",Toast.LENGTH_LONG).show();
                }
                else if(sbirthdayday.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter date", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(sbirthdayday)<1)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
                }
                else if(sbirthdayday.length()>2)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
                }

                else if(sbirthdaymonth.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter month", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(sbirthdaymonth)<1)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid month", Toast.LENGTH_LONG).show();
                }
                else if(sbirthdaymonth.length()>2)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid month", Toast.LENGTH_LONG).show();
                }
                else if(sbirthdayyear.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter year", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(sbirthdayyear)<1920 || Integer.parseInt(sbirthdayyear)>currentyear)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid year", Toast.LENGTH_LONG).show();
                }
                else if(semail.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                }
                else if(!eMailValidation(semail))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
                }
                else if(smobilenumber.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_LONG).show();
                }
                else if(smobilenumber.length()!=10)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_LONG).show();
                }

                else
                {
                    finaldate=sbirthdayday+"-"+sbirthdaymonth+"-"+sbirthdayyear;
                    Log.d("Insert: ", "Inserting ..");


                        /*byte[] name = sfullname.getBytes("UTF-8");
                        byte[] date= finaldate.getBytes("UTF-8");
                        byte[] email=semail.getBytes("UTF-8");
                        byte[] mobilenumber=smobilenumber.getBytes("UTF-8");

                        encodesfullname = Base64.encodeToString(name, Base64.DEFAULT);
                        encodefinaldate=Base64.encodeToString(date,Base64.DEFAULT);
                        encodeemail=Base64.encodeToString(email,Base64.DEFAULT);
                        encodemobilenumber=Base64.encodeToString(mobilenumber,Base64.DEFAULT);*/

                       /* encodesfullname=CipherUtils.encrypt(sfullname.trim());
                        encodefinaldate=CipherUtils.encrypt(finaldate.trim());
                        encodeemail=CipherUtils.encrypt(semail.trim());
                        encodemobilenumber=CipherUtils.encrypt(smobilenumber.trim());*/

                   try
                   {
                       encodesfullname=SimpleCryptoNew.encrypt(key,sfullname);
                       encodefinaldate=SimpleCryptoNew.encrypt(key,finaldate);
                       encodeemail=SimpleCryptoNew.encrypt(key,semail);
                       encodemobilenumber=SimpleCryptoNew.encrypt(key,smobilenumber);
                   }
                   catch (Exception ex)
                   {

                   }

                    if(encodedImage==null)
                    {
                        encodedImage="null";
                    }

                    databaseHandler.addProfile(new ProfilePojo(android_id,encodesfullname,encodefinaldate,encodeemail,encodemobilenumber,encodedImage));

                    editor = settings.edit();
                    editor.putString("android_Id", android_id);
                    editor.commit();

                    isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent)
                    {
                     new ProfileWebservice().execute(android_id,encodedImage,encodesfullname,encodefinaldate,encodeemail,encodemobilenumber);
                     //new ProfileWebservice().execute();
                    }
                    else
                    {

                    }

                    //new ProfileWebservice().execute(android_id,encodedImage,sfullname,finaldate,semail,smobilenumber);

                    Intent gotoinvestment=new Intent(ProfileScreenActivity.this,InvestmentScreenActivity.class);
                    startActivity(gotoinvestment);
                }

            }//////
        });
    }

    public static boolean eMailValidation(String emailstring)
    {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_ID && resultCode==RESULT_OK)
        {

            yourbitmap=ImagePicker.getImageFromResult(this,resultCode,data);

            if(yourbitmap!=null)
            {
                File imageFile = getTempFile(this);
                Uri selectedImageCrop;

                boolean isCamera = (data == null ||
                        data.getData() == null  ||
                        data.getData().equals(Uri.fromFile(imageFile)));
                if (isCamera)
                {
                    /** CAMERA **/
                    selectedImageCrop = Uri.fromFile(imageFile);
                    picturePath=selectedImageCrop.getPath();
                    beginCrop(selectedImageCrop);
                    //Toast.makeText(getApplicationContext(),"Camera:"+selectedImageCrop,Toast.LENGTH_SHORT).show();
                }
                else
                {            /** ALBUM **/
                    selectedImageCrop = data.getData();
                    //selectedImageCrop=getImageUri(this,yourbitmap);
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImageCrop,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    beginCrop(selectedImageCrop);
                    // Toast.makeText(getApplicationContext(),"Gallery:"+selectedImageCrop,Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                //Toast.makeText(getApplicationContext(),"The image is too large. Please use an alternate image",Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(),"The image is not compatible. Please use an alternate image",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Please try and upload another image",Toast.LENGTH_LONG).show();
            }


        }

        else if(requestCode==Crop.REQUEST_CROP)
        {
           handleCrop(resultCode,data);
        }
        else if(resultCode==RESULT_CANCELED)
        {
            // user cancelled Image capture
            /*Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();*/
        }
        else
        {
            // failed to capture image
           /* Toast.makeText(getApplicationContext(),
                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                    .show();*/
        }
    }

    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(),"cropped"));
        Crop.of(source, destination).asSquare().start(this,Crop.REQUEST_CROP);
    }

    private void handleCrop(int resultCode, Intent result)
    {
        if (resultCode == RESULT_OK)
        {

            NewImageCrop=Crop.getOutput(result);
            compress=compressImage(NewImageCrop.toString());

            try
            {
                finalbitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(), NewImageCrop);

                ExifInterface ei = new ExifInterface(picturePath);
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                if(orientation>1)
                {
                    switch(orientation)
                    {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            anglebitmap= rotateImage(finalbitmap, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            anglebitmap=rotateImage(finalbitmap, 180);
                            break;
                        // etc.
                    }

                    //FixUriFinal=getImageUri(ProfileScreenActivity.this,anglebitmap);

                    //profilepic.setImageURI(FixUriFinal);

                    profilepic.setImageBitmap(anglebitmap);
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    anglebitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                    image=stream.toByteArray();
                    //image=compress.getBytes();
                    encodedImage= Base64.encodeToString(image,Base64.DEFAULT);
                   /* ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    anglebitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    image=stream.toByteArray();
                    encodedImage= Base64.encodeToString(image,Base64.DEFAULT);*/

                }
                else
                {
                    //Bitmap btm=MediaStore.Images.Media.getBitmap(this.getContentResolver(),NewImageCrop);
                    //profilepic.setImageURI(Crop.getOutput(result));
                    profilepic.setImageBitmap(finalbitmap);
                   /* ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    finalbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    image=stream.toByteArray();
                    encodedImage= Base64.encodeToString(image,Base64.DEFAULT);*/
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    finalbitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                    image=stream.toByteArray();
                    //image=compress.getBytes();
                    encodedImage= Base64.encodeToString(image,Base64.DEFAULT);

                }

               /* ByteArrayOutputStream stream=new ByteArrayOutputStream();
                finalbitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                image=stream.toByteArray();
                //image=compress.getBytes();
                encodedImage= Base64.encodeToString(image,Base64.DEFAULT);*/

            }
            catch (IOException i)
            {
                i.printStackTrace();
            }

           // FixUriFinal=getImageUri(ProfileScreenActivity.this,anglebitmap);

            //profilepic.setImageURI(FixUriFinal);
          /*  ByteArrayOutputStream stream=new ByteArrayOutputStream();
            finalbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            image=stream.toByteArray();
            encodedImage= Base64.encodeToString(image,Base64.DEFAULT);*/
            //encodedImage=

        }
        else if (resultCode == Crop.RESULT_ERROR)
        {
            Toast.makeText(getApplicationContext(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle)
    {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String compressImage(String imageUri)
    {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private class ProfileWebservice extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args0)
        {
            try
            {
                String deviceidstring = (String)args0[0];
                String imagestring = (String)args0[1];
                String namestring =(String)args0[2];
                String  dobstring=(String)args0[3];
                String  emailstring=(String)args0[4];
                String  mobilenostring=(String)args0[5];

              //  http://qa.hrblock.in/Mobile_App_Service/api/profile/Post_profileRecord/
                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_profileRecord/";
                String data=URLEncoder.encode("Device_id","UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(imagestring,"UTF-8");
                data += "&"+URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(namestring,"UTF-8");
                data += "&"+URLEncoder.encode("DOB","UTF-8")+"="+URLEncoder.encode(dobstring,"UTF-8");
                data += "&"+URLEncoder.encode("email_id","UTF-8")+"="+URLEncoder.encode(emailstring,"UTF-8");
                data += "&"+URLEncoder.encode("Mobile_no","UTF-8")+"="+URLEncoder.encode(mobilenostring,"UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }
            catch(Exception e)
            {
                return new String("Exception: " + e.getMessage());
               // Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            //return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
           // Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }



/*
    private class ProfileWebservice extends AsyncTask<Void, Integer, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            return uploadFile();
        }

        private String uploadFile()
        {
            String responseString = null;

            String link="http://qa.hrblock.in/Mobile_App_Service/api/profile/Post_profileRecord/";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(link);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                entity.addPart("Device_id",new StringBody(android_id));
                entity.addPart("image", new StringBody(encodedImage));
                entity.addPart("Name",new StringBody(encodesfullname));
                entity.addPart("DOB",new StringBody(encodefinaldate));
                entity.addPart("email_id",new StringBody(encodeemail));
                entity.addPart("Mobile_no",new StringBody(encodemobilenumber));



                // Extra parameters if you want to pass to server
                //entity.addPart("website",new StringBody("www.androidhive.info"));
                //entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                // httppost.setHeader("Accept", "application/json");
               httppost.setHeader("Content-Type", "application/json; charset=utf-8");
                httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

               // httppost.setHeader("Content-Length",""+totalSize);
                // httppost.setHeader("Content-Length",""+httppost.getEntity().getContentLength());
                // httppost.setHeader("Content-Length",""+httppost.getEntity().getContentLength());
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                    //Toast.makeText(UploadActivity.this, responseString, Toast.LENGTH_SHORT).show();
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }
*/



/*
    private class ProfileWebservice extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args0)
        {
            String responseString = null;

            try
            {
                String deviceidstring = (String)args0[0];
                String imagestring = (String)args0[1];
                String namestring =(String)args0[2];
                String  dobstring=(String)args0[3];
                String  emailstring=(String)args0[4];
                String  mobilenostring=(String)args0[5];
                //int mobile=Integer.parseInt(mobilenostring);

                String link="http://qa.hrblock.in/Mobile_App_Service/api/profile/Post_profileRecord/";
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(link);

               // httppost.setHeader("Content-type","application/json; charset=utf-8");
               // httppost.setHeader("Content");

                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair("Device_id",deviceidstring));
                nameValuePairs.add(new BasicNameValuePair("image",imagestring));
                nameValuePairs.add(new BasicNameValuePair("Name",namestring));
                nameValuePairs.add(new BasicNameValuePair("DOB",dobstring));
                nameValuePairs.add(new BasicNameValuePair("email_id",emailstring));
                nameValuePairs.add(new BasicNameValuePair("Mobile_no",mobilenostring));

                //StringEntity se=new StringEntity(new UrlEncodedFormEntity(nameValuePairs));


                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                httppost.setHeader("Content-Type","application/json; charset=utf-8");
                httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");

               // httppost.setHeader("Accept", "application/json");
               // httpost.setHeader("Content-Type", "application/json");
               // httpost.setHeader("Content-Length",""+se.getContentLength());
               // httppost.setHeader("Content-Length",""+httppost.getEntity().getContentLength());
                httppost.setHeader("Content-Length",""+httppost.getEntity().getContentLength());


                HttpResponse response=httpclient.execute(httppost);
                HttpEntity r_entity=response.getEntity();

                int  statusCode=response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                    //Toast.makeText(UploadActivity.this, responseString, Toast.LENGTH_SHORT).show();
                }
            }
            catch (
             e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
            catch(Exception e)
            {
                return new String("Exception: " + e.getMessage());
            }


            //return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }
*/


/*
    private class ProfileWebservice extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args0)
        {
            int abc;
            String xxx;
            Object cccc;

            try
            {
                String deviceidstring = (String)args0[0];
                String imagestring = (String)args0[1];
                String namestring =(String)args0[2];
                String  dobstring=(String)args0[3];
                String  emailstring=(String)args0[4];
                String  mobilenostring=(String)args0[5];
               // int mobile=Integer.parseInt(mobilenostring);

                //http://qa.hrblock.in/Mobile_App_Service/api/profile/Post_profileRecord/
                //String link="http://qa.hrblock.in/Mobile_App_Service/api/profile/Post_profileRecord/";
                String link="http://qa.hrblock.in/Mobile_App_Service/api/profile/Post_profileRecord/";
                String data=URLEncoder.encode("Device_id","UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(imagestring,"UTF-8");
                data += "&"+URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(namestring,"UTF-8");
                data += "&"+URLEncoder.encode("DOB","UTF-8")+"="+URLEncoder.encode(dobstring,"UTF-8");
                data += "&"+URLEncoder.encode("email_id","UTF-8")+"="+URLEncoder.encode(emailstring,"UTF-8");
                data += "&"+URLEncoder.encode("Mobile_no","UTF-8")+"="+URLEncoder.encode(mobilenostring,"UTF-8");

               // x-www-form-urlencoded;
                URL url = new URL(link);
                //HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                conn.setRequestProperty("Content-Length",""+Integer.toString(data.getBytes().length));


                //conn.setRequestProperty("Content-Length", "" +conn.getContentLength());
                conn.setUseCaches (false);
               conn.setDoInput(true);

                conn.setDoOutput(true);
                //conn.setRequestMethod("POST");

             // abc= conn.getContentLength();
               // xxx=conn.getContentEncoding();
           //  cccc=conn.getContent();
               // conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                //application/json; charset=utf-8....application/x-www-form-urlencoded; charset=UTF-8’);
                //conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                //conn.setRequestProperty("Content-Length", "" +conn.getContentLength());
                //conn.setRequestProperty("Content-Language","en-US");
               // conn.setUseCaches (false);
               // conn.setDoInput(true);

                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
            //return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }
*/

}
