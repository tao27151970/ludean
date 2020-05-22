package cn.ludean.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.ludean.BottomBar;
import cn.ludean.HomeFragment;
import cn.ludean.MineFragment;
import cn.ludean.R;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.WebFragment;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.fragme_home)
    FrameLayout fragmeHome;
    @BindView(R.id.bottom_bar_home)
    BottomBar bottomBarHome;
    public static HomeActivity homeActivity;

    public static HomeActivity getHomeActivity() {
        return homeActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeActivity = this;
        JPushInterface.setAlias(getApplicationContext(), 0, SharedPrefrenceUtils.getString(this, "userId"));
        Log.e("userId",SharedPrefrenceUtils.getString(this, "userId"));
//        JPushInterface.setAlias(HomeActivity.this, SharedPrefrenceUtils.getString(this, "uuid"), null);//设置标签
        Log.e("RegistrationID", JPushInterface.getRegistrationID(this));
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
        ButterKnife.bind(this);
        bottomBarHome.setContainer(R.id.fragme_home)
                .setTitleBeforeAndAfterColor("#999999", "#000080")
                .addItem(HomeFragment.class,
                        "首页",
                        R.mipmap.home_unselected,
                        R.mipmap.home)
                .addItem(WebFragment.class,
                        "任务",
                        R.mipmap.renwu_unselected,
                        R.mipmap.renwu)
                .addItem(MineFragment.class,
                        "我的",
                        R.mipmap.mine_unselected,
                        R.mipmap.mine)
                .build();
    }

    /**
     * 监听物理返回键
     */
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
