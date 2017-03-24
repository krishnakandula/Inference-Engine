package controllers;

import java.io.File;

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

}