package controllers;

import models.Clause;
import models.Literal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public abstract class ClauseController {
    private static ClauseController controller;
    private static List<Clause> clauses;

    private static int clauseCount = 0;

    /**
     * Reads in clauses from input file and initializes them
     * @param inputFilePath
     */
    public static void initializeClauses(String inputFilePath){
        FileReader fileReader = null;
        BufferedReader reader = null;
        clauses = new ArrayList<>();

        try{
            fileReader = new FileReader(inputFilePath);
            reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                String[] rawLiterals = line.split(" ");
                List<Literal> literals = new ArrayList<>();
                for(String rawLiteral : rawLiterals)
                    literals.add(LiteralController.initializeLiteral(rawLiteral));
                clauses.add(new Clause(literals, ++clauseCount));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        printClauses();
    }

    public static List<Clause> getClauses(){
        return clauses;
    }

    public static void printClauses(){
        StringBuilder builder = new StringBuilder();
        for(Clause c : clauses)
            builder.append(c + "\n");

        System.out.println(builder.toString());
    }

    /**
     * Randomly chooses one of the clauses to start resolution with
     * @return the randomly chosen starting clause
     */
    public static Clause chooseRandomClause(){
        int range = clauses.size();
        int index = (int) ((Math.random() * range));

        return clauses.get(index);
    }

    /**
     * Adds a new clause to the clause list
     * @param clause the new clause to be added
     * @return whether or not the clause was added
     */
    public static boolean addClause(Clause clause){
        //Check to see if the clause already exists
        if(clauses.contains(clause))
            return false;

        //Give clause new number
        clause.setNumber(++clauseCount);
        clauses.add(clause);
        return true;
    }

    /**
     * Negates a given clause
     * @param clause the clause to be negated
     * @return the negated clause/clauses
     */
    public static List<Clause> negateClause(Clause clause){
        //Create copy of clause
        //Two cases
        //1. Only one literal in a clause
            //Negate that literal and return copied clause
        //2. Multiple literals
            //Negate each literal
            //Create clause for each literal and add to list

        Clause copy = new Clause(clause);
        List<Clause> negatedClauses = new ArrayList<>();

        if(clause.getLiterals().size() > 1){
            Literal negatedLiteral = LiteralController.negateLiteral(clause.getLiterals().get(0));
            clause.getLiterals().remove(0);
            clause.getLiterals().add(negatedLiteral);
            negatedClauses.add(copy);
        } else {
            for (Literal l : clause.getLiterals()) {
                Literal negatedLiteral = LiteralController.negateLiteral(l);
                List<Literal> literals = new ArrayList<>();
                literals.add(negatedLiteral);
                Clause c = new Clause(literals);
                negatedClauses.add(c);
            }
        }

        return negatedClauses;
    }
}
