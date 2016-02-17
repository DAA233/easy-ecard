package com.duang.easyecard.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.duang.easyecard.GlobalData.MyApplication;
import com.duang.easyecard.GlobalData.UrlConstant;
import com.duang.easyecard.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ManageTradingInquiryActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private AsyncHttpClient httpClient;
    private ViewPagerAdapter mViewPagerAdapter;

    private ScrollView pickDateView;
    private ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_trading_inquiry);
        // 初始化布局
        initView();
        // 初始化数据
        initData();
    }
    // 初始化布局
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // 显示home按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 设置ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Assigns the ViewPager to TabLayout.
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    // 初始化数据
    private void initData() {
        // 获得全局变量httpClient
        MyApplication myApp = (MyApplication) getApplication();
        httpClient = myApp.getHttpClient();
        // 装填POST数据
        RequestParams params = new RequestParams();
        params.add("needHeader", "false");
        // 发送POST请求
        httpClient.post(UrlConstant.TRJN_QUERY, params, new AsyncHttpResponseHandler() {
            // 成功响应，刷新全局httpClient
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyApplication myApp = (MyApplication) getApplication();
                myApp.setHttpClient(httpClient);
            }
            // 网络错误
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                Toast.makeText(ManageTradingInquiryActivity.this, R.string.network_error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Defines the number of tabs by setting appropriate fragment and tab name.
    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new ManageTradingInquiryHistoryFragment(),
                getResources().getString(R.string.history_trading_inquiry));
        mViewPagerAdapter.addFragment(new SettingsFragment(),
                getResources().getString(R.string.day_trading_inquiry));
        mViewPagerAdapter.addFragment(new SettingsFragment(),
                getResources().getString(R.string.week_trading_inquiry));
        viewPager.setAdapter(mViewPagerAdapter);
    }
    // Custom adapter class provides fragments required for the view pager.
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void addFragment(int position, Fragment fragment, String title) {
            mFragmentList.add(position, fragment);
            mFragmentTitleList.add(position, title);
        }

        public void removeFragment(int position) {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
        }

        public void clearFragments() {
            mFragmentList.clear();
            mFragmentTitleList.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
