/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sentimentanalysis;

import algorithms.*;

/**
 *
 * @author usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        SimpleCount sc = new SimpleCount();
//        sc.run();
//        ArffGenerator ag = new ArffGenerator ();
//        ag.generate();
        NaiveBayes nb = new NaiveBayes();
        nb.run();
    }

}
