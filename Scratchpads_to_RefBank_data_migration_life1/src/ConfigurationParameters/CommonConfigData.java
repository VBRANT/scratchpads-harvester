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

package ConfigurationParameters;

public class CommonConfigData {

    public static final CommonConfigData INSTANCE = new CommonConfigData();
    //
    //      change the following if necessary:    
    //
    // The folder where harvested data from Scratchpads is stored in files:
    public static final String PATH_HARVESTED_DATA = "C:\\Scratchpads_to_RefBank_HARVESTED_DATA";
    //
    // harvested data format:
    public static final String DATA_FORMAT_FROM_Scratchpads = "BibTeX";
    //
    // The folder where log files are stored:
    public static final String PATH_LOG_FILES = "C:\\Scratchpads_to_RefBank_LOGs";
    //
    // The folder where files essential for the application are stored:
    public static final String PATH_DATA_FILES = "C:\\Scratchpads_to_RefBank_DATA";
    //
    //    If you require HTTP/S access to remote services from behind the OU firewall 
    //    (on campus) the client has to use the University's http proxy;
    //    the proxy settings are:
    //          Hostname: wwwcache.open.ac.uk
    //          Port: 80
    public static final String PROXY_HOSTNAME = "wwwcache.open.ac.uk";
    public static final String PROXY_PORT = "80";
    // If you do not need to use proxy then use the following instead:
//    public static final String PROXY_HOSTNAME = null;
//    public static final String PROXY_PORT = null;
    //
    // -----------------------------------------------------------------
    //          DO NOT CHANGE ANYTHING below this line !!!!    
    public static final String DIR_SEP = Character.toString(System.getProperty("file.separator").charAt(0));
    // to be used in creating the log file name:
    public static final String DATE_FORMAT_FOR_LOG_FILE = "dd_MMMM_yyyy_HH_mm";
    // standard date format (easy to read):
    public static final String DATE_FORMAT_NOW1 = "dd MMMM yyyy HH:mm";
    // This is only generic log file name. It should have '.txt' extension.
    // This file name will be modified to add the date of every parsing process (before '.txt')
    public static final String LOG_FILE_NAME = "Scratchpads_to_RefBank_migration_log.txt";
    // Properties file used to store: (Scratchpad site harvested data filename) and (Scratchpad site URL)
    public static final String HarvestedDataFiles_vs_URLs = "HarvestedDataFiles_vs_URLs.properties";

    private CommonConfigData() {
    }

    public static CommonConfigData getInstance() {
        return INSTANCE;
    }
}
