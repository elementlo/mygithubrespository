package net.wash8.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import net.wash8.R;

public class CustomTitle extends FrameLayout {
    public ImageView iv_left_button,iv_right_button;
    public TextView iv_title_title;
    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_title,this);
        initView();
    }

    private void initView() {
        iv_left_button= (ImageView) findViewById(R.id.iv_leftmenu);
        iv_title_title= (TextView) findViewById(R.id.iv_tilte_title);
        iv_right_button= (ImageView) findViewById(R.id.iv_rightmenu);
    }
    public void setIv_title_title(String str){
        iv_title_title.setText(str);
    }
    public void setIv_left_button(int pic_name){
        iv_left_button.setImageResource(pic_name);
    }
    public void setIv_right_button(int pic_name){
        iv_right_button.setImageResource(pic_name);
    }
    public ImageView getIv_right_button(){
        return iv_right_button;
    }
    public ImageView getIv_left_button(){
        return iv_left_button;
    }


}
