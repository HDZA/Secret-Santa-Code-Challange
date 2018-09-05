package com.github.hdza.secretsanta;

import com.github.hdza.secretsanta.utils.OutputResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {
    private String filename;

    MainApplication(String textFile){ //An entry point so I can run junit tests against the main. Specifically for invalid text file case.
        filename = textFile;
        String[] args = {filename};
        MainApplication.main(args);
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(MainApplication.class.getName()); //Create a logger to output error messages.
        List<String> secretSantaGiversList = new ArrayList<>();
        List<String> inputList =  new ArrayList<>(); //List that the input file gets parsed into.
        String filename; //Empty string to hold the filename. It can be the default or a user provided one.

        if (args.length == 0) { //if args argument is empty, provide a default. This is so I can run junit tests with different inputs.
            filename = "inputlist.txt";
        } else {
            filename = Arrays.toString(args);
        }

        try{
             inputList = Files.readAllLines(Paths.get(filename)); //Read in the file input. Put it in an inputList.
        } catch (IOException e) { //Make sure we have code to catch the instance in which we don't find our inputlist.txt
            logger.log(Level.SEVERE, "The textfile input you specified was not found. Ensure that it exists under the main directory of the project", e);
        }

        for(int index = 0; index< inputList.size(); index++){
            StringTokenizer token = new StringTokenizer(inputList.get(index));
            while(token.hasMoreTokens()){
                secretSantaGiversList.add(index + " " + token.nextToken()); //Grab the line number the family was on plus the actual name of the family member.
            }
        }

        List<String> secretSantaReceiversList = new ArrayList<>(secretSantaGiversList); //Make a copy of the givers list and put it into receivers.
        SecretSantaGenerator secretSantaList = new SecretSantaGenerator();

        try {
            secretSantaList.setGiverList(secretSantaList.generateSecretSantaGiversList(secretSantaGiversList));
            secretSantaList.setYearOneList(secretSantaList.generateRecieversList(secretSantaList.getGiverList(),secretSantaReceiversList));
            secretSantaList.setYearTwoList(secretSantaList.generateRecieversList(secretSantaList.getGiverList(),secretSantaList.getYearOneList()));
            secretSantaList.setYearThreeList(secretSantaList.generateRecieversList(secretSantaList.getGiverList(),secretSantaList.getYearTwoList()));
            secretSantaList.verifyNoRepeatsOverThreeyears(); //Make sure that the lists are different from year to year. If not generate new ones.

        } catch (NotEnoughFamily | TooFewForSSException | TooFewForPartTwo e) { //Since we can have too few people in the case of repeat pairs throw a new exception.
            logger.log(Level.SEVERE, "There was an issue with the data format given in the inputlist.txt. ", e);
        }

        OutputResult outputter = new OutputResult();
        outputter.outputResultToFile(secretSantaGiversList,secretSantaList.getYearOneList(),secretSantaList.getYearTwoList(), secretSantaList.getYearThreeList());
    }
}
