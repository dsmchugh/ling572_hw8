package ling572.util;

import java.util.Map;

public interface Instance<T> {
    void addFeature(String feature, T value);

    String getLabel();

    void setLabel(String label);

    String getName();

    int getSize();

    void setName(String name);

    boolean containsFeature(String feature);

    T getFeatureValue(String feature);

    T getFeatureValueOrDefault(String feature, T val);

    Map<String,T> getFeatures();

    void removeFeature(String feature);
}
