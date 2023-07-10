package calculator.project2calculatorgui;

/**
 * Represents the division binary operation
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public class DivisionOp extends BOperation{

    /**
     * Construct a DivisionOp object
     *
     * @param left an Expression for the left operand
     * @param right an Expression for the right operand
     */
    public DivisionOp(Expression left, Expression right) {
        super("/", left, right);
    }

    /**
     * Calculates the left and right expressions with division
     *
     * @return a double of the result
     * @throws ArithmeticException when trying to divide by zero
     */
    @Override
    public double evaluate() {
        if (right.evaluate() == 0) {
            throw new ArithmeticException("Divide by zero");
        } else {
            return left.evaluate() / right.evaluate();
        }
    }

}
