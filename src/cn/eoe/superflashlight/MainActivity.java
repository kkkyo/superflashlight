package cn.eoe.superflashlight;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

public class MainActivity extends Bulb {
	
	public void onClick_ToFlashlight(View view)
	{
		hideAllUI();
		mUIFlashlight.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
		mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
	}
	
	public void onClick_ToWarningLight(View view)
	{
		hideAllUI();
		mUIWarningLight.setVisibility(view.VISIBLE);
		mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
		mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
		screenBrightness(1f);
		new WarningLightThread().start();
	}
	
	public void onClick_ToMorse(View view)
	{
		hideAllUI();
		mUIMorse.setVisibility(view.VISIBLE);
		mLastUIType = UIType.UI_TYPE_MORSE;
		mCurrentUIType = UIType.UI_TYPE_MORSE;
	}
	
	public void onClick_ToBulb(View view)
	{
		hideAllUI();
		mUIBulb.setVisibility(view.VISIBLE);
		mHideTextViewBulb.hide();
		mHideTextViewBulb.setTextColor(Color.BLACK);
		mLastUIType = UIType.UI_TYPE_BLUB;
		mCurrentUIType = UIType.UI_TYPE_BLUB;
	}
	
	public void onClick_Controller(View view) {
		hideAllUI();
		if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
			mUIMain.setVisibility(View.VISIBLE);
			mCurrentUIType = UIType.UI_TYPE_MAIN;
			mWarningLightFlicker = false;
			screenBrightness(mDefaultScreenBrightness / 255f);
			if(mBulbCrossFadeFlag)
			{
				mDrawable.reverseTransition(0);
			}
			mBulbCrossFadeFlag = false;
		} else {
			switch (mLastUIType) {
			case UI_TYPE_FLASHLIGHT:
				mUIFlashlight.setVisibility(View.VISIBLE);
				screenBrightness(1f);
				mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
				break;
			case UI_TYPE_WARNINGLIGHT:
				mUIWarningLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
				new WarningLightThread().start();
				break;
			case UI_TYPE_MORSE:
				mUIMorse.setVisibility(View.GONE);
				mCurrentUIType = UIType.UI_TYPE_MORSE;
				break;
			case UI_TYPE_BLUB:
				mUIBulb.setVisibility(View.GONE);
				mCurrentUIType = UIType.UI_TYPE_BLUB;
				break; 

			default:
				break;
			}
		}
	}
}
