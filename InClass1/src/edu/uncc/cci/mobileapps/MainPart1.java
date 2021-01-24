package edu.uncc.cci.mobileapps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * a. Assignment #1.
 * b. File Name : Group3_InClass01 (MainPart1.java)
 * c. Full name of the student : Chandan Mannem,Mahalavanya Sriram
 */

public class MainPart1 {
    /*
     * Question 1:
     * - In this question you will use the Data.users array that includes
     * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
     * - Create a User class that should parse all the parameters for each user.
     * - Insert each of the users in a list.
     * - Print out the TOP 10 oldest users.
     * */

    public static void main(String[] args) {
        Util uObj = new Util();
        ArrayList<User> users = uObj.parseUsertoArray(Data.users);

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getAge() - o1.getAge();
            }
        });

        for(int i = 0; i<10; i++){
            System.out.println(users.get(i).toString());
        }
    }
}