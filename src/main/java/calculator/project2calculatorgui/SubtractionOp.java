package calculator.project2calculatorgui;

/**
 * Represents the subtraction binary operation
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public class SubtractionOp extends BOperation{

    /**
     * Constructs a SubtractionOp object
     *
     * @param left an Expression for the left operand
     * @param right an Expression for the right operand
     */
    public SubtractionOp(Expression left, Expression right) {
        super("-", left, right);
    }

    /**
     * Calculates the left and right expressions with subtraction
     *
     * @return a double of the result
     */
    @Override
    public double evaluate() {
        return left.evaluate() - right.evaluate();
    }

}
