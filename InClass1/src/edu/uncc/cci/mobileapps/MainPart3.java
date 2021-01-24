package edu.uncc.cci.mobileapps;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
/**
 * a. Assignment #1.
 * b. File Name : Group3_InClass01 (MainPart3.java)
 * c. Full name of the student : Chandan Mannem,Mahalavanya Sriram
 */
public class MainPart3 {
    /*
    * Question 3:
    * - This is a simple programming question that focuses on finding the
    * longest increasing subarray. Given the array A = {1,2,3,2,5,2,4,6,7} the
    * longest increasing subarray is {2,4,6,7}, note that the values have to be
    * contiguous.
    * */
    public static void main(String[] args) {
        Util uObj = new Util();
        ArrayList<User> users = uObj.parseUsertoArray(Data.users);
        ArrayList<User> otherUsers = uObj.parseUsertoArray(Data.otherUsers);

        HashSet<Integer> set = new HashSet<>();
        for (User user :
                users) {
            set.add(user.hashCode());
        }
        ArrayList<User> usersList = new ArrayList<>();

        for(User user: otherUsers){
            if(set.contains(user.hashCode())){
                usersList.add(user);
            }
        }
        Collections.sort(usersList, new Comparator<User>(){
            @Override
            public int compare(User a, User b){
                return b.getState().compareTo(a.getState());
            }
        });
        System.out.println("\nUsers List:");
        for(User obj : usersList){
            System.out.println(obj.toString());
        }


    }


}