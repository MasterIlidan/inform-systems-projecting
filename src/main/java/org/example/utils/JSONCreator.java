package org.example.utils;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.Scanner;

public class JSONCreator {
    public static void main(String[] args) {
        JSONObject errors = new JSONObject();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] lines = scanner.nextLine().split("\t");
            if (lines.length < 2) {
                break;
            }
            errors.put(lines[0], lines[1]);
        }

        try (FileWriter file = new FileWriter("errors.json")) {
            file.write(errors.toJSONString());
            System.out.println("JSON file created!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
