package com.github.hdza.secretsanta;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecretSantaListGeneratorTest {

    @Test(expected = NumberOfPeopleException.class) //We expect this very specific exception to be thrown.
    public void zeroParticipantTest() throws Exception { //Test the case where we have less than 2 people
        List<String> participantList = new ArrayList<>(); //Create an empty list with nobody in it.
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaList(participantList);
    }

    @Test(expected = NumberOfPeopleException.class) //Similar exception as zeroParticipantTest but with only one guy.
    public void oneParticipantTest() throws NumberOfPeopleException { //Test the case where we have less than 2 people but at least one.
        List<String> participantList = new ArrayList<>(); //Create an empty list with at least one guy in it.
        participantList.add("Jim");
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaList(participantList);
    }

    @Test
    public void manyParticipantTest() throws Exception{ //This test covers the happy path where we have enough people to do a secret santa. We don't actually expect an error but if we do the test will fail as it should.
        List<String> participantList = new ArrayList<>();
        participantList.add("Kirk");
        participantList.add("Spock");
        participantList.add("Bones");
        Logger logger = Logger.getLogger(SecretSantaListGeneratorTest.class.getName()); //Create a logger to output error messages.
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        List<String> returnListed = testSanta.generateSecretSantaList(participantList);

        assertNotEquals("Lists match up index by index which means people are giving gifts to themselves.",participantList, returnListed);

    }
}
