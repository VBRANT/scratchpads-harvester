Scratchpads to RefBank Harvester
================================

Introduction
------------

The purpose of this software is to automatically populate RefBank, a component of the [bibliography of life](http://biblife.org) with all bibliographic references loaded into Scratchpads. Hence, all members of the biodiversity research community can benefit from curated references that are used in Scratchpads, without imposing any additional tasks on the researcher or Scratchpads administrator. 

Workflow
--------

The harvester has a two-stage workflow.

***First stage: harvesting*** 

The data from Scratchpads sites is harvested and then saved in separate files, one file for each site.
    
Scratchpads sites need to fulfil some criteria in order to be harvested:

- only Scratchpads 2 sites are harvested (at the time of writing the migration from Scratchpads had not completed), and
- there must be new entries in the Scratchpads site to harvest.

All the Java classes created exclusively for this step are located in packages that names start with `h`.

***Second stage: importing***

This stage is for importing data from each file created by harvesting Scratchpads into RefBank server over HTTP. 

All the Java classes created exclusively for this step are located in packages that names start with `i`. For example, `i.files.importTo.RefBank`.

It is possible to harvest **all** Scratchpads again by deleting the intermediate log files. You can find the file in the folder defined by:  
`CommonConfigData.PATH_DATA_FILES + CommonConfigData.DIR_SEP + HarvesterConfigData.HARVESTING_DATA_ARCHIVE`

See Installation section below for more about setting the parameters used in this command.

Installation
------------

The software can be downloaded from [ViBRANT's git repository](https://git.scratchpads.eu/v) as an anonymous user with the following command:  
`$ git clone https://git.scratchpads.eu/git/scratchpads-harvester.git`

The software is set up as a Java Netbeans project.

The following libraries are required as well as the source software:

1. [Apache Commons](http://commons.apache.org/)  
2. [EzMorph](http://ezmorph.sourceforge.net/)
3. [ApacheHttpClient](http://hc.apache.org/)
4. [JSON.simple](http://code.google.com/p/json-simple/)

They are included in the git repository in the `libraries` folder.

See also the image `ScratchpadsHarvesterLibraries.jpg` in that folder for the individual components within the libraries.  

Configuration
-------------

To run the software it must be configured by providing the following information:

1) in the ConfigurationParameters.CommonConfigData.class:

  - the folder where harvested data from Scratchpads is stored in files: PATH_HARVESTED_DATA
  - harvested data format: DATA_FORMAT_FROM_Scratchpads
  - the folder where log files are stored: PATH_LOG_FILES
  - the folder where files essential for the application are stored: PATH_DATA_FILES
  - If you require HTTP/S access to remote services from behind the OU firewall: PROXY_HOSTNAME and PROXY_PORT

2) in the ConfigurationParameters.HarvesterConfigData.class:

  - URL of JSON file with the list of current Scratchpads: CURRENT_Scratchpads_JSON_URL
  - extension of the file where the harvested data will be stored: HARVESTED_DATA_FILE_EXTENSION
  - the character or string that should be present at the beginning of data harvested from Scratchpad site: COMPULSORY_STRING

3) in the ConfigurationParameters.ImporterConfigData.class:

  - the URL where files (with data harvested from Scratchpads) are going to be accessed: HARVESTED_FILES_URL
  - the user to credit for uploaded references to RefBank: USER_NAME
  - import data to RefBank url: RefBank_UPLOAD_URL


The Configuration files have additional comments.  

These instructions are repeated in the main executable:  `Scratchpads_to_RefBank_data_migration_life1/src/scratchpads_to_refbank_data_migration_life1/Main.java`

Licence
-------

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program (LICENSE.txt); if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.


Acknowledgement
---------------

This software was developed as part of the [ViBRANT project](http://vbrant.eu).  
ViBRANT was funded by the European Union 7th Framework Programme within the Research Infrastructures group.  
Contract no. RI-261532. Period, Dec. 2010 to Nov. 2013.  
Coordinator: [Dr Vince Smith](mailto:vsmith.info).  
E-mail: [enquiries@vbrant.eu](mailto:enquiries@vbrant.eu)
