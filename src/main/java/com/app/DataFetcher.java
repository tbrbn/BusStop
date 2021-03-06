package com.app;

import org.apache.tomcat.util.codec.binary.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class DataFetcher {
    String busLines = "";
    String stopPoints = "";
    String linesPattern = "";

    public DataFetcher(URL url_busLines, URL url_stopPoints, URL url_linesPattern) throws IOException {
        System.out.println("startFetching...");
        this.busLines = url_to_str(url_busLines);
        System.out.println("1/3...");
        this.stopPoints = url_to_str(url_stopPoints);
        System.out.println("2/3...");
        this.linesPattern = url_to_str(url_linesPattern);
        System.out.println("3/3 - All fetched!");
    }

    public String getBusLines() {
        return busLines;
    }

    public String getLinesPattern() {
        return linesPattern;
    }

    public String getStopPoints() {
        return stopPoints;
    }

    public String url_to_str(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        //Integer responseStream = connection.getResponseCode(); //in case one need to handle issues
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return content.toString();
    }

}
