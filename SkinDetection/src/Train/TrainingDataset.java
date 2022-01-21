package Train;

import java.awt.*;
import java.io.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.*;

public class TrainingDataset {
    public String regularImageFilePath;
    public String maskImageFilePath;

    public double[][][] colorCountNonSkin = new double[256][256][256];
    public double[][][] colorCountSkin = new double[256][256][256];

    public int totalSkinFound = 0, totalNonSkinFound = 0;

    public TrainingDataset () throws IOException {
        this.regularImageFilePath = "ibtd/Real";
        this.maskImageFilePath = "ibtd/Mask";
        startTraining();
    }

    public TrainingDataset (String realImageFilePath, String maskImageFilePath) throws IOException {
        this.regularImageFilePath = realImageFilePath;
        this.maskImageFilePath = maskImageFilePath;
        startTraining();
    }

    private void startTraining () throws IOException {
        File[] realImages = new File(regularImageFilePath).listFiles();
        File[] maskImages = new File(maskImageFilePath).listFiles();

        Arrays.sort(Objects.requireNonNull(realImages), 0, realImages.length, Comparator.comparing(File::getName));
        Arrays.sort(Objects.requireNonNull(maskImages), 0, maskImages.length, Comparator.comparing(File::getName));

        for (int imageIndex=0; imageIndex<realImages.length; imageIndex++) {
            BufferedImage image = ImageIO.read(realImages[imageIndex]);
            BufferedImage mask = ImageIO.read(maskImages[imageIndex]);

            for (int i=0; i<image.getHeight(); i++) {
                for (int j=0; j<image.getWidth(); j++) {
                    Color pixelReal = new Color(image.getRGB(j, i));
                    Color pixelMask = new Color(mask.getRGB(j, i));

                    if (pixelMask.getRed() < 230 && pixelMask.getGreen() < 230 && pixelMask.getBlue() < 230) {
                        colorCountSkin[pixelReal.getRed()][pixelReal.getGreen()][pixelReal.getBlue()]++;
                        totalSkinFound++;

                    } else {
                        colorCountNonSkin[pixelReal.getRed()][pixelReal.getGreen()][pixelReal.getBlue()]++;
                        totalNonSkinFound++;
                    }
                }
            }
        }
    }
}
