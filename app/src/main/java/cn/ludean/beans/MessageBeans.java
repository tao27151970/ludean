package cn.ludean.beans;

import java.util.List;

public class MessageBeans {

    /**
     * success : true
     * msg : 推送信息
     * data : [{"id":"da03ef780f754e2db62880346dbd16a1","isNewRecord":false,"createDate":"2020-04-21 11:26:44","updateDate":"2020-04-21 11:26:44","title":"公告","content":"京津冀","functionType":"1","functionDataId":"i98u32697ac42347bf953acd81af2e40","audienceType":"all","audienceValue":"952e2f29d5c84321a4219da400185e87"}]
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
         * id : da03ef780f754e2db62880346dbd16a1
         * isNewRecord : false
         * createDate : 2020-04-21 11:26:44
         * updateDate : 2020-04-21 11:26:44
         * title : 公告
         * content : 京津冀
         * functionType : 1
         * functionDataId : i98u32697ac42347bf953acd81af2e40
         * audienceType : all
         * audienceValue : 952e2f29d5c84321a4219da400185e87
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String title;
        private String content;
        private String functionType;
        private String functionDataId;
        private String audienceType;
        private String audienceValue;
        private String extras;

        public boolean isNewRecord() {
            return isNewRecord;
        }

        public void setNewRecord(boolean newRecord) {
            isNewRecord = newRecord;
        }

        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFunctionType() {
            return functionType;
        }

        public void setFunctionType(String functionType) {
            this.functionType = functionType;
        }

        public String getFunctionDataId() {
            return functionDataId;
        }

        public void setFunctionDataId(String functionDataId) {
            this.functionDataId = functionDataId;
        }

        public String getAudienceType() {
            return audienceType;
        }

        public void setAudienceType(String audienceType) {
            this.audienceType = audienceType;
        }

        public String getAudienceValue() {
            return audienceValue;
        }

        public void setAudienceValue(String audienceValue) {
            this.audienceValue = audienceValue;
        }
    }
}
