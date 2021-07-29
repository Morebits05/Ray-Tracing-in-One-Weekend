package Tests;






import com.MB.Vec3;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
public final class Vector3 {
    Random gen = new Random();
    static  int LOW = -100;
    static  int HIGH = 100;
    @Test
    @DisplayName("should Initialize as 0")
    public void shouldInitializeAs0() {
        Vec3 v1 = new Vec3();


        assertEquals(0, v1.x);
        assertEquals(0, v1.y);
        assertEquals(0, v1.z);
    }

    @Test
    @DisplayName("Should Be Set To 0 in Constructor")
    public void shouldBeSetTo0() {
        Vec3 v1 = new Vec3(0, 0, 0);

        assertEquals(0, v1.x);
        assertEquals(0, v1.y);
        assertEquals(0, v1.z);
    }

    @Test
    @DisplayName("Should be <0,0,0>")
    public void shouldBeZeroZeroZero() {
        Vec3 v1 = new Vec3(0, 0, 0);


        assertEquals("<0.0,0.0,0.0>", v1.toString());
    }

    @Test
    @DisplayName("Vector 1 Should Equal Vector 2")
    public void basicEqualTest() {
        Vec3 v1 = new Vec3(0, 0, 0);
        Vec3 v2 = new Vec3(0, 0, 0);


        assertEquals(v2, v1);
    }

    public float getFloatInRange(float l, float h) {
        return gen.nextFloat() * (h - l) + l;
    }


    @Test
    @DisplayName("Should Test for Equality")
    public void shouldTestForEquality() {
        float high = 100;
        float low = -100;
        float delta = .25f;
        for (int i = 0; i < 100; i++) {
            float x = getFloatInRange(high, low);
            float y = getFloatInRange(high, low);
            float z = getFloatInRange(high, low);
            Vec3 v1 = new Vec3(x, y, z);
            Vec3 v2 = new Vec3(x, y, z);
            assertEquals(v1, v2);

            v2.x += delta;
            v2.y += delta;
            v2.z += delta;
            assertNotEquals(v1, v2);
        }
    }


    @Test
    @DisplayName("Should Add Equals With Random Numbers and Return Correct Values")
    public void shouldAddEquals() {
        float x;
        float y;
        float z;
        float x2;
        float y2;
        float z2;
        for (int i = 0; i < 20; i++) {
            x = getFloatInRange(-100, 100);
            y = getFloatInRange(-100, 100);
            z = getFloatInRange(-100, 100);
            Vec3 testVec = new Vec3(x, y, z);
            x2 = getFloatInRange(-100,100);
            y2 = getFloatInRange(-100,100);
            z2 = getFloatInRange(-100,100);


            Vec3 actualVec = testVec.addEquals(new Vec3(x2, y2, z2));
            assertEquals(x + x2, actualVec.x);
            assertEquals(y + y2, actualVec.y);
            assertEquals(z + z2, actualVec.z);
        }
    }

    @Test
    @DisplayName("should set Vector x after assignment")
    public void addEqualsAssignmentTest(){

        //Basic Vectors
        Vec3 testVec = new Vec3(-4,26,12);
        Vec3 vectorToAdd = new Vec3(-6,24,13);

        // Call Method to Add Another Vector
        testVec.addEquals(vectorToAdd);

        // Check original value has changed
        assertEquals(-10, testVec.x);

    }


    @Test
    @DisplayName("Should Add Two Vectors And Return Vector With Negativex")
    public void shouldAddTwoVectorsAndReturnVectorWithNegativex() {
        Vec3 v1 = new Vec3(1, 2, 3);

        Vec3 expected = new Vec3(-1, 2, 3);
        assertEquals(expected, v1.add(new Vec3(-2, 0, 0)));
    }


    @Test
    @DisplayName("Should Add Two Vectors And Return Vector With Positive x")
    public void shouldAddTwoVectorsAndReturnVectorWithPositivex() {
        Vec3 v1 = new Vec3(1, 2, 3);

        Vec3 expected = new Vec3(2, 2, 3);
        assertEquals(expected, v1.add(new Vec3(1, 0, 0)));
    }

    @Test
    @DisplayName("Should Add One Vector that's negative And Return With Negative y")
    public void shouldAddOneVectorsThatsNegativeAndReturnVectorWithNegativey() {
        Vec3 testVector = new Vec3(0, 1, 0);

        Vec3 expected = new Vec3(0, -1, 0);
        assertEquals(expected, testVector.add(new Vec3(0, -2, 0)));
    }

    @Test
    @DisplayName("Add Two Negative Vectors and Return a Negative")
    public void addTwoNegativeVectorsAndReturnNegative() {
        Vec3 testVector = new Vec3(-2, 0, 0);

        Vec3 expectedVector = new Vec3(-4, 0, 0);

        assertEquals(expectedVector, testVector.add(
                new Vec3(-2, 0, 0)));
    }


    @Test
    @DisplayName("Should Add Two Positive Vectors And Return Vector With Positive y")
    public void shouldAddTwoPositiveVectorsAndReturnVectorWithPositivey() {
        Vec3 testVector = new Vec3(1, 2, 3);

        Vec3 expectedVector = new Vec3(2, 2, 3);
        assertEquals(expectedVector, testVector.add(
                new Vec3(1, 0, 0)));
    }


