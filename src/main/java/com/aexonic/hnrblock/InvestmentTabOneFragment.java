package com.aexonic.hnrblock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Parikshit Patil on 12/17/2015.
 */
public class InvestmentTabOneFragment extends Fragment implements Animation.AnimationListener
{
    int color;
    //row layouts...
    LinearLayout hlprowclick,ppfrowclick,sssrowclick,vpfrowclick,elssrowclick,avenuesrowclick,recommendationlayout,npsrowclick;
   //hiding pannel layouts....
    LinearLayout hlppannel,ppfpannel,ssspannel,vpfpannel,elsspannel,avenuespannel,npspannel;
    Animation hlpUp,hlpDown,ppfUp,ppfDown;
    ScrollView scrollview;
    ImageView hlparrowdown;
    // Animation
    Animation animRotate;
    Toolbar toolbar;
    TextView toolbartv;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    int housingloan=0;
    int remaininginvestment=0;
    int total=0;
    TextView recommendationtv;

   // You can invest in the following to cover your additional 20k investment

    String reccom1="You can invest in the following to cover your additional ";
    String reccom2="0 ";
    String reccom3=" investment";
    String pricereccom,finalreccom;

    public InvestmentTabOneFragment()
    {

    }

    @SuppressLint("ValidFragment")
    public InvestmentTabOneFragment(int color)
    {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_investment_tabone, container, false);

       /* toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbartv=(TextView)toolbar.findViewById(R.id.toolbartv);
        String heading="My Investment";
        toolbartv.setText(heading);*/

        //Row click Ids...
        settings=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        housingloan=settings.getInt("housingloan",0);
        remaininginvestment=settings.getInt("investment",0);
        total=settings.getInt("total",0);


        DecimalFormat formatter = new DecimalFormat("#,###,###");
        reccom2 = formatter.format(remaininginvestment);

        //reccom2=String.valueOf(remaininginvestment);
        recommendationtv=(TextView)view.findViewById(R.id.recommendationtv);
        pricereccom="<font color='#7dc243'>"+"₹"+reccom2+"</font>";
        finalreccom=reccom1+pricereccom+reccom3;

        recommendationtv.setText(Html.fromHtml(finalreccom));

        recommendationlayout=(LinearLayout)view.findViewById(R.id.recommendationlayout);
        hlprowclick=(LinearLayout)view.findViewById(R.id.hlprowclick);
        ppfrowclick=(LinearLayout)view.findViewById(R.id.ppfrowclick);
        sssrowclick=(LinearLayout)view.findViewById(R.id.sssrowclick);
        vpfrowclick=(LinearLayout)view.findViewById(R.id.vpfrowclick);
        elssrowclick=(LinearLayout)view.findViewById(R.id.elssrowclick);
        avenuesrowclick=(LinearLayout)view.findViewById(R.id.avenuerowclick);
        npsrowclick=(LinearLayout)view.findViewById(R.id.npsrowclick);

        hlparrowdown=(ImageView)view.findViewById(R.id.hlparrowdown);

       // Toast.makeText(getActivity(),"In"+total,Toast.LENGTH_SHORT).show();

        /*if(remaininginvestment==0)
        {
            recommendationlayout.setVisibility(View.GONE);

           //Toast.makeText(getActivity(),"Call",Toast.LENGTH_SHORT).show();
        }*/
        if(total>=150000)
        {
            recommendationlayout.setVisibility(View.GONE);

        }
        else if(total==0)
        {

            recommendationlayout.setVisibility(View.VISIBLE);
            remaininginvestment=150000;
            formatter = new DecimalFormat("#,###,###");
            reccom2 = formatter.format(remaininginvestment);

            //reccom2=String.valueOf(remaininginvestment);
            recommendationtv=(TextView)view.findViewById(R.id.recommendationtv);
            pricereccom="<font color='#7dc243'>"+"₹"+reccom2+"</font>";
            finalreccom=reccom1+pricereccom+reccom3;

            recommendationtv.setText(Html.fromHtml(finalreccom));
        }

        /*else if(total==0)
        {
            recommendationlayout.setVisibility(View.GONE);

        }*/


        //hide pannel IDs..
        hlppannel=(LinearLayout)view.findViewById(R.id.hlphidepannel);
        ppfpannel=(LinearLayout)view.findViewById(R.id.ppfhidepannel);
        ssspannel=(LinearLayout)view.findViewById(R.id.ssspannel);
        vpfpannel=(LinearLayout)view.findViewById(R.id.vpfhidepannel);
        elsspannel=(LinearLayout)view.findViewById(R.id.elsshidepannel);
        avenuespannel=(LinearLayout)view.findViewById(R.id.avenueshidepannel);
        npspannel=(LinearLayout)view.findViewById(R.id.npspannel);
        scrollview=(ScrollView)view.findViewById(R.id.scrollview);
        scrollview.fullScroll(ScrollView.FOCUS_UP);

        // load the animation
        animRotate = AnimationUtils.loadAnimation(getActivity(),
                R.anim.rotate_animation);

        hlpUp= AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        hlpDown=AnimationUtils.loadAnimation(getActivity(),R.anim.slide_down);
        hlpDown.setAnimationListener(InvestmentTabOneFragment.this);
        hlpUp.setAnimationListener(InvestmentTabOneFragment.this);
        ppfUp=AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        ppfDown=AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
        ppfUp.setAnimationListener(InvestmentTabOneFragment.this);
        ppfDown.setAnimationListener(InvestmentTabOneFragment.this);
        animRotate.setAnimationListener(InvestmentTabOneFragment.this);

