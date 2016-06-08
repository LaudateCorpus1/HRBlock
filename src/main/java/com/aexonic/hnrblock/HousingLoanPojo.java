package com.aexonic.hnrblock;

/**
 * Created by Parikshit Patil on 1/11/2016.
 */
public class HousingLoanPojo
{
    int _id;
    String _androidid;
    int _housingloan;

    public HousingLoanPojo()
    {

    }

    public HousingLoanPojo(int _id, String _androidid, int _housingloan) {
        this._id = _id;
        this._androidid = _androidid;
        this._housingloan = _housingloan;
    }

    public HousingLoanPojo(String _androidid, int _housingloan) {
        this._androidid = _androidid;
        this._housingloan = _housingloan;
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

    public int get_housingloan() {
        return _housingloan;
    }

    public void set_housingloan(int _housingloan) {
        this._housingloan = _housingloan;
    }
}
