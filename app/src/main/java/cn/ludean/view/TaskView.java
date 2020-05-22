package cn.ludean.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.ludean.R;


public class TaskView extends LinearLayout {

    private View view;
    private TextView itemText1;
    private TextView itemText2;
    private ImageView itemImg;
    private int MAINCOLOR;
    private int TEXTCOLOR;
    private float TEXTSIZE = 15f;
    private Drawable IMGRESOURCE;

    public TaskView(Context context) {
        super(context);
    }
    public TaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.view_task_item, this, true);
        itemText1 = (TextView) view.findViewById(R.id.item_text1);
        itemText2 = (TextView) view.findViewById(R.id.item_text2);
        itemImg = (ImageView) view.findViewById(R.id.item_img);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        if (attributes != null) {
            //处理背景色
            MAINCOLOR = getResources().getColor(R.color.white);
            view.setBackgroundColor(MAINCOLOR);

            //设置textView的文字
            String titleText = attributes.getString(R.styleable.SettingItemView_title_text);
            String contextText = attributes.getString(R.styleable.SettingItemView_context_text);
            int resourceId = attributes.getResourceId(R.styleable.SettingItemView_src,R.drawable.app_logo);
            itemImg.setImageResource(resourceId);
            if (!TextUtils.isEmpty(titleText)) {
                itemText1.setText(titleText);
            }
            if (!TextUtils.isEmpty(titleText)) {
                itemText2.setText(contextText);
            }
            //设置文字颜色
            TEXTCOLOR = getResources().getColor(R.color.black);
            itemText1.setTextColor(TEXTCOLOR);
            //设置文字大小
            float titleTextSize = TEXTSIZE;
            itemText1.setTextSize(titleTextSize);
            //设置文字颜色
            TEXTCOLOR = getResources().getColor(R.color.black_333);
            itemText2.setTextColor(TEXTCOLOR);
            //设置文字大小
            itemText2.setTextSize(13f);
        }

    }
}
