package com.savindu.plugin_api;

/**
 * This the interface which the plugins will be implement inorder to register to the plugin api system and receive callbacks from the main
 * I used only two methods (initialCallback and notifyExecution) to send the main app data to the plugins because those two are the main executions of the main app
 * Grab the implementation idea by the observer design pattern which described in the lecture 6 notes.
 * Refer following blogs to get the clear idea about observer design pattern
 *      *https://www.tutorialspoint.com/design_pattern/observer_pattern.htm - this has not worked fine but provided a basic idea
 *      *https://stackabuse.com/the-observer-design-pattern-in-java/ - this has worked and i grab the ideation to develop this
 */

/**
 * How to connect a new plugin to the Plugin API system
 *      * All the plugins exists on the system should implement this interface inorder to obtain the callback (data which sent from the main app to the plugin api when main app completed a important execution)
 *      * Moreover this PluginObserver interface object will be required to when plugin call the PluginController class inorder to register to the plugin system
 */

public interface PluginObserver {

    //send main app data to plugin when main app calls the sendInitialData() inorder to send equation and its data to the plugin which connected to the plugin api
    void initialCallback(String equation, double min, double max, double inc);

    //send main app data to plugin when main app's python script calls the updatePluginData() inorder to send equation results to the plugin which connected to the plugin api
    //every time the python script executes a evaluation this will be called and connected plugin will be able to get the results by using the callback interface
    void notifyExecution(double xValue, double yValue);

}
