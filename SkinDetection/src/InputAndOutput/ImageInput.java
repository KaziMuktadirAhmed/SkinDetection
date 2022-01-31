package InputAndOutput;

import Train.TrainingDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageInput {
    BufferedImage image;
    BufferedImage output;

    public ImageInput () throws IOException {
        image = ImageIO.read(new File("sample.jpg"));
        output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    }

    public ImageInput(String imagePath) throws IOException {
        image = ImageIO.read(new File(imagePath));
        output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//        detectSkin();
    }

    public void detectSkin (TrainingDataset dSet) throws IOException {
        int height = image.getHeight();
        int width = image.getWidth();

        int[] inputImagePixels = image.getRGB(0,0,width,height,null,0,width);
        int[] outputImagePixels = new int[inputImagePixels.length];

        for (int i=0; i<inputImagePixels.length; i++) {
            int alpha = (inputImagePixels[i] & 0xFF000000) >> 24;
            int red = (inputImagePixels[i] & 0x00FF0000) >> 16;
            int green = (inputImagePixels[i] & 0x0000FF00) >> 8;
            int blue = (inputImagePixels[i] & 0x000000FF);

            if ( (dSet.colorCountSkin[red][green][blue]/dSet.colorCountNonSkin[red][green][blue]) > .35 ) {
                red = 250;
                green = 250;
                blue = 250;

            } else {
                red = 10;
                green = 10;
                blue = 10;
            }

            outputImagePixels[i] = ((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }

        System.out.println("h-> " +height+" w-> "+width+ " ol-> "+outputImagePixels.length+" il-> "+inputImagePixels.length);

        output.setRGB(0, 0, width, height, outputImagePixels, 0, width);
        File outputFile = new File("outputImage.png");
        ImageIO.write(output, "png", outputFile);
    }
}
