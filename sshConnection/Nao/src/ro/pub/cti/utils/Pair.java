package ro.pub.cti.utils;

public class Pair {
	private String parameter;
	private String value;
	
	public Pair(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}
	
	public String getParameter() {
		return parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
