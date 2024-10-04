package org.example.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JSONReader {
    //получить данные для лабораторной работы 2
    public static HashMap<Integer,Integer> getDataFromJSONLaba2(String filename) throws IOException, ParseException {
        HashMap<Integer,Integer> stringList = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser
                .parse(new FileReader(filename));
        JSONObject jsonObject = (JSONObject) object;
        for (Object key : jsonObject.keySet()) {
            stringList.put(Integer.parseInt((String)key),
                    Integer.parseInt((String) jsonObject.get(key)));
        }
        return stringList;
    }
    //получить данные для лабораторной работы 3
    public static JSONObject getDataFromJSONLaba3(String filename) throws IOException, ParseException {
        HashMap<String, Integer> stringList = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(new FileReader(filename));
        JSONObject jsonObject = (JSONObject) object;
        return jsonObject;
    }
}
