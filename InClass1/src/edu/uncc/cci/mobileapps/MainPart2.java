package edu.uncc.cci.mobileapps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * a. Assignment #1.
 * b. File Name : Group3_InClass01 (MainPart2.java)
 * c. Full name of the student : Chandan Mannem,Mahalavanya Sriram
 */


public class MainPart2 {
    /*
    * Question 2:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - The goal is to count the number of users living each state.
    * - Print out the list of State, Count order in ascending order by count.
    * */

    public static void main(String[] args) {

        //example on how to access the Data.users array.
        Util uObj = new Util();
        ArrayList<User> userArray = uObj.parseUsertoArray(Data.users);

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (User obj : userArray) {
            String state = obj.getState();
            map.put(state, map.getOrDefault(state,0) + 1);
        }

        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
        //System.out.println(map.toString());
    }
}