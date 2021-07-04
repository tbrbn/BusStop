package com.app;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import java.util.*;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

public class BusLine {
    String lineNumber;
    List<String> stopList = new ArrayList<String>();
    Filter lineFilter;
    public BusLine(String lineNumber, Object lineData, Object stopData){
        this.lineNumber = lineNumber;
        this.lineFilter = filter(where("LineNumber").is(this.lineNumber));
        generateDaList(lineData,stopData);
    }

    public String getLineName() {
        return lineNumber;
    }

    public List<String> getStops() {
        return this.stopList;
    }

    public void generateDaList(Object lineData, Object stopData){
        List<String> allTheStopsRaw = JsonPath.read(lineData,"$..Result[?].JourneyPatternPointNumber",this.lineFilter);
        allTheStopsRaw.forEach(stopID->{
            BusStop stop = new BusStop(stopID,stopData);
            this.stopList.add(stop.getStopName());
        });
        removeDuplicates();
    }

    public void removeDuplicates(){
        Set<String> set = new LinkedHashSet<>(this.stopList);
        this.stopList = new ArrayList<>(set);
    }



}
