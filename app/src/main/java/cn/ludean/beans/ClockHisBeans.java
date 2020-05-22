package cn.ludean.beans;

import java.util.List;

public class ClockHisBeans {

    /**
     * success : true
     * msg : 今日打卡记录
     * data : [{"id":"7c2598723f9844ad810948506fdb6d1a","isNewRecord":false,"createDate":"2020-05-14 11:12:24","updateDate":"2020-05-14 11:12:24","clockId":"01c2cdb41faf4b149c198ec14bf68702","clockName":"test","clockAddress":"爱普大厦","clockTime":"16:58:09","clockStat":"0"},{"id":"de4b332a36614ae98fcea2faf4435656","isNewRecord":false,"createDate":"2020-05-14 11:12:44","updateDate":"2020-05-14 11:12:44","clockId":"01c2cdb41faf4b149c198ec14bf68702","clockName":"test","clockAddress":"爱普大厦","clockTime":"16:58:09","clockStat":"1"}]
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
         * id : 7c2598723f9844ad810948506fdb6d1a
         * isNewRecord : false
         * createDate : 2020-05-14 11:12:24
         * updateDate : 2020-05-14 11:12:24
         * clockId : 01c2cdb41faf4b149c198ec14bf68702
         * clockName : test
         * clockAddress : 爱普大厦
         * clockTime : 16:58:09
         * clockStat : 0
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String clockId;
        private String clockName;
        private String clockAddress;
        private String clockTime;
        private String clockStat;

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

        public String getClockId() {
            return clockId;
        }

        public void setClockId(String clockId) {
            this.clockId = clockId;
        }

        public String getClockName() {
            return clockName;
        }

        public void setClockName(String clockName) {
            this.clockName = clockName;
        }

        public String getClockAddress() {
            return clockAddress;
        }

        public void setClockAddress(String clockAddress) {
            this.clockAddress = clockAddress;
        }

        public String getClockTime() {
            return clockTime;
        }

        public void setClockTime(String clockTime) {
            this.clockTime = clockTime;
        }

        public String getClockStat() {
            return clockStat;
        }

        public void setClockStat(String clockStat) {
            this.clockStat = clockStat;
        }
    }
}
