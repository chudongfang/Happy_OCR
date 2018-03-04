package myapp.lenovo.viewpager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class MainActivity extends FragmentActivity implements MyOcrFragment.OcrContent{
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton ocr,document,practice,me;
    private Drawable[] drawables=new Drawable[8];
    private ColorStateList[] csls=new ColorStateList[2];
    public List<Fragment> fragments;
    private static MyDocumentFragment myDocumentFragment;

    private static final int FILECHOOSER=1;
    private static final int FILECHOOSERFORANDROID5=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setRadioGroup();
        setViewPager();
    }

    private void initView(){

        viewPager= (ViewPager) findViewById(R.id.view_pager);
        radioGroup= (RadioGroup) findViewById(R.id.radio_group);
        ocr= (RadioButton) findViewById(R.id.ocr_rb);
        document= (RadioButton) findViewById(R.id.document_rb);
        practice= (RadioButton) findViewById(R.id.practice_rb);
        me= (RadioButton) findViewById(R.id.me_rb);

        csls[0]=MainActivity.this.getResources().getColorStateList(R.color.colorDarkGray);
        csls[1]=MainActivity.this.getResources().getColorStateList(R.color.colorDarkBlue);
    }

    private void setRadioGroup() {

        drawables[0]=MainActivity.this.getResources().getDrawable(R.drawable.ocr_off);
        drawables[1]=MainActivity.this.getResources().getDrawable(R.drawable.my_document_off);
        drawables[2]=MainActivity.this.getResources().getDrawable(R.drawable.setting_off);
        drawables[3]=MainActivity.this.getResources().getDrawable(R.drawable.tab_off);
        drawables[4]=MainActivity.this.getResources().getDrawable(R.drawable.ocr_on);
        drawables[5]=MainActivity.this.getResources().getDrawable(R.drawable.my_document_on);
        drawables[6]=MainActivity.this.getResources().getDrawable(R.drawable.setting_on);
        drawables[7]=MainActivity.this.getResources().getDrawable(R.drawable.tab_on);
        for(int i=0;i<8;i++)
            drawables[i].setBounds(0,0,100,100);
        ocr.setCompoundDrawables(null,drawables[0],null,null);
        document.setCompoundDrawables(null,drawables[1],null,null);
        practice.setCompoundDrawables(null,drawables[2],null,null);
        me.setCompoundDrawables(null,drawables[3],null,null);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                resetRadioGroupDrawableColor();
                switch (i){
                    case R.id.ocr_rb:
                        //actionBar.hide();
                        ocr.setTextColor(csls[0]);
                        ocr.setCompoundDrawables(null,drawables[4],null,null);
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.document_rb:
                        //actionBar.show();
                        document.setTextColor(csls[0]);
                        document.setCompoundDrawables(null,drawables[5],null,null);
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.practice_rb:
                        //actionBar.show();
                        practice.setTextColor(csls[0]);
                        practice.setCompoundDrawables(null,drawables[6],null,null);
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.me_rb:
                        //actionBar.show();
                        me.setTextColor(csls[0]);
                        me.setCompoundDrawables(null,drawables[7],null,null);
                        viewPager.setCurrentItem(3,false);
                        break;
                }
            }

        });
    }

    private void setViewPager() {

        MyOcrFragment myOcrFragment=new MyOcrFragment();
        myDocumentFragment=new MyDocumentFragment();
        MyPracticeFragment myPracticeFragment=new MyPracticeFragment();
        MyMeFragment myMeFragment=new MyMeFragment();
        fragments=new ArrayList<>();
        fragments.add(myOcrFragment);
        fragments.add(myDocumentFragment);
        fragments.add(myPracticeFragment);
        fragments.add(myMeFragment);

        MyFragmentPagerAdapter fpg=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(fpg);
        //actionBar.hide();
        ocr.setTextColor(csls[0]);
        ocr.setCompoundDrawables(null, drawables[4], null, null);
        viewPager.setCurrentItem(0, false);
        radioGroup.check(R.id.ocr_rb);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetRadioGroupDrawableColor();
                switch (position) {
                    case 0:
                        //actionBar.hide();
                        ocr.setTextColor(csls[0]);
                        ocr.setCompoundDrawables(null, drawables[4], null, null);
                        viewPager.setCurrentItem(0, false);
                        radioGroup.check(R.id.ocr_rb);
                        break;
                    case 1:
                        //actionBar.show();
                        document.setTextColor(csls[0]);
                        document.setCompoundDrawables(null, drawables[5], null, null);
                        viewPager.setCurrentItem(1, false);
                        radioGroup.check(R.id.document_rb);
                        break;
                    case 2:
                        //actionBar.show();
                        practice.setTextColor(csls[0]);
                        practice.setCompoundDrawables(null, drawables[6], null, null);
                        viewPager.setCurrentItem(2, false);
                        radioGroup.check(R.id.practice_rb);
                        break;
                    case 3:
                        //actionBar.show();
                        me.setTextColor(csls[0]);
                        me.setCompoundDrawables(null, drawables[7], null, null);
                        viewPager.setCurrentItem(3, false);
                        radioGroup.check(R.id.me_rb);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetRadioGroupDrawableColor(){

        ocr.setCompoundDrawables(null,drawables[0],null,null);
        document.setCompoundDrawables(null,drawables[1],null,null);
        practice.setCompoundDrawables(null,drawables[2],null,null);
        me.setCompoundDrawables(null,drawables[3],null,null);
        ocr.setTextColor(csls[1]);
        document.setTextColor(csls[1]);
        practice.setTextColor(csls[1]);
        me.setTextColor(csls[1]);
    }

    @Override
    public void getOcrContent(Uri uri,String html) {
        myDocumentFragment.getOcrContent(uri,html);
    }


    @Override
    protected void onDestroy() {
        Log.d("destroy","haha");
        BmobUser.logOut();
        super.onDestroy();
    }
}
