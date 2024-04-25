package guioptimiser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvaluateEnergyConsumption {
    public static void main(String[] args) {
        try {
            String[][] processedData = processCSV("/Users/yuyanwang/sbse-assignment2/sbseAssignment2/GuiOptimiser/src/guioptimiser/colorAll.csv");
            // for (String[] row : processedData) {
            //     for (String cell : row) {
            //         System.out.print(cell + " ");
            //     }
            //     System.out.println();
            // }            
            int optimalRow = findOptimalRow(processedData);
            System.out.println("Optimal row index: " + optimalRow);
            int optimalRowHillClimber = findOptimalRowHillClimber(processedData);
            System.out.println("Optimal row index(Hill Climber): " + optimalRowHillClimber);
            int optimalRowSimulateAnnealing = simulateAnnealing(processedData);
            System.out.println("Optimal row index(Simulate Annealing): " + optimalRowSimulateAnnealing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int simulateAnnealing(String[][] processedData) {
        Random rand = new Random();
        double temp = 10000;
        double coolingRate = 0.003;

        int currentPos = rand.nextInt(processedData.length);
        double bestValue = Double.MAX_VALUE;
        int bestIndex = currentPos;

        while (temp > 1) {
            int nextPos = rand.nextInt(processedData.length);
            double currentValue = Double.parseDouble(processedData[currentPos][processedData[currentPos].length - 1]);
            double nextValue = Double.parseDouble(processedData[nextPos][processedData[nextPos].length - 1]);

            if (acceptanceProbability(currentValue, nextValue, temp) > rand.nextDouble()) {
                currentPos = nextPos;
                if (nextValue < bestValue) {
                    int[] rgb1 = parseRGB(processedData[currentPos][24]);
                    int[] rgb2 = parseRGB(processedData[currentPos][25]);

                    if (colorDistance(rgb1, rgb2)) {
                        bestValue = nextValue;
                        bestIndex = currentPos;
                    }
                }
            }
            temp *= 1 - coolingRate;
        }

        return bestIndex;
    }

    private static double acceptanceProbability(double currentEnergy, double newEnergy, double temperature) {
        if (newEnergy < currentEnergy) {
            return 1.0;
        }
        return Math.exp((currentEnergy - newEnergy) / temperature);
    }

    public static int findOptimalRowHillClimber(String[][] processedData) {
        Random rand = new Random();
        int n = rand.nextInt(processedData.length);
        double bestValue = Double.MAX_VALUE;
        int bestIndex = -1;

        for (int i = 0; i < 10000; i++) { 
            double currentValue = Double.parseDouble(processedData[n][processedData[n].length - 1]);
            if (currentValue < bestValue) {
                int[] rgb1 = parseRGB(processedData[n][24]);
                int[] rgb2 = parseRGB(processedData[n][25]);

                if (colorDistance(rgb1, rgb2)) {
                    bestValue = currentValue;
                    bestIndex = n;
                }
            }
            n = rand.nextInt(processedData.length);
        }

        return bestIndex;
    }

    public static int findOptimalRow(String[][] processedData) {
        Random random = new Random();
        int bestN = -1;
        double minValue = Double.MAX_VALUE;

        for (int i = 0; i < 10000; i++) {
            int n = random.nextInt(processedData.length);
            double lastValue = Double.parseDouble(processedData[n][processedData[n].length - 1]);

            if (lastValue < minValue) {
                int[] rgb1 = parseRGB(processedData[n][24]);
                int[] rgb2 = parseRGB(processedData[n][25]);

                if (colorDistance(rgb1, rgb2)) {
                    minValue = lastValue;
                    bestN = n;
                }
            }
        }

        return bestN;
    }

    private static int[] parseRGB(String rgb) {
        rgb = rgb.substring(1, rgb.length() - 1);
        String[] parts = rgb.split(",");
        return new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])};
    }

    public static String[][] processCSV(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String[]> data = new ArrayList<>();
        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] cells = line.split(",");
            for (int i = 0; i < cells.length; i++) {
                if (i < cells.length - 1) {
                    cells[i] = "[" + cells[i].replace('_', ',') + "]";
                } else {
                    try {
                        double lastValue = Double.parseDouble(cells[i]);
                        cells[i] = String.valueOf(lastValue);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid double format in last column: " + cells[i]);
                        cells[i] = "0.0";
                    }
                }
            }
            data.add(cells);
        }
        reader.close();

        String[][] arrayData = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            arrayData[i] = data.get(i);
        }

        return arrayData;
    }
    
    
    public static boolean colorDistance(int[] rgb1, int[] rgb2) {
        double distance = Math.sqrt(Math.pow(rgb1[0] - rgb2[0], 2) +
                                    Math.pow(rgb1[1] - rgb2[1], 2) +
                                    Math.pow(rgb1[2] - rgb2[2], 2));
        return distance > 128;
    }
}
