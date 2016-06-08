package com.aexonic.hnrblock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parikshit Patil on 12/17/2015.
 */
public class InvestmentTabActivity extends ActionBarActivity
{
    Toolbar toolbar;
    TextView toolbartv;
    ImageView headerlogo;
    String[] titleName={"My Investments","My Tax Exemptions","My Deductions","My Summary"};
    int[] toolbarlogo={R.drawable.investment,R.drawable.nontaxsalary,R.drawable.deduction,R.drawable.summary_icon} ;

    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    int housingloan=0;
    int remaininginvestment=0;
    String android_Id;
    String gotonontaxsalarytab,gotodeductiontab,camefrommedicalreim,camefromlta,camefromhra,camefromhousing,camefrommedicalinsurance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investment_tab_activity);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbartv=(TextView)toolbar.findViewById(R.id.toolbartv);
        headerlogo=(ImageView)toolbar.findViewById(R.id.headerlogo);

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        housingloan=settings.getInt("housingloan",0);
        remaininginvestment=settings.getInt("investment",0);
        android_Id=settings.getString("android_Id","");


        final ViewPager viewPager = (ViewPager) findViewById(R.id.tab_viewpager);

        //OLD CODE............
        setupViewPager(viewPager);

        SlidingTabLayout slidingTabLayout=(SlidingTabLayout)findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.tab_indicator,android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.greencolor));
        slidingTabLayout.setDistributeEvenly(true);
        //slidingTabLayout.setElevation(0);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {

                int resIdLength=titleName.length;
                if(position<0 || position>=resIdLength)
                    return;
                String text=titleName[position];
                toolbartv.setText(text);

                int icons=toolbarlogo[position];
                headerlogo.setImageResource(icons);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        slidingTabLayout.setViewPager(viewPager);

        if(getIntent().getExtras()!=null)
        {

            camefrommedicalreim=getIntent().getExtras().getString("camefrommedicalreim");
            if(camefrommedicalreim!=null && camefrommedicalreim.equals("camefrommedicalreim"))
            {
                //Toast.makeText(getApplicationContext(), "Medical", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);

            }

            camefromlta=getIntent().getExtras().getString("camefromlta");
            if(camefromlta!=null && camefromlta.equals("camefromlta"))
            {
                //gotonontaxsalarytab=getIntent().getExtras().getString("camefromlta");
                //Toast.makeText(getApplicationContext(), gotonontaxsalarytab, Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
            }

            camefromhra=getIntent().getExtras().getString("camefromhra");
            if(camefromhra!=null && camefromhra.equals("camefromhra"))
            {

              //  gotonontaxsalarytab=getIntent().getExtras().getString("camefromhra");
                //Toast.makeText(getApplicationContext(), gotonontaxsalarytab, Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);

            }

            camefromhousing=getIntent().getExtras().getString("camefromhousing");
            if(camefromhousing!=null && camefromhousing.equals("camefromhousing"))
            {
               // gotodeductiontab=getIntent().getExtras().getString("camefromhousing");
                //Toast.makeText(getApplicationContext(), gotonontaxsalarytab, Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(2);

            }

            camefrommedicalinsurance=getIntent().getExtras().getString("camefrommedicalinsurance");
            if(camefrommedicalinsurance!=null && camefrommedicalinsurance.equals("camefrommedicalinsurance"))
            {
               // gotodeductiontab=getIntent().getExtras().getString("camefrommedicalinsurance");
               // Toast.makeText(getApplicationContext(), "dedeuction", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(2);
            }

        }
       /* if(getIntent().getExtras()!=null)
        {
            gotonontaxsalarytab=getIntent().getExtras().getString("camefromlta");
            //Toast.makeText(getApplicationContext(), gotonontaxsalarytab, Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(1);
        }
        if(getIntent().getExtras()!=null)
        {
            gotonontaxsalarytab=getIntent().getExtras().getString("camefromhra");
            //Toast.makeText(getApplicationContext(), gotonontaxsalarytab, Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(1);
        }



        if(getIntent().getExtras()!=null)
        {
            gotodeductiontab=getIntent().getExtras().getString("camefromhousing");
            //Toast.makeText(getApplicationContext(), gotonontaxsalarytab, Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(2);
        }
        if(getIntent().getExtras()!=null)
        {
            gotodeductiontab=getIntent().getExtras().getString("camefrommedicalinsurance");
            Toast.makeText(getApplicationContext(), "dedeuction", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(2);
        }*/


    }

    private void setupViewPager(ViewPager viewPager)
    {
        editor = settings.edit();
        editor.putInt("housingloan",housingloan);
        editor.putInt("investment",remaininginvestment);
        editor.putString("android_Id",android_Id);
        editor.commit();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
       // adapter.addFrag(InvestmentTabOneFragment.newInstance(1),"INVESTMENT");
        adapter.addFrag(new InvestmentTabOneFragment(getResources().getColor(R.color.white)), "INVESTMENTS");
        adapter.addFrag(new NonTaxTabtwoFragment(getResources().getColor(R.color.white)), "TAX EXEMPTIONS");
        adapter.addFrag(new DeductionTabThree(getResources().getColor(R.color.white)), "DEDUCTIONS");
        adapter.addFrag(new SummaryTabFour(getResources().getColor(R.color.white)), "SUMMARY");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }

    }

}
