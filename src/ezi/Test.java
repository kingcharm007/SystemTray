/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chandan
 */
public class Test {
    public static void main(String[] arg)  {
    HashMap<String, HashMap<String, String>> finalArray = new HashMap<String, HashMap<String, String>>();

    String[] langArray = {"en","fr","de","no","es"};

    //Initialize each language key ahead of time
    for(String lang : langArray) { // foreach lang in langArray
      if (!finalArray.containsKey(lang)) {
        finalArray.put(lang, new HashMap<String, String>());
      }
    }

    //loop one - assign names
    for(String lang : langArray) {
      String theName = lang + " name"; //go get the name from somewhere
      finalArray.get(lang).put("name", theName);
    }

    //loop two - assign description
    for(String lang : langArray) {
      String theDesc = lang + " description"; //go get the description from somewhere
      finalArray.get(lang).put("desc", theDesc);
    }

    //loop three - assign keywords
    for(String lang : langArray) {
      String theKeys = lang + " keywords"; //go get the keywords from somewhere
      finalArray.get(lang).put("keys", theKeys);
    }

    //display output
    for(String lang : langArray) {
      System.out.println("LANGUAGE: " + lang);
      System.out.println(finalArray.get(lang).get("name"));
      System.out.println(finalArray.get(lang).get("desc"));
      System.out.println(finalArray.get(lang).get("keys"));
    }

    //example to retrieve/get values
    String english_name = finalArray.get("en").get("name");
    String french_desc = finalArray.get("fr").get("desc");

    }
}