        if(housingloan==0)
        {
            hlprowclick.setVisibility(View.GONE);
        }

        hlprowclick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(hlppannel.getVisibility()==View.VISIBLE)
                {
                    hlppannel.setVisibility(View.GONE);
                   // hlppannel.startAnimation(hlpUp);
                }
                else
                {
                    hlppannel.setVisibility(View.VISIBLE);
                   // hlppannel.startAnimation(hlpDown);
                    //hlparrowdown.startAnimation(animRotate);
                    hlppannel.requestFocus();

                    //Others pannel
                    ppfpannel.setVisibility(View.GONE);
                    ssspannel.setVisibility(View.GONE);
                    vpfpannel.setVisibility(View.GONE);
                    elsspannel.setVisibility(View.GONE);
                    avenuespannel.setVisibility(View.GONE);
                    npspannel.setVisibility(View.GONE);

                }
            }
        });

        ppfrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(ppfpannel.getVisibility()==View.VISIBLE)
                {
                   ppfpannel.setVisibility(View.GONE);
                    //ppfpannel.startAnimation(ppfUp);
                }
                else
                {
                    ppfpannel.setVisibility(View.VISIBLE);
                    //ppfpannel.startAnimation(ppfDown);
                    ppfpannel.requestFocus();
                    //Others pannel
                    hlppannel.setVisibility(View.GONE);
                    ssspannel.setVisibility(View.GONE);
                    vpfpannel.setVisibility(View.GONE);
                    elsspannel.setVisibility(View.GONE);
                    avenuespannel.setVisibility(View.GONE);
                    npspannel.setVisibility(View.GONE);

                }
            }
        });

        sssrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(ssspannel.getVisibility()==View.VISIBLE)
                {
                    ssspannel.setVisibility(View.GONE);
                }
                else
                {
                    ssspannel.setVisibility(View.VISIBLE);
                    ssspannel.requestFocus();


                    //Others pannel
                    ppfpannel.setVisibility(View.GONE);
                    hlppannel.setVisibility(View.GONE);
                    vpfpannel.setVisibility(View.GONE);
                    elsspannel.setVisibility(View.GONE);
                    avenuespannel.setVisibility(View.GONE);
                    npspannel.setVisibility(View.GONE);

                }

            }
        });

        vpfrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               if(vpfpannel.getVisibility()==View.VISIBLE)
               {
                   vpfpannel.setVisibility(View.GONE);
               }
               else
               {
                   vpfpannel.setVisibility(View.VISIBLE);
                   vpfpannel.requestFocus();

                   //Others pannel
                   ppfpannel.setVisibility(View.GONE);
                   ssspannel.setVisibility(View.GONE);
                   hlppannel.setVisibility(View.GONE);
                   elsspannel.setVisibility(View.GONE);
                   avenuespannel.setVisibility(View.GONE);
                   npspannel.setVisibility(View.GONE);

               }

            }
        });

        elssrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(elsspannel.getVisibility()==View.VISIBLE)
                {
                    elsspannel.setVisibility(View.GONE);
                }
                else
                {
                  elsspannel.setVisibility(View.VISIBLE);
                  elsspannel.requestFocus();

                    //Others pannel
                    ppfpannel.setVisibility(View.GONE);
                    ssspannel.setVisibility(View.GONE);
                    vpfpannel.setVisibility(View.GONE);
                    hlppannel.setVisibility(View.GONE);
                    avenuespannel.setVisibility(View.GONE);
                    npspannel.setVisibility(View.GONE);

                }

            }
        });

        avenuesrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(avenuespannel.getVisibility()==View.VISIBLE)
                {
                    avenuespannel.setVisibility(View.GONE);
                }
                else
                {
                  avenuespannel.setVisibility(View.VISIBLE);
                  avenuespannel.requestFocus();

                    //Others pannel
                    ppfpannel.setVisibility(View.GONE);
                    ssspannel.setVisibility(View.GONE);
                    vpfpannel.setVisibility(View.GONE);
                    elsspannel.setVisibility(View.GONE);
                    hlppannel.setVisibility(View.GONE);
                    npspannel.setVisibility(View.GONE);

                }
            }
        });

        npsrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             if(npspannel.getVisibility()==View.VISIBLE)
             {
                 npspannel.setVisibility(View.GONE);
             }
              else
             {
                 npspannel.setVisibility(View.VISIBLE);
                 npspannel.requestFocus();

                 ppfpannel.setVisibility(View.GONE);
                 ssspannel.setVisibility(View.GONE);
                 vpfpannel.setVisibility(View.GONE);
                 elsspannel.setVisibility(View.GONE);
                 hlppannel.setVisibility(View.GONE);
                 avenuespannel.setVisibility(View.GONE);


             }
            }
        });


        return view;

    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        if(animation==ppfDown)
        {
            hlppannel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        if(animation==hlpDown)
        {
            //Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
            //hiddenPanel.setVisibility(View.GONE);
            //hlppannel.setVisibility(View.GONE);
        }
        if (animation==hlpUp)
        {
            //Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
            hlppannel.setVisibility(View.GONE);

        }

        if (animation==ppfUp)
        {
            ppfpannel.setVisibility(View.GONE);

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
