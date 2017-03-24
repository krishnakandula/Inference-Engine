package controllers;

import models.Literal;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class LiteralController {
    public static Literal initializeLiteral(String input){
        boolean isNegated = input.charAt(0) == '~' ? true : false;
        String name = isNegated ? input.substring(1) : input;

        return new Literal(name, isNegated);
    }
}
