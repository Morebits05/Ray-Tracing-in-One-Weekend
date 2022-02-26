package Unit_Tests;

import com.MB.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HitTableListSpec {


    @Test
    @DisplayName("When add is called Should add Object to List")
    public void addTest(){
        HitTableList list = new HitTableList();
        list.add(new Sphere(new Vec3(0,0,0),0.5f));

        assertTrue((long) list.objects.size() > 0);
    }

    @Test
    @DisplayName("When Hit is Called, should calculate hit on object")
    public void HitTest(){
        HitTableList list = new HitTableList();
        Sphere s1 = new Sphere(new Vec3(0,0,-1),.5F);

        list.add(s1);

        Vec3 rO = new Vec3(0,0,0); // ray Origin
        Vec3 rD = new Vec3(0,0,-1); // ray Direction

        Ray r = new Ray(rO,rD);
        assertTrue(list.hit(r,0,1,new HitRecord()));
    }
}
