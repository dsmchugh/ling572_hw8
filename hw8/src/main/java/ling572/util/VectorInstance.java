package ling572.util;

import cern.colt.function.tdouble.IntDoubleProcedure;
import cern.colt.map.tdouble.OpenIntDoubleHashMap;
import cern.colt.matrix.tdouble.DoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.SparseDoubleMatrix1D;

import java.util.Map;

public class VectorInstance implements Instance<Double> {

    private OpenIntDoubleHashMap features = new OpenIntDoubleHashMap();
    private String label;
    private String name;
    private double weight;
    private int maxFeature = 0;

    /**
     * Assumes libSVM format (i.e. integer-valued) features
     * @param feature  an integer-valued feature, represented as a string
     * @param value the value of the feature
     */
    public void addFeature(String feature, Double value) {
        int feat_idx = Integer.parseInt(feature);
        addFeature(feat_idx, value);
    }

    public void addFeature(int feat_idx, Double value) {
        if (feat_idx > maxFeature) maxFeature = feat_idx;
        features.put(feat_idx, value);
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return features.size();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setWeight(double weight) {
    	this.weight = weight;
    }
    
    public double getWeight() {
    	return this.weight;
    }

    public boolean containsFeature(String feature) {
        int feat_idx = Integer.parseInt(feature);
        return containsFeature(feat_idx);
    }

    public boolean containsFeature(int feat_idx)  {
        return (features.get(feat_idx) != 0);
    }

    public Double getFeatureValue(String feature) {
        int feat_idx = Integer.parseInt(feature);
        return getFeatureValue(feat_idx);
    }

    public Double getFeatureValue(int feat_idx) {
        return Double.valueOf(features.get(feat_idx));
    }

    public Double getFeatureValueOrDefault(String feature, Double val) {
        int feat_idx = Integer.parseInt(feature);
        return getFeatureValueOrDefault(feat_idx, val);
    }

    public Double getFeatureValueOrDefault(int feat_idx, Double val) {
        if (feat_idx > maxFeature)
            return val;
        else
            return features.get(feat_idx);
    }

    public Map<String, Double> getFeatures() {
        return null;  // not used here
    }

    public void removeFeature(String feature) {
        // not used here
    }

    public DoubleMatrix1D getVector() {
        // if memory started to become an issue, could swap to SparseDoubleMatrix1D, but dense is faster.
        final DenseDoubleMatrix1D vector = new DenseDoubleMatrix1D(maxFeature+1);
        features.forEachPair( new IntDoubleProcedure() {
            @Override
            public boolean apply(int first, double second) {
                vector.set(first,second);
                return true;
            }
        });
        return vector;
    }
}
