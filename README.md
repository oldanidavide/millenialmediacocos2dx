Cocos2d-x Millenial Media Integration
======================

This is a really simple integration of Millenial Media in Cocos2d-X (i've used a version 2.2)

Follow me on Twitter <a href="https://twitter.com/oldanidavide" target="_blank">@oldanidavide</a>

### STEP

  * Create a new cocos2d project
  * copy our library in your project folder (java and cpp files)
  * copy the MMSDK library in your android libs folder
  * associate the library at cocos project
  * configure the android manifest
  * write code!!


### HOW DOES IT WORK

This class works with regular banner and with the interstitial

### HOW TO MAKE

Start a new cocos2dx project and copy the files `OCCMillennialMedia.cpp`, `OCCMillennialMedia.h` in your `Classes` folder.<br/>
From `OCCMillennialMedia.cpp` you need to change a line: 19 and 26 with the correct package name, in my case the name of package is: <b>org_cocos2dx_hellocpp </b>

Copy the file `OCCMillennialMedia.java` in your java source folder.<br/>
<i>Example: proj.android/src/org/cocos2dx/hellocpp<br/></i>
Remember to change a package name, in my project the package name is: package org.cocos2dx.hellocpp; (Line 7)<br/>

Download from <a target="_blank" href="http://www.millennialmedia.com/">Millenial Media</a> the <b>Android SDK</b>, extract them and copy the file `MMSDK.jar` in your java `Libs` folder.<br>
<i>Example: project.android/libs</i><br/>
If the folder doesn't exist, make it.

Now need to associate the `MMSDK.jar` library in your cocos2dx project for do that open `Eclipse` and import your cocos2dx project<br/>
<b>File->Import / Android->Existing Android Code Into Workspace</b>

Click `Browse` and search your cocos2dx project folder, select them and click on `Finish`

Now right click on the opened project and click `Properties`, select `Java Build Path`, and click `Add External JARs`, 
and select the `MMSDK.jar` copied before in libs folder 

Clean Project if necessary

Save and close Eclipse

Now you have setup all for start coding and integrate the millenial media ADV in your project.

### Android Manifest

Open `AndroidManifest.xml` and add the following permissions:

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        
    <uses-feature android:name="android.hardware.microphone" android:required="false" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

then add the following activity

    <activity android:name="com.millennialmedia.android.MMActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar" android:configChanges="keyboardHidden|orientation|keyboard" ></activity>     

### Setup Android.mk

Open the Android.mk (Located in proj.android/jni) and add the `OCCMillennialMedia.cpp` in `LOCAL_SRC_FILES`

the result looks like that:

	LOCAL_SRC_FILES := hellocpp/main.cpp \
                   ../../Classes/AppDelegate.cpp \
                   ../../Classes/OCCMillennialMedia.cpp \
                   ../../Classes/MillenialMediaTutorial.cpp


Now under the 

	LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes

add	
	
	LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../../../../cocos2dx/platform/android/jni/


### BANNER

For add a banner in your project only need to add some line of code in your main .java file.<br/>
Example: proj.android/src/org/cocos2dx/hellocpp/HelloCpp.java

Import the millenial media class with:

	import org.cocos2dx.hellocpp.OCCMillennialMedia;

remember to change a package if you have another name.

Add the following lines before the protected void onCreate(Bundle savedInstanceState){...}

    protected OCCMillennialMedia advMedia;

    //ALIGNMENT
    protected static final int LEFT         = -1;
    protected static final int CENTER       = 0;
    protected static final int RIGHT        = 1;    
    protected static final int BOTTOM       = -1;    
    protected static final int MIDDLE       = 0;    
    protected static final int TOP          = 0;        

    //BANNER TYPE
    protected static final int TYPE_LEADERBOARD            	= 0;
    protected static final int TYPE_SMALL_BANNER         	= 1;
    protected static final int TYPE_MEDIUM_BANNER       	= 2;    
    protected static final int TYPE_RECTANGLE_BANNER       	= 3;    
    protected static final int TYPE_INTERSTITIAL_BANNER     	= 4;

Now inside the onCreate method under "super.onCreate(savedInstanceState)" add

	advMedia = new OCCMillennialMedia(this);
	advMedia.init("YOUR-MILLENIAL-MEDIA-APP-ID",TYPE_SMALL_BANNER);		
	advMedia.setAlignment(CENTER,MIDDLE);


You can change a banner position with:
    
    advMedia.setAlignment(CENTER,MIDDLE);
    
For the first value (`CENTER`) is available `LEFT`, `CENTER`, `RIGHT`<br/>
For the second value (`MIDDLE`) is available `BOTTOM`, `MIDDLE`, `TOP`
<br/>
You can also change a banner type, for do that in the line 

	advMedia.init("YOUR-MILLENIAL-MEDIA-APP-ID",TYPE_SMALL_BANNER);
	
change a `TYPE_SMALL_BANNER` with one of that:

    TYPE_LEADERBOARD            = 728x90;
    TYPE_SMALL_BANNER         	= 320x50;
    TYPE_MEDIUM_BANNER       	= 480x60;    
    TYPE_RECTANGLE_BANNER       = 300x250;    


### HIDE AND SHOW BANNER

If you want hide the adv inside your game just import the `OCCMillennialMedia.h`.

Open your main class header file<br/> 
Example: Classes/HelloWorldScene.h<br>

and include the MillenialMedia file: 

`#include "OCCMillennialMedia.h"`

now you need only write in your game code

`OCCMillennialMedia::setAdvVisible(false);`

where you want hide (or show) the adv.


### INTERSTITIAL

For add an interstitial in your project need to add some line of code in your main .java file.
Example: proj.android/src/org/cocos2dx/hellocpp/HelloCpp.java

Import the millenial media class with:

	import org.cocos2dx.hellocpp.OCCMillennialMedia;

remember to change a package if you have another name.

Add the following line before the protected void onCreate(Bundle savedInstanceState){...}

    protected OCCMillennialMedia advMedia;

Now inside the onCreate method under "super.onCreate(savedInstanceState)" add

	advMedia = new OCCMillennialMedia(this);

Open your main class header file 
Example: Classes/HelloWorldScene.h

and include the MillenialMedia file:

	#include "OCCMillennialMedia.h"

now you need add write in your cpp file

	OCCMillennialMedia *adView = OCCMillennialMedia::create("YOUR-MILLENIAL-MEDIA-INTERSTITIAL-ID", kCCAdTypeInterstitial);
	this->addChild(adView, 100);

