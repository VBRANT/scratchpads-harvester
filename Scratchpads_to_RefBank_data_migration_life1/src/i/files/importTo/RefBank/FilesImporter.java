// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// this class is to import the files with data harvested from Scratchpads sites into RefBank server
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

package i.files.importTo.RefBank;

import ConfigurationParameters.CommonConfigData;
import ConfigurationParameters.ImporterConfigData;
import common.code.LogFile;
import common.code.Proxy;
import hkUtilityClasses.HkDateTimeCalendar;
import java.io.File;
import java.util.Date;

public class FilesImporter {

    private static String[] getFiles() {
        File filesDirectory = new File(CommonConfigData.PATH_HARVESTED_DATA);
        return filesDirectory.list();
    }
    
    public static void dataImportFromFiles_intoRefBank(LogFile logFile) {
        String info = "\n\n+++++++++++++++++++++++++++++++++++++++++++++++ Data Import - START +++++++++++++++++++++++++++++++++++++++++++++++";
        info = info + "\n\t\t RefBank server import URL: " + ImporterConfigData.RefBank_UPLOAD_URL;
        info = info + "\n\t\t The URL for the files with data harvested from Scratchpads sites: " + ImporterConfigData.HARVESTED_FILES_URL;
        logFile.write(info);
        System.out.println(info);
        long startImporting = new Date().getTime();
        String[] files = getFiles();
        String fileURL;
        int idx = 0;
        for (int i = 0; i < files.length; i++) {
            fileURL = ImporterConfigData.HARVESTED_FILES_URL + files[i];
            idx = i + 1;
            info = idx + ") " + fileURL;
            logFile.write(info);
            System.out.println(info);
            OneFileImport.uploadFile(fileURL, files[i], logFile);
        }
        long endImporting = new Date().getTime();
        info = "\n\t\t IMPORTING TIME: " + HkDateTimeCalendar.timeElapsed(startImporting, endImporting) + "\n";
        logFile.write(info);
        System.out.println(info);
        info = "\n+++++++++++++++++++++++++++++++++++++++++++++++ Data Import - END +++++++++++++++++++++++++++++++++++++++++++++++";
        logFile.write(info);
        System.out.println(info);
    }
    
    // REMEMBER that upload of files to RefBank server goes over HTTP
    // The files should be in the folder accessible over http
    // as defined in CommonConfigData.PATH_HARVESTED_DATA and ImporterConfigData.HARVESTED_FILES_URL
    // for testing purposes only:
    public static void main(String[] args) {
        Proxy.setUpProxy();
        LogFile logFile2 = new LogFile();
        dataImportFromFiles_intoRefBank(logFile2);
        logFile2.close();
    }
}
