package ling572;

import java.util.*;
import java.io.*;
import ling572.util.*;

public class TBLModel {
	private List<MapInstance<Integer>> instances = new ArrayList<>();
	private String defaultLabel = null;
	private int minGain = 0;
	
	private Map<Integer,String> curLabels = new HashMap<>();
	
	private int highestGain = 0;
	private Transformation highestGainTrans;
	
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
		//	set initial annotation
		for (int i=0; i<this.instances.size(); i++)
			this.curLabels.put(i, this.defaultLabel);

		this.calculateInformationGain();
		
		while (this.highestGain >= this.minGain) {
			this.applyTransformation(this.highestGainTrans);
			this.calculateInformationGain();
		}
	}
	
	private void applyTransformation(Transformation highestGainTransformation) {
		//	TODO update curLabels affected by transformation
	}
	
	private void calculateInformationGain() {
		Map<Transformation,Integer> informationGain = new HashMap<>();
		
		for (int i=0; i<this.instances.size(); i++) {
			Instance<Integer> instance = this.instances.get(i);
			String goldClass = instance.getLabel(); 
			String fromClass = this.curLabels.get(i);
			
			for (Map.Entry<String, Integer> entry : instance.getFeatures().entrySet()) {
				String featName = entry.getKey();

				for (String toClass : this.getLabels()) {
					if (fromClass.equals(toClass))
						continue;
					
					Transformation trans = new Transformation(featName, fromClass, toClass);
					
					Integer infoGain = informationGain.get(trans);
					
					if (infoGain==null) {
						infoGain=0;
						informationGain.put(trans, infoGain);
					}
					
					if (goldClass.equals(toClass))
						infoGain++;
					  else
						infoGain--;
				}
			}
		}
		
		//	TODO determine highest gain and set highestGain and highestGainTrasn vars
	}
	
	public void generateModel(File modelFile) {
		//	TODO print model
	}
	
	private Set<String> getLabels() {
		return new HashSet<String>();
		//	TODO get set of possible labels
	}
}
