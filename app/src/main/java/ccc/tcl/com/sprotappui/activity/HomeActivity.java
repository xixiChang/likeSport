package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;

import java.util.ArrayList;
import java.util.Random;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.entity.TabEntity;
import ccc.tcl.com.sprotappui.fragment.MyFragment;
import ccc.tcl.com.sprotappui.fragment.SimpleCardFragment;
import ccc.tcl.com.sprotappui.fragment.SportCircleFragment;
import ccc.tcl.com.sprotappui.fragment.SportFragment;
import ccc.tcl.com.sprotappui.service.IMService;
import ccc.tcl.com.sprotappui.utils.ViewFindUtils;

import static ccc.tcl.com.sprotappui.App.userInfo;
import static ccc.tcl.com.sprotappui.service.IMService.mIMKit;


public class HomeActivity extends BaseActivity {

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private static final String TAG = "HomeActivity";

    private String[] mTitles = {"运动", "运动圈", "消息", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.table_run_unclick, R.mipmap.tab_circle_unclick,
            R.mipmap.tab_message_unclick, R.mipmap.tab_mine_unclick};
    private int[] mIconSelectIds = {
            R.mipmap.table_run_click, R.mipmap.tab_circle_click,
            R.mipmap.tab_message_click, R.mipmap.tab_mine_click};

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
        initIMConnect();
        initView();
        //setFullScreen(true);
    }


    private void initIMConnect() {
        if (mIMKit == null){
            new IMService();
        }
        try {
            connect();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
    }

    private void initView() {
        mFragments.add(SportFragment.getInstance());
        SportCircleFragment sportCircleFragment = SportCircleFragment.getInstance();
        mFragments.add(sportCircleFragment);
        if (mIMKit != null){
            mFragments.add(mIMKit.getConversationFragment());
        }else {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + mTitles[2]));
        }
        mFragments.add(MyFragment.getInstance());
        fragmentManager = getSupportFragmentManager();

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.home_view_pager);

        mViewPager.setAdapter(new MyPagerAdapter(fragmentManager));
        mTabLayout = (CommonTabLayout) findViewById(R.id.home_tab_layout);
        setTabLayout();
        mTabLayout.showMsg(2, 55);
        mTabLayout.setMsgMargin(0, -5, 5);



//        Window window = this.getWindow();
//        //设置透明状态栏,这样才能让 ContentView 向上
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        window.setStatusBarColor(Color.TRANSPARENT);
//
//        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
//        View mChildView = mContentView.getChildAt(0);
//        if (mChildView != null) {
//            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
//            ViewCompat.setFitsSystemWindows(mChildView, false);
//        }
    }

    private Random mRandom = new Random();

    private void showToolBar(int position) {
        Log.d(TAG, "showToolBar: ");
        switch (position) {
            case 0:
                toolbar.setVisibility(View.GONE);
//                toolbar.setTitle(R.string.toolbar_name_sport);
                break;
            case 1:
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setTitle(R.string.toolbar_name_sport_circle);
                break;
            case 2:
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setTitle(R.string.toolbar_name_contacts);
                break;
            case 3:
                toolbar.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void setTabLayout() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                startActivity(new Intent(mContext, TestActivity.class));
                return true;
            default:
                break;
        }
        return true;
    }

    private void connect() {
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userInfo.getIm_uid(),
                userInfo.getSession());
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                Log.i(TAG, "onSuccess: " + arg0);
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onProgress: ");
            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                Log.e(TAG, "onError: " + errCode + ">" + description);
            }
        });
    }


}
