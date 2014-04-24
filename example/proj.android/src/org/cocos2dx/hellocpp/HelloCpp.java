/****************************************************************************
Copyright (c) 2010-2012 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.hellocpp;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.hellocpp.OCCMillennialMedia;
import android.os.Bundle;

public class HelloCpp extends Cocos2dxActivity{

protected OCCMillennialMedia advMedia;

//ALIGNMENT
protected static final int LEFT         = -1;
protected static final int CENTER       = 0;
protected static final int RIGHT        = 1;    
protected static final int BOTTOM       = -1;    
protected static final int MIDDLE       = 0;    
protected static final int TOP          = 0;        

//BANNER TYPE
protected static final int TYPE_LEADERBOARD             = 0;
protected static final int TYPE_SMALL_BANNER            = 1;
protected static final int TYPE_MEDIUM_BANNER           = 2;    
protected static final int TYPE_RECTANGLE_BANNER        = 3;    
protected static final int TYPE_INTERSTITIAL_BANNER         = 4;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		advMedia = new OCCMillennialMedia(this);
		advMedia.init("YOUR-MILLENIAL-MEDIA-APP-ID",TYPE_SMALL_BANNER);     
		advMedia.setAlignment(CENTER,BOTTOM);		
	}
	
    static {
         System.loadLibrary("hellocpp");
    }
}
