package cn.ludean.beans;

import java.util.List;

public class SaveClock {
    /**
     * success : true
     * msg : 打卡成功
     * data : [{"id":"2dc9de38a68f4aa997e8fec3828f2d53","isNewRecord":false,"createDate":"2020-05-14 08:39:03","updateDate":"2020-05-14 08:39:03","clockId":"b4e6c8621c36495bb5cb1b61fc4af347","clockStat":"0"},{"id":"513dacf22fce4faf93bddb24ec438fab","isNewRecord":false,"createDate":"2020-05-14 14:02:34","updateDate":"2020-05-14 14:02:34","clockId":"b4e6c8621c36495bb5cb1b61fc4af347","clockStat":"1"}]
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
         * id : 2dc9de38a68f4aa997e8fec3828f2d53
         * isNewRecord : false
         * createDate : 2020-05-14 08:39:03
         * updateDate : 2020-05-14 08:39:03
         * clockId : b4e6c8621c36495bb5cb1b61fc4af347
         * clockStat : 0
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String clockId;
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

        public String getClockStat() {
            return clockStat;
        }

        public void setClockStat(String clockStat) {
            this.clockStat = clockStat;
        }
    }
}
