public abstract class Operation {
	public static final String ADD = "add";
	public static final String SUBTRACT = "subtract";
	public static final String MULTIPLY = "multiply";
	public static final String DIVIDE = "divide";

	public abstract double getResult(double a, double b);
}
