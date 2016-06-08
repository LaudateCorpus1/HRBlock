package com.aexonic.hnrblock;

/**
 * Created by Parikshit Patil on 1/11/2016.
 */
public class LTAPojo
{
    int _id;
    String _androidid;
    String _islta;
    String _isvacation;
    String _isclaimlta;
    String _claimnumber;

    public LTAPojo()
    {

    }

    public LTAPojo(int _id, String _androidid, String _islta, String _isvacation, String _isclaimlta, String _claimnumber) {
        this._id = _id;
        this._androidid = _androidid;
        this._islta = _islta;
        this._isvacation = _isvacation;
        this._isclaimlta = _isclaimlta;
        this._claimnumber = _claimnumber;
    }

    public LTAPojo(String _androidid, String _islta, String _isvacation, String _isclaimlta, String _claimnumber) {
        this._androidid = _androidid;
        this._islta = _islta;
        this._isvacation = _isvacation;
        this._isclaimlta = _isclaimlta;
        this._claimnumber = _claimnumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_androidid() {
        return _androidid;
    }

    public void set_androidid(String _androidid) {
        this._androidid = _androidid;
    }

    public String get_islta() {
        return _islta;
    }

    public void set_islta(String _islta) {
        this._islta = _islta;
    }

    public String get_isvacation() {
        return _isvacation;
    }

    public void set_isvacation(String _isvacation) {
        this._isvacation = _isvacation;
    }

    public String get_isclaimlta() {
        return _isclaimlta;
    }

    public void set_isclaimlta(String _isclaimlta) {
        this._isclaimlta = _isclaimlta;
    }

    public String get_claimnumber() {
        return _claimnumber;
    }

    public void set_claimnumber(String _claimnumber) {
        this._claimnumber = _claimnumber;
    }
}
