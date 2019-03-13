package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class RedditAPI {
    private static String url = "https://oauth.reddit.com/hot";

    public static void getRequest(String token) {
        System.out.println(token);
        GetRequest request = Unirest.get(url).header("Authorization", "bearer " + token).header("Accept", "application/json");
        System.out.println(request.getHeaders());

        HttpResponse<JsonNode> response = null;
        try {
            response = request.asJson();
        } catch (UnirestException e) {
            System.out.println("Trying too often");
        }

        System.out.println(response.getBody().toString());
    }

}
