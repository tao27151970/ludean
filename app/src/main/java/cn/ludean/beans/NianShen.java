package cn.ludean.beans;

public class NianShen {
    /**
     * success : true
     * msg : 年审信息
     * data : {"id":"fea29798aa9c46b1aee0b8ca9c0843e3","isNewRecord":false,"createDate":"2020-02-15 00:01:01","updateDate":"2020-02-15 00:01:01","carId":"af4202e6fa4645d192c114431c28b1a9","plateNum":"冀AR9X08","carSerialNum":"冀AR9X08-1805221001","carBrand":"大众","carVersion":"帕萨特政采版","driverId":"57b74982c0d94685ae628f6e5adf5a14","driverName":"吴东起","annualExaminationTime":"2020-05-15 00:00:00","isExamination":"0","carArtificialRating":"A"}
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
         * id : fea29798aa9c46b1aee0b8ca9c0843e3
         * isNewRecord : false
         * createDate : 2020-02-15 00:01:01
         * updateDate : 2020-02-15 00:01:01
         * carId : af4202e6fa4645d192c114431c28b1a9
         * plateNum : 冀AR9X08
         * carSerialNum : 冀AR9X08-1805221001
         * carBrand : 大众
         * carVersion : 帕萨特政采版
         * driverId : 57b74982c0d94685ae628f6e5adf5a14
         * driverName : 吴东起
         * annualExaminationTime : 2020-05-15 00:00:00
         * isExamination : 0
         * carArtificialRating : A
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String carId;
        private String plateNum;
        private String carSerialNum;
        private String carBrand;
        private String carVersion;
        private String driverId;
        private String driverName;
        private String annualExaminationTime;
        private String isExamination;
        private String carArtificialRating;

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

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public String getCarSerialNum() {
            return carSerialNum;
        }

        public void setCarSerialNum(String carSerialNum) {
            this.carSerialNum = carSerialNum;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getCarVersion() {
            return carVersion;
        }

        public void setCarVersion(String carVersion) {
            this.carVersion = carVersion;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getAnnualExaminationTime() {
            return annualExaminationTime;
        }

        public void setAnnualExaminationTime(String annualExaminationTime) {
            this.annualExaminationTime = annualExaminationTime;
        }

        public String getIsExamination() {
            return isExamination;
        }

        public void setIsExamination(String isExamination) {
            this.isExamination = isExamination;
        }

        public String getCarArtificialRating() {
            return carArtificialRating;
        }

        public void setCarArtificialRating(String carArtificialRating) {
            this.carArtificialRating = carArtificialRating;
        }
    }
}
