package org.arthan.semantic.desktop.sample;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
            return br.readLine();

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
