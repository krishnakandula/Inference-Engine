package controllers;

import models.Literal;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public abstract class LiteralController {
    public static Literal initializeLiteral(String input){
        boolean isNegated = input.charAt(0) == '~' ? true : false;
        String name = isNegated ? input.substring(1) : input;

        return new Literal(name, isNegated);
    }

    /**
     * Creates a new literal that is the negation of the original
     * @param literal the literal to be negated
     * @return the negated literal
     */
    public static Literal negateLiteral(Literal literal){
        Literal copy = new Literal(literal);
        copy.setNegation(!literal.isNegated());

        return copy;
    }

    /**
     * Checks two literals to see if one is a negation of the other
     * @param literal the first literal to check
     * @param negated the second literal to check
     * @return whether or not the literals are negations of each other
     */
    public static boolean isNegation(Literal literal, Literal negated){
        if(literal.getName().equals(negated.getName()) && !literal.isNegated() == negated.isNegated())
            return true;
        return false;
    }
}
