package calculator.project2calculatorgui;

/**
 * CSC 305 Project 2
 * Represents the modulo binary operation
 *
 * @author Thien An Tran
 * @version 2.0
 */

public class ModuloOp extends BOperation{

    /**
     * Constructs a ModuloOp object
     *
     * @param left an Expression for the left operand
     * @param right an Expression for the right operand
     */
    public ModuloOp(Expression left, Expression right) {
        super("%", left, right);
    }

    /**
     * Calculates the left and right expressions with modulo
     *
     * @return a double of the result
     * @throws ArithmeticException when trying to modulo by zero (divide by zero error)
     */
    @Override
    public double evaluate() {
        if (right.evaluate() == 0) {
            throw new ArithmeticException("Divide by zero");
        } else {
            return left.evaluate() % right.evaluate();
        }
    }

}
