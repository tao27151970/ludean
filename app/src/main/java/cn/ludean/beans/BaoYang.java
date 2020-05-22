package cn.ludean.beans;

public class BaoYang {

    /**
     * success : true
     * msg : 保养信息
     * data : {"id":"587b75f6d6874de6b8a390437294d31f","isNewRecord":false,"carId":"66a095d4a0bb4916b0a7e60bdd10e6ae","driverId":"3c6a8d606dc74a3790cf3afe2f09cb33","upkeepItem":"u1","driverName":"周爱民","plateNum":"冀ABY214","carType":"哈弗H5","carBrand":"长城","mileage":2000,"endTime":"2020-05-12 18:14:08","status":"0","appluStatus":"0","diffMileage":3232}
     */

    private boolean success;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 587b75f6d6874de6b8a390437294d31f
         * isNewRecord : false
         * carId : 66a095d4a0bb4916b0a7e60bdd10e6ae
         * driverId : 3c6a8d606dc74a3790cf3afe2f09cb33
         * upkeepItem : u1
         * driverName : 周爱民
         * plateNum : 冀ABY214
         * carType : 哈弗H5
         * carBrand : 长城
         * mileage : 2000.0
         * endTime : 2020-05-12 18:14:08
         * status : 0
         * appluStatus : 0
         * diffMileage : 3232
         */

        private String id;
        private boolean isNewRecord;
        private String carId;
        private String driverId;
        private String upkeepItem;
        private String driverName;
        private String plateNum;
        private String carType;
        private String carBrand;
        private double mileage;
        private String endTime;
        private String status;
        private String appluStatus;
        private int diffMileage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
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

        public String getUpkeepItem() {
            return upkeepItem;
        }

        public void setUpkeepItem(String upkeepItem) {
            this.upkeepItem = upkeepItem;
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

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public double getMileage() {
            return mileage;
        }

        public void setMileage(double mileage) {
            this.mileage = mileage;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAppluStatus() {
            return appluStatus;
        }

        public void setAppluStatus(String appluStatus) {
            this.appluStatus = appluStatus;
        }

        public int getDiffMileage() {
            return diffMileage;
        }

        public void setDiffMileage(int diffMileage) {
            this.diffMileage = diffMileage;
        }
    }
}
