package com.savindu.main.script;

import org.python.core.PyFloat;
import org.python.util.PythonInterpreter;

/**
 * python script executor class which evaluates the expressions
 */

public class ScriptHandler {

    public double evaluate(String expression, double val){
        PythonInterpreter py = new PythonInterpreter();
        py.set("x", val);
        double result = 0;

        try {
            result = ((PyFloat) py.eval("float(" + expression + ")")).getValue();
            py.set("res", result);
            /**
             * load java function using python import statement inorder to send the callbacks to the plugins api
             */
            py.exec("from com.savindu.main.MainApp import updatePluginData");
            py.exec("updatePluginData(x, res)");
        }catch (Exception e){
            System.out.println("Exception " + e);
        }

        return result;
    }
}
