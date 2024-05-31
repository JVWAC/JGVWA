package com.jvwac.jgvwa.controller;

import io.swagger.annotations.ApiOperation;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@RequestMapping(value = "/ssrf")
@RestController
public class SSRFController {

    @ApiOperation(value = "org.apache.commons.io.IOUtils#toByteArray", notes = "https://mvnrepository.com/artifact/commons-io/commons-io")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public byte[] V1(@RequestParam("url") String url) throws Exception {
        return IOUtils.toByteArray(new URL(url));
    }

    @ApiOperation(value = "java.net.URLConnection#openConnection")
    @RequestMapping(value = "/v2a", method = RequestMethod.GET)
    public String V2A(@RequestParam("url") String url) throws Exception {
        URLConnection connection = new URL(url).openConnection();
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = buff.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    @ApiOperation(value = "java.net.HttpURLConnection#openConnection")
    @RequestMapping(value = "/v2b", method = RequestMethod.GET)
    public String V2B(@RequestParam("url") String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = buff.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    @ApiOperation(value = "org.apache.commons.httpclient.HttpClient#executeMethod", notes = "https://mvnrepository.com/artifact/commons-httpclient/commons-httpclient")
    @RequestMapping(value = "/v3", method = RequestMethod.GET)
    public byte[] V3(@RequestParam("url") String url) throws Exception {
        GetMethod method = new GetMethod(url);
        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(method);
        return method.getResponseBody();
    }

    @ApiOperation(value = "org.apache.http.impl.client.CloseableHttpClient#execute", notes = "https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient")
    @RequestMapping(value = "/v4", method = RequestMethod.GET)
    public String V4(@RequestParam("url") String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return "null";
    }

    @ApiOperation(value = "org.springframework.web.client.RestTemplate#getForEntity")
    @RequestMapping(value = "/v5", method = RequestMethod.GET)
    public String V5(@RequestParam("url") String url) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    @ApiOperation(value = "okhttp3.Call#execute", notes = "https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp")
    @RequestMapping(value = "/v6", method = RequestMethod.GET)
    public String V6(@RequestParam("url") String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.toString();
    }
}
