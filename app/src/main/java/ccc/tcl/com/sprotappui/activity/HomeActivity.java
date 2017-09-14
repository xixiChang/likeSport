package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;

import java.util.ArrayList;
import java.util.Random;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.FMSportItem;
import ccc.tcl.com.sprotappui.entity.TabEntity;
import ccc.tcl.com.sprotappui.fragment.MyFragment;
import ccc.tcl.com.sprotappui.fragment.SimpleCardFragment;
import ccc.tcl.com.sprotappui.fragment.SportFragment;
import ccc.tcl.com.sprotappui.utils.ViewFindUtils;

public class HomeActivity extends BaseActivity {
    private Context mContext = this;
    private String[] mTitles = {"运动", "运动圈", "消息", "我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private static final String TAG = "HomeActivity";

    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.home_icon_message, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        super.setToolBar(toolbar, R.string.toolbar_name_sport, false);

        SportFragment sportFragment = SportFragment.getInstance("Switch ViewPager " + mTitles[0]);

        mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + mTitles[1]));
        mFragments.add(sportFragment);
        mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + mTitles[2]));
        mFragments.add(MyFragment.getInstance());
        fragmentManager = getSupportFragmentManager();


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.home_view_pager);

        mViewPager.setAdapter(new MyPagerAdapter(fragmentManager));

        mTabLayout = (CommonTabLayout) findViewById(R.id.home_tab_layout);
        
        tl_2();
        mTabLayout.showMsg(2, 55);
        mTabLayout.setMsgMargin(0, -5, 5);
    }



    private Random mRandom = new Random();

    private void showToolBar(int position) {
        Log.d(TAG, "showToolBar: ");
        switch (position) {
            case 0:
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setTitle(R.string.toolbar_name_sport);
                break;
            case 1:
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setTitle(R.string.toolbar_name_sport_circle);
                break;
            case 2:
                toolbar.setVisibility(View.GONE);
                break;
            case 3:
                toolbar.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void tl_2() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showToolBar(position);
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 2) {
                    mTabLayout.showMsg(2, mRandom.nextInt(100) + 1);
                    UnreadMsgUtils.show(mTabLayout.getMsgView(2), mRandom.nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showToolBar(position);
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_toolbar, menu);
        return true;
    }

}
