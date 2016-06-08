package com.aexonic.hnrblock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Parikshit Patil on 12/22/2015.
 */
public class UpdateProfile extends ActionBarActivity
{
    Button update_profile_btn;
    Toolbar toolbar;
    CircleImageView profilepic;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_ID = 234;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    Bitmap finalbitmap,anglebitmap,camerabitmap;
    Uri NewImageCrop,FixUriFinal,Forphoto;
    int orientation;
    String picturePath,finalimagename;
    String finaldate,encodedImage;
    String sfullname,sbirthdayday,sbirthdaymonth,sbirthdayyear,semail,smobilenumber;
    EditText etfullname,etbirthdayday,etbirthdaymonth,etbirthdayyear,etemail,etmobileno;
    TextView tv_android_id;
    private Bitmap yourbitmap;
    byte[] image;
    Calendar calendar;// = Calendar.getInstance();
    int currentyear;// = calendar.get(Calendar.YEAR);
    int databasecount;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;//= getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor; //= settings.edit();

    //database....
    DatabaseHandler databaseHandler;

    //getDataVariables from sqlite....
    int _id;
    String _fullname;
    String _birthday;
    String _email;
    String _phonenumber;
    String _imagestring;
    String _androidId;
    ProfilePojo updatepojo;
    TextView toolbarTitle;
    String android_id;
    ConnectionDetector cd;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    String decodfullname,decodedate,decodeemail,decodemobilenumber;
    String encodesfullname,encodefinaldate,encodeemail,encodemobilenumber;
    String key="hnrblock123@612#";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen_update);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);


        cd = new ConnectionDetector(getApplicationContext());

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        calendar= Calendar.getInstance();
        // currentyear=String.valueOf(calendar.get(Calendar.YEAR));
        currentyear=calendar.get(Calendar.YEAR);

        profilepic=(CircleImageView)findViewById(R.id.profilepic);
        update_profile_btn=(Button)findViewById(R.id.update_profile_btn);

        tv_android_id=(TextView)findViewById(R.id.tv_android_id);
        toolbarTitle=(TextView)toolbar.findViewById(R.id.toolbarTitle);
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
        // etpf.setFilters(new InputFilter[]{new InputFilterMinMax("1", "200000")});
        databaseHandler=new DatabaseHandler(this);

        databasecount=databaseHandler.getProfileCount();
        ///Toast.makeText(getApplicationContext(),"DB COUNT:"+databasecount,Toast.LENGTH_SHORT).show();
        if(databasecount>0)
        {
            toolbarTitle.setText("Update Profile");
            update_profile_btn.setText("Update Profile");
            Log.d("Reading: ", "Reading all profiless..");

           List<ProfilePojo> profilePojoList = databaseHandler.getAllProfile();

            for (ProfilePojo cn : profilePojoList)
            {
               // String imageString=cn.get_imagestring();
                //Toast.makeText(getApplicationContext(),imageString,Toast.LENGTH_SHORT).show();
                _androidId=cn.get_andoid_id();
                _fullname=cn.get_fullname();
                _birthday=cn.get_birthday();
                _email=cn.get_email();
                _phonenumber=cn.get_phonenumber();
                _imagestring=cn.get_imagestring();

            }

            if(!_imagestring.equals("null"))
            {
                byte[] decodedString = Base64.decode(_imagestring, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profilepic.setImageBitmap(decodedByte);
            }
            // Receiving side
           /* try
            {
                byte[] name = Base64.decode(_fullname, Base64.DEFAULT);
                byte[] dob=Base64.decode(_birthday,Base64.DEFAULT);
                byte[] email=Base64.decode(_email,Base64.DEFAULT);
                byte[] number=Base64.decode(_phonenumber,Base64.DEFAULT);

                decodfullname = new String(name, "UTF-8");
                decodedate=new String(dob,"UTF-8");
                decodeemail=new String(email,"UTF-8");
                decodemobilenumber=new String(number,"UTF-8");

            }
            catch (UnsupportedEncodingException ex)
            {
                ex.printStackTrace();
            }*/

            try
            {
                /*decodfullname=SimpleCrypto.decrypt(key,_fullname);
                decodedate=SimpleCrypto.decrypt(key,_birthday);
                decodeemail=SimpleCrypto.decrypt(key,_email);
                decodemobilenumber=SimpleCrypto.decrypt(key,_phonenumber);*/

                decodfullname=SimpleCryptoNew.decrypt(key,_fullname);
                decodedate=SimpleCryptoNew.decrypt(key,_birthday);
                decodeemail=SimpleCryptoNew.decrypt(key,_email);
                decodemobilenumber=SimpleCryptoNew.decrypt(key,_phonenumber);

            }
            catch (Exception e)
            {

            }

            etfullname.setText(decodfullname);
            int firstDash = decodedate.indexOf("-"); // detect the first space character
            String _date = decodedate.substring(0, firstDash);  // get everything upto the first space character
            String _month = decodedate.substring(firstDash+1,decodedate.lastIndexOf("-")); // get everything after the first space, trimming the spaces off
            String _year=decodedate.substring(decodedate.lastIndexOf("-")+1).trim();
            etbirthdayday.setText(_date);
            etbirthdaymonth.setText(_month);
            etbirthdayyear.setText(_year);
            etemail.setText(decodeemail);
            etmobileno.setText(decodemobilenumber);
            tv_android_id.setText(_androidId);

           // Toast.makeText(getApplicationContext(),_androidId,Toast.LENGTH_SHORT).show();
        }
        else
        {
            toolbarTitle.setText("Create Profile");
            update_profile_btn.setText("Create Profile");
        }

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(UpdateProfile.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });

        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar.setTitleTextColor(getResources().getColor(R.color.textcolorfaint));


        update_profile_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent gotoinvestment=new Intent(ProfileScreenActivity.this,InvestmentScreenActivity.class);
                //startActivity(gotoinvestment);
                String buttonname=update_profile_btn.getText().toString();

                sfullname=etfullname.getText().toString().trim();
                sbirthdayday=etbirthdayday.getText().toString().trim();
                sbirthdaymonth=etbirthdaymonth.getText().toString().trim();
                sbirthdayyear=etbirthdayyear.getText().toString().trim();
                semail=etemail.getText().toString().trim();
                smobilenumber=etmobileno.getText().toString().trim();
                //android_id= Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
                android_id=tv_android_id.getText().toString();


                if(buttonname.equals("Update Profile"))
                    {
                      // onBackPressed();

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
                        else if (Integer.parseInt(sbirthdayyear)<1920 || Integer.parseInt(sbirthdayyear)>currentyear)
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

                           /* try
                            {
                                byte[] name = sfullname.getBytes("UTF-8");
                                byte[] date= finaldate.getBytes("UTF-8");
                                byte[] email=semail.getBytes("UTF-8");
                                byte[] mobilenumber=smobilenumber.getBytes("UTF-8");

                                encodesfullname = Base64.encodeToString(name, Base64.DEFAULT);
                                encodefinaldate=Base64.encodeToString(date,Base64.DEFAULT);
                                encodeemail=Base64.encodeToString(email,Base64.DEFAULT);
                                encodemobilenumber=Base64.encodeToString(mobilenumber,Base64.DEFAULT);

                            }
                            catch (UnsupportedEncodingException ex)
                            {
                                ex.printStackTrace();
                            }*/


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

                            if(_imagestring.equals("null"))
                            {
                                _imagestring="null";
                            }

                            databaseHandler.updateProfile(new ProfilePojo(android_id,encodesfullname,encodefinaldate,encodeemail,encodemobilenumber,_imagestring));
                            Toast.makeText(getApplicationContext(),"Your profile has been updated",Toast.LENGTH_SHORT).show();

                            isInternetPresent = cd.isConnectingToInternet();
                            if(isInternetPresent)
                            {
                             new UpdateWebservice().execute(android_id,_imagestring,encodesfullname,encodefinaldate,encodeemail,encodemobilenumber);

                            }
                            else
                            {

                            }

                            onBackPressed();
                        }
                    }
                    else
                    {
                        //Intent gotoinvestment=new Intent(UpdateProfile.this,InvestmentScreenActivity.class);
                        //startActivity(gotoinvestment);
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
                        else if (Integer.parseInt(sbirthdayyear)<1920 || Integer.parseInt(sbirthdayyear)>currentyear)
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
                            //Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(), "Current Year: "+currentyear, Toast.LENGTH_LONG).show();
                            finaldate=sbirthdayday+"-"+sbirthdaymonth+"-"+sbirthdayyear;
                            //Toast.makeText(getApplicationContext(), "Final Date:"+finaldate, Toast.LENGTH_LONG).show();
                            Log.d("Insert: ", "Inserting ..");
                           /* try
                            {
                                byte[] name = sfullname.getBytes("UTF-8");
                                byte[] date= finaldate.getBytes("UTF-8");
                                byte[] email=semail.getBytes("UTF-8");
                                byte[] mobilenumber=smobilenumber.getBytes("UTF-8");

                                encodesfullname = Base64.encodeToString(name, Base64.DEFAULT);
                                encodefinaldate=Base64.encodeToString(date,Base64.DEFAULT);
                                encodeemail=Base64.encodeToString(email,Base64.DEFAULT);
                                encodemobilenumber=Base64.encodeToString(mobilenumber,Base64.DEFAULT);

                            }
                            catch (UnsupportedEncodingException ex)
                            {
                                ex.printStackTrace();
                            }*/

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
                            if(_imagestring==null)
                            {
                                _imagestring="null";
                            }

                            databaseHandler.addProfile(new ProfilePojo(android_id, encodesfullname, encodefinaldate, encodeemail, encodemobilenumber, _imagestring));
                            editor = settings.edit();
                            editor.putString("android_Id", android_id);
                            editor.commit();
                            isInternetPresent = cd.isConnectingToInternet();
                            if(isInternetPresent)
                            {
                                new UpdateWebservice().execute(android_id,_imagestring,encodesfullname,encodefinaldate,encodeemail,encodemobilenumber);

                            }
                            else
                            {

                            }
                            Intent gotoinvestment=new Intent(UpdateProfile.this,InvestmentScreenActivity.class);
                            startActivity(gotoinvestment);
                            //Toast.makeText(getApplicationContext(),"Your profile has been updated",Toast.LENGTH_SHORT).show();

                        }
                    }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
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
                }
            }
            else
            {
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
           /* Toast.makeText(getApplicationContext(),
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
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result)
    {
        if (resultCode == RESULT_OK)
        {

            NewImageCrop=Crop.getOutput(result);

            try
            {
                finalbitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(), NewImageCrop);

                ExifInterface ei = new ExifInterface(picturePath);
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                if(orientation>1)
                {
                    switch(orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            anglebitmap= rotateImage(finalbitmap, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            anglebitmap=rotateImage(finalbitmap, 180);
                            break;
                        // etc.
                    }

                    //FixUriFinal=getImageUri(UpdateProfile.this,anglebitmap);

                   // profilepic.setImageURI(FixUriFinal);

                    profilepic.setImageBitmap(anglebitmap);
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    anglebitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                    image=stream.toByteArray();

                    _imagestring= Base64.encodeToString(image,Base64.DEFAULT);

                }
                else
                {
                   // profilepic.setImageURI(Crop.getOutput(result));
                    //Bitmap btm=MediaStore.Images.Media.getBitmap(this.getContentResolver(),NewImageCrop);
                    //profilepic.setImageURI(Crop.getOutput(result));
                    profilepic.setImageBitmap(finalbitmap);
                   /* ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    finalbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    image=stream.toByteArray();

                    _imagestring= Base64.encodeToString(image,Base64.DEFAULT);*/
                     ByteArrayOutputStream stream=new ByteArrayOutputStream();
                finalbitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                image=stream.toByteArray();

                _imagestring= Base64.encodeToString(image,Base64.DEFAULT);


                }

               /* ByteArrayOutputStream stream=new ByteArrayOutputStream();
                finalbitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                image=stream.toByteArray();

                _imagestring= Base64.encodeToString(image,Base64.DEFAULT);*/


            }
            catch (IOException i)
            {
                i.printStackTrace();

            }

           // FixUriFinal=getImageUri(UpdateProfile.this,anglebitmap);

           // profilepic.setImageURI(FixUriFinal);

        }
        else if (resultCode == Crop.RESULT_ERROR)
        {
            Toast.makeText(getApplicationContext(),Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
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

    private class UpdateWebservice extends AsyncTask<String,String,String>
    {

        //api/profile/Get_ProfileRecord/
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
                //int mobile=Integer.parseInt(mobilenostring);
               //String link="https://qa.hrblock.in/Mobile_App_Service/api/profile/Get_ProfileRecord";
                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_profileRecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
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
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
            //return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
           //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }

}
