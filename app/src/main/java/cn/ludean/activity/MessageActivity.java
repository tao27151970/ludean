package cn.ludean.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ludean.R;
import cn.ludean.activity.message.MessageFragment;
import cn.ludean.adapter.MViewPagerAdapter;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.tit_back)
    ImageView titBack;
    @BindView(R.id.message_tab)
    SlidingTabLayout messageTab;
    @BindView(R.id.message_view_pager)
    ViewPager messageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
        titBack.setOnClickListener(v -> finish());
        ArrayList<String> tabs = new ArrayList<>();
        String functionType = getIntent().getStringExtra("functionType");

        ArrayList<Fragment> fragments = new ArrayList<>();
        tabs.add("公告");
        tabs.add("消息");
        MessageFragment merchantFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        merchantFragment.setArguments(bundle);
        MessageFragment merchantFragment1 = new MessageFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 1);
        merchantFragment1.setArguments(bundle1);
        fragments.add(merchantFragment);
        fragments.add(merchantFragment1);
        MViewPagerAdapter pagerAdapter = new MViewPagerAdapter(this.getSupportFragmentManager(), tabs, fragments);
        messageViewPager.setAdapter(pagerAdapter);
        messageTab.setViewPager(messageViewPager);
        if (functionType==null||functionType.equals("1")){
            messageViewPager.setCurrentItem(0);
        }else {
            messageViewPager.setCurrentItem(1);
        }
    }
}
