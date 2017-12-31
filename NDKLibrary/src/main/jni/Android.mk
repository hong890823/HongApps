LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := NDKLibrary
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \

LOCAL_SRC_FILES := \
	C:\HongApps\NDKLibrary\src\main\jni\Any.c \
	C:\HongApps\NDKLibrary\src\main\jni\NDKLibrary.c \

LOCAL_C_INCLUDES += C:\HongApps\NDKLibrary\src\main\jni
LOCAL_C_INCLUDES += C:\HongApps\NDKLibrary\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
