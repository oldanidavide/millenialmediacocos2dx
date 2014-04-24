#ifndef __OCCMillennialMedia_SCENE_H__
#define __OCCMillennialMedia_SCENE_H__

#include "cocos2d.h"


USING_NS_CC;

typedef enum _CCAdType
{   
    kCCAdTypeLeaderBoardRectangle,
    kCCAdTypeSmallBanner,
    kCCAdTypeMediumBanner,
    kCCAdTypeRectangle,
    kCCAdTypeInterstitial
} CCAdType;

typedef enum _CCHorizontalAlignment
{
    kCCHorizontalLeft = -1,
    kCCHorizontalCenter = 0,
    kCCHorizontalRight = 1
} CCHorizontalAlignment;

typedef enum _CCVerticalAlignment
{
    kCCVerticalBottom = -1,
    kCCVerticalCenter = 0,
    kCCVerticalTop = 1


} CCVerticalAlignment;

class OCCMillennialMedia : public CCNode
{

private:


public:

    static OCCMillennialMedia* create(const char* appId,CCAdType adType);
    virtual bool init(const char* appId,CCAdType adType);

    static void setAdvVisible(bool visible);

    OCCMillennialMedia(void);


};

#endif // __OCCMillennialMedia_SCENE_H__
