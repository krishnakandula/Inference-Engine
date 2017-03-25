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
        if(copy.isNegated())
            copy.setNegation(false);

        return copy;
    }
}
