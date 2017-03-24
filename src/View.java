import controllers.ClauseController;

import java.io.File;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class View {

    public static String INPUT_FILE_PATH = new File("")
            .getAbsolutePath()
            .concat("/");

    public static void main(String... args){
        if(args.length < 1){
            System.out.println("ERROR: Incorrect number of arguments.");
            System.out.println("Please include a text file input.");
            System.exit(1);
        }

        String clauseFile = args[0];
        ClauseController.initializeClauses(INPUT_FILE_PATH.concat(clauseFile));
    }
}
