public class OperationFactory {
	public static Operation getOperation(String operationType) {
		switch (operationType) {
		case Operation.ADD:
			return new Add();
		case Operation.SUBTRACT:
			return new Subtract();
		case Operation.MULTIPLY:
			return new Multiply();
		case Operation.DIVIDE:
			return new Divide();
		}
		return null;
	}
}
