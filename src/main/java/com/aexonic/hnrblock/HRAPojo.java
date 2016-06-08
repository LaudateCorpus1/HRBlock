package com.aexonic.hnrblock;

/**
 * Created by Parikshit Patil on 1/11/2016.
 */
public class HRAPojo
{

    int _id;
    String _androidid;
    int _basicsalary;
    int _hra;
    int _rentpaid;
    String _accomodationcity;
    int _excemption;
    int _metrobsalary;
    int _actualreceived;
    int _excessrentpaid;
    int _maximumtax;
    int _maxmaximumtax;

    public HRAPojo()
    {

    }

    public HRAPojo(int _id, String _androidid, int _basicsalary, int _hra, int _rentpaid, String _accomodationcity, int _excemption, int _metrobsalary, int _actualreceived, int _excessrentpaid, int _maximumtax, int _maxmaximumtax) {
        this._id = _id;
        this._androidid = _androidid;
        this._basicsalary = _basicsalary;
        this._hra = _hra;
        this._rentpaid = _rentpaid;
        this._accomodationcity = _accomodationcity;
        this._excemption = _excemption;
        this._metrobsalary = _metrobsalary;
        this._actualreceived = _actualreceived;
        this._excessrentpaid = _excessrentpaid;
        this._maximumtax = _maximumtax;
        this._maxmaximumtax = _maxmaximumtax;
    }

    public HRAPojo(String _androidid, int _basicsalary, int _hra, int _rentpaid, String _accomodationcity, int _excemption, int _metrobsalary, int _actualreceived, int _excessrentpaid, int _maximumtax, int _maxmaximumtax) {
        this._androidid = _androidid;
        this._basicsalary = _basicsalary;
        this._hra = _hra;
        this._rentpaid = _rentpaid;
        this._accomodationcity = _accomodationcity;
        this._excemption = _excemption;
        this._metrobsalary = _metrobsalary;
        this._actualreceived = _actualreceived;
        this._excessrentpaid = _excessrentpaid;
        this._maximumtax = _maximumtax;
        this._maxmaximumtax = _maxmaximumtax;
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

    public int get_basicsalary() {
        return _basicsalary;
    }

    public void set_basicsalary(int _basicsalary) {
        this._basicsalary = _basicsalary;
    }

    public int get_hra() {
        return _hra;
    }

    public void set_hra(int _hra) {
        this._hra = _hra;
    }

    public int get_rentpaid() {
        return _rentpaid;
    }

    public void set_rentpaid(int _rentpaid) {
        this._rentpaid = _rentpaid;
    }

    public String get_accomodationcity() {
        return _accomodationcity;
    }

    public void set_accomodationcity(String _accomodationcity) {
        this._accomodationcity = _accomodationcity;
    }

    public int get_excemption() {
        return _excemption;
    }

    public void set_excemption(int _excemption) {
        this._excemption = _excemption;
    }

    public int get_metrobsalary() {
        return _metrobsalary;
    }

    public void set_metrobsalary(int _metrobsalary) {
        this._metrobsalary = _metrobsalary;
    }

    public int get_actualreceived() {
        return _actualreceived;
    }

    public void set_actualreceived(int _actualreceived) {
        this._actualreceived = _actualreceived;
    }

    public int get_excessrentpaid() {
        return _excessrentpaid;
    }

    public void set_excessrentpaid(int _excessrentpaid) {
        this._excessrentpaid = _excessrentpaid;
    }

    public int get_maximumtax() {
        return _maximumtax;
    }

    public void set_maximumtax(int _maximumtax) {
        this._maximumtax = _maximumtax;
    }

    public int get_maxmaximumtax() {
        return _maxmaximumtax;
    }

    public void set_maxmaximumtax(int _maxmaximumtax) {
        this._maxmaximumtax = _maxmaximumtax;
    }
}
