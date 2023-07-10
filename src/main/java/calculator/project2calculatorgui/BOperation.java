package calculator.project2calculatorgui;

/**
 * CSC 305 Project 2
 * Abstract class representing binary operations in an expression
 *
 * @author Thien An Tran
 * @version 2.0
 */
public abstract class BOperation implements Expression {
    protected String operator;
    protected Expression left;
    protected Expression right;

    /**
     * Constructs a BOperation object
     *
     * @param operator a string representing an operator type
     * @param left as an Expression for the left operand
     * @param right as an Expression for the right operand
     */
    protected BOperation(String operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /**
     * Returns a string representation of a BOperation object in infix notation
     *
     * @return a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String leftOp = this.left.toString();
        String rightOp = this.right.toString();
        sb.append(leftOp).append(" ").append(this.operator).append(" ").append(rightOp);
        return sb.toString();
    }

    /**
     * Returns a string representation of a BOperation object in prefix notation
     *
     * @return a string
     */
    @Override
    public String toPrefixString(){
        StringBuilder sb = new StringBuilder();
        String leftOp = this.left.toPrefixString();
        String rightOp = this.right.toPrefixString();
        sb.append(this.operator).append(" ").append(leftOp).append(" ").append(rightOp);
        return sb.toString();
    }

    /**
     * Returns a string representation of a BOperation object in postfix notation
     *
     * @return a string
     */
    @Override
    public String toPostfixString(){
        StringBuilder sb = new StringBuilder();
        String leftOp = this.left.toPostfixString();
        String rightOp = this.right.toPostfixString();
        sb.append(leftOp).append(" ").append(rightOp).append(" ").append(this.operator);
        return sb.toString();
    }

}
