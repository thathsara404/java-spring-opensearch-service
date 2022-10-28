package com.thath.opensaerch.function;

/**
 * Represents a function that accepts three argument and produces a result.
 * Type parameters: <R> – the type of the return value of the function
 * */
@FunctionalInterface
public interface TriArgsFunction<O, P, Q, R> {

    /**
     * Apply the given instructions.
     * @return R return type
     * */
    public abstract R apply(O o, P p, Q q);
}
