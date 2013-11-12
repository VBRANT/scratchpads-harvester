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

package h.harvesting.scratchpads;

import ConfigurationParameters.CommonConfigData;
import ConfigurationParameters.HarvesterConfigData;
import common.code.LogFile;
import common.code.URLsAndHarvestedDataFiles;
import h.scratchpads.list.json.HarvestingDataArchive;
import input.and.output.URLReader;

public class Harvester {

    // Save data read from Scratchpad url to file. The files then will be imported to RefBank.
    // Returns true if saving went OK.
    private static boolean saveScratchpadDataToFile(int fileIdx, String scratchpadData, String scratchpadURL, LogFile logFile, URLsAndHarvestedDataFiles urlsAndHarvestedDataFilesData) {
        String fileName = "harvestedData" + fileIdx + "." + HarvesterConfigData.HARVESTED_DATA_FILE_EXTENSION;
        HarvestedDataToFileWriter writer = new HarvestedDataToFileWriter(fileName, logFile);
        urlsAndHarvestedDataFilesData.setValue(fileName, scratchpadURL);
        return writer.write(scratchpadData);
    }



    // Gets data from Scratchpads (their URLs) and then save it to the files in folder HarvesterConfigData.PATH_HARVESTED_DATA
    public static int getDataFromScratchpads(String[] scratchpadURLs, HarvestingDataArchive archive, LogFile logFile) {
        String scratchpadUrlToReadFrom;
        String scratchpadData;
        int numberOfFilesCreated = 0;
        URLsAndHarvestedDataFiles urlsAndHarvestedDataFilesData = new URLsAndHarvestedDataFiles(logFile);
        for (int i = 0; i < scratchpadURLs.length; i++) {
            // construct url to read from:
            scratchpadUrlToReadFrom = scratchpadURLs[i] + "/biblio_search_export/" + CommonConfigData.DATA_FORMAT_FROM_Scratchpads;
            // Reads data from scratchpadUrlToReadFrom:
            scratchpadData = URLReader.readFromUrl(scratchpadUrlToReadFrom, logFile);
            if (DataValidator.checkIfAnyDataReceivedFromScratchPad(archive, scratchpadData, scratchpadURLs[i], scratchpadUrlToReadFrom, logFile)) {
                if (saveScratchpadDataToFile(i + 1, scratchpadData, scratchpadURLs[i], logFile, urlsAndHarvestedDataFilesData)) {
                    numberOfFilesCreated++;
                }
            }
        }
        urlsAndHarvestedDataFilesData.saveDataToFile(logFile);
        return numberOfFilesCreated;
    }
}
