// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// http://docs.oracle.com/javase/tutorial/essential/environment/properties.html
// Use for checking the data in the ConfigurationParameters.CommonConfigData.HarvestedDataFiles_vs_URLs properties file
// Properties file used to store: (Scratchpad site harvested data filename) and (Scratchpad site URL)
// That will be useful eq. when we neeed to check what is the URL of the Scratchpad site data file we are importing into RefBank
// and in case of http 500 response code that Scratchpad URL can be removed from archive and then harvested at the next run
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class URLsAndHarvestedDataFiles {

    private LogFile logFile;
//    key: Scratchpad site URL
//    value: file name (with data harvested from Scratchpad site)
    private Properties data = new Properties();
    private String fullPath = CommonConfigData.PATH_DATA_FILES + CommonConfigData.DIR_SEP + CommonConfigData.HarvestedDataFiles_vs_URLs;

    public URLsAndHarvestedDataFiles(LogFile logFile) {
        this.logFile = logFile;
    }

//    public void deleteRecordAndSave(String key, LogFile logFile) {
//        if (this.data.remove(key) != null) {
//            saveData(logFile);
//        }
//    }
    public void saveDataToFile(LogFile logFile) {
        String info;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fullPath);
            String comment = "--- Last saved: " + HkDateTimeCalendar.now() + "---";
            this.data.store(out, comment);
        } catch (FileNotFoundException ex) {
            info = "\nUnable to find the file to save data: \n" + fullPath + "\n";
            info = info + "FileNotFoundException: " + ex.getMessage() + "\n";
            logFile.write(info);
            System.out.println(info);
        } catch (IOException ex) {
            info = "\nUnable to save data to the file: \n" + fullPath + "\n";
            info = info + "IOException: " + ex.getMessage() + "\n";
            logFile.write(info);
            System.out.println(info);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                info = "\nProblem when closing the file (saving data): \n" + fullPath + "\n";
                info = info + "IOException: " + ex.getMessage() + "\n";
                logFile.write(info);
                System.out.println(info);
            }
        }
    }

    private boolean loadDataFromFile(LogFile logFile) {
        boolean dataLoaded = false;
        String info;
        FileInputStream in = null;
        try {
            in = new FileInputStream(this.fullPath);
            this.data.load(in);
            dataLoaded = true;
        } catch (FileNotFoundException ex) {
            dataLoaded = false;
            info = "\nUnable to find the file to load data: \n" + fullPath + "\n";
            logFile.write(info);
            System.out.println(info);
        } catch (IOException ex) {
            dataLoaded = false;
            info = "\nUnable to load data from the file: \n" + fullPath + "\n";
            info = info + "IOException: " + ex.getMessage() + "\n";
            logFile.write(info);
            System.out.println(info);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                info = "\nProblem when closing the file: \n" + fullPath + "\n";
                info = info + "IOException: " + ex.getMessage() + "\n";
                logFile.write(info);
                System.out.println(info);
            }
        }
        return dataLoaded;
    }

    public String getValueFromFile(String key, LogFile logFile) {
        String value = null;
        if (loadDataFromFile(logFile)) {
            value = getValue(key);
        }
        return value;
    }

    private String getValue(String key) {
        return this.data.getProperty(key);
    }

    public void setValue(String key, String value) {
        this.data.setProperty(key, value);
    }

    // for testing only
    public static void main(String[] args) {
        LogFile logFile = new LogFile();
        
//        URLsAndHarvestedDataFiles data = new URLsAndHarvestedDataFiles(logFile);
//        data.setValue("qwfqwefeqwf.bib", "http://localise-npt.gbif.org");
//        data.setValue("rheretrth.bib", "http://amaryllidaceae.e-monocot.org");
//        data.setValue("ewergwergewr.bib", "http://npt.yurib.mooo.com");
//        data.saveDataToFile(logFile);
//        System.out.println(data.getValueFromFile("ewergwergewr.bib", logFile));

        
        URLsAndHarvestedDataFiles data = new URLsAndHarvestedDataFiles(logFile);
        System.out.println(data.getValueFromFile("harvestedData19.bib", logFile));
        
        logFile.close();
    }
}
