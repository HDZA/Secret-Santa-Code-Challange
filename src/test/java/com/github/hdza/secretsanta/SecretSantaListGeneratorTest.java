package com.github.hdza.secretsanta;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SecretSantaListGeneratorTest {

    //Part 2 Note: I refactored the custom exception we throw to be more specific. In this case we're providing a list with less than 2 people. Which is not enough to even do a SS under part 1 conditions.
    @Test(expected = TooFewForSSException.class) //We expect this very specific exception to be thrown.
    public void zeroParticipantTest() throws Exception { //Test the case where we have less than 2 people
        List<String> participantList = new ArrayList<>(); //Create an empty list with nobody in it.
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaGiversList(participantList);
    }

    //Part 2 Note: This test just looked at the case where there was at least one person to make sure nothing odd happens with processing if there's only one guy.  Also throws TooFewForSSException.
    @Test(expected = TooFewForSSException.class) //Similar exception as zeroParticipantTest but with only one guy.
    public void oneParticipantTest() throws TooFewForSSException, TooFewForPartTwo, NotEnoughFamily { //Test the case where we have less than 2 people but at least one.
        List<String> participantList = new ArrayList<>(); //Create an empty list with at least one guy in it.
        participantList.add("Jim");
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaGiversList(participantList);
    }


    @Test(expected = TooFewForPartTwo.class) //Similar exception as zeroParticipantTest but with only one guy.
    public void threeParticipantTest() throws TooFewForSSException, TooFewForPartTwo, NotEnoughFamily  { //Test the case where we have less than 2 people but at least one.
        List<String> participantList = new ArrayList<>(); //Create an empty list with at least one guy in it.
        participantList.add("Jim");
        participantList.add("Spock");
        participantList.add("Bones");
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.generateSecretSantaGiversList(participantList);
    }


    /*
        There exists family combos that make it impossible to do a secret santa
        without it being required for close family members to buy gifts for each other.
        This tests for that specific error and the code's ability to detect it.
     */
    @Test(expected = NotEnoughFamily.class)
    public void testIfEnoughFamilyExists() throws NotEnoughFamily,TooFewForPartTwo, TooFewForSSException{
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        List<String> testParticipantList = new ArrayList<>();
        testParticipantList.add("0 James");
        testParticipantList.add("0 Kirk");
        testParticipantList.add("0 Spock");
        testParticipantList.add("1 Aldo");

        testSanta.generateSecretSantaGiversList(testParticipantList);

    }

    @Test
    public void manyParticipantTest() throws TooFewForSSException, TooFewForPartTwo, NotEnoughFamily{ //This test covers the happy path where we have enough people to do a secret santa. We don't actually expect an error but if we do the test will fail as it should.
        List<String> participantList = new ArrayList<>();
        participantList.add("0 Kirk");
        participantList.add("0 Spock");
        participantList.add("0 Sulu");
        participantList.add("1 Bones");
        participantList.add("1 Chekov");
        participantList.add("1 Aldo");
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.setGiverList(testSanta.generateSecretSantaGiversList(participantList));
        testSanta.setYearOneList(testSanta.generateRecieversList(testSanta.getGiverList(),participantList));


        assertNotEquals("Lists match up index by index which means people are giving gifts to themselves.",testSanta.getGiverList(), testSanta.getYearOneList()); //Make sure that the two Lists are a valid derangement of the set.
    }

    @Test
    public void manyParticipantsOverThreeYearsTest() throws TooFewForSSException, TooFewForPartTwo, NotEnoughFamily{ //This test covers the Part 2 requirement that nobody get the same secret santa over the course of three years.
        List<String> participantList = new ArrayList<>();
        participantList.add("Kirk");
        participantList.add("Spock");
        participantList.add("Bones");
        participantList.add("Chekov");
        Logger logger = Logger.getLogger(SecretSantaListGeneratorTest.class.getName()); //Create a logger to output error messages.
        SecretSantaGenerator testSanta = new SecretSantaGenerator();
        testSanta.setGiverList(testSanta.generateSecretSantaGiversList(participantList));
        testSanta.setYearOneList(testSanta.generateRecieversList(testSanta.getGiverList(),participantList));
        testSanta.setYearTwoList(testSanta.generateRecieversList(testSanta.getGiverList(), testSanta.getYearOneList()));
        testSanta.setYearThreeList(testSanta.generateRecieversList(testSanta.getGiverList(), testSanta.getYearTwoList()));

        /*
            I don't usually like having more than one assert in a Unit test but I think in this instance it's OK but not good. The nature of this specific test requires that you look at 3 lists touched
            by the object. I could redesign the classes to return a list of a list so we could check it all in one line but at the end of the day you're still examining 3 Lists against one List.
            Brushing things under the carpet like that isn't good code style either. @Before wont help either since it makes no sense to run this specific function everytime before every test. That causes it's own host of issues.
         */

        assertNotEquals("Lists match up index by index in year one which means people are giving gifts to themselves.",testSanta.getGiverList(), testSanta.getYearOneList()); //Make sure that the two Lists are a valid derangement of the set.
        assertNotEquals("Lists match up index by index in year two which means people are giving gifts to themselves.",testSanta.getGiverList(), testSanta.getYearTwoList()); //Make sure that the two Lists are a valid derangement of the set.
        assertNotEquals("Lists match up index by index in year three which means people are giving gifts to themselves.",testSanta.getGiverList(), testSanta.getYearThreeList()); //Make sure that the two Lists are a valid derangement of the set.
    }





}