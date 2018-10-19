package test;

import org.junit.Test;
import risk.action.FileController;

import java.io.File;

import static org.junit.Assert.*;

public class FileControllerTest {

    @Test
    public void verifyMapFile() {
        FileController fileController = new FileController();
        String commonPath = "src/test/";

        String fileRightMap = commonPath + "Montreal.map";
        assertTrue(fileController.verifyMapFile(fileRightMap));
        System.out.println("the result of verifying test file : " + fileRightMap + " is correct");

        String errorMap1 = commonPath + "error1.map";      // error place : continent=2a
        assertFalse(fileController.verifyMapFile(errorMap1));
        System.out.println("the result of verifying test file : " + errorMap1 + " is correct");

        String errorMap2 = commonPath + "error2.map";     //error place : continent==4
        assertFalse(fileController.verifyMapFile(errorMap2));
        System.out.println("the result of verifying test file : " + errorMap2 + " is correct");

        String errorMap3 = commonPath + "error3.map";     // the name of continent in node is different in Continents
        assertFalse(fileController.verifyMapFile(errorMap3));
        System.out.println("the result of verifying test file : " + errorMap3 + " is correct");

        String errorMap4 = commonPath + "error4.map";     // only have[Continents] information in file
        assertFalse(fileController.verifyMapFile(errorMap4));
        System.out.println("the result of verifying test file : " + errorMap4 + " is correct");

        String errorMap5 = commonPath + "error5.map";     // it's empty file
        assertFalse(fileController.verifyMapFile(errorMap5));
        System.out.println("the result of verifying test file : " + errorMap5 + " is correct");

        String correctMap1 = commonPath + "correctFile1.map";     //somewhere different : a1,200,300,av,de,
        assertTrue(fileController.verifyMapFile(correctMap1));
        System.out.println("the result of verifying test file : " + correctMap1 + " is correct");

        String correctMap2 = commonPath + "correctFile2.map";     //the continent name in [Continents] and [Territories]
        assertTrue(fileController.verifyMapFile(correctMap2));
        System.out.println("the result of verifying test file : " + correctMap2 + " is correct");
    }
}