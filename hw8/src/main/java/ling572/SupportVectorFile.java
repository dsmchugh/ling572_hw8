package ling572;

import java.io.*;
import java.util.*;

import ling572.util.VectorInstance;

public class SupportVectorFile {
	private KernelType kernel_type;
	private Double rho = null;
	private Double coef0 = null;
	private Double gamma = null;
	private Double degree = null;
	
	private List<VectorInstance> instances = new ArrayList<>();
	
	public void read(File dataFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null && !line.equals("SV")) {
                String[] splitLine = line.split("\\s+");
                
                switch(splitLine[0]) {
	                case "kernel_type":
	                	this.kernel_type = KernelType.valueOf(splitLine[1]);
	                	break;
	                case "rho":
	                	this.rho = Double.parseDouble(splitLine[1]);
	                	break;
	                case "degree":
	                	this.degree = Double.parseDouble(splitLine[1]);
	                	break;
	                case "gamma":
	                	this.gamma = Double.parseDouble(splitLine[1]);
	                	break;
	                case "coef0":
	                	this.coef0 = Double.parseDouble(splitLine[1]);
	                	break;
	                default:
	                	continue;
                }
            }
            
            while ((line = reader.readLine()) != null) {
            	String[] splitLine = line.split("\\s+");
            	
            	VectorInstance instance = new VectorInstance();
            	double weight = Double.parseDouble(splitLine[0]);
            	instance.setWeight(weight);
            	
            	for (int i=1; i<splitLine.length; i++) {
            		String[] featureSplit = splitLine[i].split(":");
            		
            		Integer feature_idx;
    				Double value;
    				
    				try {
    					feature_idx = Integer.parseInt(featureSplit[0]);
    					value = Double.parseDouble(featureSplit[1]);
    				} catch (NumberFormatException e) {
    					System.out.println("Error: cannot convert " + splitLine[i] + " to feature/value pair");
    					continue;
    				} catch (ArrayIndexOutOfBoundsException e) {
    					//	ignore. extra space in file
    					continue;
    				}
    				
    				instance.addFeature(feature_idx, value);
    			}
    			
    			this.instances.add(instance);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
		
	}	

	public Double getRho() {
		return this.rho;
	}
	
	public Double getCoef0() {
		return this.coef0;
	}
	
	public Double getGamma() {
		return this.gamma;
	}
	
	public Double getDegree() {
		return this.degree;
	}
	
	public KernelType getKernelType() {
		return this.kernel_type;
	}
	
	public List<VectorInstance> getVectorInstances() {
		return this.instances;
	}
}
