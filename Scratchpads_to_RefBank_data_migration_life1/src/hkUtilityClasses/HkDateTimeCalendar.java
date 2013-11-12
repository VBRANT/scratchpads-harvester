// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
//
// Developed for the ViBRANT project, http://vbrant.eu.  
// ViBRANT was funded by the European Union 7th Framework Programme within the 
// Research Infrastructures group.  
// Contract no. RI-261532. Period, Dec. 2010 to Nov. 2013.  
// Coordinator: Dr Vince Smith.
// E-mail: enquiries@vbrant.eu.
//
// This program is free software; you can redistribute it and/or modify it 
// under the terms of the GNU General Public License as published by the Free 
// Software Foundation; either version 2 of the License, or (at your option) 
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT 
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for 
// more details.
//

package hkUtilityClasses;

import ConfigurationParameters.CommonConfigData;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HkDateTimeCalendar {

    public static String timeElapsed(long start, long end) {
        long timeInSeconds = (end - start) / 1000;
        int hours, minutes, seconds;
        hours = (int) (timeInSeconds / 3600);
        timeInSeconds = (int) (timeInSeconds - (hours * 3600));
        minutes = (int) (timeInSeconds / 60);
        timeInSeconds = (int) (timeInSeconds - (minutes * 60));
        seconds = (int) timeInSeconds;
        return hours + " hour(s) " + minutes + " minute(s) " + seconds + " second(s)";
    }

    public static String now() {
        return now(CommonConfigData.DATE_FORMAT_NOW1);
    }

    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}
