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

package common.code;

import ConfigurationParameters.CommonConfigData;
import hkUtilityClasses.HkDateTimeCalendar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// REMEMBER to close the log file at the end
public class LogFile {

    private BufferedWriter bufferedFileWriter = null;
    private String pathName;

    // create and keep the log file opened for writing
    public LogFile() {
        this.pathName = CommonConfigData.PATH_LOG_FILES + CommonConfigData.DIR_SEP + createLogFileName();
        File aFile = new File(this.pathName);
        try {
            bufferedFileWriter = new BufferedWriter(new FileWriter(aFile));
            bufferedFileWriter.write("This log file covers harvesting all references held in Scratchpads 2 sites and importing them into RefBank.");
            bufferedFileWriter.write("\n\t\t\t\t\t (" + HkDateTimeCalendar.now() + ")\n");
        } catch (Exception ex) {
            System.out.println("Exception when creating log file: " + ex.getMessage());
        }
    }

    public void write(String str) {
        if (this.bufferedFileWriter != null) {
            try {
                this.bufferedFileWriter.write(str + "\n");
            } catch (IOException ex) {
                System.out.println("IOException when writing to log file: " + ex.getMessage());
            }
        } else {
            System.out.println("Can not write to log file: this.bufferedFileWriter = null");
        }
    }

    public void close() {
        if (this.bufferedFileWriter != null) {
            try {
                this.bufferedFileWriter.close();
            } catch (IOException ex) {
                System.out.println("IOException when closing log file: " + ex.getMessage());
            }
        }
    }

    private static String createLogFileName() {
        int dotIdx = CommonConfigData.LOG_FILE_NAME.indexOf(".");
        String logFileName = CommonConfigData.LOG_FILE_NAME.substring(0, dotIdx);
        String theRest = CommonConfigData.LOG_FILE_NAME.substring(dotIdx);
        logFileName = logFileName + "_" + HkDateTimeCalendar.now(CommonConfigData.DATE_FORMAT_FOR_LOG_FILE).toLowerCase() + theRest;
        // System.out.println(logFileName);
        return logFileName;
    }
}
