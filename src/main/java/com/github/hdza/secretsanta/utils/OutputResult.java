package com.github.hdza.secretsanta.utils;

import com.github.hdza.secretsanta.MainApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputResult {
         /*
            this section of code handles  outputting the secret santa gift giver/reciever pairings. This is purely for the benefit of the person reviewing this code.
            I'd feel bad making you have to read from a console using lowtech System.out.println(). It also cleans up the MainApplication. Which is starting to get a little too hairy
            with all the work we're doing with lists in there. It's also migrated to it's own little utils package so it's seperate from the main classes doing the work.

            Given my time limitations I can't create unit tests that handle this section of code. If I had more time i'd create a mocking framework around this object.
            That would look like an interface that would use this class. The tests would be for Save() which would write things to disc and verify that a file persists to the disc and another test
            that verifies that when I write things to a stream it actually looks like what I want it to look like in memory. I think Rachio is more concerned with the unit tests surrounding the actual answer
            to their challenge anyway. So I think we'll be ok.
         */

   public void outputResultToFile(List<String> secretSantaList, List<String> yearOneList, List<String> yearTwoList, List<String> yearThreeList){
        Logger logger = Logger.getLogger(MainApplication.class.getName()); //Create a logger to output error messages.
        String outputFileName = "output.txt";
        try(PrintWriter outStream = new PrintWriter(outputFileName)){
            outStream.println("YEAR ONE:");
            String willGiveGiftTo = " will give a gift to "; //Use this instead of just repeating the same string.
            for(int index = 0; index<secretSantaList.size(); index++){
                outStream.println(secretSantaList.get(index)+ willGiveGiftTo +yearOneList.get(index)); //Simple output. Returns a list of who is giving a gift to who.
            }
            outStream.println("YEAR TWO:");
            for(int index = 0; index<secretSantaList.size(); index++){
                outStream.println(secretSantaList.get(index)+ willGiveGiftTo +yearTwoList.get(index)); //Simple output. Returns a list of who is giving a gift to who.
            }
            outStream.println("YEAR THREE:");
            for(int index = 0; index<secretSantaList.size(); index++){
                outStream.println(secretSantaList.get(index)+ willGiveGiftTo +yearThreeList.get(index)); //Simple output. Returns a list of who is giving a gift to who.
            }
        }catch(IOException e){ //This can actually catch a fileNotFound exception too. But that's a subclass of the IOException. So we're ok there.
            logger.log(Level.SEVERE,"Something is broken about your output.txt code.", e.getMessage());
        }
    }
}
