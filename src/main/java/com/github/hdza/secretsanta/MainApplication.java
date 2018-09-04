package com.github.hdza.secretsanta;

import com.github.hdza.secretsanta.utils.OutputResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainApplication {
    private String filename;

    MainApplication(String textFile){ //An entry point so I can run junit tests against the main. Specifically for invalid text file case.
        filename = textFile;
        String[] args = {filename};
        MainApplication.main(args);
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(MainApplication.class.getName()); //Create a logger to output error messages.
        List<String> secretSantaList = new ArrayList<>();
        String filename;

        if (args.length == 0) { //if args argument is empty, provide a default. This is so I can run junit tests with different inputs.
            filename = "inputlist.txt";
        } else {
            filename = Arrays.toString(args);
        }

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {  //Try with statement closes the stream for us sooner than the GC would. Stream also implements AutoCloseable so OS resources aren't tied up.
            secretSantaList = stream.collect(Collectors.toList()); //Parse the text file line by line, then convert it to a List of type String.
        } catch (IOException e) { //Make sure we have code to catch the instance in which we don't find our inputlist.txt
            logger.log(Level.SEVERE, "The textfile input you specified was not found. Ensure that it exists under the main directory of the project", e);
        }

        SecretSantaGenerator christmasList = new SecretSantaGenerator();

        /*
            We can pretend that these Lists for year one two and three are actually just calls to a "database" keeping track of who was giving gifts to who.
         */
        List<String> yearOneList = new ArrayList<>();
        List<String> yearTwoList = new ArrayList<>();
        List<String> yearThreeList = new ArrayList<>();
        try {
            secretSantaList = christmasList.generateSecretSantaList(secretSantaList); //Generate the initial secret santa "seed" list. This will be the gift giver section of the gift giver/receiver pair.
            yearOneList = christmasList.generateGiftReceiverList(secretSantaList);//Generates the secret santa receiver parings for the gift giver using the secretSantaList as a seed.
            yearTwoList = christmasList.generateGiftReceiverList(yearOneList);//Generates the secret santa receiver parings using the yearOneList as a seed.
            yearThreeList = christmasList.generateGiftReceiverList(yearTwoList);//Generates the secret santa receiver parings using the yearTwoList as a seed.
        } catch (TooFewForSSException | TooFewForPartTwo e) { //Since we can have too few people in the case of repeat pairs throw a new exception.
            logger.log(Level.SEVERE, "There was an issue with the data format in the generateSecretSantaList function.", e.getMessage());
        }

        OutputResult outputToFile = new OutputResult();
        outputToFile.outputResultToFile(secretSantaList, yearOneList, yearTwoList, yearThreeList);

    }
}
 