package com.atlassian.interview.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StoryPointService implements iStoryPointService {

    @Value("${JIRA_BASE_URL}")
    private String jiraBaseUrl;

    @Value("${QUEUE_URL}")
    private String queueUrl;

    @Value("${QUEUE_NAME}")
    private String queueName;

    @Value("${JIRA_SEARCH_URL_PATH}")
    private String jiraSearchUrlPath;


    @Override
    public void getStoryPointsForSearchedQuery(String query, String name) {
        try {
            long totalStoryPoints =
                    getTotalStoryPointsForQuery(jiraBaseUrl + jiraSearchUrlPath + "?q=" + query);
            JsonObject body = new JsonObject();
            body.addProperty("name", name);
            body.addProperty("totalPoints", totalStoryPoints);
            postMessageInQueue(queueUrl + queueName + "?Action=SendMessage&MessageBody=" + body.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getTotalStoryPointsForQuery(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        long totalStoryPoints = 0;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("storyPoints")) {
                totalStoryPoints += Long.parseLong(inputLine.split(":")[1].trim());
            }
        }
        in.close();
        return totalStoryPoints;
    }

    private void postMessageInQueue(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.flush();
        wr.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
    }
}
