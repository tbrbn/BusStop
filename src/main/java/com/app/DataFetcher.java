package com.app;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
        String output = "";
        //BLOCK TO COMMENT WHEN RUNNING LOCAL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        Integer responseStream = connection.getResponseCode();
        if (responseStream != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseStream);
        } else {
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                output+=scanner.nextLine();
            }
            scanner.close();
        }
        connection.disconnect();
        //END OF BLOCK
        System.out.println(output);
        return output;
    }

}
