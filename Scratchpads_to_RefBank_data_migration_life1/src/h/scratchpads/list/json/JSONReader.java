// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// http://code.google.com/p/json-simple/wiki/DecodingExamples
// http://code.google.com/p/json-simple/wiki/MappingBetweenJSONAndJavaEntities
// http://code.google.com/p/json-simple/wiki/DecodingExamples#Example_2_-_Faster_way:_Reuse_instance_of_JSONParser
// To read JSON file from provided URL and gets 
// the URLs list of Scratchpads that will be harvested
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

import input.and.output.URLReader;
import ConfigurationParameters.HarvesterConfigData;
import common.code.LogFile;
import common.code.Proxy;
import hkUtilityClasses.HkStrings;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

    private static JSONArray readJSON(String jsonFileUrl, LogFile logFile) {
        String info;
        String jsonStr = URLReader.readFromUrl(jsonFileUrl, logFile);
        JSONArray arrayJSON = null;
        if (!HkStrings.isNullOrEmptyString(jsonStr)) {
            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(jsonStr);
                arrayJSON = (JSONArray) obj;
            } catch (ParseException ex) {
                info = "Unable to parse JSON file at the URL: \n" + jsonFileUrl + "\n";
                info = info + "Exception: " + ex.getMessage();
                logFile.write("\n!!!\n"
                        + info
                        + "\n!!!\n");
                System.out.println(info);
            }
        } else {
            info = "No data from JSON file at URL: \n" + jsonFileUrl;
            logFile.write("\n!!!\n"
                    + info
                    + "\n!!!\n");
            System.out.println(info);
        }
        return arrayJSON;
    }

    // Return the list of Scratchpads URLs that will be harvested
    public static String[] getScratchpadsForHarvesting(HarvestingDataArchive archive, LogFile logFile) {
        String info;
        String[] scratchpadURLs = null;
        JSONArray arrayJSON = readJSON(HarvesterConfigData.CURRENT_Scratchpads_JSON_URL, logFile);
        if (arrayJSON != null) {
            List<String> urls = new ArrayList<String>();            
            info = "The list of current Scratchpads sites contains " + arrayJSON.size() + " URL(s)";
            logFile.write(info);
            System.out.println(info);
            String field_website;
            String field_profile;
            String field_last_node_changed;
            String fieldLastNodeChangedFromArchive;
            boolean wasThereAnyChangeToHarvestingDataArchive = false;
            for (int i = 0; i < arrayJSON.size(); i++) {
                JSONObject jsonObject = (JSONObject) arrayJSON.get(i);
                field_website = (String) jsonObject.get("field_website");
                if (field_website.startsWith("http")) {
                    field_profile = (String) jsonObject.get("field_profile");
                    if ((field_profile.equalsIgnoreCase("scratchpad_2")) || (field_profile.equalsIgnoreCase("scratchpad_2_migrate"))) {
                        field_last_node_changed = jsonObject.get("field_last_node_changed").toString();
                        fieldLastNodeChangedFromArchive = archive.getValue(field_website);
                        if (!field_last_node_changed.equalsIgnoreCase(fieldLastNodeChangedFromArchive)) {
                            archive.setValue(field_website, field_last_node_changed);
                            wasThereAnyChangeToHarvestingDataArchive = true;
                            urls.add(field_website);
                        }
                    }
                }
            }
            // if there was any change to HarvestingDataArchive then save it:
            if (wasThereAnyChangeToHarvestingDataArchive) {
                archive.saveHarvestingData(logFile);
            }
            scratchpadURLs = new String[urls.size()];
            urls.toArray(scratchpadURLs);
        }
        return scratchpadURLs;
    }

    // for testing only
    public static void main(String[] args) {
        Proxy.setUpProxy();
        LogFile logFile = new LogFile();
        HarvestingDataArchive archive = new HarvestingDataArchive(logFile);
        getScratchpadsForHarvesting(archive, logFile);
        logFile.close();
    }
}
