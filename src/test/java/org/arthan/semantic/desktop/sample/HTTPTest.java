package org.arthan.semantic.desktop.sample;

import com.google.common.base.Charsets;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by artur.shamsiev on 25.05.2015
 */
public class HTTPTest {
    @Test
    public void testConnection() throws Exception {
        String spec = "http://localhost:8080/semantic/restful/music";
        URL url = new URL(spec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "text/plain; charset=utf-8");
        String urlParams = "id=vallk";

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.write(urlParams.getBytes(Charsets.UTF_8));
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        conn.disconnect();

    }
}
