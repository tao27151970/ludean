package cn.ludean.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ludean.R;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.beans.BaoYang;
import cn.ludean.beans.GongGao;
import cn.ludean.beans.JianBen;
import cn.ludean.beans.MessageBeans;
import cn.ludean.beans.NianShen;
import cn.ludean.beans.UpkeepItem;
import cn.ludean.net.NetCallBack;
import cn.ludean.net.NetConstant;
import cn.ludean.net.RetrofitUtils;
import io.reactivex.disposables.Disposable;

public class MessageDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tit_back)
    ImageView titBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.driverName)
    TextView driverName;
    @BindView(R.id.driverCert)
    TextView driverCert;
    @BindView(R.id.driverExaminationTime)
    TextView driverExaminationTime;
    @BindView(R.id.driverExamination_differ_time)
    TextView driverExaminationDifferTime;
    @BindView(R.id.isExamination)
    TextView isExamination;
    @BindView(R.id.functionType4)
    LinearLayout functionType4;
    @BindView(R.id.plateNum)
    TextView plateNum;
    @BindView(R.id.carBrand)
    TextView carBrand;
    @BindView(R.id.carVersion)
    TextView carVersion;
    @BindView(R.id.annualExaminationTime)
    TextView annualExaminationTime;
    @BindView(R.id.annualExamination_differ_time)
    TextView annualExaminationDifferTime;
    @BindView(R.id.isExamination2)
    TextView isExamination2;
    @BindView(R.id.functionType2)
    LinearLayout functionType2;
    @BindView(R.id.plateNum3)
    TextView plateNum3;
    @BindView(R.id.carBrand3)
    TextView carBrand3;
    @BindView(R.id.carVersion3)
    TextView carVersion3;
    @BindView(R.id.upkeepItem)
    TextView upkeepItem;
    @BindView(R.id.mileage)
    TextView mileage;
    @BindView(R.id.functionType3)
    LinearLayout functionType3;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.updateDate)
    TextView updateDate;
    @BindView(R.id.functionType1)
    LinearLayout functionType1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        ButterKnife.bind(this);
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
        titBack.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        String functionType = intent.getStringExtra("functionType");
        String id = intent.getStringExtra("id");
        String extras = intent.getStringExtra("extras");
        String functionDataId = intent.getStringExtra("functionDataId");
        Map<String, String> map = new HashMap<>();
        map.put("functionType", functionType);
        map.put("id", id);
        map.put("extras", extras);
        map.put("functionDataId", functionDataId);
        switch (functionType) {
            case "1":
                title.setText("公告详情");
                getgonggao(map);
                break;
            case "2":
                title.setText("年审信息");
                getnianshen(map);
                break;
            case "3":
                title.setText("保养信息");
                getbaoyang(map);
                break;
            case "4":
                title.setText("检本信息");
                getjianben(map);
                break;
        }
    }

    private void getgonggao(Map<String, String> map) {
        RetrofitUtils.getInstance().postHttp(NetConstant.findPushInfo, SharedPrefrenceUtils.getString(this, "uuid"), map, new NetCallBack<GongGao>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof GongGao) {
                    GongGao gongGao = (GongGao) o;
                    if (gongGao.getData() != null) {
                        functionType1.setVisibility(View.VISIBLE);
                        content.setText("公告内容：   " + gongGao.getData().getContent());
                        updateDate.setText(gongGao.getData().getUpdateDate());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

    }

    private void getbaoyang(Map<String, String> map) {
        RetrofitUtils.getInstance().postHttp(NetConstant.findPushInfo, SharedPrefrenceUtils.getString(this, "uuid"), map, new NetCallBack<BaoYang>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof BaoYang) {
                    BaoYang baoYang = (BaoYang) o;
                    if (baoYang.getData() != null) {
                        functionType3.setVisibility(View.VISIBLE);
                        BaoYang.DataBean data = baoYang.getData();
                        plateNum3.setText("车牌号：" + data.getPlateNum());
                        carBrand3.setText("车辆品牌：" + data.getCarBrand());
                        carVersion3.setText("车辆型号：" + data.getCarType());
                        getUpkeepItem(data.getUpkeepItem());
//                        upkeepItem.setText("保养项目：" + data.getUpkeepItem());
                        mileage.setText("保养里程：" + data.getMileage());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

    }

    private void getUpkeepItem(String upkeepItem) {
        Map<String, String> map = new HashMap<>();
        map.put("value", upkeepItem);
        RetrofitUtils.getInstance().postHttp(NetConstant.getUpkeepDictLabel, SharedPrefrenceUtils.getString(this, "uuid"), map, new NetCallBack<UpkeepItem>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof UpkeepItem) {
                    UpkeepItem baoYang = (UpkeepItem) o;
                    MessageDetailsActivity.this.upkeepItem.setText("保养项目：" + baoYang.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

    }

    private void getnianshen(Map<String, String> map) {
        RetrofitUtils.getInstance().postHttp(NetConstant.findPushInfo, SharedPrefrenceUtils.getString(this, "uuid"), map, new NetCallBack<NianShen>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof NianShen) {
                    NianShen nianShen = (NianShen) o;
                    if (nianShen.getData() != null) {
                        functionType2.setVisibility(View.VISIBLE);
                        NianShen.DataBean data = nianShen.getData();
                        plateNum.setText("车牌号：" + data.getPlateNum());
                        carBrand.setText("车辆品牌：" + data.getCarBrand());
                        carVersion.setText("车辆型号：" + data.getCarVersion());
                        String[] s = data.getAnnualExaminationTime().split(" ");
                        annualExaminationTime.setText("到期时间：" + s[0]);
                        if (data.getIsExamination().equals("0")) {
                            isExamination2.setText("状态：" + "未年审");
                            try {
                                if (CalculateDays(data.getAnnualExaminationTime()) == null) {
                                    annualExaminationDifferTime.setVisibility(View.GONE);
                                } else {
                                    annualExaminationDifferTime.setText("相差天数：" + CalculateDays(data.getAnnualExaminationTime()));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            isExamination2.setText("状态：" + "已年审");
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

    }

    private void getjianben(Map<String, String> map) {
        RetrofitUtils.getInstance().postHttp(NetConstant.findPushInfo, SharedPrefrenceUtils.getString(this, "uuid"), map, new NetCallBack<JianBen>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object o) {
                if (o instanceof JianBen) {
                    JianBen jianBen = (JianBen) o;
                    if (jianBen.getData() != null) {
                        functionType4.setVisibility(View.VISIBLE);
                        JianBen.DataBean data = jianBen.getData();
                        driverName.setText("驾驶员：" + data.getDriverName());
                        driverCert.setText("身份证号码：" + data.getDriverCert());
                        if (data.getIsExamination().equals("0")) {
                            isExamination.setText("状态：未检本");
                            try {
                                if (CalculateDays(data.getDriverExaminationTime()) == null) {
                                    driverExaminationDifferTime.setVisibility(View.GONE);
                                } else {
                                    driverExaminationDifferTime.setText("距离检本：" + CalculateDays(data.getDriverExaminationTime()));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            isExamination.setText("状态：已检本");

                        }
                        String[] s = data.getDriverExaminationTime().split(" ");
                        driverExaminationTime.setText("驾本到期时间：" + s[0]);


                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    public String CalculateDays(String date) throws ParseException {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = formatter.format(currentTime);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = df.parse(date1);
        Date d2 = df.parse(date);
        System.out.println();
        if ((d1.getTime() - d2.getTime()) / (60 * 60 * 1000 * 24) > 0) {
            return String.valueOf((d1.getTime() - d2.getTime()) / (60 * 60 * 1000 * 24));
        } else {
            return null;
        }
    }


}
