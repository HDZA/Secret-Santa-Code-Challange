package com.github.hdza.secretsanta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class SecretSantaGenerator {
    SecretSantaGenerator() {}

    /*
        I'm afraid we will have to use...MATH.
        Attacking this problem as a set theory question. So what we really want
        is a derangement of a set. This function is generating a cheap to produce
        derangement in O(n) time by generating a second set where all the elements are shifted one to the right
        and the tail end segment is placed in the 0 index. This guarantees everyone
        gets somebody to give a gift to and it's not themselves.
     */
    List<String> generateSecretSantaList(List<String> secretSantaList) throws NumberOfPeopleException {
        if(secretSantaList.size()<2){ //Make sure there are at least two people involved in this secret santa. If not throw an exception.
            throw new NumberOfPeopleException("You need at least two people in order to do Secret Santa!"); //Throw excpetion with message on what went wrong.
        }

        List<String> returnList = new ArrayList<>();
        Collections.shuffle(secretSantaList); //Shuffles up the input list. Secret santa people wouldn't notice otherwise but it insures that the list inputter can't influences who gets a gift from who.
        for (int index = 0; index < secretSantaList.size(); index++) {
            if (index != secretSantaList.size() - 1) { //Process up until right before we hit the last index.
                returnList.add(secretSantaList.get(index + 1));
            } else                                     //This is the last index, it needs to go in the secretSantaList[0] spot in order to complete the derangement.
                returnList.add(secretSantaList.get(0));
        }
        return returnList;
    }





}
