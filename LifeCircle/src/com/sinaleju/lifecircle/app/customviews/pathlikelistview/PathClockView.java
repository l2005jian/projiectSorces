package com.sinaleju.lifecircle.app.customviews.pathlikelistview;

import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.customviews.pathlikelistview.PathLikeListView.OnPositionChangedListener;

public class PathClockView extends FrameLayout implements
		OnPositionChangedListener {

	private ImageView mImgHourPointer;
	private ImageView mImgMinPointer;
	private TextView mTextTime;

	public PathClockView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PathClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PathClockView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.view_custom_pathlist_clock, this,true);

		mImgHourPointer = (ImageView) findViewById(R.id.clock_face_hour);
		mImgHourPointer
				.setImageResource(R.drawable.shape_pathlist_clock_hour_pointer);

		mImgMinPointer = (ImageView) findViewById(R.id.clock_face_minute);

		mTextTime = (TextView) findViewById(R.id.clock_digital_time);
	}

	private float[] computMinAndHour(int currentMinute, int currentHour) {
		float minuteRadian = 6f * currentMinute;

		float hourRadian = 360f / 12f * currentHour;

		float[] rtn = new float[2];
		rtn[0] = minuteRadian;
		rtn[1] = hourRadian;
		return rtn;
	}

	private float[] lastTime = { 0f, 0f };

	private RotateAnimation[] computeAni(int min, int hour) {

		RotateAnimation[] rtnAni = new RotateAnimation[2];
		float[] timef = computMinAndHour(min, hour);
		// AnimationSet as = new AnimationSet(true);
		// 创建RotateAnimation对象
		// 0--图片从哪开始旋转
		// 360--图片旋转多少度
		// Animation.RELATIVE_TO_PARENT, 0f,// 定义图片旋转X轴的类型和坐标
		// Animation.RELATIVE_TO_PARENT, 0f);// 定义图片旋转Y轴的类型和坐标
		RotateAnimation ra = new RotateAnimation(lastTime[0], timef[0],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setFillAfter(true);
		ra.setFillBefore(true);
		// 设置动画的执行时间
		ra.setDuration(800);
		// 将RotateAnimation对象添加到AnimationSet
		// as.addAnimation(ra);
		// 将动画使用到ImageView
		rtnAni[0] = ra;

		lastTime[0] = timef[0];

		// AnimationSet as2 = new AnimationSet(true);
		// 创建RotateAnimation对象
		// 0--图片从哪开始旋转
		// 360--图片旋转多少度
		// Animation.RELATIVE_TO_PARENT, 0f,// 定义图片旋转X轴的类型和坐标
		// Animation.RELATIVE_TO_PARENT, 0f);// 定义图片旋转Y轴的类型和坐标
		RotateAnimation ra2 = new RotateAnimation(lastTime[1], timef[1],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		// 设置动画的执行时间
		ra2.setFillAfter(true);
		ra2.setFillBefore(true);
		ra2.setDuration(800);
		// 将RotateAnimation对象添加到AnimationSet
		// as2.addAnimation(ra2);
		// 将动画使用到ImageView
		rtnAni[1] = ra2;
		lastTime[1] = timef[1];
		return rtnAni;
	}

	@Override
	public void onPositionChanged(PathLikeListView listView,
			int firstVisiblePosition, View scrollBarPanel) {
		TextView datestr = ((TextView) findViewById(R.id.clock_digital_date));
		datestr.setText("上午");

		int hour = (new Random().nextInt() >>> 1) % 24;
		String tmpstr = "";
		if (hour > 12) {
			hour = hour - 12;
			datestr.setText("下午");
			tmpstr += " ";
		} else if (0 < hour && hour < 10) {

			tmpstr += " ";
		}
		int min = (new Random().nextInt() >>> 1) % 60;
		tmpstr += hour + ":" + min;

		// ((TextView) findViewById(R.id.clock_digital_time)).setText(tmpstr);
		
		//text
		mTextTime.setText(tmpstr);
		
		RotateAnimation[] tmp = computeAni(min, hour);

		// ImageView minView = (ImageView) findViewById(R.id.clock_face_minute);
		
		//min pointer anim
		mImgMinPointer.startAnimation(tmp[0]);

		// ImageView hourView = (ImageView) findViewById(R.id.clock_face_hour);
		// hourView.setImageResource(R.drawable.shape_pathlist_clock_hour_pointer);
		
		//hour pointer anim
		mImgHourPointer.startAnimation(tmp[1]);

	}

	@Override
	public void onScollPositionChanged(View scrollBarPanel, int top) {

		MarginLayoutParams layoutParams = (MarginLayoutParams) this
				.getLayoutParams();
		layoutParams.setMargins(0, top, 0, 0);
		this.setLayoutParams(layoutParams);

	}

}