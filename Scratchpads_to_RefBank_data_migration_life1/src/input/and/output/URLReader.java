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
package input.and.output;

import ConfigurationParameters.CommonConfigData;
import ConfigurationParameters.HarvesterConfigData;
import common.code.LogFile;
import common.code.Proxy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLReader {

    public static String readFromUrl(String urlStr, LogFile logFile) {
        String info;
        String content = null;        
        URL url = null;
        InputStream is;
        BufferedReader in = null;
        try {
            url = new URL(urlStr);
            is = url.openStream();
            in = new BufferedReader(new InputStreamReader(is));
            StringBuilder contentFromURL = new StringBuilder(1000);
            String inputLine = in.readLine();
            while (inputLine != null) {
                //System.out.println(inputLine);
                contentFromURL.append(inputLine);
                inputLine = in.readLine();
            }
            content = contentFromURL.toString();
        } catch (MalformedURLException ex) {
            info = "Problem with reading from URL: \n" + urlStr + "\n";
            info = info + "MalformedURLException: " + ex.getMessage();
            logFile.write("\n!!!\n"
                    + info
                    + "\n!!!\n");
            System.out.println(info);
        } catch (IOException ex) {
            info = "Problem with reading from URL: \n" + urlStr + "\n";
            info = info + "IOException: " + ex.getMessage();
            logFile.write("\n!!!\n"
                    + info
                    + "\n!!!\n");
            System.out.println(info);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                info = "Problem when closing InputStream for URL: \n" + urlStr + "\n";
                info = info + "IOException " + ex.getMessage();
                logFile.write("\n!  " + info + "   !\n");
                System.out.println(info);
            }
        }
        return content;
    }

    // for testing only
    public static void main(String[] args) {
        Proxy.setUpProxy();
        // TO CHECK IF I CAN READ FROM ANY DOMAIN
        LogFile logFile = new LogFile();
        System.out.println("\n\n\n" + readFromUrl(HarvesterConfigData.CURRENT_Scratchpads_JSON_URL, logFile));
        // TO CHECK IF I CAN READ FROM THe OU DOMAIN
//        System.out.println("\n\n\n" + readFromUrl("http://mcs.open.ac.uk/hk228/", logFile));
        logFile.close();
    }
}
