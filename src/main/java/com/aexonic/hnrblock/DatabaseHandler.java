package com.aexonic.hnrblock;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parikshit Patil on 12/27/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hnrManager";

    // Contacts table name
    private static final String TABLE_PROFILE = "profilev1";
    private static final String TABLE_INVESTMENT = "myinvestment";
    private static final String TABLE_HRA = "hratable";
    private static final String TABLE_LTA = "ltatable";
    private static final String TABLE_MEDICAL = "medical";
    private static final String TABLE_HOUSINGLOAN = "housingloan";
    private static final String TABLE_MEDICALINSURANCE = "medicalinsurance";


    // Table Columns names of TABLE_PROFILE
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_EMAIL = "useremail";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PRO_IMAGE = "image";

    // Table Columns namesof TABLE_INVESTMENT
    private static final String KEY_INVID = "id";
    private static final String KEY_PFDEDUCTED = "pfdeducted";
    private static final String KEY_LIP = "lifeinsurance";
    private static final String KEY_HLP = "housingloan";
    private static final String KEY_CTF = "childrentution";
    private static final String KEY_STAMP = "stampduty";
   // private static final String KEY_NPS = "national";
    private static final String KEY_OTHERINV = "otherinvestment";
    private static final String KEY_TOTALINV = "totalinvestment";
    private static final String KEY_SAVEINV = "saveinvestment";
    private static final String KEY_PERCENTAGE = "percentage";
    private static final String KEY_ANDROID_ID = "androidid";

    // Table Columns namesof TABLE_HRA
    private static final String KEY_BASICSALARY = "basicsalary";
    private static final String KEY_HRA = "hra";
    private static final String KEY_RENTPAID = "rentpaid";
    private static final String KEY_ACCOMODATIONCITY = "accomodationcity";
    private static final String KEY_EXEMPTION = "excemption";
    private static final String KEY_METROBASICSALARY = "metrobsalary";
    private static final String KEY_ACTUALRECEIVED = "actualreceived";
    private static final String KEY_EXCESS = "excessrentpaid";
    private static final String KEY_MAXIMUMTAX = "maximumtax";
    private static final String KEY_MAXMAXIMUMTAX = "maxmaximumtax";

    //private static final String KEY_ANDROID_ID = "androidid";

    // Table Columns namesof TABLE_LTA
    private static final String KEY_ISLTA = "islta";
    private static final String KEY_ONVACATION = "onvacation";
    private static final String KEY_CLAIMNLTA = "claimlta";
    private static final String KEY_CLAIMNUMBER = "claimnumber";

    // Table Columns namesof TABLE_MEDICAL
    private static final String KEY_MEDICALEXPENSES = "islta";

    // Table Columns namesof TABLE_HOUSING
    private static final String KEY_HOUSINGLOANAMOUNT = "housingloanamount";

    // Table Columns namesof TABLE_MEDICALINSURANCE
    private static final String KEY_DIDHEALTHINSURANCE = "didhealth";
    //private static final String KEY_WHOM = "whomhealth";
    private static final String KEY_SELF = "self";
    private static final String KEY_SPOUSE = "spouse";
    private static final String KEY_CHILDREN = "children";
    private static final String KEY_DEPENDENT = "dependentparents";
    private static final String KEY_OTHERS = "others";
    private static final String KEY_AGE = "age";
    private static final String KEY_PREVENTIVE = "preventive";
    private static final String KEY_MEDICALCULATE = "medicalcalculate";
    private static final String KEY_PREMIUMPAID = "premiumpaid";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
       //String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRO_IMAGE +" BLOB,"+ KEY_NAME + " TEXT,"+ KEY_EMAIL +" TEXT,"+ KEY_PH_NO + " TEXT" + ")";
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_NAME + " TEXT,"+ KEY_BIRTHDAY + " TEXT,"+ KEY_EMAIL +" TEXT,"+ KEY_PH_NO + " TEXT,"+ KEY_PRO_IMAGE + " TEXT"+")";
        db.execSQL(CREATE_PROFILE_TABLE);

        String CREATE_INVESTMENT_TABLE = "CREATE TABLE " + TABLE_INVESTMENT + "("+ KEY_INVID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_PFDEDUCTED + " INTEGER,"+ KEY_LIP + " INTEGER,"+ KEY_HLP +" INTEGER,"+ KEY_CTF + " INTEGER,"+ KEY_STAMP + " INTEGER,"+ KEY_OTHERINV +" INTEGER,"+ KEY_TOTALINV +" INTEGER,"+ KEY_SAVEINV +" INTEGER,"+ KEY_PERCENTAGE + " INTEGER"+")";
        db.execSQL(CREATE_INVESTMENT_TABLE);

        String CREATE_HRA_TABLE = "CREATE TABLE " + TABLE_HRA + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_BASICSALARY + " INTEGER,"+ KEY_HRA + " INTEGER,"+ KEY_RENTPAID +" INTEGER,"+ KEY_ACCOMODATIONCITY + " TEXT,"+ KEY_EXEMPTION + " INTEGER,"+ KEY_METROBASICSALARY +" INTEGER,"+ KEY_ACTUALRECEIVED +" INTEGER,"+ KEY_EXCESS +" INTEGER,"+ KEY_MAXIMUMTAX + " INTEGER,"+ KEY_MAXMAXIMUMTAX + " INTEGER"+")";
        db.execSQL(CREATE_HRA_TABLE);
        //databaseHandler.addHRA(new HRAPojo(android_Id,basicsalary,actualhra,rentpaid,scityspinner,amountexcemption,percenthra,actualhra,minus,maximumamount));

        String CREATE_LTA_TABLE = "CREATE TABLE " + TABLE_LTA + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_ISLTA + " TEXT,"+ KEY_ONVACATION + " TEXT,"+ KEY_CLAIMNLTA +" TEXT,"+ KEY_CLAIMNUMBER + " TEXT"+")";
        db.execSQL(CREATE_LTA_TABLE);

        String CREATE_MEDICAL_TABLE = "CREATE TABLE " + TABLE_MEDICAL + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_MEDICALEXPENSES + " INTEGER"+")";
        db.execSQL(CREATE_MEDICAL_TABLE);

        String CREATE_HOUSING_TABLE = "CREATE TABLE " + TABLE_HOUSINGLOAN + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_HOUSINGLOANAMOUNT + " INTEGER"+")";
        db.execSQL(CREATE_HOUSING_TABLE);

       // String CREATE_MEDICAL_INSURANCE = "CREATE TABLE " + TABLE_MEDICALINSURANCE + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_DIDHEALTHINSURANCE +" TEXT,"+ KEY_WHOM +" TEXT,"+ KEY_AGE + " TEXT,"+ KEY_PREVENTIVE +" TEXT,"+ KEY_MEDICALCULATE +" INTEGER,"+ KEY_PREMIUMPAID +" INTEGER"+")";
        //db.execSQL(CREATE_MEDICAL_INSURANCE);

        String CREATE_MEDICAL_INSURANCE = "CREATE TABLE " + TABLE_MEDICALINSURANCE + "("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_ANDROID_ID +" TEXT,"+ KEY_DIDHEALTHINSURANCE +" TEXT,"+ KEY_SELF +" TEXT,"+ KEY_SPOUSE +" TEXT,"+ KEY_CHILDREN +" TEXT,"+ KEY_DEPENDENT +" TEXT,"+ KEY_OTHERS +" TEXT,"+ KEY_AGE + " TEXT,"+ KEY_PREVENTIVE +" TEXT,"+ KEY_MEDICALCULATE +" INTEGER,"+ KEY_PREMIUMPAID +" INTEGER"+")";
        db.execSQL(CREATE_MEDICAL_INSURANCE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
         // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVESTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HRA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSINGLOAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICALINSURANCE);
       // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addProfile(ProfilePojo profilePojo)
    {
       //byte[] img1=getBitmapAsByteArray(profilePojo.get_imBitmap());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,profilePojo.get_andoid_id());
        values.put(KEY_NAME, profilePojo.get_fullname()); // Contact Name
        values.put(KEY_BIRTHDAY,profilePojo.get_birthday());
        values.put(KEY_EMAIL, profilePojo.get_email()); // Contact Phone
        values.put(KEY_PH_NO,profilePojo.get_phonenumber());
        values.put(KEY_PRO_IMAGE,profilePojo.get_imagestring());

        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    // Getting All Contacts
    public List<ProfilePojo> getAllProfile()
    {
        List<ProfilePojo> profileList = new ArrayList<ProfilePojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                ProfilePojo profile = new ProfilePojo();
                profile.set_id(Integer.parseInt(cursor.getString(0)));
                profile.set_andoid_id(cursor.getString(1));
                profile.set_fullname(cursor.getString(2));
                profile.set_birthday(cursor.getString(3));
                profile.set_email(cursor.getString(4));
                profile.set_phonenumber(cursor.getString(5));
                profile.set_imagestring(cursor.getString(6));
               // byte[] img=cursor.getBlob(4);
               // profile.set_image(img);
                //Bitmap bitmap1= BitmapFactory.decodeByteArray(img,0,img.length);
               // profile.set_imBitmap(bitmap1);
                // Adding contact to list
                profileList.add(profile);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return profileList;
    }

    // Updating single contact
    public int updateProfile(ProfilePojo profilePojo)
    {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,profilePojo.get_andoid_id());
        values.put(KEY_NAME, profilePojo.get_fullname());
        values.put(KEY_BIRTHDAY,profilePojo.get_birthday());
        values.put(KEY_EMAIL,profilePojo.get_email());
        values.put(KEY_PH_NO, profilePojo.get_phonenumber());
        values.put(KEY_PRO_IMAGE,profilePojo.get_imagestring());

        return db.update(TABLE_PROFILE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }



    public int getProfileCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    // Adding new contact
    void addInvestment(InvestmentScreenPojo investmentScreenPojo)
    {
        //byte[] img1=getBitmapAsByteArray(profilePojo.get_imBitmap());
     //   databaseHandler.addInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,iother,total,investtl,setpercent));

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,investmentScreenPojo.get_androidid());
        values.put(KEY_PFDEDUCTED, investmentScreenPojo.get_pfdeducted()); // Contact Name
        values.put(KEY_LIP,investmentScreenPojo.get_lifeinsurance());
        values.put(KEY_HLP,investmentScreenPojo.get_housingloan());
        values.put(KEY_CTF,investmentScreenPojo.get_childrentution());
        values.put(KEY_STAMP,investmentScreenPojo.get_stampduty());
        values.put(KEY_OTHERINV,investmentScreenPojo.get_otherinvestment());
        values.put(KEY_TOTALINV,investmentScreenPojo.get_totalinvestment());
        values.put(KEY_SAVEINV,investmentScreenPojo.get_saveinvestment());
        values.put(KEY_PERCENTAGE,investmentScreenPojo.get_percentage());

        // Inserting Row
        db.insert(TABLE_INVESTMENT, null, values);
        db.close(); // Closing database connection

    }

    // Getting All Contacts
    public List<InvestmentScreenPojo> getAllInvestment()
    {
        List<InvestmentScreenPojo> investmentList = new ArrayList<InvestmentScreenPojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INVESTMENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                InvestmentScreenPojo invest = new InvestmentScreenPojo();
                invest.set_invid(Integer.parseInt(cursor.getString(0)));
                invest.set_androidid(cursor.getString(1));
                invest.set_pfdeducted(Integer.parseInt(cursor.getString(2)));
                invest.set_lifeinsurance(Integer.parseInt(cursor.getString(3)));
                invest.set_housingloan(Integer.parseInt(cursor.getString(4)));
                invest.set_childrentution(Integer.parseInt(cursor.getString(5)));
                invest.set_stampduty(Integer.parseInt(cursor.getString(6)));
                invest.set_otherinvestment(Integer.parseInt(cursor.getString(7)));
                invest.set_totalinvestment(Integer.parseInt(cursor.getString(8)));
                invest.set_saveinvestment(Integer.parseInt(cursor.getString(9)));
                invest.set_percentage(Integer.parseInt(cursor.getString(10)));

                investmentList.add(invest);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return investmentList;
    }

    //new
    // Updating single contact
    public int updateInvestment(InvestmentScreenPojo investmentScreenPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ANDROID_ID,investmentScreenPojo.get_androidid());
        values.put(KEY_PFDEDUCTED, investmentScreenPojo.get_pfdeducted()); // Contact Name
        values.put(KEY_LIP,investmentScreenPojo.get_lifeinsurance());
        values.put(KEY_HLP,investmentScreenPojo.get_housingloan());
        values.put(KEY_CTF,investmentScreenPojo.get_childrentution());
        values.put(KEY_STAMP,investmentScreenPojo.get_stampduty());
        values.put(KEY_OTHERINV,investmentScreenPojo.get_otherinvestment());
        values.put(KEY_TOTALINV,investmentScreenPojo.get_totalinvestment());
        values.put(KEY_SAVEINV,investmentScreenPojo.get_saveinvestment());
        values.put(KEY_PERCENTAGE,investmentScreenPojo.get_percentage());


        return db.update(TABLE_INVESTMENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }

    public int getInvestmentCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_INVESTMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    // Adding new contact
    void addHRA(HRAPojo hraPojo)
    {
        //byte[] img1=getBitmapAsByteArray(profilePojo.get_imBitmap());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,hraPojo.get_androidid());
        values.put(KEY_BASICSALARY, hraPojo.get_basicsalary()); // Contact Name
        values.put(KEY_HRA,hraPojo.get_hra());
        values.put(KEY_RENTPAID,hraPojo.get_rentpaid());
        values.put(KEY_ACCOMODATIONCITY,hraPojo.get_accomodationcity());
        values.put(KEY_EXEMPTION,hraPojo.get_excemption());
        values.put(KEY_METROBASICSALARY,hraPojo.get_metrobsalary());
        values.put(KEY_ACTUALRECEIVED,hraPojo.get_actualreceived());
        values.put(KEY_EXCESS,hraPojo.get_excessrentpaid());
        values.put(KEY_MAXIMUMTAX,hraPojo.get_maximumtax());
        values.put(KEY_MAXMAXIMUMTAX,hraPojo.get_maxmaximumtax());

        // Inserting Row
        db.insert(TABLE_HRA, null, values);
        db.close(); // Closing database connection

    }

    // Getting All Contacts
    public List<HRAPojo> getAllHra()
    {
        List<HRAPojo> hraList = new ArrayList<HRAPojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HRA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                HRAPojo hra = new HRAPojo();
                hra.set_id(Integer.parseInt(cursor.getString(0)));
                hra.set_androidid(cursor.getString(1));
                hra.set_basicsalary(Integer.parseInt(cursor.getString(2)));
                hra.set_hra(Integer.parseInt(cursor.getString(3)));
                hra.set_rentpaid(Integer.parseInt(cursor.getString(4)));
                hra.set_accomodationcity(cursor.getString(5));
                hra.set_excemption(Integer.parseInt(cursor.getString(6)));
                hra.set_metrobsalary(Integer.parseInt(cursor.getString(7)));
                hra.set_actualreceived(Integer.parseInt(cursor.getString(8)));
                hra.set_excessrentpaid(Integer.parseInt(cursor.getString(9)));
                hra.set_maximumtax(Integer.parseInt(cursor.getString(10)));
                hra.set_maxmaximumtax(Integer.parseInt(cursor.getString(11)));
                hraList.add(hra);

            }
            while (cursor.moveToNext());
        }
        // return contact list
        return hraList;
    }

    //new
    // Updating single contact
    public int updateHRA(HRAPojo hraPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,hraPojo.get_androidid());
        values.put(KEY_BASICSALARY, hraPojo.get_basicsalary()); // Contact Name
        values.put(KEY_HRA,hraPojo.get_hra());
        values.put(KEY_RENTPAID,hraPojo.get_rentpaid());
        values.put(KEY_ACCOMODATIONCITY,hraPojo.get_accomodationcity());
        values.put(KEY_EXEMPTION,hraPojo.get_excemption());
        values.put(KEY_METROBASICSALARY,hraPojo.get_metrobsalary());
        values.put(KEY_ACTUALRECEIVED,hraPojo.get_actualreceived());
        values.put(KEY_EXCESS,hraPojo.get_excessrentpaid());
        values.put(KEY_MAXIMUMTAX,hraPojo.get_maximumtax());
        values.put(KEY_MAXMAXIMUMTAX,hraPojo.get_maxmaximumtax());

        return db.update(TABLE_HRA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }


    public int getHRACount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_HRA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    // Adding new contact
    void addLTA(LTAPojo ltaPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,ltaPojo.get_androidid());
        values.put(KEY_ISLTA, ltaPojo.get_islta()); // Contact Name
        values.put(KEY_ONVACATION,ltaPojo.get_isvacation());
        values.put(KEY_CLAIMNLTA, ltaPojo.get_isclaimlta()); // Contact Phone
        values.put(KEY_CLAIMNUMBER,ltaPojo.get_claimnumber());

        // Inserting Row
        db.insert(TABLE_LTA, null, values);
        db.close(); // Closing database connection

    }

    // Getting All Contacts
    public List<LTAPojo> getAllLta()
    {
        List<LTAPojo> ltaList = new ArrayList<LTAPojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LTA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                LTAPojo lta = new LTAPojo();
                lta.set_id(Integer.parseInt(cursor.getString(0)));
                lta.set_androidid(cursor.getString(1));
                lta.set_islta(cursor.getString(2));
                lta.set_isvacation(cursor.getString(3));
                lta.set_isclaimlta(cursor.getString(4));
                lta.set_claimnumber(cursor.getString(5));

                ltaList.add(lta);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return ltaList;
    }

    //new
    // Updating single contact
    public int updateLTA(LTAPojo ltaPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,ltaPojo.get_androidid());
        values.put(KEY_ISLTA, ltaPojo.get_islta()); // Contact Name
        values.put(KEY_ONVACATION,ltaPojo.get_isvacation());
        values.put(KEY_CLAIMNLTA, ltaPojo.get_isclaimlta()); // Contact Phone
        values.put(KEY_CLAIMNUMBER,ltaPojo.get_claimnumber());

        return db.update(TABLE_LTA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }

    public int getLTACount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_LTA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    // Adding new contact
    void addMEDICAL(MedicalReiumPojo medicalReiumPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,medicalReiumPojo.get_androidid());
        values.put(KEY_MEDICALEXPENSES, medicalReiumPojo.get_medicalexpense()); // Contact Name


        // Inserting Row
        db.insert(TABLE_MEDICAL, null, values);
        db.close(); // Closing database connection

    }
    // Getting All Contacts
    public List<MedicalReiumPojo> getAllMedical()
    {
        List<MedicalReiumPojo> medicalReiumList = new ArrayList<MedicalReiumPojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICAL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                MedicalReiumPojo medical = new MedicalReiumPojo();
                medical.set_id(Integer.parseInt(cursor.getString(0)));
                medical.set_androidid(cursor.getString(1));
                medical.set_medicalexpense(Integer.parseInt(cursor.getString(2)));


                medicalReiumList.add(medical);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return medicalReiumList;
    }

    //new
    // Updating single contact
    public int updateMedical(MedicalReiumPojo medicalReiumPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,medicalReiumPojo.get_androidid());
        values.put(KEY_MEDICALEXPENSES, medicalReiumPojo.get_medicalexpense()); // Contact Name

        return db.update(TABLE_MEDICAL, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }

    public int getMEDICALREIMCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_MEDICAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    // Adding new contact
    void addHOUSING(HousingLoanPojo housingLoanPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,housingLoanPojo.get_androidid());
        values.put(KEY_HOUSINGLOANAMOUNT, housingLoanPojo.get_housingloan()); // Contact Name

        // Inserting Row
        db.insert(TABLE_HOUSINGLOAN, null, values);
        db.close(); // Closing database connection

    }

    // Getting All Contacts
    public List<HousingLoanPojo> getAllHousing()
    {
        List<HousingLoanPojo> housingList = new ArrayList<HousingLoanPojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HOUSINGLOAN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                HousingLoanPojo housing = new HousingLoanPojo();
                housing.set_id(Integer.parseInt(cursor.getString(0)));
                housing.set_androidid(cursor.getString(1));
                housing.set_housingloan(Integer.parseInt(cursor.getString(2)));


                housingList.add(housing);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return housingList;
    }

    //new
    // Updating single contact
    public int updateHOUSINGLOAN(HousingLoanPojo housingLoanPojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,housingLoanPojo.get_androidid());
        values.put(KEY_HOUSINGLOANAMOUNT, housingLoanPojo.get_housingloan()); // Contact Name

        return db.update(TABLE_HOUSINGLOAN, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }

    public int getHOUSINGLOANCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_HOUSINGLOAN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    // Adding new contact
   // TABLE_HEALTHINSURANCE
    void addHEALTHINSURANCE(HealthInsurancePojo healthInsurancePojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,healthInsurancePojo.get_androidid());
        values.put(KEY_DIDHEALTHINSURANCE, healthInsurancePojo.get_didhealth()); //Contact Name
        values.put(KEY_SELF,healthInsurancePojo.get_selfcheck());
        values.put(KEY_SPOUSE,healthInsurancePojo.get_spousecheck());
        values.put(KEY_CHILDREN,healthInsurancePojo.get_childrencheck());
        values.put(KEY_DEPENDENT,healthInsurancePojo.get_dependentparents());
        values.put(KEY_OTHERS,healthInsurancePojo.get_others());
        values.put(KEY_AGE,healthInsurancePojo.get_age());
        values.put(KEY_PREVENTIVE,healthInsurancePojo.get_preventive());
        values.put(KEY_MEDICALCULATE,healthInsurancePojo.get_medicalcalculate());
        values.put(KEY_PREMIUMPAID,healthInsurancePojo.get_premiumpaid());
        // Inserting Row
        db.insert(TABLE_MEDICALINSURANCE, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<HealthInsurancePojo> getAllHealthInsurance()
    {
        List<HealthInsurancePojo> healthlist = new ArrayList<HealthInsurancePojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICALINSURANCE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                //android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,totalbenifitrestricted,totalmedical
                HealthInsurancePojo health = new HealthInsurancePojo();
                health.set_id(Integer.parseInt(cursor.getString(0)));
                health.set_androidid(cursor.getString(1));
                health.set_didhealth(cursor.getString(2));
                health.set_selfcheck(cursor.getString(3));
                health.set_spousecheck(cursor.getString(4));
                health.set_childrencheck(cursor.getString(5));
                health.set_dependentparents(cursor.getString(6));
                health.set_others(cursor.getString(7));
                health.set_age(cursor.getString(8));
                health.set_preventive(cursor.getString(9));
                health.set_medicalcalculate(Integer.parseInt(cursor.getString(10)));
                health.set_premiumpaid(Integer.parseInt(cursor.getString(11)));
                healthlist.add(health);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return healthlist;
    }

    //new
    // Updating single contact
    public int updateHEALTHINSURANCE(HealthInsurancePojo healthInsurancePojo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANDROID_ID,healthInsurancePojo.get_androidid());
        values.put(KEY_DIDHEALTHINSURANCE, healthInsurancePojo.get_didhealth()); //Contact Name
        values.put(KEY_SELF,healthInsurancePojo.get_selfcheck());
        values.put(KEY_SPOUSE,healthInsurancePojo.get_spousecheck());
        values.put(KEY_CHILDREN,healthInsurancePojo.get_childrencheck());
        values.put(KEY_DEPENDENT,healthInsurancePojo.get_dependentparents());
        values.put(KEY_OTHERS,healthInsurancePojo.get_others());
        values.put(KEY_AGE,healthInsurancePojo.get_age());
        values.put(KEY_PREVENTIVE,healthInsurancePojo.get_preventive());
        values.put(KEY_MEDICALCULATE,healthInsurancePojo.get_medicalcalculate());
        values.put(KEY_PREMIUMPAID,healthInsurancePojo.get_premiumpaid());

        return db.update(TABLE_MEDICALINSURANCE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
    }

    public int getMEDICALINSURANCECount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_MEDICALINSURANCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }



}
