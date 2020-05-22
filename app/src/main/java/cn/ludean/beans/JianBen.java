package cn.ludean.beans;

public class JianBen {
    /**
     * success : true
     * msg : 检本信息
     * data : {"id":"i98u32697ac42347bf953acd81af2e40","isNewRecord":false,"driverCert":"642126197001120218","driverId":"0686b265a90d4e86aa7172ec6f16e09b","driverName":"白文平","driverExaminationTime":"2020-05-20 16:43:16","driverExaminationRecordTime":"2020-04-30 16:44:31","isExamination":"0"}
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
         * id : i98u32697ac42347bf953acd81af2e40
         * isNewRecord : false
         * driverCert : 642126197001120218
         * driverId : 0686b265a90d4e86aa7172ec6f16e09b
         * driverName : 白文平
         * driverExaminationTime : 2020-05-20 16:43:16
         * driverExaminationRecordTime : 2020-04-30 16:44:31
         * isExamination : 0
         */

        private String id;
        private boolean isNewRecord;
        private String driverCert;
        private String driverId;
        private String driverName;
        private String driverExaminationTime;
        private String driverExaminationRecordTime;
        private String isExamination;

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

        public String getDriverCert() {
            return driverCert;
        }

        public void setDriverCert(String driverCert) {
            this.driverCert = driverCert;
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

        public String getDriverExaminationTime() {
            return driverExaminationTime;
        }

        public void setDriverExaminationTime(String driverExaminationTime) {
            this.driverExaminationTime = driverExaminationTime;
        }

        public String getDriverExaminationRecordTime() {
            return driverExaminationRecordTime;
        }

        public void setDriverExaminationRecordTime(String driverExaminationRecordTime) {
            this.driverExaminationRecordTime = driverExaminationRecordTime;
        }

        public String getIsExamination() {
            return isExamination;
        }

        public void setIsExamination(String isExamination) {
            this.isExamination = isExamination;
        }
    }
}
