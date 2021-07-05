package com.app;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    //RESTcontroller to get the data
    @GetMapping("/getdata")
    public String load_the_data() throws IOException, JSONException {
        URL url_stopPointsDB = new URL("https://api.sl.se/api2/LineData.json?model=StopPoint&key=16e6ac2b112b4e9f8424697d432f846d&DefaultTransportModeCode=BUS");
        URL url_allBusLines = new URL("https://api.sl.se/api2/linedata.json?key=16e6ac2b112b4e9f8424697d432f846d&model=Line&DefaultTransportModeCode=BUS");
        URL url_busLinesPattern = new URL("https://api.sl.se/api2/linedata.json?key=16e6ac2b112b4e9f8424697d432f846d&model=JourneyPatternPointOnLine&DefaultTransportModeCode=BUS");

        DataFetcher fetcher = new DataFetcher(url_allBusLines, url_stopPointsDB, url_busLinesPattern);
        BusNetwork busNetwork = new BusNetwork(fetcher);

        return busNetwork.jsonOutput.get("Results").toString();
    }
}

@Controller
class testController {
    //rendering the results.html page wich contains the js to fetch data from the restcontroller above
    @GetMapping("/results")
    public String test(){
        return "results.html";
    }


}