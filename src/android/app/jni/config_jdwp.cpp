#include <stdbool.h>
#include <string>
#include <iostream>
#include "byopen.h"
#include "config_jdwp.h"

namespace art {
    static JDWP::JdwpOptions gJdwpOptions;
    void configure_jdwp(by_pointer_t handler) {
        //重新配置gJdwpOptions

        void (*ConfigureJdwp)(const JDWP::JdwpOptions* jdwp_options);
        ConfigureJdwp = (void (*)(const JDWP::JdwpOptions*)) by_dlsym(handler,
            "_ZN3art3Dbg13ConfigureJdwpERKNS_4JDWP11JdwpOptionsE");
        //std::string options = "transport=dt_socket,address=8000,server=y,suspend=n";

        ConfigureJdwp(&gJdwpOptions);
    }
}

extern "C"
JNIEXPORT jboolean Java_singleheaven_remotedebug_android_JdwpJni_restartJdwp(JNIEnv* env, jclass jthis, jboolean stop)
{
    unsigned char b = stop;
    by_bool_t ok = by_false;
    by_pointer_t handler = by_dlopen("libart.so", BY_RTLD_LAZY);
    if (handler) {
        void (*allowJdwp)(bool);
        allowJdwp = (void (*)(bool)) by_dlsym(handler, "_ZN3art3Dbg14SetJdwpAllowedEb");
        //对于debuggable false的配置，重新设置为可调试
        allowJdwp(!b);
        by_trace("allowJdwp succeed");

        void (*pfun)();
        //关闭之前启动的jdwp-thread
        pfun = (void (*)()) by_dlsym(handler, "_ZN3art3Dbg8StopJdwpEv");
        pfun();
        by_trace("StopJdwp succeed");

        if (!b) {
            art::configure_jdwp(handler);
            by_trace("ConfigureJdwp succeed");

            //重新startJdwp
            pfun = (void (*)()) by_dlsym(handler, "_ZN3art3Dbg9StartJdwpEv");
            pfun();
            by_trace("StartJdwp succeed");
        }
        // load ok
        ok = by_true;
        by_dlclose(handler);
    }
    return ok;
}