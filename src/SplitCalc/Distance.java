package SplitCalc;

public class Distance{
	private double split;
	private double pace;
	
	public Distance() {
		split = 0;
		pace = 0;
	}
	
	public Distance(double mile, double pace) {
		this.split = mile;
		this.pace = pace;
	}

	public double getSplit() {
		return split;
	}

	public void setSplit(double split) {
		this.split = split;
	}

	public double getPace() {
		return pace;
	}

	public void setPace(double pace) {
		this.pace = pace;
	}
	
}