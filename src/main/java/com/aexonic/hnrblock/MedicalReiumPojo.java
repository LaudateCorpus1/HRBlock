package com.aexonic.hnrblock;

/**
 * Created by Parikshit Patil on 1/11/2016.
 */
public class MedicalReiumPojo
{
    int _id;
    String _androidid;
    int _medicalexpense;

    public MedicalReiumPojo()
    {

    }

    public MedicalReiumPojo(int _id, String _androidid, int _medicalexpense) {
        this._id = _id;
        this._androidid = _androidid;
        this._medicalexpense = _medicalexpense;
    }

    public MedicalReiumPojo(String _androidid, int _medicalexpense) {
        this._androidid = _androidid;
        this._medicalexpense = _medicalexpense;
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

    public int get_medicalexpense() {
        return _medicalexpense;
    }

    public void set_medicalexpense(int _medicalexpense) {
        this._medicalexpense = _medicalexpense;
    }
}

