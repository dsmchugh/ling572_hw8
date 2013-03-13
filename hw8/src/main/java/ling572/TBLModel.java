package ling572;

import java.util.*;
import java.io.*;
import ling572.util.*;

public class TBLModel {
	private List<MapInstance<Integer>> instances = new ArrayList<>();
	private String defaultLabel = null;
	private int minGain = 0;
	
	
	public void setInstances(List<MapInstance<Integer>> instances) {
		this.instances = instances;
		
		//	"initial annotator" tags each instance with first class in training data
		this.defaultLabel = instances.get(0).getLabel();
	}
	
	public List<MapInstance<Integer>> getInstances() {
		return this.instances;
	}
	
	public void setMinGain(int minGain) {
		this.minGain = minGain;
	}
	
	public void buildModel() {
		//	TODO build model based on instances
	}
	
	public void printModel(File modelFile) {
		//	TODO print model
	}
}
