package com.app;

import com.jayway.jsonpath.JsonPath;

import java.util.List;

public class BusStop {
    String stopName;
    String stopID;
    Object document;
    public BusStop(String stopID, Object stopData){
        this.stopID = stopID;
        this.document=stopData;
        setStopName(this.stopID);
    }

    public void setStopName(String stopCode){
        try {
            List<String> tmp_name = JsonPath.read(this.document,"$..Result[?(@.StopPointNumber=~/"+stopCode+"/)].StopPointName");
            this.stopName = tmp_name.get(0);
        }
        catch (Exception e){
            //Some stops don't have name (outdated perhaps?) so instead return a n/a + code
            this.stopName = "n/a-"+stopCode;
        }
    }

    public String getStopID() {
        return this.stopID;
    }

    public String getStopName() {
        return this.stopName;
    }
}
