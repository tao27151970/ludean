package cn.ludean.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ludean.R;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.beans.ClockHisBeans;
import cn.ludean.beans.ClockingIn;
import cn.ludean.beans.DrivingBehavior;
import cn.ludean.net.NetCallBack;
import cn.ludean.net.NetConstant;
import cn.ludean.net.RetrofitUtils;
import io.reactivex.disposables.Disposable;

public class TaskActivity extends AppCompatActivity {

    @BindView(R.id.tit_back)
    ImageView titBack;
    @BindView(R.id.clockName)
    TextView clockName;
    @BindView(R.id.clockTime)
    TextView clockTime;
    @BindView(R.id.clockAddress)
    TextView clockAddress;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.clockTime1)
    TextView clockTime1;
    @BindView(R.id.clockAddress1)
    TextView clockAddress1;
    @BindView(R.id.address1)
    LinearLayout address1;
    @BindView(R.id.task_btn)
    LinearLayout taskBtn;
    @BindView(R.id.address)
    LinearLayout address;
    @BindView(R.id.tv_officeHours)
    TextView tvOfficeHours;
    @BindView(R.id.tb_closingTime)
    TextView tbClosingTime;
    @BindView(R.id.tv_amorpm)
    TextView tvAmorpm;
    @BindView(R.id.shangbanban_view)
    View shangbanbanView;
    @BindView(R.id.xiaban_view)
    View xiabanView;
    private String mclockName;
    private String clockId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
        if (new GregorianCalendar().get(GregorianCalendar.AM_PM) == 0) {
            tvAmorpm.setText("上班打卡");
            address.setVisibility(View.VISIBLE);
            clockAddress.setText(SharedPrefrenceUtils.getString(this, "clockAddress"));
            shangbanbanView.setBackground(this.getResources().getDrawable(R.drawable.view_yuan_blue));
            xiabanView.setBackground(this.getResources().getDrawable(R.drawable.view_yuan));
        } else {
            address1.setVisibility(View.VISIBLE);
            clockAddress1.setText(SharedPrefrenceUtils.getString(this, "clockAddress"));
            tvAmorpm.setText("下班打卡");
            xiabanView.setBackground(this.getResources().getDrawable(R.drawable.view_yuan_blue));
            shangbanbanView.setBackground(this.getResources().getDrawable(R.drawable.view_yuan));
        }
        new TimeThread().start();
        initData();
        findClockHis();
        initOnclick();

    }

    private void initOnclick() {
        titBack.setOnClickListener(v -> {
            finish();
        });
        taskBtn.setOnClickListener(v -> {
            saveClockInfo();
        });
    }

    private void saveClockInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("clockId", clockId);
        map.put("clockName", mclockName);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        map.put("clockTime", formatter.format(curDate));
        map.put("clockAddress", SharedPrefrenceUtils.getString(this, "clockAddress"));
        RetrofitUtils.getInstance().saveClockInfo(NetConstant.saveClockInfo, SharedPrefrenceUtils.getString(this, "uuid"), map, new NetCallBack<ClockHisBeans>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @Override
            public void onSuccess(Object o) {
                if (o instanceof ClockHisBeans) {
                    ClockHisBeans clockingIn = (ClockHisBeans) o;
                    //上下班卡都已经打了
                    if (clockingIn.getData() != null && clockingIn.getData().size() > 1) {
                        address.setVisibility(View.VISIBLE);
                        address1.setVisibility(View.VISIBLE);
                        taskBtn.setVisibility(View.GONE);
                        //上班卡
                        if (clockingIn.getData().get(0).getClockStat().equals("0")) {
                            clockTime.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockTime1.setText("打卡时间" + clockingIn.getData().get(1).getClockTime());
                            clockAddress.setText(clockingIn.getData().get(0).getClockAddress());
                            clockAddress1.setText(clockingIn.getData().get(1).getClockAddress());
                            //下班卡
                        } else {
                            clockTime.setText("打卡时间" + clockingIn.getData().get(1).getClockTime());
                            clockTime1.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockAddress.setText(clockingIn.getData().get(1).getClockAddress());
                            clockAddress1.setText(clockingIn.getData().get(0).getClockAddress());
                        }
                        //只打了一次卡
                    } else if (clockingIn.getData() != null && clockingIn.getData().size() == 1) {
                        //上班卡
                        if (clockingIn.getData().get(0).getClockStat().equals("0")) {
                            clockTime.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockAddress.setText(clockingIn.getData().get(0).getClockAddress());
                            taskBtn.setVisibility(View.VISIBLE);
                            address.setVisibility(View.VISIBLE);
                            address1.setVisibility(View.GONE);
                            //下班卡
                        } else {
                            taskBtn.setVisibility(View.GONE);
                            address.setVisibility(View.GONE);
                            clockTime.setText("缺卡");
                            clockTime.setVisibility(View.VISIBLE);
                            clockTime.setTextColor(TaskActivity.this.getResources().getColor(R.color.red));
                            clockTime1.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockAddress1.setText(clockingIn.getData().get(0).getClockAddress());
                        }
                    }
                    drivingBehavior();

                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    private void drivingBehavior() {
        RetrofitUtils.getInstance().getMarker(NetConstant.drivingBehavior, new NetCallBack<DrivingBehavior>() {
            @Override
            public void star(Disposable d) {

            }

            @Override
            public void onSuccess(Object o) {
                if (o instanceof DrivingBehavior) {
                    DrivingBehavior clockingIn = (DrivingBehavior) o;
                    Dialog dialog = new Dialog(TaskActivity.this, R.style.dialog03);
                    dialog.setContentView(R.layout.pop_task);
                    ImageView openBtn = dialog.findViewById(R.id.pop_dimiss);
                    TextView pop_dim = dialog.findViewById(R.id.pop_dim);
                    TextView rapidCeleration = dialog.findViewById(R.id.rapidCeleration);
                    TextView rapidDeceleration = dialog.findViewById(R.id.rapidDeceleration);
                    TextView rapidTurn = dialog.findViewById(R.id.rapidTurn);
                    TextView overspeedNumber = dialog.findViewById(R.id.overspeedNumber);
                    TextView fatigueDrivingNumber = dialog.findViewById(R.id.fatigueDrivingNumber);
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    DisplayMetrics dm = new DisplayMetrics();
                    Log.e("curActivity : ", this.getClass().getName());
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    lp.width = dm.widthPixels; // 设置宽度
                    dialog.getWindow().setAttributes(lp);
                    dialog.setCanceledOnTouchOutside(true);
                    openBtn.setOnClickListener(view -> {
                        dialog.cancel();
                    });
                    pop_dim.setOnClickListener(view -> {
                        dialog.cancel();
                    });
                    if (clockingIn.getData() != null) {
                        DrivingBehavior.DataBean data = clockingIn.getData();
                        rapidCeleration.setText(data.getRapidCeleration());
                        rapidDeceleration.setText(data.getRapidDeceleration());
                        rapidTurn.setText(data.getRapidTurn());
                        overspeedNumber.setText(data.getOverspeedNumber());
                        fatigueDrivingNumber.setText(data.getFatigueDrivingNumber());
                    }
                    dialog.show();
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        }, SharedPrefrenceUtils.getString(this, "uuid"));

    }

    private void findClockHis() {
        RetrofitUtils.getInstance().getMarker(NetConstant.findClockHis, new NetCallBack<ClockHisBeans>() {
            @Override
            public void star(Disposable d) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof ClockHisBeans) {
                    ClockHisBeans clockingIn = (ClockHisBeans) o;
                    //上下班卡都已经打了
                    if (clockingIn.getData() != null && clockingIn.getData().size() > 1) {
                        clockTime.setVisibility(View.VISIBLE);
                        clockTime1.setVisibility(View.VISIBLE);
                        address.setVisibility(View.VISIBLE);
                        address1.setVisibility(View.VISIBLE);
                        taskBtn.setVisibility(View.GONE);
                        //上班卡
                        if (clockingIn.getData().get(0).getClockStat().equals("0")) {
                            clockTime.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockTime1.setText("打卡时间" + clockingIn.getData().get(1).getClockTime());
                            clockAddress.setText(clockingIn.getData().get(0).getClockAddress());
                            clockAddress1.setText(clockingIn.getData().get(1).getClockAddress());
                            //下班卡
                        } else {
                            clockTime.setText("打卡时间" + clockingIn.getData().get(1).getClockTime());
                            clockTime1.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockAddress.setText(clockingIn.getData().get(1).getClockAddress());
                            clockAddress1.setText(clockingIn.getData().get(0).getClockAddress());
                        }
                        //只打了一次卡
                    } else if (clockingIn.getData() != null && clockingIn.getData().size() == 1) {
                        //上班卡
                        if (clockingIn.getData().get(0).getClockStat().equals("0")) {
                            clockTime.setVisibility(View.VISIBLE);
                            clockTime.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockAddress.setText(clockingIn.getData().get(0).getClockAddress());
                            taskBtn.setVisibility(View.VISIBLE);
                            address1.setVisibility(View.GONE);
                            address.setVisibility(View.VISIBLE);
                            //下班卡
                        } else {
                            clockTime.setVisibility(View.VISIBLE);
                            taskBtn.setVisibility(View.GONE);
                            address.setVisibility(View.GONE);
                            clockTime.setText("缺卡");
                            clockTime.setTextColor(TaskActivity.this.getResources().getColor(R.color.red));
                            clockTime1.setText("打卡时间" + clockingIn.getData().get(0).getClockTime());
                            clockAddress1.setText(clockingIn.getData().get(0).getClockAddress());
                        }
                        //一次也没打（需要判断当前当前时间是上午还是下午）
                    } else {
//                        if (new GregorianCalendar().get(GregorianCalendar.AM_PM) == 1) {
                        taskBtn.setVisibility(View.VISIBLE);
                        clockTime.setVisibility(View.VISIBLE);
                        address.setVisibility(View.GONE);
//                            clockTime.setText("缺卡");
//                            clockTime.setTextColor(TaskActivity.this.getResources().getColor(R.color.red));
//                        } else {
                        clockTime.setVisibility(View.GONE);
                        tvAmorpm.setText("打卡");
//                        }
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        }, SharedPrefrenceUtils.getString(this, "uuid"));

    }

    private void initData() {
        RetrofitUtils.getInstance().getMarker(NetConstant.clockingIn, new NetCallBack<ClockingIn>() {
            @Override
            public void star(Disposable d) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof ClockingIn) {
                    ClockingIn clockingIn = (ClockingIn) o;
                    if (clockingIn.getData() != null && clockingIn.getData().size() != 0) {
                        tvOfficeHours.setText("上班时间：" + clockingIn.getData().get(0).getOfficeHours());
                        tbClosingTime.setText("下班时间：" + clockingIn.getData().get(0).getClosingTime());
                        clockName.setText(clockingIn.getData().get(0).getDriverName());
                        mclockName = clockingIn.getData().get(0).getDriverName();
                        clockId = clockingIn.getData().get(0).getDriverId();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        }, SharedPrefrenceUtils.getString(this, "uuid"));
    }

    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);

        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("SimpleDateFormat")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                    break;
            }
            return false;
        }
    });

}
