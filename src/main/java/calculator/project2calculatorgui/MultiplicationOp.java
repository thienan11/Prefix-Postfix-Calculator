package calculator.project2calculatorgui;

/**
 * Represents the multiplication binary operation
 *
 * @author Thien An Tran
 * @version May 22, 2023
 */
public class MultiplicationOp extends BOperation{

    /**
     * Constructs a MultiplicationOp object
     *
     * @param left an Expression for the left operand
     * @param right an Expression for the right operand
     */
    public MultiplicationOp(Expression left, Expression right) {
        super("*", left, right);
    }

    /**
     * Calculates the left and right expression with multiplication
     *
     * @return a double of the result
     */
    @Override
    public double evaluate() {
        return left.evaluate() * right.evaluate();
    }

}
