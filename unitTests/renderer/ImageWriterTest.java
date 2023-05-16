package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * test of Image Writer class
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
class ImageWriterTest {

    /**Test method for {@link ImageWriter#writeToImage()}*/
    @Test
    void testWriteToImage() {
        // Create a basic image.
        ImageWriter image = new ImageWriter("firstImage",800,500);
        // Write the image to the file.
        for (int i = 0; i < image.getNx(); i++)
        {
            for (int j = 0; j < image.getNy();j++)
            {
                if(i % 50 == 0 || j % 50 == 0)
                    image.writePixel(i, j, new Color(189,56,126));
                else
                    image.writePixel(i, j, new Color(200,150,178));
            }
        }
        image.writeToImage();
    }
}
