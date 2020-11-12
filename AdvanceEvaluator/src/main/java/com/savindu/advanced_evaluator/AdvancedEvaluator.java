package com.savindu.advanced_evaluator;

public class AdvancedEvaluator {

    public long factorialUsingForLoop(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
    public void fibonacci(int value){

        int n1=0;
        int n2=1;
        int n3;
        int i;

        System.out.print(n1+" "+n2);

        for(i=2;i<value;++i)
        {
            n3=n1+n2;
            System.out.print(" "+n3);
            n1=n2;
            n2=n3;
        }
    }

}
