package com.github.hdza.secretsanta;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class MainApplicationTest {
    @Test(expected = FileNotFoundException.class)
    public void inputFileNotFoundTest(){ //Test to make sure that if you give an invalid or non-existant textfile input you'll get an exception back.
        MainApplication testMain = new MainApplication("fakeTextFile.txt");
    }
}
