import controllers.ClauseController;
import controllers.LiteralController;
import models.Clause;
import models.Literal;

import java.io.File;
import java.util.*;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class View {

    public static String INPUT_FILE_PATH = new File("")
            .getAbsolutePath()
            .concat("/");

    public static Map<Integer, Integer> resolvedClauseCombinations;

    public static void main(String... args){
        if(args.length < 1){
            System.err.println("ERROR: Incorrect number of arguments.");
            System.err.println("Please include a text file input.");
            System.exit(1);
        }

        String clauseFile = args[0];
        resolvedClauseCombinations = new HashMap<>();
        ClauseController.initializeClauses(INPUT_FILE_PATH.concat(clauseFile));
        startResolution();
    }

    private static void startResolution(){
        Clause startingClause = ClauseController.chooseRandomClause();

        //Negate starting clause and add to Clause list
        for(Clause c : ClauseController.negateClause(startingClause))
            ClauseController.addClause(c);

        //Loop until a contradiction is reached
        while(true) {
            //Check to see if contradiction exists
            List<Integer> contradiction = checkContradiction();
            if (contradiction != null) {
                //Clause and its negation exist in knowledge base
                System.out.println("DONE");
                ClauseController.printClauses();
                return;
            }

            //Choose a clause at random
            Clause randomClause = ClauseController.chooseRandomClause();
            //Choose another clause containing negation of at least one of the literals
            Clause resolvingClause = chooseResolvingClause(randomClause);
            if(resolvingClause != null){
                //Check if a combination of those two clause resolutions has already been done
                if(!checkResolvedClauseCombinations(randomClause.getNumber(), resolvingClause.getNumber())){
                    //If it hasn't resolve the two clauses and add resolution to clause list
                    Clause resolvedClause = ClauseController.resolveClauses(randomClause, resolvingClause);
                    //Add resolved clause combo to map
                    resolvedClauseCombinations.put(resolvedClause.getCombinedClauses().get(0),
                            resolvedClause.getCombinedClauses().get(1));
                    System.out.println(resolvedClause);
                    ClauseController.addClause(resolvedClause);
                }
                //If it has, continue the loop without resolving
            }
        }
    }

    /**
     * Checks all the clauses in the knowledge base to see if a contradiction exists.
     * Contradictions should only be checked if the clause has a single literal.
     * @return the indices of the contradicting clauses
     */
    private static List<Integer> checkContradiction(){
        //Iterate through all the clauses
        //Check all clauses after current clause for contradiction
            //2 cases (for both check only literals with single clauses)
                //1. Multiple literals in a clause
                    //Don't check for this, this is why we have resolution
                //2. Single literal in clause
                    //Check if negation of single literal exists in knowledge base

        //Loop only needs to check size - 1 clauses because inner loop checks last clause
        for(int i = 0; i < ClauseController.getClauses().size() - 1; i++){
            Clause c = ClauseController.getClauses().get(i);
            //If more than one literal in clause, don't check for contradictions
            if(c.getLiterals().size() <= 1) {
                //negatedClauses list should only contain one element so get first one
                Clause negated = ClauseController.negateClause(c).get(0);

                //Nested inner loop only needs to check from i to end
                boolean foundNegatedClause = false;
                int contradictionIndex = -1;
                for (int x = i; x < ClauseController.getClauses().size(); x++) {
                    if (negated.equals(ClauseController.getClauses().get(x))) {
                        foundNegatedClause = true;
                        contradictionIndex = ClauseController.getClauses().get(x).getNumber();
                    }
                }
                if (!foundNegatedClause)
                    return null;
                else {
                    //Return indices of contradictory statements
                    List<Integer> contradictionsList = new ArrayList<>();
                    contradictionsList.add(c.getNumber());
                    contradictionsList.add(contradictionIndex);

                    return contradictionsList;
                }
            }
        }
        return null;
    }

    /**
     * Finds a clause that contains at least one of the negated literals in the current clause
     * @param clause the clause to be resolved
     * @return clause containing at least one negated literal that will be used for resolution
     */
    public static Clause chooseResolvingClause(Clause clause){
        //Start at random clause in list
        //Iterate through list finding a clause with at least one negated literal from original clause
        //return that clause
        int range = ClauseController.getClauses().size();
        int index;

        //Select a starting index that doesn't equal the clause's index
        do {
            index = (int) ((Math.random()) * range);
        } while (index == clause.getNumber());

        while(index != clause.getNumber()){
            //Index reaches end of list, loop back around to beginning
            if(index == range)
                index = 0;
            else {
                Clause resolvingClause = ClauseController.getClauses().get(index);
                //Check if resolving clause contains a literal that is a negation of a literal in original clause
                for(Literal l : clause.getLiterals()){
                    for(Literal n : resolvingClause.getLiterals())
                        if(LiteralController.isNegation(l, n))
                            return resolvingClause;
                }
                index++;
            }
        }
        return null;
    }

    /**
     * Checks if a resolving clause combination already exists in the resolved combinations
     * @param clauseNumber1 the first clause number to check
     * @param clauseNumber2 the second clause number to check
     * @return whether the combination already has been resolved
     */
    public static boolean checkResolvedClauseCombinations(int clauseNumber1, int clauseNumber2){
        for(Map.Entry<Integer, Integer> entry : resolvedClauseCombinations.entrySet()){
            //Check if
            if(entry.getKey() == clauseNumber1 && entry.getValue() == clauseNumber2)
                return true;
            if(entry.getKey() == clauseNumber2 && entry.getValue() == clauseNumber1)
                return true;
        }

        return false;
    }
}
