package algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import sentimentanalysis.Preprocess;

/**
 *
 * @author Adolfo
 */
public class ArffGenerator {

    private final String POSITIVE_TRAINING_FILE = "C:/files/training_pos.txt";
    private final String NEGATIVE_TRAINING_FILE = "C:/files/training_neg.txt";
    private final String ARFF_FILE = "C:/files/Telecom_Tweets.arff";

    public void generate() {
        BufferedReader entrada = null;
        BufferedWriter salida = null;
        try {
            File f = new File(POSITIVE_TRAINING_FILE);
            entrada = new BufferedReader(new FileReader(f));
            salida = new BufferedWriter(new FileWriter(ARFF_FILE));
            createHeader(salida);
            Preprocess pp = new Preprocess();
            if (f.exists()) {
                String tweet = entrada.readLine();
                do {
                    System.out.println(tweet);
                    tweet = pp.preprocessTweet(tweet);
                    tweet = tweet.replaceAll("\\s+", " ");
                    salida.newLine();
                    System.out.println(tweet);
                    salida.write("'" + tweet + " ',pos");
                    tweet = entrada.readLine();
                } while (tweet != null);
            }
            entrada.close();
            f = new File(NEGATIVE_TRAINING_FILE);
            entrada = new BufferedReader(new FileReader(f));
            if (f.exists()) {
                String tweet = entrada.readLine();
                do {
                    tweet = pp.preprocessTweet(tweet);
                    salida.newLine();
                    salida.write("'" + tweet + " ',neg");
                    tweet = entrada.readLine();
                } while (tweet != null);
            }
            entrada.close();
            salida.flush();
            salida.close();
        } catch (Exception ex) {
            System.out.println("ERROR. Excepcion: " + ex);
        }
    }

    private void createHeader(BufferedWriter salida) {
        try {
            salida.write("% Tuits de entrenamiento para los clasificadores");
            salida.newLine();
            salida.write("% Mismo conjunto de muestra utilizado para el diccionario");
            salida.newLine();
            salida.newLine();
            salida.write("@relation Telecom_Tweets");
            salida.newLine();
            salida.newLine();
            salida.write("@attribute text string");
            salida.newLine();
            salida.write("@attribute @@class@@ {pos,neg}");
            salida.newLine();
            salida.newLine();
            salida.write("@data");
            salida.newLine();
        } catch (Exception ex) {
            System.out.println("Error creando cabecera: " + ex.getMessage());
        }
    }

}
