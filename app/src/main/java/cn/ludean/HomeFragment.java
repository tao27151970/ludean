package cn.ludean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ludean.activity.MessageActivity;
import cn.ludean.activity.TaskActivity;
import cn.ludean.activity.WebActivity;
import cn.ludean.beans.MarkerBeans;
import cn.ludean.net.NetCallBack;
import cn.ludean.net.NetConstant;
import cn.ludean.net.RetrofitUtils;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LocationSource, AMapLocationListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.message_img)
    ImageView messageImg;
    @BindView(R.id.message_new)
    View messageNew;
    @BindView(R.id.location_img)
    ImageView locationImg;
    @BindView(R.id.handover_management_img)
    ImageView handoverManagementImg;
    private Unbinder unbinder;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient locationClient;
    private String uuid;
    public static HomeFragment instance;
    private UiSettings uiSettings;
    private ArrayList<Marker> MarkerOptionsList = new ArrayList<>();
    private int position;
    private List<MarkerBeans.DataBean> marketList;
    private Marker onclickMarker;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        instance = this;
        if (SharedPrefrenceUtils.getBoolean(getActivity(), "message_new", false)) {
            messageNew.setVisibility(View.VISIBLE);
        } else {
            messageNew.setVisibility(View.GONE);
        }
        //获取地图控件引用
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        initview(savedInstanceState);
        uuid = SharedPrefrenceUtils.getString(getActivity(), "uuid");
        if (this.uuid != null && !this.uuid.isEmpty()) {
            getMarkerList();
        }
        aMap.setOnMarkerClickListener(marker -> {
            for (int i = 0; i < MarkerOptionsList.size(); i++) {
                if (MarkerOptionsList.get(i).equals(marker)) {
                    position = i;
                }
            }
            onclickMarker = marker;
            marker.showInfoWindow();
            return false;
        });
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (onclickMarker != null) {
                    onclickMarker.hideInfoWindow();
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (onclickMarker != null) {
                    onclickMarker.hideInfoWindow();
                }
            }
        });

        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public View getInfoWindow(Marker marker) {
                View infoWindow = LayoutInflater.from(getActivity()).inflate(R.layout.marker_pop, null);
                MarkerBeans.DataBean dataBean = marketList.get(position);
                TextView plateNum = infoWindow.findViewById(R.id.plateNum);
                plateNum.setText("车牌号：" + dataBean.getPlateNum());
                TextView belongUnit = infoWindow.findViewById(R.id.belongUnit);
                belongUnit.setText("用车单位：" + dataBean.getBelongUnit());
                TextView driverName = infoWindow.findViewById(R.id.driverName);
                driverName.setText("驾驶员：" + dataBean.getDriverName());
                TextView locTime = infoWindow.findViewById(R.id.locTime);
                locTime.setText("定位时间：" + dataBean.getLocTime());
                TextView curSpeed = infoWindow.findViewById(R.id.curSpeed);
                curSpeed.setText("车速：" + dataBean.getCurSpeed());
                TextView time = infoWindow.findViewById(R.id.time);
                time.setText("运营时间：" + String.format("%.1f", dataBean.getTime() / 60)+"h");
                TextView distance = infoWindow.findViewById(R.id.distance);
                distance.setText("公里数：" + new BigDecimal(dataBean.getDistance() / 1000).setScale(0, BigDecimal.ROUND_HALF_UP)+"公里");
                TextView celeration_deceleration_turn = infoWindow.findViewById(R.id.celeration_deceleration_turn);
                celeration_deceleration_turn.setText("三急次数：" + dataBean.getThreeUrgent() );
                TextView overSpeedNumber = infoWindow.findViewById(R.id.overSpeedNumber);
                overSpeedNumber.setText("超速次数：" + dataBean.getOverSpeedNumber());
                return infoWindow;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public View getInfoContents(Marker marker) {
                View infoWindow = LayoutInflater.from(getActivity()).inflate(R.layout.marker_pop, null);
                MarkerBeans.DataBean dataBean = (MarkerBeans.DataBean) marker.getObject();
                TextView plateNum = infoWindow.findViewById(R.id.plateNum);
                plateNum.setText("车牌号：" + dataBean.getPlateNum());
                TextView belongUnit = infoWindow.findViewById(R.id.belongUnit);
                belongUnit.setText("用车单位：" + dataBean.getBelongUnit());
                TextView driverName = infoWindow.findViewById(R.id.driverName);
                driverName.setText("驾驶员：" + dataBean.getDriverName());
                TextView locTime = infoWindow.findViewById(R.id.locTime);
                locTime.setText("定位时间：" + dataBean.getLocTime());
                TextView curSpeed = infoWindow.findViewById(R.id.curSpeed);
                curSpeed.setText("车速：" + dataBean.getCurSpeed());
                TextView time = infoWindow.findViewById(R.id.time);
                time.setText("运营时间：" + dataBean.getTime() / 60);
                TextView distance = infoWindow.findViewById(R.id.distance);
                distance.setText("公里数显示：" + dataBean.getDistance() / 1000);
                TextView celeration_deceleration_turn = infoWindow.findViewById(R.id.celeration_deceleration_turn);
                celeration_deceleration_turn.setText("三急数据：" + dataBean.getCeleration() + dataBean.getDeceleration() + dataBean.getTurn());
                TextView overSpeedNumber = infoWindow.findViewById(R.id.overSpeedNumber);
                overSpeedNumber.setText("超速次数：" + dataBean.getOverSpeedNumber());

                return infoWindow;
            }
        });//主要监听
        return view;
    }

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Boolean getTimeFormatText(Date date) {
        long minute = 60 * 1000;// 1分钟
        long hour = 60 * minute;// 1小时
        long day = 24 * hour;// 1天
        long month = 31 * day;// 月
        long year = 12 * month;// 年

        if (date == null) {
            return false;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            return false;
        }
        if (diff > month) {
            return false;
        }
        if (diff > day) {
            return false;
        }
        if (diff > hour) {
            r = (diff / hour);
            if (r > 1) {
                return false;
            }
        }
        if (diff > minute) {
            r = (diff / minute);
            return true;
        }
        return true;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (getActivity() != null && aMap != null && this.uuid != null && !this.uuid.isEmpty() && !hidden) {
            getMarkerList();
            if (SharedPrefrenceUtils.getBoolean(getActivity(), "message_new", false)) {
                messageNew.setVisibility(View.VISIBLE);
            } else {
                messageNew.setVisibility(View.GONE);
            }
        }
    }


    public void setMessageNew() {
        if (messageNew != null) {
            messageNew.setVisibility(View.VISIBLE);
            SharedPrefrenceUtils.saveBoolean(getActivity(), "message_new", true);
        }

    }

    private void getMarkerList() {
        RetrofitUtils.getInstance().getMarker(NetConstant.homePage, new NetCallBack<MarkerBeans>() {
            @Override
            public void star(Disposable d) {

            }

            @Override
            public void onSuccess(Object o) {
                if (o instanceof MarkerBeans) {
                    MarkerBeans markerBeans = (MarkerBeans) o;
                    if (markerBeans.getData() != null) {
                        marketList = markerBeans.getData();
                        aMap.clear(true);
                        MarkerOptionsList.clear();
                        //                flag :1  表示离线
//                flag :0   flag为0时，如果速度curSpeed为0，则表示静止；
//                如果报警数量alarmNum大于0，则表示报警；
//                其余状态下则表示运行
                        for (int i = 0; i < marketList.size(); i++) {
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(marketList.get(i).getLat(),//设置纬度
                                    marketList.get(i).getLng())));
                            BitmapDescriptor icon;
                            if (marketList.get(i).getFlag() != null && marketList.get(i).getFlag().equals("1")) {
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.car_lixian);
                            } else {
                                if (getTimeFormatText(StrToDate(marketList.get(i).getLocTime()))){
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.car_yunxin);
                                    if (marketList.get(i).getFlag() != null && marketList.get(i).getCurSpeed() == 0) {
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.car_jin);
                                    }
                                    if (marketList.get(i).getFlag() != null && marketList.get(i).getAlarmNum() > 0) {
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.car_baojing);
                                    }
                                }else {
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.car_lixian);
                                }
                            }
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(marketList.get(i).getLat(),//设置纬度
                                    marketList.get(i).getLng()))
                                    .icon(icon);
                            Marker marker = aMap.addMarker(markerOptions);
                            marker.setObject(marketList.get(i));
                            MarkerOptionsList.add(marker);
                        }
                        uiSettings = aMap.getUiSettings();
                        setMapAttribute();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        }, uuid);
    }


    /**
     * 设置地图属性
     */
    private void setMapAttribute() {
        //设置默认缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        //隐藏的右下角缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setLogoBottomMargin(-80);
        uiSettings.setMyLocationButtonEnabled(false);
        //设置marker点击事件监听
//        aMap.setOnMarkerClickListener(this);
        //设置自定义信息窗口
//        aMap.setInfoWindowAdapter(this);
        //设置地图点击事件监听
//        aMap.setOnMapClickListener(this);
    }

    private void initMarker() {

    }

    private void initview(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(getActivity());
            AMapLocationClientOption clientOption = new AMapLocationClientOption();
            locationClient.setLocationListener(this);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
            clientOption.setOnceLocationLatest(true);//设置单次精确定位
            locationClient.setLocationOption(clientOption);
            locationClient.startLocation();
        }

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                String address = aMapLocation.getAddress();
//                Log.e("clockAddress", address);
                if (!SharedPrefrenceUtils.getString(getActivity(), "clockAddress").equals(address)) {
                    SharedPrefrenceUtils.saveString(getActivity(), "clockAddress", address);
                }
//                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        } else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && aMap != null && this.uuid != null && !this.uuid.isEmpty()) {
            getMarkerList();
            if (SharedPrefrenceUtils.getBoolean(getActivity(), "message_new", false)) {
                messageNew.setVisibility(View.VISIBLE);
            } else {
                messageNew.setVisibility(View.GONE);
            }
        }
        mapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (locationClient != null) {
            locationClient.onDestroy();
        }
    }

    @OnClick({R.id.message_img, R.id.location_img, R.id.handover_management_img})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        switch (view.getId()) {
            case R.id.message_img:
                messageNew.setVisibility(View.GONE);
                SharedPrefrenceUtils.saveBoolean(getActivity(), "message_new", false);
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.location_img:
                startActivity(new Intent(getActivity(), TaskActivity.class));
                break;
            case R.id.handover_management_img:
                intent.putExtra("webUrl", "http://117.50.43.6/html/connectManagement.html");
                startActivity(intent);
                break;
        }
    }
}



