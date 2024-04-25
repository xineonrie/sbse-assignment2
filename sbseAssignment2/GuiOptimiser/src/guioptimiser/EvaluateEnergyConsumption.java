package guioptimiser;

public class EvaluateEnergyConsumption {
    
    public static boolean colorDistance(int[] rgb1, int[] rgb2) {
        double distance = Math.sqrt(Math.pow(rgb1[0] - rgb2[0], 2) +
                                    Math.pow(rgb1[1] - rgb2[1], 2) +
                                    Math.pow(rgb1[2] - rgb2[2], 2));
        return distance > 128;
    }
}
