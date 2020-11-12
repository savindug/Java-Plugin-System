package com.savindu.progressbar;

import com.savindu.plugin_api.PluginController;
import com.savindu.plugin_api.PluginObserver;

public class ProgressBarPlugin {

    private String equation = null;
    private double minX = 0;
    private double maxX = 0;
    private double inc = 0;
    private int noOfExecutions = 0;
    private int completedExecutions = 0;

    private double xValue = 0;
    private double yValue = 0;

    private PluginController pc = null;


    /**
     * this inner class will implement the Plugin Observer interface inorder to get callbacks from the plugin api
     */
    private static class ProgressBarCallback implements PluginObserver {
        private ProgressBarPlugin pbp;

        /**
         * cal back method which send execution data from main app to the plugin
         * @param xValue
         * @param yValue
         */
        @Override
        public void notifyExecution(double xValue, double yValue) {
            this.pbp.setX(xValue);
            this.pbp.setY(yValue);
            pbp.calcCompletedExecutions();
            pbp.loadProgressBar();
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
            this.pbp = new ProgressBarPlugin(equation, min, max, inc);
        }

    }

    /**
     * this overloaded constructor will be sue to load the ProgressBarPlugin dynamically from the main app
     * @param pc
     *
     */
    public ProgressBarPlugin(PluginController pc) {

        System.out.println("Activating Progress bar Plugin........");

        this.pc = pc;

        //create progress bar callback to handle callbacks from the API
        ProgressBarCallback callbackHandler = new ProgressBarCallback();

        //register plugin to the plugin api system inorder to get the callbacks from it
        pc.registerPlugin(callbackHandler);

    }

    public ProgressBarPlugin(String equation, double minX, double maxX, double inc) {
        this.equation = equation;
        this.minX = minX;
        this.maxX = maxX;
        this.inc = inc;

        //calculate and set no of executions in the increment loop
        this.noOfExecutions = (int) ((maxX -minX) / inc +1);

        //System.out.println(this.toString());


    }

    public void setX(double xValue) {
        this.xValue = xValue;
    }

    public void setY(double yValue) {
        this.yValue = yValue;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public void setInc(double inc) {
        this.inc = inc;
    }

    public void setNoOfExecutions(int noOfExecutions) {
        this.noOfExecutions = noOfExecutions;
    }

    public void setCompletedExecutions(int completedExecutions) {
        this.completedExecutions = completedExecutions;
    }

    //calculate the completed executions in the loop
    private void calcCompletedExecutions(){
        int completedExe = 0;
        for(double i = this.minX; i<=xValue; i+=this.inc){
            completedExe++;
        }

        setCompletedExecutions(completedExe);

    }

    public void loadProgressBar(){
        ProgressBar pb = new ProgressBar();

        float percentage = (completedExecutions * 100) / noOfExecutions;

//        System.out.println("completedExecutions = " + completedExecutions + " noOfExecutions = " + noOfExecutions + " | percentage = " + percentage);

        try {
            pb.printProgressBar(percentage, completedExecutions, noOfExecutions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ProgressBarPlugin{" +
                "equation='" + equation + '\'' +
                ", minX=" + minX +
                ", maxX=" + maxX +
                ", inc=" + inc +
                ", noOfExecutions=" + noOfExecutions +
                ", completedExecutions=" + completedExecutions +
                '}';
    }
}
