package org.arthan.semantic.desktop.sample.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Arthur Shamsiev on 29.05.15.
 * Using IntelliJ IDEA
 * Project - semantic.desktop
 */
public class JsonUtils {
    public static String parseAnswer(String answer) {
        JSONParser parser = new JSONParser();
        JSONObject jsonAnswer;
        try {
            jsonAnswer = (JSONObject) parser.parse(answer);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jo = (JSONObject) jsonAnswer.get("answer");
        return (String) jo.get("status");
    }
}
