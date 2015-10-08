package com.fcr.demo.views;

import com.fcr.demo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 自定义的弹出popupwindow
 * 
 * @author chaoranf@jumei.com
 * @date 2015-8-19
 */
public class SelectPicPopupWindow extends PopupWindow {

	private View mTakePhotoLayout, mPickPhotoLayout, mCancel;
	private View mPopView;

	public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView = inflater.inflate(R.layout.select_img_popwindow, null);
		mTakePhotoLayout = mPopView.findViewById(R.id.btn_take_photo);
		mPickPhotoLayout = mPopView.findViewById(R.id.btn_pick_photo);
		mCancel = mPopView.findViewById(R.id.btn_cancel);
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dismiss();
			}
		});
		mPickPhotoLayout.setOnClickListener(itemsOnClick);
		mTakePhotoLayout.setOnClickListener(itemsOnClick);
		this.setContentView(mPopView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.popwindow_animation_style);
		ColorDrawable dw = new ColorDrawable(0x66000000);
		this.setBackgroundDrawable(dw);
		mPopView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mPopView.findViewById(
						R.id.personal_center_pop_content_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

}
