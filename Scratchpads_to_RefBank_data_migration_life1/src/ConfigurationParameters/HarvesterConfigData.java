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

public class HarvesterConfigData {

    public static final HarvesterConfigData INSTANCE = new HarvesterConfigData();
    //
    //      change the following if necessary:
    //
    // URL of JSON file with the list of current Scratchpads:
    public static final String CURRENT_Scratchpads_JSON_URL = "http://scratchpads.eu/explore/sites-list/json";
    //
    // extension of the file where the harvested data will be stored (it should correspond with DATA_FORMAT_FROM_Scratchpads):
    public static final String HARVESTED_DATA_FILE_EXTENSION = "bib";
    //
    // The character or string that should be present at the beginning of data harvested from Scratchpad site. E.g. in case of BibTex format it is '@'
    // Used by h.harvesting.scratchpads.DataValidator (you can write your own)
    public static final String COMPULSORY_STRING = "@";
    //
    // -----------------------------------------------------------------
    //          DO NOT CHANGE ANYTHING below this line !!!!    
    // Properties file used to check if 'current json field_last_node_changed' not equal archive json 'field_last_node_changed'
    public static final String HARVESTING_DATA_ARCHIVE = "HarvestingDataArchive.properties";

    private HarvesterConfigData() {
    }

    public static HarvesterConfigData getInstance() {
        return INSTANCE;
    }
}
