package test;

import org.junit.Test;

import risk.controller.FileController;

import static org.junit.Assert.*;

public class FileControllerTest {

    @Test
    public void verifyMapFile1(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String fileRightMap = commonPath + "Montreal.map";
        assertTrue(fileController.verifyMapFile(fileRightMap));
    }
    @Test
    public void verifyMapFile2(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap1 = commonPath + "error1.map";      // error place : continent=2a
        assertFalse(fileController.verifyMapFile(errorMap1));
    }
    @Test
    public void verifyMapFile3(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap2 = commonPath + "error2.map";     //error place : continent==4
        assertFalse(fileController.verifyMapFile(errorMap2));
    }
    @Test
    public void verifyMapFile4(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap3 = commonPath + "error3.map";     // the name of continent in node is different in Continents
        assertFalse(fileController.verifyMapFile(errorMap3));
    }
    @Test
    public void verifyMapFile5(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap4 = commonPath + "error4.map";     // only have[Continents] information in file
        assertFalse(fileController.verifyMapFile(errorMap4));
    }
    @Test
    public void verifyMapFile6(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap5 = commonPath + "error5.map";     // it's empty file
        assertFalse(fileController.verifyMapFile(errorMap5));
    }
    @Test
    public void verifyMapFile7(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String correctMap1 = commonPath + "correctFile1.map";     //somewhere different : a1,200,300,av,de,
        assertTrue(fileController.verifyMapFile(correctMap1));
    }
    @Test
    public void verifyMapFile8(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String correctMap2 = commonPath + "correctFile2.map";     //the continent name in [Continents] and [Territories]
        assertTrue(fileController.verifyMapFile(correctMap2));
    }
}