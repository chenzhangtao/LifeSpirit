package me.xiaopan.lifespirit;

import me.xiaopan.androidlibrary.app.BaseActivity;
import android.os.Bundle;
import android.view.MenuItem;

public abstract class MyBaseActivity extends BaseActivity {

	@Override
	protected void onPreInit(Bundle savedInstanceState) {
		ApplicationExceptionHandler.getInstance().setContext(this);
		if(!isRemoveTitleBar()){
			getActionBar().setBackgroundDrawable(getDrawable(R.drawable.shape_comm_titlebar));
			if(isEnableBackHome()){
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	}

	@Override
	protected boolean isUseCustomAnimation() {
		return true;
	}

	/**
	 * 判断是否启用返回主页
	 * @return 是否启用返回主页
	 */
	protected boolean isEnableBackHome() {
		return true;
	}

	@Override
	protected int[] onGetDefaultStartActivityAnimation() {
		return new int[]{R.anim.base_slide_to_left_in, R.anim.base_normal};
	}

	@Override
	protected int[] onGetDefaultFinishActivityAnimation() {
		return new int[]{R.anim.base_normal, R.anim.base_slide_to_right_out};
	}

	@Override
	protected int onGetLoadingHintViewId() {
		return R.id.comm_layout_loadingHint;
	}

	@Override
	protected int onGetListEmptyHintViewId() {
		return R.id.comm_layout_listEmptyHint;
	}

	@Override
	protected void onClickListEmptyHintView() {
	}

	@Override
	public String getHostServerAddress(){
		return "";
	}

	@Override
	protected void onNetworkNotAvailable() {
	}

	@Override
	public void onBecauseExceptionFinishActivity() {
		finishActivity(R.anim.base_alpha_show, R.anim.base_alpha_hide);
	}
	
	public MyApplication getMyApplication(){
		return (MyApplication) getApplication();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: finishActivity(); break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}