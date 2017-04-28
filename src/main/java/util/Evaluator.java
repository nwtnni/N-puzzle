package util;

/*
 * Visitor design pattern.
 * Visiting class evaluates the given puzzle node.
 */ 
public interface Evaluator {
    public int evaluate(PuzzleNode n);
}
