package com.github.hdza.secretsanta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class SecretSantaGenerator {
    SecretSantaGenerator() {
    }

    /*
        Part 3 note: The changes listed in part 3 were drastic enough that I felt like I had to do a redesign of this class to match what was asked of me.
        This section of code takes advantage of family numbers which are just the line number appended to each name in my inputlist.txt.
        If the family numbers of two names do not match up it's valid gift pairing. If not we iterate down the secretSantaReceiversList until we find
        a family number that does not match. We then swap it with whatever index we were currently at and continue on.

        It's  perfectly possible for there to be family combos that will just not work with the restrictions placed on us  in Part 3. Family A consisting of 3 people
        cannot have every member of that family give a gift
     */
    List<String> generateSecretSantaList(List<String> secretSantaGiversList, List<String> secretSantaReceiversList) throws TooFewForSSException, TooFewForPartTwo {

        List<String> localSecretSantaGiversList = new ArrayList<>(secretSantaGiversList); //Create local variables to copy to since I keep getting weird errors. Where changes to third year list bubble up to first year and second year list.
        List<String> localSecretSantaReceiversList = new ArrayList<>(secretSantaGiversList);

        Collections.shuffle(secretSantaGiversList); //Shuffle the two lists. Much like in part 1 I don't think people would actually notice but it prevents undue influence from whoever controls the program.
        if (secretSantaGiversList.size() < 2) {
            throw new TooFewForSSException("You don't have enough people to do a secret santa event.");
        } else if (secretSantaGiversList.size() < 4) {
            throw new TooFewForPartTwo("You don't have enough people to make it possible to not get a repeat within 3 years");
        }


        return generateRecieversList(localSecretSantaGiversList, localSecretSantaReceiversList);
    }

    List<String> generateRecieversList(List<String> localSecretSantaGiversList, List<String> localSecretSantaReceiversList) {
        Collections.shuffle(localSecretSantaReceiversList);
        int familyIndexNumber = 0; //This constant is pulling the number I assigned to each family.
        int recieverIndex; //Number will be set later on in the for loop.
        for (int index = 0; index < localSecretSantaGiversList.size(); index++) {
            char giversFamNumber = localSecretSantaGiversList.get(index).charAt(familyIndexNumber);

            if (giversFamNumber == localSecretSantaReceiversList.get(index).charAt(familyIndexNumber)) {
                if (index == localSecretSantaGiversList.size() - 1) {
                    recieverIndex = 0;
                } else {
                    recieverIndex = index + 1;
                }
                while (giversFamNumber == localSecretSantaReceiversList.get(recieverIndex).charAt(familyIndexNumber)) { //If the family numbers of the givers and recievers don't match up. We have a valid pairing! Otherwise keep checking.
                    if (recieverIndex == localSecretSantaGiversList.size() - 1) {
                        recieverIndex = 0; //If we have checked all indexes of the reciever list. Then start at the beginning.
                    } else {
                        recieverIndex += 1; //Don't want to do swaps on valid assignments( if any ). Do checks for possible switches to the right of whatever Index the giversList is on.
                    }
                }
                Collections.swap(localSecretSantaReceiversList, index, recieverIndex);
            }
        }
        return localSecretSantaGiversList;
    }


    void verifyNoRepeatsOverThreeyears(List<String> secretSantaGiversList, List<String> yearOneList, List<String> yearTwoList, List<String> yearThreeList) throws TooFewForPartTwo, TooFewForSSException{
        while(yearTwoList.equals(yearOneList)){
            yearTwoList = this.generateSecretSantaList(secretSantaGiversList, yearOneList);
        }

        while(yearTwoList.equals(yearThreeList)){
            yearTwoList = this.generateSecretSantaList(secretSantaGiversList, yearThreeList);
        }

        while(yearThreeList.equals(yearOneList)){
            yearThreeList = this.generateSecretSantaList(secretSantaGiversList, yearOneList);
        }

        while(yearThreeList.equals(yearTwoList)){
            yearThreeList = this.generateSecretSantaList(secretSantaGiversList, yearTwoList);
        }
    }
}
