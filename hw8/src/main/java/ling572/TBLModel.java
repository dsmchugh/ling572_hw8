package ling572;

import java.util.*;
import java.io.*;
import ling572.util.*;

public class TBLModel {
	private List<MapInstance<Integer>> instances = new ArrayList<>();
	
	public void setInstances(List<MapInstance<Integer>> instances) {
		this.instances = instances;
	}
	
	public List<MapInstance<Integer>> getInstances() {
		return this.instances;
	}
	
	public void buildModel() {
		//	TODO build model based on instances
	}
	
	public void printModel(File modelFile) {
		//	TODO print model
	}
}
