package ling572.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SVMLightReader {
    public static List<VectorInstance> indexInstances(File dataFile) {
        List<VectorInstance> instances = new ArrayList<>();

        // line formatted as: label feature1:value1 feature2:value2 ..."
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split("\\s+");

                String label = splitLine[0];
                VectorInstance instance = new VectorInstance();
                instance.setLabel(label);

                for (int i = 1; i < splitLine.length; i++) {
                    String[] featureSplit = splitLine[i].split(":");
                    if (featureSplit.length != 2) continue;
                    String feature = featureSplit[0];
                    double value = Double.parseDouble(featureSplit[1]);
                    instance.addFeature(feature, value);
                }

                instances.add(instance);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return instances;
    }
}
