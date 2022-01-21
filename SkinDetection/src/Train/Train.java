package Train;

import java.io.*;
import java.util.Scanner;

public class Train {
    public TrainingDataset dataset;
    public ProbabilitySet probabilitySet;

    public Train () throws IOException, ClassNotFoundException {
        File fileInput = new File("savedProbability.txt");
//        Scanner scan = new Scanner(fileInput);

        if (fileInput.getTotalSpace() > 0) {
            System.out.println("Preveious dataset exixts");
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(fileInput.getName()));
            this.probabilitySet = (ProbabilitySet) obj.readObject();

        } else {
            System.out.println("Preveious dataset does not exixts");
            this.dataset = new TrainingDataset();
            this.probabilitySet = new ProbabilitySet(dataset.totalSkinFound, dataset.totalNonSkinFound);
            calculateProbability();
        }
    }

    public Train (TrainingDataset set) throws IOException {
        this.dataset = set;
        this.probabilitySet = new ProbabilitySet(dataset.totalSkinFound, dataset.totalNonSkinFound);
        calculateProbability();
    }

    public void calculateProbability () throws IOException {

        for (int i=0; i<256; i++) {
            for (int j=0; j<256; j++) {
                for (int k=0; k<256; k++) {
                    probabilitySet.probabilitySkin[i][j][k] = dataset.colorCountSkin[i][j][k]/dataset.totalSkinFound;
                    probabilitySet.probabilityNonSkin[i][j][k] = dataset.colorCountNonSkin[i][j][k]/dataset.totalNonSkinFound;
                }
            }
        }

        storeOnFile();
    }

    private void storeOnFile () throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("savedProbability.txt");
        ObjectOutputStream obj = new ObjectOutputStream(fileOutput);
        obj.writeObject(this.probabilitySet);
    }
}
