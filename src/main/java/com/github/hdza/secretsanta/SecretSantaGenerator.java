package com.github.hdza.secretsanta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class SecretSantaGenerator {
    /*
        Approaching as a product for a family that likes long term planning. So i'm assuming this software will write seceret santa's for the next three years
        and output to a data base or in our case a text file.
     */
    private List<String> giverList = new ArrayList<>();
    private List<String> yearOneList = new ArrayList<>();
    private List<String> yearTwoList = new ArrayList<>();
    private List<String> yearThreeList = new ArrayList<>();

    SecretSantaGenerator() {
    }

    /*
        Part 3 note: The changes listed in part 3 were drastic enough that I felt like I had to do a redesign of this class to match what was asked of me.
        This section of code takes advantage of family numbers which are just the line number appended to each name in my inputlist.txt.
        If the family numbers of two names do not match up it's valid gift pairing. If not we iterate down the secretSantaReceiversList until we find
        a family number that does not match. We then swap it with whatever index we were currently at and continue on.

        It's  perfectly possible for there to be family combos that will just not work with the restrictions placed on us  in Part 3. Family A consisting of 3 people
        paired against family b
     */
    List<String> generateSecretSantaGiversList(List<String> secretSantaGiversList) throws TooFewForSSException, TooFewForPartTwo, NotEnoughFamily {

        if (secretSantaGiversList.size() < 2) {
            throw new TooFewForSSException("You don't have enough people to do a secret santa event.");
        } else if (secretSantaGiversList.size() < 4) {
            throw new TooFewForPartTwo("You don't have enough people to make it possible to not get a repeat within 3 years");
        }else if(!possibleToGenerateThreeYearList(secretSantaGiversList)){
            throw new NotEnoughFamily("You dont have enough familes to generate secret santa for three years without duplicates");
        }

        Collections.shuffle(secretSantaGiversList); //Shuffle the giver list.
        return secretSantaGiversList;
    }


    /*
        This function tests if we can actually generate secret santa lists for three  years with the family combos we have
        Treats the giver and reciever list as two stacks. If they can be paired off it pops them off
        Once we iterate through the whole list if we have anything left in the list we know we can't generate
        a list with the family combos we have.
     */
   private Boolean possibleToGenerateThreeYearList(List<String> secretSantaGiversList){
        List<String> localGiversList = new ArrayList<>(secretSantaGiversList);
        List<String> localRecieversList = new ArrayList<>(secretSantaGiversList);
        int familyIndexNumber = 0; //This constant is pulling the number I assigned to each family.

       for(int receiversIndex = 0; receiversIndex<localRecieversList.size(); receiversIndex++){
           if(localGiversList.get(0).charAt(familyIndexNumber) !=  localRecieversList.get(receiversIndex).charAt(familyIndexNumber)){
               localGiversList.remove(0);
               localRecieversList.remove(receiversIndex);
               receiversIndex=-1; //Start from the absolute top of the list again.
           }
       }

        if(!localGiversList.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    List<String> generateRecieversList(List<String> secretSantaGiversList, List<String> secretSantaReceiversList) {
        List<String> localSecretSantaReceiversList = new ArrayList<>(secretSantaReceiversList);//Make a local copy otherwise you mess up the previous list you created!
        Collections.shuffle(localSecretSantaReceiversList);

        int familyIndexNumber = 0; //This constant is pulling the number I assigned to each family.
        int recieverIndex; //Number will be set later on in the for loop.
        for (int index = 0; index < secretSantaGiversList.size(); index++) {
            char giversFamNumber = secretSantaGiversList.get(index).charAt(familyIndexNumber);

            if (giversFamNumber == localSecretSantaReceiversList.get(index).charAt(familyIndexNumber)) {
                if (index == secretSantaGiversList.size() - 1) {
                    recieverIndex = 0;
                } else {
                    recieverIndex = index + 1;
                }
                while (giversFamNumber == localSecretSantaReceiversList.get(recieverIndex).charAt(familyIndexNumber)) { //If the family numbers of the givers and recievers don't match up. We have a valid pairing! Otherwise keep checking.
                    if (recieverIndex == secretSantaGiversList.size() - 1) {
                        recieverIndex = 0; //If we have checked all indexes of the reciever list. Then start at the beginning.
                    } else {
                        recieverIndex += 1; //Don't want to do swaps on valid assignments( if any ). Do checks for possible switches to the right of whatever Index the giversList is on.
                    }
                }
                Collections.swap(localSecretSantaReceiversList, index, recieverIndex);
            }
        }
        return localSecretSantaReceiversList;
    }

    void verifyNoRepeatsOverThreeyears() {
        while(areListsEqual(yearTwoList, yearOneList)){ //Year two should just have to be different from year one. Year one was here first, it shouldn't have to change. Year two needs to be different from year one.
            yearTwoList = this.generateRecieversList(getGiverList(), yearTwoList);
        }

        while(areListsEqual(yearThreeList,yearOneList) || areListsEqual(yearThreeList,yearTwoList)){ //Year three needs to be different from year one and two. It was here last so it's subject to more checks to look for uniqueness.
            yearThreeList = generateRecieversList(giverList, yearThreeList);
        }
    }

   private boolean areListsEqual(List<String> secretSantaLeft, List<String> secretSantaRight){
        for(int index = 0; index<secretSantaLeft.size(); index++){
            if(secretSantaLeft.get(index).equals(secretSantaRight.get(index))){
                return true;
            }
        }
        return false;
    }

     List<String> getGiverList() {
        return giverList;
    }

     void setGiverList(List<String> giverList) {
        this.giverList = giverList;
    }

     List<String> getYearOneList() {
        return yearOneList;
    }

     void setYearOneList(List<String> yearOneList) {
        this.yearOneList = yearOneList;
    }

     List<String> getYearTwoList() {
        return yearTwoList;
    }

     void setYearTwoList(List<String> yearTwoList) {
        this.yearTwoList = yearTwoList;
    }

     List<String> getYearThreeList() {
        return yearThreeList;
    }

     void setYearThreeList(List<String> yearThreeList) {
        this.yearThreeList = yearThreeList;
    }
}
