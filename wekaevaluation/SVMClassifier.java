package wekaevaluation;

import weka.core.Instances;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import weka.classifiers.functions.LibSVM;

/**
 *
 * @author Adolfo
 */
public class SVMClassifier {

    private final String UNBALANCED_TRAIN_FILE = "C:/files/ent_desbal.arff";
    private final String UNBALANCED_EVAL_FILE = "C:/files/test_desbal.arff";
    private final String BALANCED_TRAIN_FILE = "C:/files/ent_bal.arff";
    private final String BALANCED_EVAL_FILE = "C:/files/test_bal.arff";
    private final String OUTPUT_FILE = "C:/files/svm.csv";
    BufferedReader reader = null;
    BufferedWriter output = null;

    public void run() {
        try {
            output = new BufferedWriter(new FileWriter(OUTPUT_FILE));
            output.write("DISTRIBUTION,TESTED_ON,KERNEL,COEF_0,COST,DEGREE,GAMMA,NUM_INSTANCES,CORRECTION_PCT");
            output.newLine();

            /***** UNBALANCED *****/
            reader = new BufferedReader(new FileReader(UNBALANCED_TRAIN_FILE));
            Instances data = new Instances(reader);
            reader.close();
            data.setClassIndex(data.numAttributes() - 1);

            // <editor-fold defaultstate="collapsed" desc="Linear - Training set">
            LibSVM svm = this.setNewClassifier(LibSVM.KERNELTYPE_LINEAR);
            svm.buildClassifier(data);
            this.classifyAndSummarize(svm, data, "UNBALANCED,TRAINING_SET,LIN,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Linear - Test set">
            reader = new BufferedReader(new FileReader(UNBALANCED_EVAL_FILE));
            Instances test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);
            this.classifyAndSummarize(svm, test, "UNBALANCED,EVAL_SET,LIN,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Polynomial - Training set">
            svm = this.setNewClassifier(LibSVM.KERNELTYPE_POLYNOMIAL);
            svm.buildClassifier(data);
            this.classifyAndSummarize(svm, data, "UNBALANCED,TRAINING_SET,POL,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Polynomial - Test set">
            reader = new BufferedReader(new FileReader(UNBALANCED_EVAL_FILE));
            test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);
            this.classifyAndSummarize(svm, test, "UNBALANCED,EVAL_SET,POL,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Radial Basis - Training set">
            svm = this.setNewClassifier(LibSVM.KERNELTYPE_RBF);
            svm.buildClassifier(data);
            this.classifyAndSummarize(svm, data, "UNBALANCED,TRAINING_SET,RAD,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Radial Basis - Test set">
            reader = new BufferedReader(new FileReader(UNBALANCED_EVAL_FILE));
            test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);
            this.classifyAndSummarize(svm, test, "UNBALANCED,EVAL_SET,RAD,");
            // </editor-fold>

            /***** BALANCED *****/
            reader = new BufferedReader(new FileReader(BALANCED_TRAIN_FILE));
            data = new Instances(reader);
            reader.close();
            data.setClassIndex(data.numAttributes() - 1);

            // <editor-fold defaultstate="collapsed" desc="Linear - Training set">
            svm = this.setNewClassifier(LibSVM.KERNELTYPE_LINEAR);
            svm.buildClassifier(data);
            this.classifyAndSummarize(svm, data, "BALANCED,TRAINING_SET,LIN,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Linear - Test set">
            reader = new BufferedReader(new FileReader(BALANCED_EVAL_FILE));
            test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);
            this.classifyAndSummarize(svm, test, "BALANCED,EVAL_SET,LIN,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Polynomial - Training set">
            svm = this.setNewClassifier(LibSVM.KERNELTYPE_POLYNOMIAL);
            svm.buildClassifier(data);
            this.classifyAndSummarize(svm, data, "BALANCED,TRAINING_SET,POL,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Polynomial - Test set">
            reader = new BufferedReader(new FileReader(BALANCED_EVAL_FILE));
            test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);
            this.classifyAndSummarize(svm, test, "BALANCED,EVAL_SET,POL,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Radial Basis - Training set">
            svm = this.setNewClassifier(LibSVM.KERNELTYPE_RBF);
            svm.buildClassifier(data);
            this.classifyAndSummarize(svm, data, "BALANCED,TRAINING_SET,RAD,");
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Radial Basis - Test set">
            reader = new BufferedReader(new FileReader(BALANCED_EVAL_FILE));
            test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);
            this.classifyAndSummarize(svm, test, "BALANCED,EVAL_SET,RAD,");
            // </editor-fold>

            // Finish
            output.flush();
            output.close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

    }

    /*
     * @param svm - The LibSVM object reference
     * @Instances - The Instances collection reference
     * @header - The DISTRIBUION,TESTED_ON,KERNEL_TYPE, values for the output
     */
    private void classifyAndSummarize(LibSVM svm, Instances instances, String header) {
        int corrects = 0;
        double predicted, actual, percent;

        try {
            // Classify
            for (int i = 0; i < instances.numInstances(); i++) {
                predicted = svm.classifyInstance(instances.instance(i));
                actual = instances.instance(i).classValue();
                if (predicted == actual) {
                    corrects++;
                }
            }

            // Summary
            percent = corrects / Double.valueOf(instances.numInstances());
            percent = percent * Double.valueOf("100.0");
            output.write(header + svm.getCoef0() + ","
                    + svm.getCost() + "," + svm.getDegree() + ","
                    + svm.getGamma() + "," + instances.numInstances() + "," + percent);
            output.newLine();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    private LibSVM setNewClassifier(int kernelType) {
        LibSVM svm = new LibSVM();
        weka.core.SelectedTag tagKernel = new weka.core.SelectedTag(kernelType, LibSVM.TAGS_KERNELTYPE);
        svm.setKernelType(tagKernel);
        svm.setCoef0(0.0);
        svm.setCost(1.0);
        svm.setDegree(3);
        svm.setEps(0.0010);
        svm.setGamma(0.0);
        svm.setLoss(0.1);
        svm.setNu(0.5);
        svm.setNormalize(false);
        svm.setProbabilityEstimates(false);
        svm.setDoNotReplaceMissingValues(false);
        svm.setSeed(1);
        svm.setShrinking(true);
        return svm;
    }
}