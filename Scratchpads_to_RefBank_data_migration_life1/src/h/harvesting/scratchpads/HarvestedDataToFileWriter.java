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
import common.code.LogFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HarvestedDataToFileWriter {

    private BufferedWriter bufferedFileWriter = null;
    private LogFile logFile;
    private String fileName;
    private boolean savedOK = false;
    private String fileFullPath;

    public HarvestedDataToFileWriter(String fileName, LogFile logFile) {
        this.fileName = fileName;
        this.fileFullPath = CommonConfigData.PATH_HARVESTED_DATA + CommonConfigData.DIR_SEP + fileName;
        this.logFile = logFile;
        File aFile = new File(this.fileFullPath);
        try {
            bufferedFileWriter = new BufferedWriter(new FileWriter(aFile));
        } catch (Exception ex) {
            this.savedOK = false;
            this.logFile.write("\n!!!\n"
                    + "Problem when creating harvested data file: \n" + this.fileFullPath + "\n"
                    + "Exception: " + ex.getMessage()
                    + "\n!!!\n");
        }
    }

    // Saves data from Scratchpad to the files in folder HarvesterConfigData.PATH_HARVESTED_DATA
    public boolean write(String str) {
        if (this.bufferedFileWriter != null) {
            try {
                this.bufferedFileWriter.write(str);
                this.savedOK = true;
            } catch (IOException ex) {
                this.savedOK = false;
                this.logFile.write("\n!!!\n"
                        + "Problem when writing to harvested data file: \n" + this.fileFullPath + "\n"
                        + "IOException: " + ex.getMessage()
                        + "\n!!!\n");
            } catch (Exception ex) {
                this.savedOK = false;
                this.logFile.write("\n!!!\n"
                        + "Problem when writing to harvested data file: \n" + this.fileFullPath + "\n"
                        + "Exception: " + ex.getMessage()
                        + "\n!!!\n");
            } finally {
                try {
                    this.bufferedFileWriter.close();
                } catch (IOException ex) {
                    this.savedOK = false;
                    this.logFile.write("\n!!!\n"
                            + "Problem when closing harvested data file: \n" + this.fileFullPath + "\n"
                            + "IOException: " + ex.getMessage()
                            + "\n!!!\n");
                } catch (Exception ex) {
                    this.savedOK = false;
                    this.logFile.write("\n!!!\n"
                            + "Problem when closing harvested data file: \n" + this.fileFullPath + "\n"
                            + "Exception: " + ex.getMessage()
                            + "\n!!!\n");
                }
            }
        } else {
            this.savedOK = false;
        }
        return this.savedOK;
    }
}
