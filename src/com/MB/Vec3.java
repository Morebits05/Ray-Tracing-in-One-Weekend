package com.MB;


import java.util.Objects;

/**
 * Vector 3
 * <p>
 * Consisting of X,Y & Z with Static Utility Methods.
 * </p>
 */
public final class Vec3 {

    /**
     * X Coordinate.
     * <code>Vec3 v1 = new Vec3(2,3,4);
     * v1.x = 4;</code>
     */
    public float x;

    /**
     * Y Coordinate.
     * <code>Vec3 v1 = new Vec3(2,3,4);
     * * v1.y = 6;</code>
     */
    public float y;

    /**
     * Z Coordinate.
     * <code>Vec3 v1 = new Vec3(2,3,4);
     * * v1.z = 7;</code>
     */
    public float z;

    /**
     * Name: Vec3.
     * <p>
     * Base Constructor takes 3 parameters.
     * <p>
     *
     * @param newX - float
     * @param newY - float
     * @param newZ - float
     * @<code> Vec3 v1 = new Vec3(3,4,5);
     * System.out.println(v1.x());
     * </code>
     */
    public Vec3(final float newX, final float newY, final float newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    /**
     * Default Constructor for Vector3.
     */
    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }


    /**** Multiply or Scale.
     * <p>
     * Takes a Vector and scales it by a float.
     * <p>
     * <code>{{@link #Vec3()}.scale(v1,2.0f);</code>
     * <p>
     * @param t - the Float to Scale By
     * @param vec - The Vector to Scale
     * @return A new vector scaled by t
     */
    public static Vec3 scale(final float t, final Vec3 vec) {
        return vec.scale(t);
    }

    /**** Divide.
     * <p>
     * Takes a Vector and divides it by a float
     * <p>
     * <code>{@link #Vec3()}.divide(v1,2.0f)</code>
     * <p>
     * @param t - the Float to Scale By
     * @param vectorToDivide - The Vector to Scale
     * @return A new vector
     */
    public static Vec3 divide(final Vec3 vectorToDivide, final float t) {
        return scale(1 / t, vectorToDivide);
    }


    /*** Normalize.
     *
     * <p>Takes a Vector and returns a normalized version.
     *    If the Vector is <0,0,0> it just returns a new vector of <0,0,0>,
     *    to avoid errors.</p>
     *
     * @param v - Vector to normalize
     *    <code> {@link #Vec3()}.normalize(v1);</code>
     * @return normalized vector.
     * */
    public static Vec3 normalize(final Vec3 v) {
        if (v.x == 0 && v.y == 0 && v.z == 0) {
            return new Vec3(0, 0, 0);
        }
        return divide(v, v.length());
    }

    /**
     * Dot Product.
     *
     * <p>Performs the dot product of two vectors</p>
     *
     * <code>{@link #Vec3()}.dot(v1,v2)</code>
     *
     * @param vectorU First Vector
     * @param vectorV Second Vector
     * @return the dot product of the two vectors
     */
    public static float dot(final Vec3 vectorU, final Vec3 vectorV) {
        return vectorU.x * vectorV.x
                + vectorU.y * vectorV.y
                + vectorU.z * vectorV.z;
    }

    /**
     * Returns the Angle between two vectors.
     *
     * @param a - first vector
     * @param b - second vector
     * @return the angle as a float.
     * @see Vec3
     ***/
    public static float angle(final Vec3 a, final Vec3 b) {
        return (float) Math.acos(Vec3.dot(a, b) / (a.length() * b.length()));
    }

    /**
     * Linear Interpolation function.
     *
     * @param v0 - First Point
     * @param v1 - Second Point
     * @param t  - tweak value
     * @return New Vector between v0 and v1
     * @see <a href="https://en.wikipedia.org/wiki/Linear_interpolation"></a>
     **/
    public static Vec3 lerp(Vec3 v0, Vec3 v1, float t) {
        return v0.scale(1 - t).add(v1.scale(t));
    }

    /**
     * toString.
     *
     * @return Vector in String format as <X,Y,Z>
     */
    @Override
    public String toString() {
        return "<" + x + "," + y + "," + z + ">";
    }

    /**
     * Default equals method.
     **/
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vec3 vec3 = (Vec3) o;
        return x == vec3.x && y == vec3.y && z == vec3.z;
    }

    /**
     * Default hashcode.
     **/
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    /**
     * Add.
     * <p>
     * Adds this vector to second vector.
     * </p>
     *
     * @param other - vector to add.
     * @return new Vector of addition.
     */
    public Vec3 add(final Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtracts other vector from this vector.
     * and returns a new vector of the subtraction.
     *
     * @param other - Vector to Subtract.
     * @return new vector of the subtraction
     * @see #Vec3()
     */
    public Vec3 subtract(final Vec3 other) {
        return new Vec3(x - other.x,
                y - other.y,
                z - other.z);
    }

    /**
     * Scale.
     * Scales this vector by another Vector.
     *
     * @param other - vector to scale by;
     * @return new Vector scaled by other vector.
     * @see #Vec3().
     */
    public Vec3 scale(final Vec3 other) {
        return new Vec3(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Scale.
     * See {@link #scale(float, Vec3)}
     *
     * @param t - float to scale this vector by.
     * @return this vector scaled by float.
     */
    public Vec3 scale(final float t) {
        return new Vec3(x * t, y * t, z * t);
    }

    /**
     * Add Equals.
     * <p>
     * Adds another Vector to this vector and
     * returns this vector back.
     * </p>
     *
     * @param other - Vector to add
     * @return this vector back.
     */
    public Vec3 addEquals(final Vec3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    /**
     * Scale Equals.
     * Scales this vector by the other vector and returns
     * this vector back
     *
     * @param other - the vector to scale by
     * @return This Vector
     */
    public Vec3 scaleEquals(final Vec3 other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    /**
     * Scale Equals.
     * Scales this vector by a float and returns
     * this vector back
     *
     * @param t - the float to scale by
     * @return This Vector
     */
    public Vec3 scaleEquals(final float t) {
        this.x *= t;
        this.y *= t;
        this.z *= t;
        return this;
    }

    /**
     * Divide Equals.
     * Divides this vector by the float and returns
     * this vector back
     *
     * @param t - the float to divide by
     * @return This Vector
     */
    public Vec3 divideEqual(final float t) {
        return scaleEquals(1 / t);
    }

    /**
     * Subtract Equals.
     * Subtracts this vector by the other vector and returns
     * this vector back
     *
     * @param other - the vector to subtract by
     * @return This Vector
     */
    public Vec3 subtractEquals(final Vec3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    /**
     * Length Squared.
     * <p>
     * Calculates the Length Squared and returns it as a float.
     * </p>
     *
     * @return a float
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Length.
     * Returns the actual Length of the Vector.
     *
     * @return Length of the vector as Square root of {@link #lengthSquared()}.
     ***/
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Set.
     * Allows setting of the X,Y and Z variables, by
     * using another vector.
     * <p>
     * Used because of Java References.
     * </p>
     *
     * @param other - other vector to set variables.
     */
    public void set(final Vec3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    /**
     * Return this vector normalized
     **/
    public Vec3 normalize() {
        return Vec3.normalize(this);
    }

    /** Return this vector inverted */
    public Vec3 neg() {
        return new Vec3(-x,-y,-z);
    }
}
