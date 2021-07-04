package com.app;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;



public class BusNetwork {
    Object doc_busLines = "";
    Object doc_stopPoints = "";
    Object doc_linesPattern = "";
    Filter direction_filter = filter(where("DirectionCode").is("1"));
    List<String> allTheLines;
    Map<String,Integer> lineSize = new HashMap<String,Integer>();
    Map<String,Integer> lineSizeSorted = new LinkedHashMap<String, Integer>();
    JSONObject jsonOutput = new JSONObject();


    public BusNetwork(DataFetcher fetcher) throws IOException {
        this.doc_busLines = toObject(fetcher.getBusLines());
        this.doc_stopPoints = toObject(fetcher.getStopPoints());
        this.doc_linesPattern = toObject(fetcher.getLinesPattern());

        //Block to skip fetching and use local files instead
//        String allLinesDB = FileUtils.readFileToString(new File("allthelines.json"), StandardCharsets.UTF_8);
//        String infoLinesDB = FileUtils.readFileToString(new File("linedata.json"), StandardCharsets.UTF_8);
//        String infoStopDB = FileUtils.readFileToString(new File("stopdata-init.json"), StandardCharsets.UTF_8);
//
//
//        //Declare object to avoid parsing all the time
//        this.doc_busLines = Configuration.defaultConfiguration().jsonProvider().parse(allLinesDB);
//        this.doc_linesPattern = Configuration.defaultConfiguration().jsonProvider().parse(infoLinesDB);
//        this.doc_stopPoints = Configuration.defaultConfiguration().jsonProvider().parse(infoStopDB);

        this.allTheLines = getAllTheLines(doc_busLines);
        System.out.println("assigning Size...");
        assignSizeToLine(this.allTheLines);
        System.out.println("...Done");
        slashToTopN(this.lineSize,10);
        System.out.println("Generating Output File...");
        generateNetworkFile(this.lineSizeSorted);
        System.out.println("...Done");
    }

    //This one is to avoid having to parse the json string everytime
    public Object toObject(String str){
        return Configuration.defaultConfiguration().jsonProvider().parse(str);
    }

    //This one filters the raw "bus lines" to isolate the Line numbers
    public List<String> getAllTheLines(Object docBusLines){
        return JsonPath.read(docBusLines, "$.ResponseData.Result[*].LineNumber");
    }

    //for each line number assign the amount of stops using doc_linesPattern
    //A bit brutal, I could beforehand generate a bunch of objects "BusStops" and "BusLines" but a lot of them would end in oblivion
    public void assignSizeToLine(List<String> listOfLines){
        listOfLines.forEach((lineNr)->{
            Filter lineFilter = filter(where("LineNumber").is(lineNr));
            List<String> amountOfStops = JsonPath.read(this.doc_linesPattern,"$..Result[?].JourneyPatternPointNumber",lineFilter);
            this.lineSize.put(lineNr, amountOfStops.size());
        });
    }

    //sortering and isolate the top N of the map values
    public void slashToTopN(Map<String,Integer> lineSize, Integer n){
        lineSize.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .forEachOrdered(x->this.lineSizeSorted.put(x.getKey(),x.getValue()));
    }

    public void generateNetworkFile(Map<String,Integer> lineSize){
        ArrayList forFinal = new ArrayList<>(); //intermediate array list used for the final json output (dirty?)
        lineSize.keySet().forEach(lineNumber->{
            BusLine line = new BusLine(lineNumber, this.doc_linesPattern, this.doc_stopPoints);
            JSONObject jsonAllStopsForThisLine = new JSONObject(); //temp json  to fill the final one
            jsonAllStopsForThisLine.put("Line", line.getLineName());
            jsonAllStopsForThisLine.put("Stops",line.getStops());
            forFinal.add(jsonAllStopsForThisLine);
        });
        this.jsonOutput.put("Result",forFinal);
    }





}
