package Unit_Tests;

import com.MB.PPM;
import com.MB.Utils;
import com.MB.Vec3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PPMClass {

    @Test
    @DisplayName("Should translate Vector to RGB")
    public void vectorToRGB() {
        Vec3 vec3 = new Vec3(0, 0, 1);

        assertEquals(PPM.vectorToRGB(vec3, 1), "0 0 255");

        vec3 = new Vec3(1, 0, 0);

        assertEquals(PPM.vectorToRGB(vec3, 1), "255 0 0");
    }

    @Test
    @DisplayName("Should Clamp Numbers to Range")
    public void vectorToRBGMethodTest() {
        Vec3 testVector = new Vec3(23, 25, 23);

        float red = testVector.x;
        float green = testVector.y;
        float blue = testVector.z;

        int samplesPerPixel = 100;


        float scale = 1.0F / samplesPerPixel;

        red = (float) (scale * red);
        green = (float) (scale * green);
        blue = (float) (scale * blue);

        int newRed = (int) (256 * Utils.clamp(red,0.0F,0.999F));
        int newGreen = (int) (256 * Utils.clamp(green,0.0F,0.999F));
        int newBlue = (int) (256 * Utils.clamp(blue,0.0F,0.999F));

        String actual = PPM.vectorToRGB(testVector, samplesPerPixel);

        assertEquals(String.format("%d %d %d",newRed,newGreen,newBlue),actual);
    }

    @Test
    @DisplayName("Clamp Method Clamps Number to Range")
    public void ClampMethodTest(){
        float f = 34F;
        assertEquals(0.999F,Utils.clamp(f,0.0F,0.999F));
        f = -0.34F;
        assertEquals(0.0F,Utils.clamp(f,0.0F,0.999F));
        f = 0.34F;
        assertEquals(0.34F,Utils.clamp(f,0.0F,0.999F));
    }


}
