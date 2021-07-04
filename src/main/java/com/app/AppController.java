package com.app;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @GetMapping("/data")
    public String load_the_data() throws IOException, JSONException {
        URL url_stopPointsDB = new URL("https://api.sl.se/api2/LineData.json?model=StopPoint&key=16e6ac2b112b4e9f8424697d432f846d&DefaultTransportModeCode=BUS");
        URL url_allBusLines = new URL("https://api.sl.se/api2/linedata.json?key=16e6ac2b112b4e9f8424697d432f846d&model=Line&DefaultTransportModeCode=BUS");
        URL url_busLinesPattern = new URL("https://api.sl.se/api2/linedata.json?key=16e6ac2b112b4e9f8424697d432f846d&model=JourneyPatternPointOnLine&DefaultTransportModeCode=BUS");

        DataFetcher fetcher = new DataFetcher(url_allBusLines, url_stopPointsDB, url_busLinesPattern);
        BusNetwork busNetwork = new BusNetwork(fetcher);

        FileWriter output_file = new FileWriter("./src/main/resources/public/output.json");
        output_file.write(busNetwork.jsonOutput.get("Results").toString());
        output_file.close();
        return "data.html";
    }

    @GetMapping("test")
    public String test(){
        return "test.html";
    }


}