package Unit_Tests;

import com.MB.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.MB.Vec3.dot;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class DialecticTest {

    @Test
    @DisplayName("Refract Should Calculate Properly")
    public void refractTest() {
        float etaRatio = 1.0f / 1.5f; // air into glass

        // 45-degree incident ray, normalized
        float inv = (float)(1.0 / Math.sqrt(2.0));
        Vec3 uv = new Vec3(inv, 0, -inv);

        // Surface normal pointing TOWARD the incoming ray
        Vec3 n = new Vec3(0, 0, 1);

        Vec3 negUv = uv.neg();
        float cosTheta = Math.min(dot(negUv, n), 1.0f);

        Vec3 rayOutPerpendicular = Vec3.scale(etaRatio, uv.add(n.scale(cosTheta)));
        Vec3 rayOutParallel = n.scale((float) -Math.sqrt(Math.abs(1.0f - rayOutPerpendicular.lengthSquared())));

        Vec3 refracted = rayOutPerpendicular.add(rayOutParallel);

        float epsilon = 1e-4f;
        Assertions.assertEquals(0.4714f, refracted.x, epsilon);
        Assertions.assertEquals(0.0f, refracted.y, epsilon);
        Assertions.assertEquals(-0.8819f, refracted.z, epsilon);
    }

    private static float getCosTheta(float a) {
        float cosTheta;
        if (Float.isNaN(a)) {
            cosTheta = a;// a is NaN
        } else {
            cosTheta = Math.min(a, 1.0F);
        }
        return cosTheta;
    }

    @Test
    public void ScatteredTest(){
        float indexRefraction = 1.5f;
        Dielectric dialectric = new Dielectric(indexRefraction);
        Sphere s= new Sphere(new Vec3(0,0,-1f),.5f,dialectric);
        HitTableList world = new HitTableList(s);


        Ray r = new Ray(new Vec3(0f,0f,0f),new Vec3(0f,0f,-1f));
        HitRecord rec = new HitRecord();
        Vec3 attenuation = new Vec3();
        Ray scatteredRay = new Ray();

        Vec3 rayColour  = rayColor(r,world,1);
        assertThat(rayColour.x,is(0.0F));
        assertThat(rayColour.y,is(0.0F));
        assertThat(rayColour.z,is(0.0F));
    }


    /***
     * Refract
     * @param uv incoming ray
     * @param n normal
     * @param ir angle of refraction
     * @return The Vector of the refracted ray
     */
    private Vec3 refract(Vec3 uv, Vec3 n, float ir){
        float cosTheta = Math.min(dot((uv.neg()),n),1.0F);

        assertThat(cosTheta, is(1.0F));
        Vec3 rayOutPerpendicular = Vec3.scale(ir,(uv.add(n.scale(cosTheta))));

        assertThat(rayOutPerpendicular, equalTo (new Vec3(0,0,0)));
        Vec3 rayOutParallel = n.scale((float) -(Math.sqrt(Math.abs(1.0F-rayOutPerpendicular.lengthSquared()))));
        return rayOutPerpendicular.add(rayOutParallel);
    }

    private boolean scatter(Ray rayIn, HitRecord hitRecord, Vec3 attenuation, Ray scattered) {

        attenuation.set(new Vec3(1.0F,1.0F,1.0F));
        float ir = 1.5F;
        float refractionRatio = hitRecord.frontFace ?  (1.0F / ir) : ir;

        Vec3 unitDirection = Vec3.normalize(rayIn.direction());

        assertThat(unitDirection,equalTo(new Vec3(0,0,-1.0f)));
        Vec3 refracted = refract(unitDirection,hitRecord.normal,refractionRatio);


        //scattered.set(new Ray(hitRecord.point,refracted));


        return true;
    }

    /**
     * RayColor.
     * Calculates the colour of the pixel.
     *
     * @param ray - Ray to use
     * @return The Background Colour if no hit,
     * else returns the Color of the object.
     */
    public Vec3 rayColor(final Ray ray,final HitTableList world,int depth) {
        HitRecord hitRecord = new HitRecord();
        // If the depth is 0, there's no light
        if (depth <= 0)
            return new Vec3(0, 0, 0);

        if (world.hit(ray,0.001F,Float.POSITIVE_INFINITY,hitRecord)){
            Ray scattered = new Ray();
            Vec3 attenuation = new Vec3();
            if (scatter(ray,hitRecord,attenuation,scattered))
                return attenuation.scale(rayColor(scattered,world,depth-1));
            return new Vec3(0,0,0);
        }

        Vec3 unitDirection = Vec3.normalize(ray.direction);
        float t = 0.5f * (unitDirection.y + 1.0F);
        final Vec3 a = new Vec3(1.0F, 1.0F, 1.0F);
        final Vec3 b = new Vec3(0.5F, 0.7F, 1.0F);
        return Vec3.lerp(a, b, t);
    }
}
