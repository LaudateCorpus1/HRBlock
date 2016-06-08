package com.aexonic.hnrblock;

import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by Parikshit Patil on 12/27/2015.
 */
public class ProfilePojo
{
    int _id;
    String _andoid_id;
    String _fullname;
    String _birthday;
    String _email;
    String _phonenumber;
    String _imagestring;

    public ProfilePojo()
    {

    }

    public ProfilePojo(int _id, String _andoid_id, String _fullname, String _birthday, String _email, String _phonenumber, String _imagestring) {
        this._id = _id;
        this._andoid_id = _andoid_id;
        this._fullname = _fullname;
        this._birthday = _birthday;
        this._email = _email;
        this._phonenumber = _phonenumber;
        this._imagestring = _imagestring;
    }

    public ProfilePojo(String _andoid_id, String _fullname, String _birthday, String _email, String _phonenumber, String _imagestring) {
        this._andoid_id = _andoid_id;
        this._fullname = _fullname;
        this._birthday = _birthday;
        this._email = _email;
        this._phonenumber = _phonenumber;
        this._imagestring = _imagestring;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_andoid_id() {
        return _andoid_id;
    }

    public void set_andoid_id(String _andoid_id) {
        this._andoid_id = _andoid_id;
    }

    public String get_fullname() {
        return _fullname;
    }

    public void set_fullname(String _fullname) {
        this._fullname = _fullname;
    }

    public String get_birthday() {
        return _birthday;
    }

    public void set_birthday(String _birthday) {
        this._birthday = _birthday;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_phonenumber() {
        return _phonenumber;
    }

    public void set_phonenumber(String _phonenumber) {
        this._phonenumber = _phonenumber;
    }

    public String get_imagestring() {
        return _imagestring;
    }

    public void set_imagestring(String _imagestring) {
        this._imagestring = _imagestring;
    }
}
