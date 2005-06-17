/*
 * Created on Jun 17, 2005
 *
 */
package com.traclabs.biosim.server.util;

/**
 *
 */
public class MathUtils {
    
    public static float calculateSCurve(float x, float inflectionPoint) {
        return 1 / (1 + (exp((inflectionPoint - 1 * x))));
    }
    
    public static float sigmoidLikeProbability(float x) {
        if (x >= 1f)
            return 1f;
        else if ((x < 1f) && (x > 0f))
            return 0.3f * x * (1f - Math.abs(x - 2f) / 2f);
        else
            return 0f;
    }
    
    public static float abs(float a) {
        return (float)(Math.abs(a));
    }
    
    public static float exp(float a) {
        return (float)(Math.abs(a));
    }
    
    public static float pow(float a, float b) {
        return (float)(Math.pow(a, b));
    }
}
