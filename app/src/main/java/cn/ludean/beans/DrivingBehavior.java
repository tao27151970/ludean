package cn.ludean.beans;

public class DrivingBehavior {
    /**
     * success : true
     * msg : 驾驶行为
     * data : {"id":"1","isNewRecord":false,"createDate":"2019-10-12 01:01:00","updateDate":"2019-10-12 01:01:00","obdId":"3136303930303031","staDate":"2020-05-14 00:00:00","rapidCeleration":"0","rapidDeceleration":"0","rapidTurn":"0","overspeedNumber":"0","overspeedTime":"0.00","overspeedMileage":"0.00","fatigueDrivingNumber":"0","fatigueDrivingTime":"0.0","fatigueDrivingMileage":"0.0","driverId":"01c2cdb41faf4b149c198ec14bf68702"}
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
         * id : 1
         * isNewRecord : false
         * createDate : 2019-10-12 01:01:00
         * updateDate : 2019-10-12 01:01:00
         * obdId : 3136303930303031
         * staDate : 2020-05-14 00:00:00
         * rapidCeleration : 0
         * rapidDeceleration : 0
         * rapidTurn : 0
         * overspeedNumber : 0
         * overspeedTime : 0.00
         * overspeedMileage : 0.00
         * fatigueDrivingNumber : 0
         * fatigueDrivingTime : 0.0
         * fatigueDrivingMileage : 0.0
         * driverId : 01c2cdb41faf4b149c198ec14bf68702
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String obdId;
        private String staDate;
        private String rapidCeleration;
        private String rapidDeceleration;
        private String rapidTurn;
        private String overspeedNumber;
        private String overspeedTime;
        private String overspeedMileage;
        private String fatigueDrivingNumber;
        private String fatigueDrivingTime;
        private String fatigueDrivingMileage;
        private String driverId;

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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getObdId() {
            return obdId;
        }

        public void setObdId(String obdId) {
            this.obdId = obdId;
        }

        public String getStaDate() {
            return staDate;
        }

        public void setStaDate(String staDate) {
            this.staDate = staDate;
        }

        public String getRapidCeleration() {
            return rapidCeleration;
        }

        public void setRapidCeleration(String rapidCeleration) {
            this.rapidCeleration = rapidCeleration;
        }

        public String getRapidDeceleration() {
            return rapidDeceleration;
        }

        public void setRapidDeceleration(String rapidDeceleration) {
            this.rapidDeceleration = rapidDeceleration;
        }

        public String getRapidTurn() {
            return rapidTurn;
        }

        public void setRapidTurn(String rapidTurn) {
            this.rapidTurn = rapidTurn;
        }

        public String getOverspeedNumber() {
            return overspeedNumber;
        }

        public void setOverspeedNumber(String overspeedNumber) {
            this.overspeedNumber = overspeedNumber;
        }

        public String getOverspeedTime() {
            return overspeedTime;
        }

        public void setOverspeedTime(String overspeedTime) {
            this.overspeedTime = overspeedTime;
        }

        public String getOverspeedMileage() {
            return overspeedMileage;
        }

        public void setOverspeedMileage(String overspeedMileage) {
            this.overspeedMileage = overspeedMileage;
        }

        public String getFatigueDrivingNumber() {
            return fatigueDrivingNumber;
        }

        public void setFatigueDrivingNumber(String fatigueDrivingNumber) {
            this.fatigueDrivingNumber = fatigueDrivingNumber;
        }

        public String getFatigueDrivingTime() {
            return fatigueDrivingTime;
        }

        public void setFatigueDrivingTime(String fatigueDrivingTime) {
            this.fatigueDrivingTime = fatigueDrivingTime;
        }

        public String getFatigueDrivingMileage() {
            return fatigueDrivingMileage;
        }

        public void setFatigueDrivingMileage(String fatigueDrivingMileage) {
            this.fatigueDrivingMileage = fatigueDrivingMileage;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }
    }
}
