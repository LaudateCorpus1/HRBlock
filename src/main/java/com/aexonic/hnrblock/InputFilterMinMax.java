package com.aexonic.hnrblock;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Parikshit Patil on 12/17/2015.
 */
public class InputFilterMinMax implements InputFilter {
    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {
       /* try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }*/


        // Remove the string out of destination that is to be replaced
        try
        {
        String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
        // Add the new string in
        newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());

            //String replacement = source.subSequence(start, end).toString();
           // String newVal = dest.subSequence(0, dstart).toString() + replacement + dest.subSequence(dend, dest.length()).toString();
        int input = Integer.parseInt(newVal);
        if (isInRange(min, max, input))
            return null;
       }
        catch (NumberFormatException nfe){}
            return "";
    }
    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

}


