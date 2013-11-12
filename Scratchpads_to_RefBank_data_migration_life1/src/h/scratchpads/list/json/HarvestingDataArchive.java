// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// http://docs.oracle.com/javase/tutorial/essential/environment/properties.html
// Use for checking the data in the ConfigurationParameters.HarvesterConfigData.HARVESTING_DATA_ARCHIVE properties file
// Used to check if 'current json field_last_node_changed' not equal archive json 'field_last_node_changed'
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

package h.scratchpads.list.json;

import ConfigurationParameters.CommonConfigData;
import ConfigurationParameters.HarvesterConfigData;
import common.code.LogFile;
import hkUtilityClasses.HkDateTimeCalendar;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class HarvestingDataArchive {

    private LogFile logFile;
//    key: Scratchpad URL
//    value: field_last_node_changed
    private Properties harvestingData = new Properties();
    private String fullPath = CommonConfigData.PATH_DATA_FILES + CommonConfigData.DIR_SEP + HarvesterConfigData.HARVESTING_DATA_ARCHIVE;

    public HarvestingDataArchive(LogFile logFile) {
        this.logFile = logFile;
        loadHarvestingData(logFile);
    }

    private void loadHarvestingData(LogFile logFile) {
        String info;
        FileInputStream in = null;
        try {
            in = new FileInputStream(fullPath);
            this.harvestingData.load(in);
        } catch (FileNotFoundException ex) {
            info = "\nUnable to find the file to load data: \n" + fullPath + "\n";
            info = info + "New harvesting data archive file will be created\n";
            logFile.write(info);
            System.out.println(info);
        } catch (IOException ex) {
            info = "\nUnable to load data from the file: \n" + fullPath + "\n";
            info = info + "New harvesting data archive file will be created\n";
            info = info + "IOException: " + ex.getMessage() + "\n";
            logFile.write(info);
            System.out.println(info);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                info = "\nProblem when closing the file (loading data): \n" + fullPath + "\n";
                info = info + "IOException: " + ex.getMessage() + "\n";
                logFile.write(info);
                System.out.println(info);
            }
        }
    }

    public boolean deleteRecordAndSave(String key, LogFile logFile) {
        boolean recordDeletedOK = false;
        if (this.harvestingData.remove(key) != null) {
            recordDeletedOK = saveHarvestingData("The record for: \n" + key + "\nhas been deleted and Harvesting data archive file updated. The application will try to harvest this URL at next run.", logFile);
        }
        return recordDeletedOK;
    }

    public boolean saveHarvestingData(LogFile logFile) {
        return this.saveHarvestingData("\nSome new data was saved to Harvesting data archive file: \n" + fullPath + "\n", logFile);
    }

    public boolean saveHarvestingData(String msg, LogFile logFile) {
        boolean dataSavedOK = false;
        String info;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fullPath);
            String comment = "--- Last saved: " + HkDateTimeCalendar.now() + "---";
            this.harvestingData.store(out, comment);
            dataSavedOK = true;
        } catch (FileNotFoundException ex) {
            dataSavedOK = false;
            info = "\nUnable to find the file to save data: \n" + fullPath + "\n";
            info = info + "FileNotFoundException: " + ex.getMessage() + "\n";
            logFile.write(info);
            System.out.println(info);
        } catch (IOException ex) {
            dataSavedOK = false;
            info = "\nUnable to save data to the file: \n" + fullPath + "\n";
            info = info + "IOException: " + ex.getMessage() + "\n";
            logFile.write(info);
            System.out.println(info);
        } finally {
            try {
                if (out != null) {
                    out.close();
                    info = msg;
                    logFile.write(info);
                    System.out.println(info);
                }
            } catch (IOException ex) {
                info = "\nProblem when closing the file (saving data): \n" + fullPath + "\n";
                info = info + "IOException: " + ex.getMessage() + "\n";
                logFile.write(info);
                System.out.println(info);
            }
        }
        return dataSavedOK;
    }

    public String getValue(String key) {
        return this.harvestingData.getProperty(key);
    }

    public void setValue(String key, String value) {
        this.harvestingData.setProperty(key, value);
    }

    // for testing only
    public static void main(String[] args) {
        LogFile logFile = new LogFile();
        HarvestingDataArchive data = new HarvestingDataArchive(logFile);
//        data.setValue("http://localise-npt.gbif.org", "1364293047");
//        data.setValue("http://amaryllidaceae.e-monocot.org", "1364311868");
//        data.setValue("http://npt.yurib.mooo.com", "1366847572");
//        data.saveHarvestingData(logFile);
        System.out.println(data.getValue("http://amaryllidaceae.e-monocot.org"));

        logFile.close();
    }
}
