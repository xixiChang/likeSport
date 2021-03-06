package ccc.tcl.com.sprotappui.activity;

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
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.entity.TabEntity;
import ccc.tcl.com.sprotappui.fragment.MyFragment;
import ccc.tcl.com.sprotappui.fragment.NewSportFragment;
import ccc.tcl.com.sprotappui.fragment.SportCircleFragment;
import ccc.tcl.com.sprotappui.service.IMService;
import ccc.tcl.com.sprotappui.utils.ViewFindUtils;

import static ccc.tcl.com.sprotappui.App.userInfo;
import static ccc.tcl.com.sprotappui.service.IMService.mIMKit;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private static final String TAG = "HomeActivity";

    private String[] types = {"步行", "跑步", "骑行"};
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

    private TextView sportTypeSpinner;
    private NormalListDialog normalListDialog;
    private NewSportFragment newsportFragment;

    private MenuItem menuItem;

    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar, "" , false);

        initIMConnect();
        initView();
    }


    private void initIMConnect() {
        if (mIMKit == null) {
            new IMService();
        }
        try {
            connect();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
    }

    private void initView() {
        sportTypeSpinner = (TextView) findViewById(R.id.home_sport_type);

        newsportFragment = NewSportFragment.getInstance();
        mFragments.add(newsportFragment);

        SportCircleFragment sportCircleFragment = SportCircleFragment.getInstance();
        mFragments.add(sportCircleFragment);


        mFragments.add(mIMKit.getConversationFragment());

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

        sportTypeSpinner.setOnClickListener(this);
    }

    private void showToolBar(int position) {
        switch (position) {
            case 0:
                toolbar.setVisibility(View.VISIBLE);
                sportTypeSpinner.setVisibility(View.VISIBLE);
                menuItem.setIcon(R.mipmap.before_record);
                menuItem.setVisible(true);
                toolbar.setTitle("");
                break;
            case 1:
                toolbar.setVisibility(View.VISIBLE);
                sportTypeSpinner.setVisibility(View.GONE);
                menuItem.setIcon(R.mipmap.ic_pick_pic);
                menuItem.setVisible(true);
                toolbar.setTitle(R.string.toolbar_name_sport_circle);
                break;
            case 2:
                toolbar.setVisibility(View.VISIBLE);
                sportTypeSpinner.setVisibility(View.GONE);
                menuItem.setVisible(false);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_sport_type:
                showPopupWindow();
                break;
            default:
                break;
        }
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
        menuItem = menu.getItem(0);
        return true;
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    //    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                int position = mViewPager.getCurrentItem();
                if (position == 2)
                    return true;
                Intent create = new Intent();
                if (position == 0){
                    create = new Intent(this, RecordActivity.class);
                }
                else if (position == 1){
                    create = new Intent(this, PickPictureActivity.class);
                }
                startActivity(create);
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


    private void showPopupWindow() {
        if (normalListDialog == null) {
            normalListDialog = new NormalListDialog(this, types);
            normalListDialog.isTitleShow(false);
            normalListDialog.widthScale(0.7f);
            normalListDialog.layoutAnimation(null);
//            normalListDialog.showAtLocation(Gravity.TOP, 0, 50);
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (normalListDialog.isShowing())
                        normalListDialog.superDismiss();
                    sportTypeSpinner.setText(types[position]);
                    newsportFragment.setSport_Type(position);
                }
            });

        }
        normalListDialog.show();
    }

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
