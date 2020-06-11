package com.example.eyeballinapp.SpeechStuff;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechParser {

    String mCommand;
    String mDestination;

    public SpeechParser() {
        mCommand = "";
    }

    //find --> word is in sentence

    //get the sentence and look for keywords: yes, no, take me, room, stop, setup, commands
    /*Pattern p = Pattern.compile("^.*(\\byes\\b|\\bno\\b).*$");
        Matcher m = p.matcher("yes no");
        boolean b = m.matches(); --> true*/
    public String getSpeechCommand(String sentence) {
        Pattern p = Pattern.compile("^.*(\\btake me to\\b|\\broom\\b|\\bgo to\\b|\\bnavigate to\\b).*$");
        Matcher m = p.matcher(sentence);
        if(m.matches()) {
            // TODO: 12/4/2019 Parse the string that isn't part of the pattern p instead of the digit
            // this is really stupid but im sleepy
            //splits the string with the last match from the pattern about, trims the string and sets
            //that as the destination. A problem would be the capital letters in the nodes that dont match
            //if we want to go to a non numbered room location
            mDestination = sentence.split(m.group(m.groupCount()))[1].trim();
            if(mDestination != null) {
                return "repeat";
            }
            else
                return "room number";
//            if(m2.find()) {
//                mDestination = m2.group(m2.groupCount());
//                return "repeat";
//            }
//            else
//                return "room number";
        }
        else {
            Pattern p2 = Pattern.compile("^.*(\\byes\\b|\\bno\\b|\\bset up\\b|\\bcommands\\b|\\bstop\\b).*$");
            Matcher m2 = p2.matcher(sentence);
            if(m2.matches()) {
                switch (m2.group(1)) {
                    case "yes": if(mDestination != null)
                        return "navigate";
                    else
                        return "ask again";
                    default: return m2.group(1);
                }
            }
            else
                return "ask again";
            //get occurrence of whatever word
        }
        //if command is navigate, use set the return value of getDestination to the room number or something.
    }

    //we could use the global variable here....
    public String getDestination() {
        return mDestination;
    }

    public void clear() {
        mDestination = "";
    }

    private Pattern getPattern(String word) {
        return Pattern.compile("^.*(\\b" + word + "\\b).*$");
    }
}
