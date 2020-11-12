package com.savindu.progressbar;

/**
 * Ideation of the progress bar
 *      *https://nakkaya.com/2009/11/08/command-line-progress-bar/
 */
public class ProgressBar {
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String ANSI_RESET = "\u001B[0m";

    public ProgressBar() { }

    public static void printProgressBar(float percentage, int completed, int total) throws InterruptedException {
        StringBuilder progress_bar = new StringBuilder("[");

        for(int i = 0; i < 100; i++){
            if( i < (percentage)){
                progress_bar.append(ANSI_WHITE_BACKGROUND+" "+ANSI_RESET);

                /*used to get the actual fell of a progress bar
                Thread.currentThread().sleep(1);
                */

            }else if( i == (percentage)){
                progress_bar.append(">");
            }else{
                progress_bar.append("_");
            }
        }

        progress_bar.append("]   " +  percentage + "%     [" + completed + "/" + total + "]");
        System.out.print("\r" + progress_bar.toString());
    }
}
