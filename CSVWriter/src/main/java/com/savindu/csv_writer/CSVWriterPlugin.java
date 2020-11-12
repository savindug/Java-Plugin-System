package com.savindu.csv_writer;

import com.savindu.plugin_api.PluginController;
import com.savindu.plugin_api.PluginObserver;

public class CSVWriterPlugin {

    private String equation = null;
    private double minX = 0;
    private double maxX = 0;
    private double inc = 0;
    private int noOfExecutions = 0;
    private int completedExecutions = 0;

    private PluginController pc = null;


    CSVWriter csvW = new CSVWriter();

    /**
     * this inner class will implement the Plugin Observer interface inorder to get callbacks from the plugin api
     */
    private static class CSVWritterCallback implements PluginObserver {
        private CSVWriterPlugin pbp;
        /**
         * cal back method which send execution data from main app to the plugin
         * @param xValue
         * @param yValue
         */
        @Override
        public void notifyExecution(double xValue, double yValue) {
            pbp.write2csv(xValue, yValue);
        }

        /**
         *  cal back method which send initial execution data from main app to the plugin
         * @param equation
         * @param min
         * @param max
         * @param inc
         */
        @Override
        public void initialCallback(String equation, double min, double max, double inc) {
            this.pbp = new CSVWriterPlugin(equation, min, max, inc);
        }
    }

    /**
     * this overloaded constructor will be sue to load the CSVWriterPlugin dynamically from the main app
     * @param pc
     *
     */
    public CSVWriterPlugin(PluginController pc) {

        System.out.println("Activating CSV Writer Plugin........");

        this.pc = pc;
        //create CSVWritterCallback callback to handle callbacks from the API
        CSVWritterCallback callbackHandler = new CSVWritterCallback();

        //register plugin to get callbacks
        pc.registerPlugin(callbackHandler);

    }

    public CSVWriterPlugin(String equation, double minX, double maxX, double inc) {
        this.equation = equation;
        this.minX = minX;
        this.maxX = maxX;
        this.inc = inc;

        this.noOfExecutions = (int) ((maxX -minX) / inc +1);

    }

    public void write2csv(double x, double y){
        csvW.writeData(x, y);
    }

}
