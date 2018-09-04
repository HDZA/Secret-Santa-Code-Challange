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

        In Part 2 of this code this specific generateSecretSantaList method exists to essentially just shuffle the List on the first iteration and do some light checking to make sure we have enough people to satisfy the part 2 requirements.
     */
    List<String> generateSecretSantaList(List<String> secretSantaList) throws TooFewForSSException, TooFewForPartTwo {
        if (secretSantaList.size() < 2) { //Make sure there are at least two people involved in this secret santa. If not throw an exception.
            throw new TooFewForSSException("You need at least two people in order to do Secret Santa!"); //Throw excpetion with message on what went wrong.
        } else if (secretSantaList.size() < 4) { //Anything less than 4 people can't satisfy part 2's requirements. With 3 people each person can only give to at most two people and over a 3 year period this assures a repeat.
            throw new TooFewForPartTwo("You need at least 4 people if you don't want to have repeats of the same gift giver to gift reciever pairs");
        }
            Collections.shuffle(secretSantaList); //Shuffles up the input list. Secret santa people wouldn't notice otherwise but it insures that the list inputter can't influences who gets a gift from who.
            return generateGiftReceiverList(secretSantaList);
        }


     /*
        This code was exported from the original part 1 location down to it's own method. It exists  to generate a derangement of a set based on the initial secretSantaList.
        The list itself has been shuffled in the above method. The reason we want to do this is because we're generating derangements with the initial shuffled List as a "seed".
        This method will run in O(n) time which is great but the downside is it's flawed to N-1 valid derangements. But that works out fine for us given the restrictions of part 2.

      */
     List<String> generateGiftReceiverList(List<String> secretSantaList){
        List<String> returnList = new ArrayList<>();
        for (int index = 0; index < secretSantaList.size(); index++) {
            if (index != secretSantaList.size() - 1) { //Process up until right before we hit the last index.
                returnList.add(secretSantaList.get(index + 1));
            } else                                     //This is the last index, it needs to go in the secretSantaList[0] spot in order to complete the derangement.
                returnList.add(secretSantaList.get(0));
        }
        return returnList;
    }
}