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

public class ImporterConfigData {

    public static final ImporterConfigData INSTANCE = new ImporterConfigData();
    //
    //      change the following if necessary:
    //
    // The URL where files (with data harvested from Scratchpads) are going to be accessed.
    // This way the RefBank will be able to load them
    // eq: http://mcs.open.ac.uk/hk228/Scratchpads_to_RefBank_HARVESTED_DATA/harvestedData1.bib
    public static final String HARVESTED_FILES_URL = "http://mcs.open.ac.uk/hk228/Scratchpads_to_RefBank_HARVESTED_DATA/";
    //
    // The user to credit for uploaded references to RefBank:
    public static final String USER_NAME = "Dauvit";
    //
    // Import data to RefBank url (only the upload servlet knows about special parsers for reference data formats like BibTeX or RIS):
//    public static final String RefBank_UPLOAD_URL = "http://mct-vibrant.open.ac.uk:8080/RefBank/upload";
    public static final String RefBank_UPLOAD_URL = "http://vbrant.ipd.uka.de/RefBank/upload";

    //
    //
    // -----------------------------------------------------------------
    //              DO NOT CHANGE ANYTHING below this line !!!!  
    private ImporterConfigData() {
    }

    public static ImporterConfigData getInstance() {
        return INSTANCE;
    }
}
