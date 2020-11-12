package com.savindu.main;

import com.savindu.plugin_api.PluginController;
import com.savindu.plugin_api.PluginObserver;

/**
 * ProgressBarCpp class contains the java native implementation of the c progress bar plugin
 * c jni implementation is available in the c_library/src/main/cpp/example_c_library.cpp
 */

public class ProgressBarCpp {

    /**
     * this inner class will implement the Plugin Observer interface inorder to get callbacks from the plugin api
     */
    private static class ProgressBarCallbackCpp implements PluginObserver {

        /**
         * This method has implemented in the c_library
         * cal back method which send execution data from main app to the plugin
         * @param xValue
         * @param yValue
         */
        @Override
        public native void notifyExecution(double xValue, double yValue);

        /**
         * This method has implemented in the c_library
         *  cal back method which send initial execution data from main app to the plugin
         * @param equation
         * @param min
         * @param max
         * @param inc
         */
        @Override
        public native void initialCallback(String equation, double min, double max, double inc);

    }

    /**
     * this constructor does not perform any implementation only print to the terminal and call the  progressBarCpp native method
     * @param pc
     */
    public ProgressBarCpp(PluginController pc){
        System.out.println("Activating Progress bar Plugin Cpp version........");
        progressBarCpp(pc);
    }

    public native void progressBarCpp(PluginController pc);

}
