#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_android_project_nnfriends_1_MenuActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "NNfriends";
    return env->NewStringUTF(hello.c_str());
}
