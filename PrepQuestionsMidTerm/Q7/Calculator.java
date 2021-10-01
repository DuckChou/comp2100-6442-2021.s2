public class Calculator {

	public double add(double a, double b) {

		//START YOUR CODE
		return a+b;


		//END YOUR CODE
	}

	public double subtract(double a, double b) {

		//START YOUR CODE
		return a-b;

		//END YOUR CODE
	}

	public double multiply(double a, double b) {

		//START YOUR CODE

		return a*b;
		//END YOUR CODE
	}

	public double divide(double a, double b) {

		//START YOUR CODE
		if(b==0)
			throw new ArithmeticException();

		return a/b;
		//END YOUR CODE
	}
}
