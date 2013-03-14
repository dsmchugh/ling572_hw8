package ling572;

public class Transformation {
	private String featName;
	private String fromClass;
	private String toClass;
	
	private int netGain = 0;
	
	private String transString = null;
	
	public Transformation(String featName, String fromClass, String toClass) {
		this.featName = featName;
		this.fromClass = fromClass;
		this.toClass = toClass;
	}
	
	public Transformation(String transString) {
		this.parseTransString(transString);
	}
	
	public String getFeatName() {
		return this.featName;
	}
	
	public String getFromClass() {
		return this.fromClass;
	}
	
	public String getToClass() {
		return this.toClass;
	}
	
	public int getNetGain() {
		return this.netGain;
	}
	
	public void setNetGain(int netGain) {
		this.netGain = netGain;
	}
	
	private void parseTransString(String transString) {
		String[] splitString = transString.split("\\s");
		
		if (splitString.length != 4) 
			return;	//	invalid string
		
		this.featName = splitString[0];
		this.fromClass = splitString[1];
		this.toClass = splitString[2];
		this.netGain = Integer.parseInt(splitString[3]);
	}
	
	@Override
	public String toString() {
		if (this.transString == null) {
			this.transString = this.featName +  " " + this.fromClass + " " + this.toClass;
		}
		
		return this.transString;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override 
	public boolean equals(Object other) {        
        return other.toString().equals(this.toString());
    }
}
