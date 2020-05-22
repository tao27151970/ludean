package cn.ludean.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ludean.R;
import cn.ludean.beans.MessageBeans;

public class MessageRecycerAdapter extends BaseQuickAdapter<MessageBeans.DataBean, BaseViewHolder> {
    public MessageRecycerAdapter(int layoutResId, @Nullable List<MessageBeans.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBeans.DataBean item) {
        if (item.getTitle()!=null&&!item.getTitle().isEmpty()){
            helper.setText(R.id.item_title, item.getTitle());
        }else {
            switch (item.getFunctionType()) {
                case "1":
                    helper.setText(R.id.item_title, "公告");
                    break;
                case "2":
                    helper.setText(R.id.item_title, "年审信息");
                    break;
                case "3":
                    helper.setText(R.id.item_title, "保养信息");
                    break;
                case "4":
                    helper.setText(R.id.item_title, "检本信息");
                    break;
            }
        }
        helper.setText(R.id.updateDate, item.getUpdateDate());
        helper.setText(R.id.item_content, item.getContent());
        TextView item_content = helper.getView(R.id.item_content);
        item_content.setText( item.getContent());
        if (item.getExtras()!=null&&item.getExtras().equals("1")){
            helper.getView(R.id.extras).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.extras).setVisibility(View.VISIBLE);
        }

    }
}
