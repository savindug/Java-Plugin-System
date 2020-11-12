package com.savindu.main;

import com.savindu.main.script.ScriptHandler;
import com.savindu.plugin_api.APIMain;
import com.savindu.plugin_api.PluginController;

import java.lang.reflect.Constructor;
import java.util.Scanner;

/**
 * Code by Savindu Gunawardena : 20205322
 *      * Main Application of the Equation Evaluator
 *      *          *This Main app is used to interact with the user as an UI
 *      *          *Get user inputs using console
 *      *          *Load Plugins to the application
 *      *          *and do the expression equations
 * Also this app contains instances of Plugin API class to connect with the plugin API
 */
public class MainApp {

    /**
     * ScriptHandler sh is the object which used to evaluate the expression using jython
     */
    private static ScriptHandler sh = new ScriptHandler();
    /**
     * PluginController pc creates a plugin controller object which will use to register the plugins when user loads them
     */
    private static PluginController pc = new PluginController();
    /**
     * APIMain api_main is the object which creates to send data from main application to the plugin api
     * this will triggers when the user enters expression, min, max and increment values and also triggers when evaluation has completed for each increment
     */
    private static APIMain api_main = new APIMain(pc);

    private static int completedOperationsCounter = 0;
    private static Scanner scanner = new Scanner(System.in);
    private static  String user_command = null;

    static
    {
        System.loadLibrary("example_c_library");
    }

    public static void main(String[] args) {

        System.out.println("---------- Welcome to Equation Evaluator ----------");


        user_command = mainMenu();

    }

    /**
     *
     * @param x
     * @param y
     * updatePluginData() is used to send the evaluation results to the plugin api when each of them as completed
     * this method will call by the python interpreter in the com.savindu.main.script.ScriptHandler
     */
    public static void updatePluginData(double x, double y){
        api_main.updatePluginData(x, y);
    }

    public static void checkAdvanceEvaluators(){

    }

    /**
     *method creates to load the mainMenu data to the console
     */
    private static String mainMenu(){
        System.out.println("Select your option\n" +
                "\t1: Evaluates an Expression\n" +
                "\t2: Plugin Manager\n" +
                "\t0: Exit\n");

        System.out.println("Enter Selection to Continue :: ");
        user_command = scanner.nextLine();
        performAction(user_command);
        return user_command;
    }

    /**
     *method creates to perform the action when user choose a selection
     */
    private static void performAction(String user_command){
        if(user_command.equals("1")){
            expressionEvaluator();
        }

        if(user_command.equals("2")){
            pluginManager();
        }
    }

    /**
     *method creates to load the plugin data to the console
     */
    private static void pluginManager(){

        String leftAlignFormat = "| %-13s | %-30s | %-20s | %-15s |%n";
        System.out.println("-----Plugin Manager-----");

        System.out.format("+---------------+--------------------------------+----------------------+-----------------+%n");
        System.out.format("| Plugin ID     | Plugin name                    | Plugin Class         | Plugin Status   |%n");
        System.out.format("+---------------+--------------------------------+----------------------+-----------------+%n");

        System.out.format(leftAlignFormat, "1" ,"Progress Bar Plugin [JAVA]", "ProgressBarPlugin", "Disabled");
        System.out.format(leftAlignFormat, "2", "CSV Writer Plugin [JAVA]", "CSVWriterPlugin", "Disabled");
        System.out.format(leftAlignFormat, "3", "Progress Bar Plugin [Cpp]", "ProgressBarCpp", "Disabled");
        System.out.format(leftAlignFormat, "4", "Advanced Executor [JAVA]", "AdvancedEvaluatorPlugin", "Disabled");

        System.out.format("+---------------+--------------------------------+----------------------+-----------------+%n");

        System.out.println("Please Enter Plugin class id/s you need to activate (If you need to load multiple plugins separate their id's by space ):: ");
        user_command = scanner.nextLine();
        System.out.println("Selection :: " + user_command.toString());

        String[] activate2plugins = user_command.split("\\s+");

        //load the each plugin which user enters to the app
        for (String plug : activate2plugins){
            loadPlugins(plug, pc);
        }

        mainMenu();

    }
    /**
     *method creates to perform the evaluation using jython (python interpreter)
     */
    private static void expressionEvaluator(){
        System.out.println("Please Enter your Expression :: ");
        String user_expression = scanner.nextLine();
        System.out.println("Enter Minimum Value for x:: ");
        double min = scanner.nextDouble();
        System.out.println("Enter Maximum Value for x:: ");
        double max = scanner.nextDouble();
        System.out.println("Enter Increment of x:: ");
        double inc = scanner.nextDouble();


        //send initial data (user entered expression, minX, maxX, increment) to the plugin api then the plugin api can distribute them to the connected plugins using callback interface
        api_main.sendInitialData(user_expression, min, max, inc);


        for (double x = min; x <= max; x+=inc){
            completedOperationsCounter++;
            String eval = user_expression.replace("x", String.valueOf(x));
            //call python evaluator by passing user expression and x value according to the increment
            double y = sh.evaluate(user_expression, x);
            //  System.out.println("\nEvaluate for "+ eval+", Result is "+ y + ", "+ completedOperationsCounter +" Executions has Completed Successfully");
        }

    }

    /**
     *
     * @param pluginID
     * @param pc
     * loadPlugins method use to implement the plugin loaded mechanism at the runtime dynamically
     * this method will dynamically load the plugin which user has selected to load using reflection
     */
    public static void loadPlugins(String pluginID, PluginController pc){
        String pluginClass = null;

        if(pluginID.equals("1")){
            pluginClass = "com.savindu.progressbar.ProgressBarPlugin";
        }else if(pluginID.equals("2")){
            pluginClass = "com.savindu.csv_writer.CSVWriterPlugin";
        }else if(pluginID.equals("3")){
            pluginClass = "com.savindu.main.ProgressBarCpp";
        }else if(pluginID.equals("4")){
            pluginClass = "com.savindu.advanced_evaluator.AdvancedEvaluatorPlugin";
        }

        try {
            Class<?> plugin_Class = Class.forName(pluginClass);
            Constructor plugin_Constructor = plugin_Class.getConstructor(PluginController.class);
            Object plugin_instance = plugin_Constructor.newInstance(pc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
