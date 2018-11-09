package test;

import org.junit.Test;

import risk.controller.FileController;

import static org.junit.Assert.*;

public class FileControllerTest {
    /**
     * test correct map
     */
    @Test
    public void verifyMapFile1(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String fileRightMap = commonPath + "Montreal.map";
        assertTrue(fileController.verifyMapFile(fileRightMap));
    }
    /**
     * test error map
     * error place : continent=2a   2a is not a number
     */
    @Test
    public void verifyMapFile2(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap1 = commonPath + "error1.map";
        assertFalse(fileController.verifyMapFile(errorMap1));
    }
    /**
     * test error map
     * error place : continent==4
     */
    @Test
    public void verifyMapFile3(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap2 = commonPath + "error2.map";
        assertFalse(fileController.verifyMapFile(errorMap2));
    }
    /**
     * test error map
     * the name of continent in node is different in Continents
     */
    @Test
    public void verifyMapFile4(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap3 = commonPath + "error3.map";
        assertFalse(fileController.verifyMapFile(errorMap3));
    }
    /**
     * test error map
     * only have[Continents] information in file
     */
    @Test
    public void verifyMapFile5(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap4 = commonPath + "error4.map";
        assertFalse(fileController.verifyMapFile(errorMap4));
    }
    /**
     * test error map
     * it's an empty file
     */
    @Test
    public void verifyMapFile6(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String errorMap5 = commonPath + "error5.map";
        assertFalse(fileController.verifyMapFile(errorMap5));
    }
    /**
     * test correct map
     * somewhere different : a1,200,300,av,de,
     */
    @Test
    public void verifyMapFile7(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String correctMap1 = commonPath + "correctFile1.map";
        assertTrue(fileController.verifyMapFile(correctMap1));
    }
    /**
     * test correct map
     * the continent name in [Continents] and [Territories] has extra space
     */
    @Test
    public void verifyMapFile8(){
        FileController fileController = new FileController();
        String commonPath = "src/test/";
        String correctMap2 = commonPath + "correctFile2.map";
        assertTrue(fileController.verifyMapFile(correctMap2));
    }
}