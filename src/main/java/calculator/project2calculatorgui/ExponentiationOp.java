package calculator.project2calculatorgui;

/**
 * CSC 305 Project 2
 * Represents the exponentiation binary operation
 *
 * @author Thien An Tran
 * @version 2.0
 */
public class ExponentiationOp extends BOperation{

    /**
     * Constructs an ExponentiationOp object
     *
     * @param left an Expression for the left operand
     * @param right an Expression for the right operand
     */
    public ExponentiationOp(Expression left, Expression right) {
        super("^", left, right);
    }

    /**
     * Calculates the left and right expressions with exponentiation
     *
     * @return a double of the result
     * @throws ArithmeticException when base is 0 and power is negative (divide by zero error)
     */
    @Override
    public double evaluate() {
        double base = left.evaluate();
        double exponent = right.evaluate();
        if (base == 0 && exponent < 0) {
            throw new ArithmeticException("Divide by zero");
        } else {
            return Math.pow(left.evaluate(), right.evaluate());
        }
    }
}
