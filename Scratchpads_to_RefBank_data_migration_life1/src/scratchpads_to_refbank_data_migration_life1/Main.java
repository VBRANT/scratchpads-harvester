// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// To automatically harvest all references held in Scratchpads 2 sites and import them into RefBank.
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

package scratchpads_to_refbank_data_migration_life1;

import ConfigurationParameters.CommonConfigData;
import ConfigurationParameters.HarvesterConfigData;
import common.code.LogFile;
import common.code.Proxy;
import h.harvesting.scratchpads.Harvester;
import h.scratchpads.list.json.HarvestingDataArchive;
import h.scratchpads.list.json.JSONReader;
import hkUtilityClasses.HkDateTimeCalendar;
import java.util.Date;

/**
 *
 * @author Henryk Krajinski Henryk.Krajinski@open.ac.uk
 */
public class Main {

    public static void main(String[] args) {
        rememberToSetupBeforeYouRunTheApplication();
        Proxy.setUpProxy();
        long start = new Date().getTime();
        LogFile logFile = new LogFile();
        if (harvestingScratchpadsSitesAndSaveDataToFiles(logFile) > 0) {
            // that means there was at least one file created with Scratchpads data
            // and it can be imported to RefBank
            importingToRefBank(logFile);
        }
        long end = new Date().getTime();
        logFile.write("\n\t\t TOTAL TIME: " + HkDateTimeCalendar.timeElapsed(start, end) + "\n");
        logFile.close();
    }

    // This is the FIRST stage of the migration process (called harvesting the data).
    // The data from Scratchpads sites is harvested and then saved in separate files (one file for each site).
    // Scratchpads sites need to fulfill some criteria in order to be harvested.
    // All the Java classes created exclusively for this step are located in packages that names start with 'h' (eq. h.scratchpads.list.json)
    // returns number of files created with Scratchpads data
    private static int harvestingScratchpadsSitesAndSaveDataToFiles(LogFile logFile) {
        int numberOfFilesCreated = 0;
        long startHarvesting = new Date().getTime();
        String info = "\n--------------------- HARVESTING  start ---------------------";
        info = info + "\n preparing the list of Scratchpads URLs that will be harvested...";
        logFile.write(info);
        System.out.println(info);
        // stores data about latest harvesting:
        HarvestingDataArchive archive = new HarvestingDataArchive(logFile);
        // Determine which Scratchpads sites will be harvested and return their URLs:
        String[] scratchpadURLs = JSONReader.getScratchpadsForHarvesting(archive, logFile);
        if (scratchpadURLs.length > 0) {
            info = "The following " + scratchpadURLs.length + " Scratchpads site(s) met the criteria and will be harvested:";
            logFile.write(info);
            System.out.println(info);
            for (int i = 0; i < scratchpadURLs.length; i++) {
                info = i + 1 + ") " + scratchpadURLs[i];
                logFile.write(info);
                System.out.println(info);
            }
            info = "\n getting data from Scratchpads sites and then save it to the files...";
            logFile.write(info);
            System.out.println(info);
            // Gets data from Scratchpads (their URLs) and then save it to the files:
            numberOfFilesCreated = Harvester.getDataFromScratchpads(scratchpadURLs, archive, logFile);
            info = "> > > > > >  Data from " + scratchpadURLs.length + " Scratchpads site(s) was saved in " + numberOfFilesCreated + " file(s)  < < < < < <";
            logFile.write(info);
            System.out.println(info);
        } else {
            info = "\nTHERE ARE NO Scratchpads sites to be harvested.\n\n"
                    + "\tThat might be just the case that there are no Scratchpads sites fulfilling harvesting criteria.\n"
                    + "\tIf you want to harvest ALL Scratchpads URLs listed at:\n\t"
                    + HarvesterConfigData.CURRENT_Scratchpads_JSON_URL + "\n\tthen just delete the file: \n\t"
                    + CommonConfigData.PATH_DATA_FILES + CommonConfigData.DIR_SEP + HarvesterConfigData.HARVESTING_DATA_ARCHIVE;
            logFile.write(info);
            System.out.println(info);
        }
        long endHarvesting = new Date().getTime();
        info = "\n\t\t HARVESTING TIME: " + HkDateTimeCalendar.timeElapsed(startHarvesting, endHarvesting) + "\n";
        info = info + "--------------------- HARVESTING  end ---------------------";
        logFile.write(info);
        System.out.println(info);
        return numberOfFilesCreated;
    }

    // This is the SECOND stage of the migration process (called importing the data).
    // This stage is for importing data from each file (holding data harvested from Scratchpads) into RefBank server over HTTP.
    // All the Java classes created exclusively for this step are located in packages that names start with 'i' (eq. i.files.importTo.RefBank)
    private static void importingToRefBank(LogFile logFile) {
        // REMEMBER that upload of files to RefBank server goes over HTTP
        // The files should be in the folder accessible over http
        // as defined in CommonConfigData.PATH_HARVESTED_DATA and ImporterConfigData.HARVESTED_FILES_URL
        // JUST UNCOMMENT THE FOLLOWING LINE OF CODE:
//        FilesImporter.dataImportFromFiles_intoRefBank(logFile);
    }

    private static void rememberToSetupBeforeYouRunTheApplication() {
        System.out.println("IN ORDER TO RUN THIS APPLICATION HAS TO BE SETUP PROPERLY");
        System.out.println("Provide the following information:");
        System.out.println();
        System.out.println("1) in the ConfigurationParameters.CommonConfigData.class:");
        System.out.println("\t\t The folder where harvested data from Scratchpads is stored in files: PATH_HARVESTED_DATA");
        System.out.println("\t\t Harvested data format: DATA_FORMAT_FROM_Scratchpads");
        System.out.println("\t\t The folder where log files are stored: PATH_LOG_FILES");
        System.out.println("\t\t The folder where files essential for the application are stored: PATH_DATA_FILES");
        System.out.println("\t\t If you require HTTP/S access to remote services from behind the OU firewall: PROXY_HOSTNAME and PROXY_PORT");
        System.out.println();
        System.out.println("2) in the ConfigurationParameters.HarvesterConfigData.class:");
        System.out.println("\t\t URL of JSON file with the list of current Scratchpads: CURRENT_Scratchpads_JSON_URL");
        System.out.println("\t\t extension of the file where the harvested data will be stored: HARVESTED_DATA_FILE_EXTENSION");
        System.out.println("\t\t the character or string that should be present at the beginning of data harvested from Scratchpad site: COMPULSORY_STRING");
        System.out.println();
        System.out.println("3) in the ConfigurationParameters.ImporterConfigData.class:");
        System.out.println("\t\t the URL where files (with data harvested from Scratchpads) are going to be accessed: HARVESTED_FILES_URL");
        System.out.println("\t\t the user to credit for uploaded references to RefBank: USER_NAME");
        System.out.println("\t\t import data to RefBank url: RefBank_UPLOAD_URL");
        System.out.println();
        System.out.println("If you want to harvest ALL Scratchpads URLs then just delete the file: \n"
                + CommonConfigData.PATH_DATA_FILES + CommonConfigData.DIR_SEP + HarvesterConfigData.HARVESTING_DATA_ARCHIVE);
        System.out.println("\n");
    }
}
