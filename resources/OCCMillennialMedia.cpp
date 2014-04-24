#include "JniHelper.h"
#include "OCCMillennialMedia.h"
#include "cocos2d.h"

using namespace cocos2d;
USING_NS_CC;

static jmethodID mid;
static jclass mClass;
static jobject mObject;


#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID) 

    #ifdef __cplusplus
    extern "C" {
    #endif 

    JNIEXPORT void JNICALL Java_org_cocos2dx_hellocpp_OCCMillennialMedia_initJNI(JNIEnv*, jobject, jobject);

    #ifdef __cplusplus
    }
    #endif
#endif

JNIEXPORT void JNICALL Java_org_cocos2dx_hellocpp_OCCMillennialMedia_initJNI(JNIEnv* env, jobject thiz, jobject weak_this)
{
    jclass clazz = env->GetObjectClass(thiz);
    mClass = (jclass)env->NewGlobalRef(clazz);
    mObject = env->NewGlobalRef(weak_this);
}

class OCCMillennialMediaEnv
{
public:
    OCCMillennialMediaEnv()
    {
        m_isAttached = false;
        int status = JniHelper::getJavaVM()->GetEnv((void**)&m_env, JNI_VERSION_1_6);
        if (status < 0)
        {
            status = JniHelper::getJavaVM()->AttachCurrentThread(&m_env, NULL);
            if (status < 0)
            {
                return;
            }
            m_isAttached = true;
        }
        return;
    }

    ~OCCMillennialMediaEnv()
    {
        if (m_isAttached) JniHelper::getJavaVM()->DetachCurrentThread();
    }

    JNIEnv* operator-> () { return m_env; }

    protected:
        bool m_isAttached;
        JNIEnv* m_env;
};


static void OCCMillennialMedia_setVisible_Android(bool visible)
{
    OCCMillennialMediaEnv env;
    mid = env->GetStaticMethodID(mClass, "nativeSetVisible", "(Z)V");
    if (mid != NULL) env->CallStaticVoidMethod(mClass, mid, visible ? 1 : 0);
}


static void OCCMillennialMedia_init_Android(const char* appId,int adType)
{

    OCCMillennialMediaEnv env;
    mid = env->GetStaticMethodID(mClass, "nativeInit", "(Ljava/lang/String;I)V");
    if (mid != NULL) env->CallStaticVoidMethod(mClass, mid, env->NewStringUTF(appId),adType);
}


OCCMillennialMedia* OCCMillennialMedia::create(const char* appId,CCAdType adType)
{
    OCCMillennialMedia *advMedia = new OCCMillennialMedia();
    
    if (advMedia->init(appId,adType)){
        advMedia->autorelease();
        return advMedia;
    }

    CC_SAFE_DELETE(advMedia);

    return NULL;
}

OCCMillennialMedia::OCCMillennialMedia()
{
}

//Init the advertising
bool OCCMillennialMedia::init(const char* appId,CCAdType adType)
{

    #if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        OCCMillennialMedia_init_Android(appId,(int)adType);
    #endif
    
}

//set visibility
void OCCMillennialMedia::setAdvVisible(bool visible)
{

    #if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        OCCMillennialMedia_setVisible_Android(visible);
    #endif
    
}
