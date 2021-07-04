package com.app;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class test {


    public static void main(String[] args) throws IOException, ParseException {
        String allLinesDB = FileUtils.readFileToString(new File("output.json"), StandardCharsets.UTF_8);
        System.out.println(allLinesDB);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(allLinesDB);
        System.out.println(json.get("Results").toString());
    }

    public test() throws IOException {

    }
}
