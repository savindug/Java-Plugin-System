#include <jni.h>
#include <stdio.h>
#include <iostream>

#define prgbarBG "\u001B[47m"
#define resetBG "\u001B[0m"

// This construct is needed to make the C++ compiler generate C-compatible compiled code.
extern "C"
{
    double c_min = 0;
    double c_max = 0;
    double c_inc = 0;
    int total_tasks = 0;
    int completed_tasks = 0;
    float percentage = 0;

    double c_x = 0;
    double c_y = 0;



    // Insert all your native functions here...

    //just a function that load the progress bar into the console
     void progress_bar(int percentage, int completed, int total){
        printf("\r");
                  printf("[");

                for(int i = 0; i < 100; i++){
                    if( i < (percentage)){
                        printf("|");
                    }else if( i == (percentage)){
                        printf(">");
                    }else{
                        printf("-");
                    }
                }

                printf("]   %d%c      [%d/%d]      Version-C", percentage, '%', completed, total);

     }
    
    JNIEXPORT void JNICALL Java_com_savindu_main_MainApp_hello(JNIEnv *env, jclass cls)
    {
        printf("Hello world\n");
    }


    //Jni implemetation of the
    JNIEXPORT void JNICALL Java_com_savindu_main_ProgressBarCpp_progressBarCpp(JNIEnv *env, jobject obj, jobject pc){

        //get the object of the java class which implement the PluginObserver interface
        jclass pbcall_cls = env->FindClass("com/savindu/main/ProgressBarCpp$ProgressBarCallbackCpp");
        jobject pbcall_obj = env->AllocObject(pbcall_cls);

        //call the register method of the PluginController which in the plugin api
        jclass pc_Cls = env->GetObjectClass(pc);
        jmethodID pc_register = env->GetMethodID(pc_Cls, "registerPlugin", "(Lcom/savindu/plugin_api/PluginObserver;)V");
        //jobject pc_obj = env->NewObject(pc_Cls, pc_register, pbcall_obj);
        jobject pc_obj = env->CallObjectMethod(pc, pc_register, pbcall_obj);

                 printf("\n");

      }


//handling the callbacks which recieved from the PluginObserver interface implementation
    JNIEXPORT void JNICALL Java_com_savindu_main_ProgressBarCpp_00024ProgressBarCallbackCpp_notifyExecution
      (JNIEnv *env, jobject cls, jdouble x, jdouble y){
            c_x = (double) x;
            c_y = (double) y;

            completed_tasks = 0;
                for(double i = c_min; i<=c_x; i+=c_inc){
                    completed_tasks++;
                }


                percentage = (completed_tasks * 100) / total_tasks;
                progress_bar(percentage, completed_tasks, total_tasks);


      }

//handling the callbacks which recieved from the PluginObserver interface implementation
    JNIEXPORT void JNICALL Java_com_savindu_main_ProgressBarCpp_00024ProgressBarCallbackCpp_initialCallback
      (JNIEnv *env, jobject obj, jstring expression, jdouble min, jdouble max, jdouble inc){
                     c_min = (double) min;
                     c_max = (double) max;
                     c_inc = (double) inc;

                     total_tasks = (int) ((c_max - c_min) / c_inc +1);


                     //printf("C initialCallback :: min = %f, max = %f, inc = %f, Total tasks = %d\n", c_min, c_max, c_inc, total_tasks);
      }



}
