#include <jni.h>
#include <string>
#include <pthread.h>
#include <string>
#include <android/log.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <zconf.h>


#define LED_IOCTL_SET_OFF			0
#define LED_IOCTL_SET_ON		1

#define LED_IOCTL_SET			7

extern "C" JNIEXPORT void JNICALL
Java_com_pcg_yuquangong_views_SortingActivity_ledON(
        JNIEnv* env,
        jobject /* this */) {
    int led1 = ::open("/dev/sunxi-gpio", O_WRONLY);
   	if( led1 < 0 )
   	{
            //Log.e(TAG, "Open sunxi-gpio fail\n");
   	}

   	::ioctl(led1, LED_IOCTL_SET, LED_IOCTL_SET_ON);
       ::close(led1);

}

extern "C" JNIEXPORT void JNICALL
Java_com_pcg_yuquangong_views_SortingActivity_ledOFF(
        JNIEnv* env,
        jobject /* this */) {
    int led1 = ::open("/dev/sunxi-gpio", O_WRONLY);
   	if( led1 < 0 )
   	{
            //Log.e(TAG, "Open sunxi-gpio fail\n");
   	}

   	::ioctl(led1, LED_IOCTL_SET, LED_IOCTL_SET_OFF);
    ::close(led1);

}
