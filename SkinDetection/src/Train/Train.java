package Train;

import java.io.*;

public class Train {
    public TrainingDataset dataset;
    public ProbabilitySet probabilitySet;

    public Train () throws IOException, ClassNotFoundException {
        File datasetInput = new File("savedData.txt");

        if (datasetInput.getTotalSpace() > 0) {
            System.out.println("Previous dataset exists");
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(datasetInput.getName()));
            this.dataset = (TrainingDataset) obj.readObject();

        } else {
            System.out.println("Previous dataset does not exists");
            this.dataset = new TrainingDataset();
            storeOnFile();
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
    }

    private void storeOnFile () throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("savedData.txt");
        ObjectOutputStream obj = new ObjectOutputStream(fileOutput);
        obj.writeObject(this.dataset);
    }
}
