package controllers;

import models.Clause;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class ClauseController {
    private static ClauseController controller;
    private static List<Clause> clauses;

    private ClauseController(){
    }

    public static ClauseController getClauseController(){
        if(controller == null)
            controller = new ClauseController();
        return controller;
    }

    /**
     * Reads in clauses from input file and initializes them
     * @param inputFilePath
     */
    public static void initializeClauses(String inputFilePath){
        FileReader fileReader = null;
        BufferedReader reader = null;

        try{
            fileReader = new FileReader(inputFilePath);
            reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Clause> getClauses(){
        return clauses;
    }
}
