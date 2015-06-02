package org.arthan.semantic.desktop.sample.service.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.arthan.semantic.desktop.sample.exceptions.ServerConnectionException;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.arthan.semantic.desktop.sample.service.HttpService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class HttpServiceImpl implements HttpService {

    private String httpWithBodyParams(String url, String params, String requestMethod) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Accept", "text/plain; charset=utf-8");

            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.write(params.getBytes(Charsets.UTF_8));
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println(new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine());
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

    /**
     * Возвращает список ресурсов, типы которых находятся в списке {@code objectClassesUri}
     * @param objectClassesUri список идентификаторов классов
     * @return список ресурсов для всех типов в {@code objectClassesUri}
     */
    @Override
    public List<GraphItem> resourcesForClasses(List<String> objectClassesUri) {
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
        try {

            resourcesJSON = sendGET(
                    "http://localhost:8080/semantic/restful/graph/class/instances",
                    "classes=" + paramString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resourcesJSON;
    }

    private static String sendGET(String urlString, String params) throws IOException {
        String urlSpec = urlString;
        if (!Strings.isNullOrEmpty(params)) {
            urlSpec += "?" + params;
        }

        URL httpUrl = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/plain; charset=utf-8");


        int responseCode = 0;
        try {
            responseCode = conn.getResponseCode();
        } catch (ConnectException e) {
            throw new ServerConnectionException();
        }

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException();
        }
        return new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream(), Charsets.UTF_8
                )
        ).readLine();
    }

    @Override
    public String addFile(String fileName, String predicateUri, String objectUri) {
        String params = Joiner.on("&").join(
                "filePath=" + fileName,
                "predicateURI=" + predicateUri,
                "objectURI=" + objectUri);
        String urlString = "http://localhost:8080/semantic/restful/graph/file";
        return httpWithBodyParams(urlString, params, "POST");
    }
}