    @Test
    @DisplayName("Should Call minus equals, with random numbers")
    public void MinusEqualsTest(){
        float x; float y;float z;
        float x2;float y2; float z2;

        for (int i = 0; i < 200; i++) {
                x = getFloatInRange(LOW,HIGH);
                y = getFloatInRange(LOW,HIGH);
                z = getFloatInRange(LOW,HIGH);

                x2 = getFloatInRange(LOW,HIGH);
                y2 = getFloatInRange(LOW,HIGH);
                z2= getFloatInRange(LOW,HIGH);

                Vec3 v1 = new Vec3(x,y,z);

                Vec3 v2 = new Vec3(x2,y2,z2);

                assertEquals(v1.x - x2,v1.subtractEquals(v2).x);
                assertEquals(v1.y - y2,v1.subtractEquals(v2).y);
                assertEquals(v1.z - z2,v1.subtractEquals(v2).z);
        }
    }


    @Test
    @DisplayName("subtract method subtracts 1 from x and returns 0")
    public void subtractMethodSubtracts1FromXandReturns0() {
        Vec3 testVec = new Vec3(2, 2, 2);

        Vec3 actualVec = testVec.subtract(new Vec3(2, 0, 0));

        assertEquals(new Vec3(0, 2, 2), actualVec);

    }

    @Test
    @DisplayName("subtract method subtracts 1 from y and returns 0")
    public void subtractMethodSubtracts1FromYandReturns0() {
        Vec3 testVec = new Vec3(2, 2, 2);

        Vec3 actualVec = testVec.subtract(new Vec3(2, 2, 0));

        assertEquals(new Vec3(0, 0, 2), actualVec);

    }

    @Test
    @DisplayName("Add adds A Postive and Negative and Returns a Negative")
    public void addAddsAPostiveAndNegativeandReturnsNegative() {
        Vec3 testVec = new Vec3(-9, -90, 20);

        Vec3 actual = testVec.add(new Vec3(-13, 41, -36));

        Vec3 expected = new Vec3(-22, -49, -16);
        assertEquals(expected,actual);
    }


    /* This Test subtracts <-3,-3,-3> from <-4,-4,-4> to get <-1,-1,-1> */
    @Test
    @DisplayName("Subtract Method Subtracts A Negative And Returns A Negative")
    public void subtractMethodSubtractsANegativeandReturnsANegative() {
        Vec3 testVec = new Vec3(-4, -4, -4);

        Vec3 expectedVec = new Vec3(-1, -1, -1);

        Vec3 actualVec = testVec.subtract(new Vec3(-3, -3, -3));


        assertEquals(expectedVec, actualVec);
    }

    /* This Test subtracts <4,4,4> from <-4,-4,-4> to get <-8,-8,-8> */
    @Test
    @DisplayName("subtract Method Subtracts A Positive and Returns a Negative")
    public void subtractMethodSubtractsAPositiveAndReturnsANegative() {
        Vec3 testVec = new Vec3(-4, -4, -4);

        Vec3 expectedVec = new Vec3(-8, -8, -8);

        Vec3 actualVec = testVec.subtract(new Vec3(4, 4, 4));


        assertEquals(expectedVec, actualVec);

    }

    /* This Test subtracts <2,9,11> from <17,-3,1> to get <-19,12,12> */
    @Test
    @DisplayName("Subtract Method Subtracts Postive and Negative - Part One")
    public void subtractMethodSubtractsPositiveAndNegativeOne() {
        Vec3 testVec = new Vec3(-2, 9, 11);

        Vec3 expectedVec = new Vec3(-19, 12, 12);

        Vec3 actualVec = testVec.subtract(new Vec3(17, -3, -1));


        assertEquals(expectedVec, actualVec);

    }


    /* This Test subtracts <3,31,-2> from <36,-2,46> to get <-33,33,-48> */
    @Test
    @DisplayName("Subtract Method Subtracts Postive and Negative - Part Two")
    public void subtractMethodSubtractsPositiveAndNegativeTwo() {
        Vec3 testVec = new Vec3(3, 31, -2);


        Vec3 actualVec = testVec.subtract(new Vec3(36, -2, 46));
        Vec3 expectedVec = new Vec3(-33, 33, -48);

        assertEquals(expectedVec, actualVec);

    }

    @Test
    @DisplayName("Multiplies x by 2 and Doubles x")
    public void multipliesxby2andDoublesx() {
        Vec3 testVec = new Vec3(2, -5, 10);
        Vec3 expected = new Vec3(4, -5, 10);
        assertEquals(expected, testVec.scale(new Vec3(2, 1, 1)));
    }


    // Test with Extreme Values
    @Test
    @DisplayName("Multiplies x by 2 and Doubles x")
    public void multipliesV1ByV2() {

        Vec3 testVec = new Vec3(2, 35, -45);
        Vec3 expected = new Vec3(-12, 1365, 1845);
        assertEquals(expected,
                testVec.scale(new Vec3(-6, 39, -41)));
    }

