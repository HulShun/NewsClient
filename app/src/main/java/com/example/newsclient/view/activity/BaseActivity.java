package com.example.newsclient.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.newsclient.Model.utils.AppUtil;
import com.example.newsclient.R;
import com.example.newsclient.presenter.BasePresenter;
import com.example.newsclient.view.impl.IBaseViewImpl;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseViewImpl {
    public T mPresenter;


    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        setContentView(view);
        mToolBar = (Toolbar) findViewById(getToolBarId());
        setSupportActionBar(mToolBar);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.bindView(this);
        }
        init();
    }


    protected abstract int getToolBarId();

    protected abstract void init();

    protected abstract int getLayoutId();

    public T getPresenter() {
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
        return mPresenter;
    }

    protected abstract T initPresenter();


    public Toolbar getToolBar() {
        return mToolBar;
    }

    private SystemBarTintManager tintManager;

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }

    }

    @Override
    public void showNoNetWork() {

    }

    @Override
    public boolean checkNetWork() {
        return AppUtil.getInstance().isNetWorkConnected();
    }

    @Override
    public boolean isVisiable() {
        return !this.isFinishing();
    }


    @Override
    public void showSuccess() {

    }
}