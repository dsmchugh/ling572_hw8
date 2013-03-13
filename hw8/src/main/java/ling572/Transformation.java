package ling572;

public class Transformation {
	private String featName;
	private String fromClass;
	private String toClass;
	
	public Transformation(String featName, String fromClass, String toClass) {
		this.featName = featName;
		this.fromClass = fromClass;
		this.toClass = toClass;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.featName);
		builder.append(' ');
		builder.append(this.fromClass);
		builder.append(' ');
		builder.append(this.toClass);
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
