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

package common.code;

import ConfigurationParameters.CommonConfigData;

public class Proxy {

    public static void setUpProxy() {
        //    If you require HTTP/S access to remote services from behind the OU firewall 
        //    (on campus) the client has to use the University's http proxy;
        //    The relevant values are defined in ConfigurationParameters.CommonConfigData.class
        if (CommonConfigData.PROXY_HOSTNAME != null) {
            System.setProperty("http.proxyHost", CommonConfigData.PROXY_HOSTNAME);
            System.setProperty("http.proxyPort", CommonConfigData.PROXY_PORT);
        }
    }
}
