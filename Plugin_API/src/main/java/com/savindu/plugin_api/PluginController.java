package com.savindu.plugin_api;

import java.util.ArrayList;
import java.util.List;

/**
 * This PluginController class is used to register plugins to the plugins api system inorder to receive callbacks from the plugin api
 * This class only called by the plugins inorder to connect to the plugin api
 * I used only two methods (initialCallback and notifyExecution) to send the main app data to the plugins because those two are the main executions of the main app
 * Grab the implementation idea by the observer design pattern which described in the lecture 6 notes.
 * Refer following blogs to get the clear idea about observer design pattern
 *      *https://www.tutorialspoint.com/design_pattern/observer_pattern.htm - this has not worked fine but provided a basic idea
 *      *https://stackabuse.com/the-observer-design-pattern-in-java/ - this has worked and i grab the ideation to develop this
 */

/**
 * How to connect a new plugin to the Plugin API system
 *      * This PluginController class is responsible to register new plugins to the plugin api using PluginObserver interface object
 *      *When plugin has loaded by the main app (using build dependencies), main method will send an object of this PluginController class to the plugin
 *      so that i order to register for the system that plugin only need to call the registerPlugin() method by using that PluginController object which sent by the main app
 *      Moreover plugin needs to pass an object of the class which implement the PluginObserver interface inorder to call the registerPlugin() method
 */

public class PluginController {

    /**
     * All the registered plugins should be added to the plugins list
     * list will keep track of every plugin which connected to the plugin api system
     */
    List<PluginObserver> plugins = new ArrayList<>();

    public PluginController() {
    }

    /**
     * registerPlugin method will add new plugins to the plugin api and register them on callback inorder to receive callbacks from main app through the plugin api
     * @param clientPlugin
     */
    public void registerPlugin(PluginObserver clientPlugin) {
        plugins.add(clientPlugin);
    }


    public void initializePlugins(String equation, double min, double max, double inc){
        for (PluginObserver plugin: plugins) {
            plugin.initialCallback(equation, min, max, inc);
        }
    }

    public void notifyPlugins(double xValue, double yValue){
        for (PluginObserver plugin: plugins) {
            plugin.notifyExecution(xValue, yValue);
        }
    }


}
