package org.java;

public class SimpleMath {
    public Double sum(Double first, Double second) {
        return first + second;
    }

    public Double subtraction(Double first, Double second) {
        return first - second;
    }

    public Double multiplication(Double first, Double second) {
        return first * second;
    }

    public Double divide(Double f, Double s) {
        if(s.equals(0D)) throw new ArithmeticException("Impossible divide by zero");
        return f / s;
    }
}
