/* 
Integration of the ad Millennial Media for Android Cocos2d-X 
made by: Davide Oldani 
Date: March 29, 2014
*/

package org.cocos2dx.hellocpp;

import org.cocos2dx.lib.Cocos2dxActivity;

import java.lang.ref.WeakReference;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;
import android.os.Handler;
import android.os.Message;

import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMInterstitial;
import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMBroadcastReceiver;
import com.millennialmedia.android.MMRequest;
import com.millennialmedia.android.MMSDK;
import com.millennialmedia.android.RequestListener.RequestListenerImpl;
import com.millennialmedia.android.RequestListener;
import com.millennialmedia.android.MMException;


public class OCCMillennialMedia {

	private static Activity mActivity = null;

	private native void initJNI(Object wadk_this);
	protected static Handler mHandler;

	//Object for avaiable Adv (interstitial or Banner)
	protected MMAdView adView;
	protected MMInterstitial interstitial;

	protected RelativeLayout mLayout;

	//Define size of avaible advertising
	private static final int LEADERBOARD_WIDTH = 728;
	private static final int LEADERBOARD_HEIGHT = 90;

	private static final int MED_BANNER_WIDTH = 480;
	private static final int MED_BANNER_HEIGHT = 60;

	private static final int SMALL_BANNER_WIDTH = 320;
	private static final int SMALL_BANNER_HEIGHT = 50;

	private static final int RECTANGLE_WIDTH = 300;
	private static final int RECTANGLE_HEIGHT = 250;

	//SIZE
	private int _width;
	private int _height;

	//MESSAGE
    protected static final int MMEDIA_INIT                  = 1;
    protected static final int MMEDIA_VISIBLE               = 2;

    //BANNER TYPE
    protected static final int TYPE_LEADERBOARD            	= 0;
    protected static final int TYPE_SMALL_BANNER         	= 1;
    protected static final int TYPE_MEDIUM_BANNER       	= 2;    
    protected static final int TYPE_RECTANGLE_BANNER       	= 3;    
    protected static final int TYPE_INTERSTITIAL_BANNER     = 4;    


    public static class InitMessage
    {
        public String appId;
        public int adType;

        public InitMessage(String appId,int adType)
        {
            this.appId = appId;
            this.adType = adType;
        }
    }

    public static class SetVisibleMessage
    {

        public boolean visible;

        public SetVisibleMessage(boolean visible)
        {
            this.visible = visible;
        }
    }


    public OCCMillennialMedia(Cocos2dxActivity activity)
    {
    	mActivity = activity;
 		
 		initJNI(new WeakReference<OCCMillennialMedia>(this));

        mHandler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                case MMEDIA_INIT:
                    {
                        InitMessage msgBody = (InitMessage)msg.obj;
                        OCCMillennialMedia.this.init(msgBody.appId,msgBody.adType);
                    }
                    break;                
                case MMEDIA_VISIBLE:
                    {
                        SetVisibleMessage msgBody = (SetVisibleMessage)msg.obj;
                        OCCMillennialMedia.this.setVisible(msgBody.visible);
                    }
                    break;            
                }      


                super.handleMessage(msg);
            }
        };
    }
	
	//Setup the Adv
    private static void nativeInit(String appId,int adType)
    {
        Message msg = new Message();
        msg.what = MMEDIA_INIT;
        msg.obj  = new InitMessage(appId,adType);
        mHandler.sendMessage(msg);
    }	

    //init adv
	public void init(String appId,int adType){

    	if (adType != TYPE_INTERSTITIAL_BANNER){
			bannerAdv(appId,adType);
		} else {
			interstitialAdv(appId);
		}

	}

	//Show the Interstitial ADV
	public void interstitialAdv(String appId){

		//clear old interstitial
        if (interstitial != null)
        {
            interstitial = null;
        }

        //create a new interstitial
	 	interstitial = new MMInterstitial(mActivity);

	 	//set app id
		interstitial.setApid(appId);

		MMRequest request = new MMRequest();
		interstitial.setMMRequest(request);		

		//load and cache it
		interstitial.fetch();
		interstitial.setListener(new RequestListenerImpl() {		
			@Override
			public void requestCompleted(MMAd mmAd) {
				//display the interstitial
			   interstitial.display();
			}
		});	

	}

	//Show the Banner / Rectangle Adv
	public void bannerAdv(String appId,int adType){

		//clear old adView and Layout		
        if (adView != null)
        {
            adView = null;
            mLayout = null;
        }

        //Create a relative layout that fill the display
		mLayout = new RelativeLayout(mActivity);

        mActivity.addContentView(mLayout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		adView = new MMAdView(mActivity);

		//set app id
		adView.setApid(appId);

		//Set the id to preserve your ad on configuration changes.
		adView.setId(MMSDK.getDefaultAdId());

		//Type of available banner / rectangle
 		switch (adType) {
            case TYPE_LEADERBOARD:  		
				_width 	= LEADERBOARD_WIDTH;
				_height = LEADERBOARD_HEIGHT;
				break;
            case TYPE_SMALL_BANNER:  		
				_width 	= SMALL_BANNER_WIDTH;
				_height = SMALL_BANNER_HEIGHT;
				break;		
            case TYPE_MEDIUM_BANNER:  		
				_width 	= MED_BANNER_WIDTH;
				_height = MED_BANNER_HEIGHT;
				break;		
            case TYPE_RECTANGLE_BANNER:  		
				_width 	= RECTANGLE_WIDTH;
				_height = RECTANGLE_HEIGHT;
				break;	
		}


        if(canFit(LEADERBOARD_WIDTH))
        {
            _width = LEADERBOARD_WIDTH;
            _height = LEADERBOARD_HEIGHT;
        }
        else if(canFit(MED_BANNER_WIDTH))
        {
            _width = MED_BANNER_WIDTH;
            _height = MED_BANNER_HEIGHT;
        }

		//Set the adView Size
		adView.setWidth(_width);
		adView.setHeight(_height);
		
		//Add the adView to the layout.
		mLayout.addView(adView);

		adView.getAd();		

	}


    public void setAlignment(int horizontal, int vertical)
    {
    	//set the layout size like a request banner size
		int layoutWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _width, mActivity.getResources().getDisplayMetrics());
		int layoutHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _height, mActivity.getResources().getDisplayMetrics());

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(layoutWidth, layoutHeight);

		//add a rule for Horinzontal and Vertical alignement
 		switch (horizontal) {
            case -1:  
            	layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case 0:  
            	layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case 1:              	
            	layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            default: 
            	layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            	break;
        }

 		switch (vertical) {
            case -1:              	
            	layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case 0:  
            	layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case 1:  
            	layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            default: 
            	layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
           		break;
        }

		adView.setLayoutParams(layoutParams);

    }

    //if have enought space change the size
    protected boolean canFit(int adWidth)
    {   

        int adWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, adWidth, mActivity.getResources().getDisplayMetrics());
        DisplayMetrics metrics = mActivity.getResources().getDisplayMetrics();

        return metrics.widthPixels >= adWidthPx;
    }

    //Show or Hide the adView
    private static void nativeSetVisible(boolean visible)
    {
        Message msg = new Message();
        msg.what = MMEDIA_VISIBLE;
        msg.obj  = new SetVisibleMessage(visible);
        mHandler.sendMessage(msg);
    }
    public void setVisible(boolean visible)
    {  
        if (adView != null)
        {        	
            adView.setVisibility(visible ? adView.VISIBLE : adView.INVISIBLE);
        }
    }


}
