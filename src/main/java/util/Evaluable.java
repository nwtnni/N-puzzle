package util;

/*
 * Visitor design pattern. Class to be visited.
 */
public interface Evaluable {
    public int accept(Evaluator e);
}
