import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class main {
    public static void main(String[] args) throws IOException {
        generateRandomizedPixelsImage("./src/files/boss-1.png", "boss", 31);
    }

    public static void generateRandomizedPixelsImage(String filePath, String fileName, int imagesCount) throws IOException {
        for (int count = 0; count < imagesCount; count++) {
            BufferedImage img = ImageIO.read(new File(filePath));
            ArrayList<Integer> reds = new ArrayList<>();
            ArrayList<Integer> greens = new ArrayList<>();
            ArrayList<Integer> blues = new ArrayList<>();
            for (int i = 0; i < img.getHeight(); i++) {
                for (int k = 0; k < img.getWidth(); k++) {
                    Color color = new Color(img.getRGB(i, k));
                    int color_red = color.getRed();
                    int color_blue = color.getBlue();
                    int color_green = color.getGreen();
                    if (color_red > 0 || color_blue > 0 || color_green > 0) {
                        if (!reds.contains(color_red)) {
                            reds.add(color_red);
                        }
                        if (!greens.contains(color_green)) {
                            greens.add(color_green);
                        }
                        if (!blues.contains(color_blue)) {
                            blues.add(color_blue);
                        }
                    }
                }
            }
            ArrayList<Integer> newReds = createNewRandomPixelsArray(reds);
            ArrayList<Integer> newGreens = createNewRandomPixelsArray(greens);
            ArrayList<Integer> newBlues = createNewRandomPixelsArray(blues);
            for (int i = 0; i < img.getHeight(); i++) {
                for (int k = 0; k < img.getWidth(); k++) {
                    Color color = new Color(img.getRGB(i, k));
                    int color_red = color.getRed();
                    int color_blue = color.getBlue();
                    int color_green = color.getGreen();
                    if (color_red > 0 || color_blue > 0 || color_green > 0) {
                        img.setRGB(i, k, getIntFromColor(newReds.get(reds.indexOf(color_red)), newGreens.get(greens.indexOf(color_green)), newBlues.get(blues.indexOf(color_blue))));
                    }
                }
            }
            File outputfile = new File(fileName + "-" + (count + 2) + ".png");
            ImageIO.write(img, "png", outputfile);
        }
    }

    public static ArrayList<Integer> createNewRandomPixelsArray(ArrayList<Integer> color) {
        ArrayList<Integer> newColor = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < color.size(); i++) {
            int newColorInt = r.nextInt(255);
            while (newColor.contains(newColorInt)) {
                newColorInt = r.nextInt(255);
            }
            newColor.add(newColorInt);
        }
        return newColor;
    }

    public static int getIntFromColor(int Red, int Green, int Blue) {
        Red = (Red << 16) & 0x00FF0000;
        Green = (Green << 8) & 0x0000FF00;
        Blue = Blue & 0x000000FF;

        return 0xFF000000 | Red | Green | Blue;
    }
}
