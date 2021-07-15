package Tests;


import com.MB.Vec3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3 {

    @Test
    @DisplayName("should Initialize as 0")
    public void shouldInitializeAs0(){
        Vec3 v1 = new Vec3(0, 0, 0);


        assertEquals(0,v1.X);
        assertEquals(0,v1.Y);
        assertEquals(0,v1.Z);
    }

    @Test
    @DisplayName("Should Be Set To 0 in Constructor")
    public void shouldBeSetTo0(){
        Vec3 v1 = new Vec3(0,0,0);

        assertEquals(0,v1.X);
        assertEquals(0,v1.Y);
        assertEquals(0,v1.Z);
    }

    @Test
    @DisplayName("Should be <0,0,0>")
    public void shouldBeZeroZeroZero(){
        Vec3 v1 = new Vec3(0,0,0);


        assertEquals("<0,0,0>",v1.toString());
    }

    @Test
    @DisplayName("Vector 1 Should Equal Vector 2")
    public void VectorOneShouldBeEqualVectorTwo(){
        Vec3 v1 = new Vec3(0,0,0);
        Vec3 v2 = new Vec3(0,0,0);

        assertEquals(v2, v1);
    }

    

}
