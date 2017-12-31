#include <stdio.h>
#include <stdlib.h>
#include "com_hong_ndklibrary_NDKString.h"
#include <android/log.h>
#define TAG "Hong"
#define LOGV(...)__android_log_print(ANDROID_LOG_VERBOSE,TAG,__VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_hong_ndklibrary_NDKString_getFromC
  (JNIEnv * env, jclass jclass){
    LOGV("进入到%s方法中","getFromC");
    return (*env)->NewStringUTF(env,"NDKLibrary from C");
  }

JNIEXPORT void JNICALL Java_com_hong_ndklibrary_NDKString_uploadFile
(JNIEnv * env, jobject jobject, jstring path){
    const char * file_path = (*env)->GetStringUTFChars(env,path,NULL);
    if(file_path!=NULL){
        LOGV("路径转换成功");
        FILE *file =  fopen(file_path,"a+");
        if(file!=NULL){
            LOGV("生成文件成功");
            char data[] = "I am Hong";
            int count = fwrite(data,strlen(data),1,file);
                if(count>0){
                    LOGV("写入文件成功");
                }
       }

        if(file!=NULL){
        fclose(file);
        }

    }
    (*env)->ReleaseStringUTFChars(env,path,file_path);
 }

JNIEXPORT jintArray JNICALL Java_com_hong_ndklibrary_NDKString_updateIntArray
        (JNIEnv *env, jobject jobject, jintArray array){
    jint* data = (*env)->GetIntArrayElements(env,array,NULL);
    jsize length = (*env)->GetArrayLength(env,array);
    int i;
    for(i=0;i<length;i++){
        data[i]+=5;
        LOGV("数组变更为%d",data[i]);
    }
    (*env)->ReleaseIntArrayElements(env,array,data,0);
    return array;
}