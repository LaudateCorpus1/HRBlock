package com.aexonic.hnrblock;

/**
 * Created by Parikshit Patil on 1/11/2016.
 */
public class HealthInsurancePojo
{
    int _id;
    String _androidid;
    String _didhealth;
    String _selfcheck;
    String _spousecheck;
    String _childrencheck;
    String _dependentparents;
    String _others;
    String _age;
    String _preventive;
    int _medicalcalculate;
    int _premiumpaid;

    public HealthInsurancePojo()
    {

    }

    public HealthInsurancePojo(int _id, String _androidid, String _didhealth, String _selfcheck, String _spousecheck, String _childrencheck, String _dependentparents, String _others, String _age, String _preventive, int _medicalcalculate, int _premiumpaid) {
        this._id = _id;
        this._androidid = _androidid;
        this._didhealth = _didhealth;
        this._selfcheck = _selfcheck;
        this._spousecheck = _spousecheck;
        this._childrencheck = _childrencheck;
        this._dependentparents = _dependentparents;
        this._others = _others;
        this._age = _age;
        this._preventive = _preventive;
        this._medicalcalculate = _medicalcalculate;
        this._premiumpaid = _premiumpaid;
    }


    public HealthInsurancePojo(String _androidid, String _didhealth, String _selfcheck, String _spousecheck, String _childrencheck, String _dependentparents, String _others, String _age, String _preventive, int _medicalcalculate, int _premiumpaid) {
        this._androidid = _androidid;
        this._didhealth = _didhealth;
        this._selfcheck = _selfcheck;
        this._spousecheck = _spousecheck;
        this._childrencheck = _childrencheck;
        this._dependentparents = _dependentparents;
        this._others = _others;
        this._age = _age;
        this._preventive = _preventive;
        this._medicalcalculate = _medicalcalculate;
        this._premiumpaid = _premiumpaid;
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

    public String get_didhealth() {
        return _didhealth;
    }

    public void set_didhealth(String _didhealth) {
        this._didhealth = _didhealth;
    }

    public String get_selfcheck() {
        return _selfcheck;
    }

    public void set_selfcheck(String _selfcheck) {
        this._selfcheck = _selfcheck;
    }

    public String get_spousecheck() {
        return _spousecheck;
    }

    public void set_spousecheck(String _spousecheck) {
        this._spousecheck = _spousecheck;
    }

    public String get_childrencheck() {
        return _childrencheck;
    }

    public void set_childrencheck(String _childrencheck) {
        this._childrencheck = _childrencheck;
    }

    public String get_dependentparents() {
        return _dependentparents;
    }

    public void set_dependentparents(String _dependentparents) {
        this._dependentparents = _dependentparents;
    }

    public String get_others() {
        return _others;
    }

    public void set_others(String _others) {
        this._others = _others;
    }

    public String get_age() {
        return _age;
    }

    public void set_age(String _age) {
        this._age = _age;
    }

    public String get_preventive() {
        return _preventive;
    }

    public void set_preventive(String _preventive) {
        this._preventive = _preventive;
    }

    public int get_medicalcalculate() {
        return _medicalcalculate;
    }

    public void set_medicalcalculate(int _medicalcalculate) {
        this._medicalcalculate = _medicalcalculate;
    }

    public int get_premiumpaid() {
        return _premiumpaid;
    }

    public void set_premiumpaid(int _premiumpaid) {
        this._premiumpaid = _premiumpaid;
    }
}
