package util;

/*
 * Visitor design pattern. Class to be visited.
 */
public interface Evaluable {
    public void accept(Evaluator e);
}
