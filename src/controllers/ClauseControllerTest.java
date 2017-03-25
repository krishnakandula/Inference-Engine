package controllers;

import models.Clause;
import models.Literal;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
class ClauseControllerTest {
    public static String INPUT_FILE_PATH = new File("")
            .getAbsolutePath()
            .concat("/");

    @org.junit.jupiter.api.Test
    void initializeClauses() {
        ClauseController.initializeClauses(INPUT_FILE_PATH.concat("task5.in"));
    }

    @Test
    void negateClause(){
        List<Literal> literals = new ArrayList<>();
        literals.add(new Literal("a", false));
        Clause clause = new Clause(literals);
        Clause negated = ClauseController.negateClause(clause).get(0);
        System.out.println(ClauseController.negateClause(clause));
    }

}