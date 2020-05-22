package cn.ludean.beans;

import java.util.List;

public class ClockingIn {

    /**
     * success : true
     * msg : 用户及打卡时间
     * data : [{"isNewRecord":true,"officeHours":"2020-04-17 16:08:30","closingTime":"2020-04-10 16:08:36","driverName":"何勇","driverId":"b4e6c8621c36495bb5cb1b61fc4af347"}]
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
         * officeHours : 2020-04-17 16:08:30
         * closingTime : 2020-04-10 16:08:36
         * driverName : 何勇
         * driverId : b4e6c8621c36495bb5cb1b61fc4af347
         */

        private boolean isNewRecord;
        private String officeHours;
        private String closingTime;
        private String driverName;
        private String driverId;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getOfficeHours() {
            return officeHours;
        }

        public void setOfficeHours(String officeHours) {
            this.officeHours = officeHours;
        }

        public String getClosingTime() {
            return closingTime;
        }

        public void setClosingTime(String closingTime) {
            this.closingTime = closingTime;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }
    }
}
