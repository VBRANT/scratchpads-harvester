// Created by: Henryk Krajinski henryk.krajinski@open.ac.uk
// November 2014
// The Faculty of Mathematics, Computing and Technology
// The Open University, Milton Keynes, United Kingdom
//
// http://hc.apache.org/httpcomponents-client-ga/
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
import common.code.URLsAndHarvestedDataFiles;
import h.scratchpads.list.json.HarvestingDataArchive;
import hkUtilityClasses.HkStrings;
import input.and.output.URLReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// this class is to import one  file into RefBank server
public class OneFileImport {

    public static void uploadFile(String fileURL, String fileName, LogFile logFile) {
        String fileContent = URLReader.readFromUrl(fileURL, logFile);
        if (!HkStrings.isNullOrEmptyString(fileContent)) {
            writeFileContent(ImporterConfigData.RefBank_UPLOAD_URL, "PUT", fileContent, fileName, logFile);
        } else {
            String info = "No data from harvested file at URL: \n" + fileURL;
            logFile.write("\n!!!\n"
                    + info
                    + "\n!!!\n");
            System.out.println(info);
        }
    }

    private static void writeFileContent(String refBankUploadUrl, String httpMethodType, String fileContent, String fileName, LogFile logFile) {
        String info;
        HttpURLConnection con = null;
        DataOutputStream out = null;
        try {
            con = getHttpConnection(refBankUploadUrl, httpMethodType, logFile);
            con.setDoInput(true);
            con.setDoOutput(true);
            out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(fileContent);
            out.flush();
        } catch (IOException e) {
            info = "\n!!!\n"
                    + "IOException: " + e.getMessage() + "\n"
                    + "for RefBank upload URL: " + refBankUploadUrl
                    + "\n!!!\n";
            System.out.println(info);
            logFile.write(info);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    info = "\n!!!\n"
                            + "IOException: " + ex.getMessage() + "\n"
                            + "for RefBank upload URL (when closing DataOutputStream)"
                            + "\n!!!\n";
                    System.out.println(info);
                    logFile.write(info);
                }
            }
        }
        if (con != null) {
            try {
                con.connect();
                getResult(con, fileName, logFile);
            } catch (IOException ex) {
                info = "\n!!!\n"
                        + "IOException: " + ex.getMessage() + "\n"
                        + "when trying to connect to RefBank to get results"
                        + "\n!!!\n";
                System.out.println(info);
                logFile.write(info);
            } finally {
                con.disconnect();
            }
        }
    }

    // httpMethodType: POST, PUT, DELETE, GET
    private static HttpURLConnection getHttpConnection(String refBankUploadUrl, String httpMethodType, LogFile logFile) {
        String info;
        URL uri = null;
        HttpURLConnection con = null;
        try {
            uri = new URL(refBankUploadUrl);
            con = (HttpURLConnection) uri.openConnection();
            con.setRequestMethod(httpMethodType);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestProperty("Accept", "text/plain");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestProperty("Data-Format", CommonConfigData.DATA_FORMAT_FROM_Scratchpads);
            con.setRequestProperty("User-Name", ImporterConfigData.USER_NAME);
        } catch (Exception e) {
            info = "\n!!!\n"
                    + "Exception: " + e.getMessage() + "\n"
                    + "for RefBank upload URL: " + refBankUploadUrl
                    + "\n!!!\n";
            System.out.println(info);
            logFile.write(info);
        }
        return con;
    }

    //result is the response we get from the RefBank side
    private static void getResult(HttpURLConnection con, String fileName, LogFile logFile) {
        String info;
        BufferedReader in = null;
        String temp = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((temp = in.readLine()) != null) {
                sb.append(temp).append("; ");
            }
        } catch (IOException ex) {
            String excMsg = ex.getMessage();
            info = "\n!!!\n"
                    + "IOException: " + excMsg + "\n"
                    + "when tried to get results from RefBank";
            if (excMsg.contains("500")) {                
                info = info + "\n" + deleteFromArchive(fileName, logFile);
            }
            info = info + "\n!!!\n";
            System.out.println(info);
            logFile.write(info);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    info = "IOException: " + ex.getMessage() + "\n"
                            + "when tried to get result from RefBank (closing BufferedReader)";
                    info = info + "\n!!!\n";
                    System.out.println(info);
                    logFile.write(info);
                }
            }
        }
        if (sb.toString().trim().length() > 0) {
            String serverResponse = sb.toString();
            info = "Data to RefBank upload result: " + serverResponse;
            System.out.println(info);
            logFile.write(info);
        }
    }

    // In case of "HTTP response code: 500" the URL will be removed from archive (HarvestingDataArchive.java)
    private static String deleteFromArchive(String fileName, LogFile logFile) {
        String info = "";
        // find out the Scratchpad site URL based on the fileName (contains harvested data for this site)
        URLsAndHarvestedDataFiles obj = new URLsAndHarvestedDataFiles(logFile);
        String url = obj.getValueFromFile(fileName, logFile);        
        if (url != null) {
            // delete Scratchpad site URL from archive
            HarvestingDataArchive archive = new HarvestingDataArchive(logFile);                       
            if (archive.deleteRecordAndSave(url, logFile)) {
                info = "\nThe following Scratchpad  site URL:"
                        + "\n" + url + "\n"
                        + "has been deleted from archive and will be harvested again at the next run.";
            }
        }
        return info;
    }

    // for testing purposes only:
    public static void main(String[] args) {
        Proxy.setUpProxy();
        LogFile logFile = new LogFile();
        String fileURL = "http://mcs.open.ac.uk/hk228/Scratchpads_to_RefBank_HARVESTED_DATA/hk_test5.bib";
        uploadFile(fileURL, "hk_test5.bib", logFile);
        logFile.close();
    }
}
