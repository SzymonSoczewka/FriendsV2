package dk.easv.friendsv2;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDate;

import dk.easv.friendsv2.Model.BEFriend;

class CheckForBirthday {
    //private static final CheckForBirthday ourInstance = new CheckForBirthday();

    private CheckForBirthday() {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    static boolean hasBirthday(BEFriend friend){
        String fBirthday = friend.getBirthday(); // Getting friend birthday - dd/MM/yyyy
        if(fBirthday != null) {
            LocalDate currentDate = LocalDate.now(); //Getting the current date value
            int currentDay = currentDate.getDayOfMonth(); //Getting the current day
            int currentMonth = currentDate.getMonthValue(); //Getting the current month
            int currentYear = currentDate.getYear(); //getting the current year

            int firstSlashIndex = fBirthday.indexOf('/');
            int lastSlashIndex = fBirthday.lastIndexOf('/');
            int day = Integer.parseInt(fBirthday.substring(0, firstSlashIndex)); //The day the user was born
            int month = Integer.parseInt(fBirthday.substring(firstSlashIndex+1, lastSlashIndex)); //The month the user was born
            int year = Integer.parseInt(fBirthday.substring(lastSlashIndex+1,fBirthday.length())); //The year the user was born

            return currentDay == day && currentMonth == month && currentYear > year;
        }
        return false;
    }
}
