package MainPackage;

import InputAndOutput.ImageInput;
import Train.Train;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("hello");
        run();
    }

    public static void run () throws IOException, ClassNotFoundException {
        System.out.println("Training Started");
        Train train = new Train();
        System.out.println("Training finished");

        ImageInput imageInput = new ImageInput("sample2.jpg");
        imageInput.detectSkin(train.dataset);
        System.out.println("Output generated");
    }
}
