/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class View {
    public static void main(String... args){
        if(args.length < 1){
            System.out.println("ERROR: Incorrect number of arguments.");
            System.out.println("Please include a text file input.");
            System.exit(1);
        }

        String clauseFile = args[0];
    }

    private static void initializeClauses(String clauseFile){
        
    }
}
