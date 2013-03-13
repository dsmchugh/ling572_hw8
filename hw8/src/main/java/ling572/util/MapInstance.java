package ling572.util;

import java.util.*;

public class MapInstance<T> implements Instance<T> {

	private String name;
    private String label;
    private Map<String,T> features;

    public MapInstance() {
        this.features = new HashMap<>();
    }

    @Override
    public void addFeature(String feature, T value) {
        this.features.put(feature, value);
    }

    @Override
    public String getLabel() {
        return this.label;
    }
    
    @Override
    public void setLabel(String label) {
    	this.label = label;
    }
    
    @Override
    public String getName() {
    	return this.name;
    }
    
    @Override
    public int getSize() {
    	return this.features.size();
    }
    
    @Override
    public void setName(String name) {
    	this.name = name;
    }

    @Override
    public boolean containsFeature(String feature) {
        return this.features.containsKey(feature);
    }

    @Override
    public T getFeatureValue(String feature) {
        return this.features.get(feature);
    }

    @Override
    public T getFeatureValueOrDefault(String feature, T val) {
        if (this.containsFeature(feature))
            return this.features.get(feature);
        else
            return val;
    }

    @Override
    public Map<String,T> getFeatures() {
        return this.features;
    }

    @Override
    public void removeFeature(String feature) {
        this.features.remove(feature);
    }
}