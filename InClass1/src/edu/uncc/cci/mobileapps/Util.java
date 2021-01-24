package edu.uncc.cci.mobileapps;

import java.util.ArrayList;

public class Util {

    public ArrayList<User> parseUsertoArray(String[] usersString){
        ArrayList<User> usersArray = new ArrayList<User>();
        for (String string : usersString) {
            String[] str = string.split(",");
            User user = new User(str[0],str[1],Integer.parseInt(str[2]),str[3],str[4],str[5],str[6]);
            usersArray.add(user);
        }
        return usersArray;
    }

}
