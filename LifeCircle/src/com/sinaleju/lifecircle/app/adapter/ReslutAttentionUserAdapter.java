package com.sinaleju.lifecircle.app.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.ChatDetailAct;
import com.sinaleju.lifecircle.app.customviews.RoundCornerImageView;
import com.sinaleju.lifecircle.app.model.Model_AttentionUserInfo;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;
public class ReslutAttentionUserAdapter extends BaseAdapter {
	protected static final String TAG = "ReslutAttentionUserAdapter";
	private Context mContext;
	private List<Model_AttentionUserInfo> resultList;
	private boolean isShowMsg;

	public ReslutAttentionUserAdapter(Context mContext, List<Model_AttentionUserInfo> resultList,boolean isShowMsg) {
		this.mContext = mContext;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		this.resultList=resultList;
		this.isShowMsg=isShowMsg;
	}

	public void setResultList(List<Model_AttentionUserInfo> resultList) {
		this.resultList = resultList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (convertView == null || null == convertView.getTag()) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_attentionuser, null);
			holder.ll_headerImage=convertView.findViewById(R.id.item_attentionuser_llheadimage);
			holder.headerImage = (RoundCornerImageView) convertView
					.findViewById(R.id.item_attentionuser_headimage);
			holder.headerImage.setRoundHeight(6);
			holder.headerImage.setRoundWidth(6);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_attentionuser_username);
			holder.vipIcon = (ImageView) convertView
					.findViewById(R.id.attentionuser_vipimage);
			holder.message = (ImageButton) convertView
					.findViewById(R.id.attentionuser_msg);
			if(!isShowMsg){
				holder.message.setVisibility(View.GONE);
			}
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		final Model_AttentionUserInfo user = (Model_AttentionUserInfo) getItem(position);
		if (user != null) {
			if(user.getId()==-1){
				holder.ll_headerImage.setVisibility(View.GONE);
			}else{
				holder.ll_headerImage.setVisibility(View.VISIBLE);
			}
			int headDrawable=PublicUtils.getUserIndexDefaultHeadImage((Integer.valueOf(user.getType())));
			holder.headerImage.setBackgroundResource(headDrawable);
			ImageLoader.getInstance((Activity)mContext).displayImage(user.getUrl(), holder.headerImage,
					SimpleImageLoaderOptions.getRoundImageOptions(headDrawable, 100));	
		//	loadIcon(holder.headerImage, user, null,headDrawable);
			holder.name.setText(user.getNickName().toString());
			if(user.getIsOAth()==1){
				holder.vipIcon.setVisibility(View.VISIBLE);
			}else{
				holder.vipIcon.setVisibility(View.GONE);
			}
		}
		holder.message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	Toast.makeText(mContext, "发送私信", Toast.LENGTH_SHORT).show();
				Intent chatIntent=new Intent(mContext, ChatDetailAct.class);
				chatIntent.putExtra(ChatDetailAct.NAME_KEY, user.getNickName());
				chatIntent.putExtra(ChatDetailAct.USER_ID_KEY, AppContext.curUser().getUid());
				chatIntent.putExtra(ChatDetailAct.TO_USER_ID_KEY, user.getId());
				int type = 0;
				try {
					type = Integer.parseInt(user.getType());
				} catch (Exception e) {
					e.printStackTrace();
				}
				chatIntent.putExtra(ChatDetailAct.TYPE_KEY, type);
				mContext.startActivity(chatIntent);
				((Activity) mContext).finish();
			}
		});

		return convertView;

	}

	private static class Holder {

		public RoundCornerImageView headerImage;
		public TextView name;
		public ImageView vipIcon;
		public ImageButton message;
		public View ll_headerImage;

	}
	
	// 加载网络图片
//	private void loadIcon(ImageView topImageView, Model_AttentionUserInfo bean,
//			final ProgressBar topProg, int imageId) {
//		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
//		builder.showStubImage(imageId);
//		builder.cacheOnDisc();
//		builder.decodingOptions(PublicUtils.getOptions());
//		DisplayImageOptions options =	SimpleImageLoaderOptions.getOptions(imageId, true);
//		ImageLoader.getInstance().displayImage(bean.getUrl(), topImageView,
//				options, new ImageLoadingListener() {
//
//					@Override
//					public void onLoadingStarted(String imageUri, View view) {
//						// TODO Auto-generated method stub
//						//topProg.setVisibility(View.VISIBLE);
//						LogUtils.e(TAG, imageUri);
//					}
//
//					@Override
//					public void onLoadingFailed(String imageUri, View view,
//							FailReason failReason) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void onLoadingComplete(String imageUri, View view,
//							Bitmap loadedImage) {
//						//topProg.setVisibility(View.GONE);
//					}
//
//					@Override
//					public void onLoadingCancelled(String imageUri, View view) {
//						// TODO Auto-generated method stub
//
//					}
//					@Override
//					public void onBitmapCreate(boolean fromMemeory, Bitmap map) {
//					}
//					
//				});
//	}
//	

}