    @ParameterizedTest
    @DisplayName("Should Multiply a Vector by a Doubles")
    @ValueSource(doubles = {2.0, 4.0, 6.0, 35, 29, -24, Double.MAX_VALUE})
    public void shouldScaleByDoubles(Double doubleToScaleBy) {
        Vec3 testVec = new Vec3(-3, -2, -1);
        Vec3 testVec2 = new Vec3(5, 10, -4);

        int x, y, z;
        x = (int) (testVec.x * doubleToScaleBy);
        y = (int) (testVec.y * doubleToScaleBy);
        z = (int) (testVec.z * doubleToScaleBy);
        testVec.scale(doubleToScaleBy);


        assertEquals(x, testVec.scale(doubleToScaleBy).x);
        assertEquals(y, testVec.scale(doubleToScaleBy).y);
        assertEquals(z, testVec.scale(doubleToScaleBy).z);
    }

    @Test
    @Disabled("should_Add_Equal_and_demonstrate_Volatility is Disabled because its only used to Demonstrate Add Equals Volatility")
    @DisplayName("Should Add Equal and demonstrate Volatility")
    public void shouldAddEqualAndDemonstrateVolatility() {
        Vec3 testVec = new Vec3(1, -3, -5);

        Vec3 vectorToBeAdded = new Vec3(3, 3, 3);

        // Remove Comment to make test fail
        //testVec.addEquals(vectorToBeAdded);

        //NOTE:
        // AddEquals use += on original values and returns itself
        // hence been Volatile

        assertTrue(testVec.x == 1 && testVec.y == -3 && testVec.z == -5, "Vector Coordinates Should have stayed same");

    }

    // Basic Test
    @Test
    @DisplayName("Should ScaleEqual with V2 and Return 42.5646")
    public void shouldScaleEqualWithV2AndReturn42point5646() {


        Vec3 testVec = new Vec3(13.26f,10,10);
        Vec3 other = new Vec3(3.21f,2,2);

        Vec3 expected = new Vec3(42.5646f,20,20);
        Vec3 actual = testVec.scaleEquals(other);

        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Should Scale Equal with Random Numbers")
    public void shouldScaleEqualWithRandom(){
            // Variables
            float x1;
            float y1;
            float z1;

            float x2;
            float y2;
            float z2;

            for (int tests = 0; tests < 20;tests++) {

                x1 = getFloatInRange(-100, 100);
                y1 = getFloatInRange(-100, 100);
                z1 = getFloatInRange(-100, 100);

                Vec3 testVector = new Vec3(x1, y1, z1);

                x2 = getFloatInRange(-100, 100);
                y2 = getFloatInRange(-100, 100);
                z2 = getFloatInRange(-100, 100);

                Vec3 vectorToBeScaledBy = new Vec3(x2, y2, z2);
                testVector.scaleEquals(vectorToBeScaledBy);

                assertEquals(x1 * x2, testVector.x);
                assertEquals(y1 * y2, testVector.y);
                assertEquals(z1 * z2, testVector.z);
            }
        }



        @Test
        @DisplayName("Should DivideEqual with V2")
        public void shouldDivideEqual () {
            float h = 100f;
            float l = -100f;
            for (int i = 0; i < 20; i++){
                float x1 = getFloatInRange(l, h);
                float y1 = getFloatInRange(l, h);
                float z1 = getFloatInRange(l, h);
                Vec3 testVec = new Vec3(x1, y1, z1);
                float x2 = getFloatInRange(l, h);
                float y2 = getFloatInRange(l, h);
                float z2 = getFloatInRange(l, h);
                Vec3 vectorDivideBy = new Vec3(x2,y2,z2);

                testVec.divideEqual(vectorDivideBy);
                assertEquals(x1 / x2, testVec.x);
                assertEquals(y1 / y2, testVec.y);
                assertEquals(z1 / z2, testVec.z);
            }

        }


        @Test
        @DisplayName("Should Give Length Squared")
        public void lengthSquaredTest(){
            for (int i = 0; i < 200; i++) {
                    float x = getFloatInRange(LOW,HIGH);
                    float y = getFloatInRange(LOW,HIGH);
                    float z = getFloatInRange(LOW,HIGH);

                    float expectedLength = x*x + y*y + z*z;
                    Vec3 v1 = new Vec3(x, y ,z );
                    assertThat(v1.lengthSquared(),is(expectedLength));
            }
        }

        @Test
        @DisplayName("Should give Length Squared")
        public void should_give_Length () {
            for (int i = 0; i < 200; i++) {
                float x = getFloatInRange(LOW,HIGH);
                float y = getFloatInRange(LOW,HIGH);
                float z = getFloatInRange(LOW,HIGH);


                Vec3 v1 = new Vec3(x, y ,z );
                float expectedLengthSquared = (float) Math.sqrt(v1.lengthSquared());

                assertThat(v1.length(),is(expectedLengthSquared));
            }
        }

        @Test
        @DisplayName("Should Give HashValue")
        public void shouldGiveHashValue(){
            Vec3 v1 = new Vec3(2,4,4);



        }
 }
