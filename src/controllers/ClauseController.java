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

    private static int clauseCount = -1;

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
        Clause random;
        do {
            int midpoint = clauses.size() / 2;
            if (midpoint > clauses.size())
                midpoint = 0;

            double prob = Math.random();
            int min;
            int max;
            if (prob > .75) {
                min = 0;
                max = midpoint;
            } else {
                min = midpoint;
                max = clauses.size();
            }
            int range = max - min;
            int index = (int) ((Math.random() * range) + min);
            random = clauses.get(index);
        } while(random.getLiterals().size() <= 1);  //Get clause with > 1 literal
        return random;
    }

    /**
     * Randomly chooses a starting clause that has more than 1 literal
     * @return
     */
//    public static Clause chooseStartingClause(){
//        Clause c = chooseRandomClause();
//        while(c.getLiterals().size() == 1)
//            c = chooseRandomClause();
//
//        return c;
////        return clauses.get(clauses.size() - 1);
//    }

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

        if(clause.getLiterals().size() == 1){
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

    /**
     * Takes two clauses and resolves them
     * @param c the first clause to resolve
     * @param c2 the second clause to resolve
     * @return the resolved clause
     */
    public static Clause resolveClauses(Clause c, Clause c2){
        //Clone both the clauses
        Clause clone = new Clause(c);
        Clause clone2 = new Clause(c2);
        //Iterate through each literal in first clause
        for(int i = 0; i < clone.getLiterals().size(); i++){
            Literal l = clone.getLiterals().get(i);
            for(int x = 0; x < clone2.getLiterals().size(); x++){
                Literal l2 = clone2.getLiterals().get(x);
                //Find literal in second clause that is the negation of the original literal
                if(LiteralController.isNegation(l, l2)){
                    //Remove both literals from the clones
                    clone.getLiterals().remove(i);
                    i--;
                    clone2.getLiterals().remove(x);
                    x--;
                }
            }
        }

        Clause resolvedClause = concatenateClauses(clone, clone2);
        resolvedClause.getCombinedClauses().add(clone.getNumber());
        resolvedClause.getCombinedClauses().add(clone2.getNumber());

        if(resolvedClause.getLiterals().isEmpty())
            resolvedClause.setNumber(Integer.MAX_VALUE);
        return resolvedClause;
    }

    public static Clause simplifyResolvedClause(Clause resolvedClause){
        //Iterate through all clauses
        //If clause size is 1, and resolvedClause contains that clause, remove it
        for(Clause c : clauses){
            if(c.getLiterals().size() == 1){
                Literal lit = c.getLiterals().get(0);
                for(int i = 0; i < resolvedClause.getLiterals().size(); i++){
                    Literal l = resolvedClause.getLiterals().get(i);
                    if(l.equals(lit) || LiteralController.isNegation(lit, l))
                        resolvedClause.getLiterals().remove(i);
                }
            }
        }

        return resolvedClause;
    }

    /**
     * Concatenates the literals of one clause to another
     * @param c
     * @param c2
     * @return the clause with the concatenated literals
     */
    public static Clause concatenateClauses(Clause c, Clause c2){
        for(Literal l : c2.getLiterals()) {
            if(!c.getLiterals().contains(l))
                c.getLiterals().add(l);
        }

        return c;
    }
}
