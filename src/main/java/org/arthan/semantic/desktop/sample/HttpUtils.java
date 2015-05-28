package org.arthan.semantic.desktop.sample;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 25.05.2015
 */
public class HttpUtils {

    public static String post(String url, String params) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "text/plain; charset=utf-8");

            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.write(params.getBytes(Charsets.UTF_8));
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String result = br.readLine();
            conn.disconnect();
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<GraphItem> anotherResourcesFor(FILE_TYPE type, String uri) {
        if (type == FILE_TYPE.DOCUMENT && uri.equals(DC.creator.getURI())) {
            return allContacts();
        }
        return Lists.newArrayList();
    }

    private static List<GraphItem> allContacts() {
        List<GraphItem> resultList = Lists.newArrayList();
        String contactsJSON = findAllContacts();
        JSONParser parser = new JSONParser();
        JSONObject jsonAnswer;
        try {
            jsonAnswer = (JSONObject) parser.parse(contactsJSON);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray jArray = (JSONArray) jsonAnswer.get("contacts");
        for (Object aJArray : jArray) {
            JSONObject contact = (JSONObject) aJArray;
            resultList.add(new GraphItem(
                    (String) contact.get("label"),
                    (String) contact.get("uri")
            ));
        }
        return resultList;
    }

    private static String findAllContacts() {
        String urlString = "http://localhost:8080/semantic/restful/contacts/all";
        try {
            BufferedReader br = readerGET(urlString);
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<GraphItem> resourcesForClasses(List<String> objectClassesUri) {
        String paramString = Joiner.on(',').join(objectClassesUri);
        String resourcesJSON = getResourcesJson(paramString);

        return parseResourceJSON(resourcesJSON);
    }

    private static List<GraphItem> parseResourceJSON(String resourcesJSON) {
        JSONParser parser = new JSONParser();
        JSONObject jsonAnswer;
        try {
            jsonAnswer = (JSONObject) parser.parse(resourcesJSON);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<GraphItem> resultList = Lists.newArrayList();
        JSONArray jArray = (JSONArray) jsonAnswer.get("resources");
        for (Object aJArray : jArray) {
            JSONObject contact = (JSONObject) aJArray;
            resultList.add(new GraphItem(
                    (String) contact.get("label"),
                    (String) contact.get("uri")
            ));
        }
        return resultList;
    }

    private static String getResourcesJson(String paramString) {
        String resourcesJSON;
        BufferedReader reader;
        try {
            reader = readerGET(
                    "http://localhost:8080/semantic/restful/graph/class/instances",
                    "classes=" + paramString);
            resourcesJSON = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resourcesJSON;
    }

    private static BufferedReader readerGET(String urlString) throws IOException {
        return readerGET(urlString, null);
    }

    private static BufferedReader readerGET(String urlString, String params) throws IOException {
        String urlSpec = urlString;
        if (!Strings.isNullOrEmpty(params)) {
            urlSpec += "?" + params;
        }

        URL httpUrl = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/plain; charset=utf-8");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException();
        }
        return new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream(), Charsets.UTF_8
                )
        );
    }
}
