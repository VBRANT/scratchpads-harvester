// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// cHECKING IF DATA HARVESTED FROM sCRATCHPAD SITE IS VALID
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

package h.harvesting.scratchpads;

import ConfigurationParameters.HarvesterConfigData;
import common.code.LogFile;
import h.scratchpads.list.json.HarvestingDataArchive;
import hkUtilityClasses.HkStrings;

public class DataValidator {

    public static boolean checkIfAnyDataReceivedFromScratchPad(HarvestingDataArchive archive, String scratchpadData, String scratchpadUrl, String scratchpadUrlToReadFrom, LogFile logFile) {
        boolean dataOK = false;
        if (HkStrings.isNullOrEmptyString(scratchpadData)) {
            dataOK = false;
            archive.deleteRecordAndSave(scratchpadUrl, logFile);
            String info = "No data from Scratchpad at URL: \n" + scratchpadUrlToReadFrom;
            logFile.write("\n!!!\n"
                    + info
                    + "\n!!!\n");
            System.out.println(info);
        } else {
            if (checkIfDataReceivedIsInTheRightFormat(scratchpadData)) {
                dataOK = true;
            } else {
                dataOK = false;
                archive.deleteRecordAndSave(scratchpadUrl, logFile);
                String info = "Data from Scratchpad at URL: \n" + scratchpadUrlToReadFrom + "\n";
                info = info + "should start with '" +HarvesterConfigData.COMPULSORY_STRING+ "'";
                logFile.write("\n!!!\n"
                        + info
                        + "\n!!!\n");
                System.out.println(info);
            }
        }
        return dataOK;
    }

    private static boolean checkIfDataReceivedIsInTheRightFormat(String scratchpadData) {
        boolean dataOK = false;
        scratchpadData = scratchpadData.trim();
        if (scratchpadData.startsWith(HarvesterConfigData.COMPULSORY_STRING)) {
            dataOK = true;
        }
        return dataOK;
    }
}
