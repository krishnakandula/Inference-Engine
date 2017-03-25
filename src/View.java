import controllers.ClauseController;
import models.Clause;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class View {

    public static String INPUT_FILE_PATH = new File("")
            .getAbsolutePath()
            .concat("/");

    public static void main(String... args){
        if(args.length < 1){
            System.err.println("ERROR: Incorrect number of arguments.");
            System.err.println("Please include a text file input.");
            System.exit(1);
        }

        String clauseFile = args[0];
        ClauseController.initializeClauses(INPUT_FILE_PATH.concat(clauseFile));
        startResolution();
    }

    private static void startResolution(){
        Clause startingClause = ClauseController.selectStartingClause();

        //Negate starting clause and add to Clause list
        for(Clause c : ClauseController.negateClause(startingClause))
            ClauseController.addClause(c);

        //Check to see if contradiction exists
        List<Integer> contradiction = checkContradiction();
        if(contradiction != null) {
            //Clause and its negation exist in knowledge base
            System.out.println("DONE");
        }
        //Choose a clause at random
        //Choose another clause containing negation of at least one of the literals
        //Resolve the two clauses and add resolution to clause list
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
                }
            }
        }
        return null;
    }
}
