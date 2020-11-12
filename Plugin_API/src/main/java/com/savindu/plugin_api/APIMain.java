package com.savindu.plugin_api;

/**
 * This class will help to establish a connection between main application and the plugins using plugin controller
 * I used only two methods to send the main app data to the plugins because those two are the main executions of the main app
 * Grab the implementation idea by the observer design pattern which described in the lecture 6 notes.
 * Refer following blogs to get the clear idea about observer design pattern
 *      *https://www.tutorialspoint.com/design_pattern/observer_pattern.htm - this has not worked fine but provided a basic idea
 *      *https://stackabuse.com/the-observer-design-pattern-in-java/ - this has worked and i grab the ideation to develop this
 */

/**
 * How to connect a new plugin to the Plugin API system
 *      * When connecting a new plugin  to the plugin api plugin developer don't need to do anything with this APIMain class because APIMain only be called by the main app
 *      also 3rd party plugins have restricted to access this APIMain class
 */

public class APIMain {


    /**
     * PluginController pc is to identify the plugin when they connecting to the plugin Api
     */
    private PluginController pc;

    /**
     * @param pc
     * when the main application call the APIMain class this PluginController pc object will be initialized and
     * it will use to update the plugin system when main application has notify a event
     */
    public APIMain(PluginController pc){
        this.pc = pc;
    }


    /**
     * sendInitialData function is using by the main application when it needs to trigger the plugin initialization in the plugin api
     * this will be only triggers when main app has send the initial user data to the plugin api
     * using the PluginController pc this method will send that data to the PluginController inorder to broadcast them with the connected plugins
     * @param equation
     * @param min
     * @param max
     * @param inc
     */
    public void sendInitialData(String equation, double min, double max, double inc){
        pc.initializePlugins(equation, min, max, inc);
    }

    /**
     *updatePluginData function is using by the main application when it needs to trigger an evaluation complete event by the python script
     *this will be triggers when main app has send evaluation results to the plugin api [each time the python script executes a evaluation this will be call by the main app]
     *using the PluginController pc this method will send that data to the PluginController inorder to broadcast them with the connected plugins
     * @param x
     * @param y
     */
    public void updatePluginData(double x, double y){
        pc.notifyPlugins(x, y);
    }


}
