package com.aexonic.hnrblock;

/**
 * Created by Parikshit Patil on 1/10/2016.
 */
public class InvestmentScreenPojo
{
    int _invid;
    String _androidid;
    int _pfdeducted;
    int _lifeinsurance;
    int _housingloan;
    int _childrentution;
    int _stampduty;
    int _otherinvestment;
    int _totalinvestment;
    int _saveinvestment;
    int _percentage;


    public InvestmentScreenPojo()
    {

    }

    public InvestmentScreenPojo(int _invid, String _androidid, int _pfdeducted, int _lifeinsurance, int _housingloan, int _childrentution, int _stampduty, int _otherinvestment, int _totalinvestment, int _saveinvestment, int _percentage) {
        this._invid = _invid;
        this._androidid = _androidid;
        this._pfdeducted = _pfdeducted;
        this._lifeinsurance = _lifeinsurance;
        this._housingloan = _housingloan;
        this._childrentution = _childrentution;
        this._stampduty = _stampduty;
        this._otherinvestment = _otherinvestment;
        this._totalinvestment = _totalinvestment;
        this._saveinvestment = _saveinvestment;
        this._percentage = _percentage;
    }

    public InvestmentScreenPojo(String _androidid, int _pfdeducted, int _lifeinsurance, int _housingloan, int _childrentution, int _stampduty, int _otherinvestment, int _totalinvestment, int _saveinvestment, int _percentage) {
        this._androidid = _androidid;
        this._pfdeducted = _pfdeducted;
        this._lifeinsurance = _lifeinsurance;
        this._housingloan = _housingloan;
        this._childrentution = _childrentution;
        this._stampduty = _stampduty;
        this._otherinvestment = _otherinvestment;
        this._totalinvestment = _totalinvestment;
        this._saveinvestment = _saveinvestment;
        this._percentage = _percentage;
    }

    public int get_invid() {
        return _invid;
    }

    public void set_invid(int _invid) {
        this._invid = _invid;
    }

    public String get_androidid() {
        return _androidid;
    }

    public void set_androidid(String _androidid) {
        this._androidid = _androidid;
    }

    public int get_pfdeducted() {
        return _pfdeducted;
    }

    public void set_pfdeducted(int _pfdeducted) {
        this._pfdeducted = _pfdeducted;
    }

    public int get_lifeinsurance() {
        return _lifeinsurance;
    }

    public void set_lifeinsurance(int _lifeinsurance) {
        this._lifeinsurance = _lifeinsurance;
    }

    public int get_housingloan() {
        return _housingloan;
    }

    public void set_housingloan(int _housingloan) {
        this._housingloan = _housingloan;
    }

    public int get_childrentution() {
        return _childrentution;
    }

    public void set_childrentution(int _childrentution) {
        this._childrentution = _childrentution;
    }

    public int get_stampduty() {
        return _stampduty;
    }

    public void set_stampduty(int _stampduty) {
        this._stampduty = _stampduty;
    }

    public int get_otherinvestment() {
        return _otherinvestment;
    }

    public void set_otherinvestment(int _otherinvestment) {
        this._otherinvestment = _otherinvestment;
    }

    public int get_totalinvestment() {
        return _totalinvestment;
    }

    public void set_totalinvestment(int _totalinvestment) {
        this._totalinvestment = _totalinvestment;
    }

    public int get_saveinvestment() {
        return _saveinvestment;
    }

    public void set_saveinvestment(int _saveinvestment) {
        this._saveinvestment = _saveinvestment;
    }

    public int get_percentage() {
        return _percentage;
    }

    public void set_percentage(int _percentage) {
        this._percentage = _percentage;
    }
}
