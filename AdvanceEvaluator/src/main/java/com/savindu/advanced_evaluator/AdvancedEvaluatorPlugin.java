package com.savindu.advanced_evaluator;

import com.savindu.plugin_api.PluginController;
import com.savindu.plugin_api.PluginObserver;

public class AdvancedEvaluatorPlugin {

    private PluginController pc = null;
    private String equation = null;
    private double minX = 0;
    private double maxX = 0;
    private double inc = 0;
    private int noOfExecutions = 0;
    private int completedExecutions = 0;

    private static class AdvancedCallback implements PluginObserver {

        private AdvancedEvaluatorPlugin advancedEvaluatorPlugin = null;

        @Override
        public void initialCallback(String equation, double min, double max, double inc) {

        }

        @Override
        public void notifyExecution(double xValue, double yValue) {

        }
    }

    public AdvancedEvaluatorPlugin(PluginController pc){

        System.out.println("Activating Advanced Evaluator Plugin........");

        this.pc = pc;

        AdvancedCallback acb = new AdvancedCallback();
        pc.registerPlugin(acb);
    }

    public AdvancedEvaluatorPlugin(String equation, double minX, double maxX, double inc) {
        this.equation = equation;
        this.minX = minX;
        this.maxX = maxX;
        this.inc = inc;

        //calculate and set no of executions in the increment loop
        this.noOfExecutions = (int) ((maxX -minX) / inc +1);

    }
}
