package cn.ludean.beans;

import java.util.List;

public class MarkerBeans {

    /**
     *
     * 首页图标判断标准
     * flag :1  表示离线
     * flag :0   flag为0时，如果速度curSpeed为0，则表示静止；
     * 如果报警数量alarmNum大于0，则表示报警；
     * 其余状态下则表示运行
     *
     * success : true
     * msg : 首页车辆定位信息
     * data : [{"isNewRecord":true,"locTime":"2020-04-26 17:55:49","curSpeed":0,"lng":118.264778,"lat":32.091114,"direction":0,"driverName":"何勇","plateNum":"辽KJQ028","belongUnit":"思达","celeration":"0","deceleration":3,"turn":0,"overSpeedNumber":1,"time":"12760","distance":"5819474"}]
     */

    private boolean success;
    private String msg;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * isNewRecord : true
         * locTime : 2020-04-26 17:55:49
         * curSpeed : 0.0
         * lng : 118.264778
         * lat : 32.091114
         * direction : 0.0
         * driverName : 何勇
         * plateNum : 辽KJQ028
         * belongUnit : 思达
         * celeration : 0
         * deceleration : 3
         * turn : 0
         * overSpeedNumber : 1
         * time : 12760
         * distance : 5819474
         *
         *
         *
         *    "isNewRecord": true,
         *             "obdId": "3138303130323338",
         *             "locTime": "2020-04-26 17:55:49",//定位时间
         *             "curSpeed": 0.0,//当前速度
         *             "lng": 118.264778, //经纬度
         *             "lat": 32.091114,//经纬度
         *             "direction": 0.0,
         *             "carId": "5c8047323d924c3d8010cb831ed4d26b",
         *             "driverId": "b4e6c8621c36495bb5cb1b61fc4af347",
         *             "driverName": "何勇",//驾驶员
         *             "contactNumber": "15178442567", //驾驶员电话
         *             "plateNum": "辽KJQ028", //车牌号
         *             "belongUnit": "思达",  //用车单位
         *             "overSpeedNumber": 0, //超速次数
         *             "time": "0",  //运行时长
         *             "distance": "0", //公里数         
         *             "threeUrgent": 0，  //三急次数
         * "alarmNum": 0, //报警数量
         *              "flag": "1", // 在线离线标记0：在线 1：离线
         */

        private boolean isNewRecord;
        private String locTime;
        private double curSpeed;
        private double lng;
        private double lat;
        private double direction;
        private String driverName;
        private String plateNum;
        private String belongUnit;
        private String celeration;
        private int deceleration;
        private int turn;
        private int overSpeedNumber;
        private float time;
        private int distance;
        private String obdId;
        private String carId;
        private String driverId;
        private String contactNumber;
        private String threeUrgent;
        private int alarmNum;
        private String flag;

        public boolean isNewRecord() {
            return isNewRecord;
        }

        public void setNewRecord(boolean newRecord) {
            isNewRecord = newRecord;
        }

        public String getObdId() {
            return obdId;
        }

        public void setObdId(String obdId) {
            this.obdId = obdId;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getThreeUrgent() {
            return threeUrgent;
        }

        public void setThreeUrgent(String threeUrgent) {
            this.threeUrgent = threeUrgent;
        }

        public int getAlarmNum() {
            return alarmNum;
        }

        public void setAlarmNum(int alarmNum) {
            this.alarmNum = alarmNum;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getLocTime() {
            return locTime;
        }

        public void setLocTime(String locTime) {
            this.locTime = locTime;
        }

        public double getCurSpeed() {
            return curSpeed;
        }

        public void setCurSpeed(double curSpeed) {
            this.curSpeed = curSpeed;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getDirection() {
            return direction;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public String getBelongUnit() {
            return belongUnit;
        }

        public void setBelongUnit(String belongUnit) {
            this.belongUnit = belongUnit;
        }

        public String getCeleration() {
            return celeration;
        }

        public void setCeleration(String celeration) {
            this.celeration = celeration;
        }

        public int getDeceleration() {
            return deceleration;
        }

        public void setDeceleration(int deceleration) {
            this.deceleration = deceleration;
        }

        public int getTurn() {
            return turn;
        }

        public void setTurn(int turn) {
            this.turn = turn;
        }

        public int getOverSpeedNumber() {
            return overSpeedNumber;
        }

        public void setOverSpeedNumber(int overSpeedNumber) {
            this.overSpeedNumber = overSpeedNumber;
        }

        public float getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
