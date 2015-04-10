package net.wash8.customview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import net.wash8.R;

/**
 * Created by admin1 on 2014/12/19.
 */
public class JumpMenuUtil {
    public static boolean isShow = false;
    public static void jumpMenu(Activity activity,View menu){
        FrameLayout.LayoutParams vgParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(activity).inflate(R.layout.jump_menu,null);
        ViewGroup content = (ViewGroup)view.findViewById(R.id.ll_content);
        content.addView(menu);
        ViewGroup rootView = (ViewGroup)activity.getWindow().getDecorView();
        rootView.addView(view);
        playAnimation(content,true,null);
        isShow = true;
    }

    public static void dismiss(Activity activity){
        final ViewGroup rootView = (ViewGroup)activity.getWindow().getDecorView();
        final View jumpMenu = rootView.findViewById(R.id.rl_jump_menu_content);
        if(jumpMenu == null)
            return;
        final ViewGroup content = (ViewGroup)jumpMenu.findViewById(R.id.ll_content);
        if(jumpMenu != null){
            playAnimation(content, false, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    jumpMenu.setVisibility(View.GONE);
                    content.removeAllViews();
                    rootView.removeView(jumpMenu);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        isShow = false;
    }

    private static void playAnimation(View v, boolean isJump, Animation.AnimationListener animationListener){
        //移动动画
        TranslateAnimation translateAnimation =
                new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,isJump ? 1 : 0,
                        Animation.RELATIVE_TO_SELF,isJump ? 0 : 1);
        translateAnimation.setDuration(300);
        v.clearAnimation();
        v.startAnimation(translateAnimation);
        v.setAnimation(translateAnimation);
        if(animationListener != null)
            translateAnimation.setAnimationListener(animationListener);
    }
}
