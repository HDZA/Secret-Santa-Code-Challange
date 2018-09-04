package com.github.hdza.secretsanta;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecretSantaListGeneratorTest {

    @Test(expected = NumberOfPeopleException.class)
    public void zeroParticipantTest() throws Exception { //Test the case where we have less than 2 people
        List<String> participantList = new ArrayList<>(); //Create an empty list with nobody in it.
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaList(participantList);
    }

    @Test(expected = NumberOfPeopleException.class)
    public void oneParticipantTest() throws NumberOfPeopleException { //Test the case where we have less than 2 people but at least one.
        List<String> participantList = new ArrayList<>(); //Create an empty list with at least one guy in it.
        participantList.add("Jim");
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaList(participantList);
    }

    @Test
    public void manyParticipantTest(){ //This test covers the happy path where we have enough people to do a secret santa.
        List<String> participantList = new ArrayList<>();
        participantList.add("Kirk");
        participantList.add("Spock");
        participantList.add("Bones");
        Logger logger = Logger.getLogger(SecretSantaListGeneratorTest.class.getName()); //Create a logger to output error messages.
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        List<String> returnListed = null;
        try {
            returnListed = testSanta.generateSecretSantaList(participantList); //Generates the secret santa parings.
        }catch(NumberOfPeopleException e){
            logger.log(Level.SEVERE,"There was an issue with the data format in the generateSecretSantaList function. This message is from the test case.", e);
        }

        assertNotEquals("Lists match up index by index which means people are giving gifts to themselves.",participantList, returnListed);

    }
}
