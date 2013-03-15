package ling572;

import java.util.*;
import java.io.*;

import ling572.util.*;

public class TBLModel {
	private List<MapInstance<Integer>> instances = new ArrayList<>();
	private String defaultLabel = null;
	private int minGain = 0;
	
	private Set<String> allLabels = new HashSet<>();
	private Map<Integer,String> curLabels = new HashMap<>();
	
	private int highestGain = 0;
	private Transformation highestGainTrans;
	
	private List<Transformation> transformations = new ArrayList<>();
	
	public void setInstances(List<MapInstance<Integer>> instances) {
		this.instances = instances;
	}
	
	public void setMinGain(int minGain) {
		this.minGain = minGain;
	}
	
	public void buildModel() {
		// initial annotation--each instance tagged with first class in training data
		this.defaultLabel = instances.get(0).getLabel();
		
		for (int i=0; i<this.instances.size(); i++) {
			this.allLabels.add(this.instances.get(i).getLabel());
		}
		
		this.calculateGain();
		
		while (this.highestGain >= this.minGain) {
			this.applyTransformation(this.highestGainTrans);
			this.calculateGain();
		}
	}
	
	public void loadModel(File model) {
		try (BufferedReader reader = new BufferedReader(new FileReader(model))) {
			String line = reader.readLine();
			
			if (line == null)
				return;

			this.defaultLabel = line;
			
			while ((line = reader.readLine()) != null) {
				Transformation trans = new Transformation(line);
				
				if (!trans.isValid()) {
					throw new IOException();
				}
				
				this.transformations.add(trans);
			}
		} catch (IOException e) {
			System.out.println("invalid model file");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void applyTransformation(Transformation transformation) {
		this.transformations.add(transformation);
		
		for (int i=0; i<this.instances.size(); i++) {
			Instance<Integer> instance = this.instances.get(i);
			String curLabel = this.getCurLabel(i);
			for (String featName : instance.getFeatures().keySet()) {
				if (featName.equals(transformation.getFeatName()) && curLabel.equals(transformation.getFromClass())) {
					this.curLabels.put(i, transformation.getToClass());
					break;
				}
			}
		}
	}
	
	private void calculateGain() {
		Map<Transformation,Integer> transGains = new HashMap<>();
		
		for (int i=0; i<this.instances.size(); i++) {
			Instance<Integer> instance = this.instances.get(i);
			String goldClass = instance.getLabel(); 
			String fromClass = this.getCurLabel(i);
				
			for (Map.Entry<String, Integer> entry : instance.getFeatures().entrySet()) {
				String featName = entry.getKey();

				for (String toClass : this.getLabels()) {
					if (fromClass.equals(toClass))
						continue;
					
					Transformation trans = new Transformation(featName, fromClass, toClass);
					
					Integer gain = transGains.get(trans);
					
					if (gain==null)
						gain=0;
					
					if (goldClass.equals(toClass))
						gain++;
					  else
						gain--;
					
					transGains.put(trans, gain);
				}
			}
		}
		
		this.highestGain = 0;
		for (Map.Entry<Transformation, Integer> entry : transGains.entrySet()) {
			if (entry.getValue() > highestGain) {
				this.highestGain = entry.getValue();
				this.highestGainTrans = entry.getKey();
				this.highestGainTrans.setNetGain(this.highestGain);
			}
			
		}	
	}
	
	private String getCurLabel(int i) {
		String curLabel = this.curLabels.get(i);
		
		if (curLabel==null)
			curLabel = this.defaultLabel;
		
		return curLabel;
	}
	
	public void generateModel(File modelFile) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(modelFile))) {
			writer.write(defaultLabel);
			writer.newLine();
			
			for (Transformation trans : this.transformations) {
				writer.write(trans.toString() + " " + trans.getNetGain());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public List<Transformation> processInstance(Instance<Integer> instance, int n) {
		List<Transformation> transList = new ArrayList<>();
		
		if (n > this.transformations.size())
			n = this.transformations.size();
			
		
		int i = 0;
		String curLabel = this.defaultLabel;
		while (i<n) {
			Transformation toTrans = this.transformations.get(i);
			
			if (instance.containsFeature(toTrans.getFeatName()) && curLabel.equals(toTrans.getFromClass())) {
				curLabel = toTrans.getToClass();
				transList.add(toTrans);
			}
			
			i++;
		}
		
		return transList;
	}

	public String getDefaultLabel() {
		return this.defaultLabel;
	}

	private Set<String> getLabels() {
		return this.allLabels;
	}
}
