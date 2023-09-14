public class Polynomial {
	
	//Data fields
	private double[] coefficients;
	
	//Constructor
	public Polynomial() {
		this.coefficients = new double[]{0};
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;
	}
	
	//Method
	public Polynomial add(Polynomial p) {
		int length = Math.max(this.coefficients.length, p.coefficients.length);
		double[] result = new double [length];
		
		for (int i = 0; i < length; i++) {
			double a = (i < this.coefficients.length) ? this.coefficients[i] : 0;
			double b = (i < p.coefficients.length) ? p.coefficients[i] : 0;
			result[i] = a + b;
		}
		
		return new Polynomial(result);
	}

	public double evaluate(double x) {
		double result = 0;
		for (int i = this.coefficients.length - 1; i >= 0; i--) {
			result = result * x + this.coefficients[i];
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}		
	
}