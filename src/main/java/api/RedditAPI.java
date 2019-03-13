package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class RedditAPI {
    private static String url = "https://oauth.reddit.com/hot";
    public static String token;

    public static void getRequest() {
        System.out.println(token);
        GetRequest request = Unirest.get(url).header("Authorization", "bearer " + token).header("Accept", "application/json");
        System.out.println(request.getHeaders());

        HttpResponse<JsonNode> response = null;
        try {
            response = request.asJson();
        } catch (UnirestException e) {
            try {
                HttpResponse<String> stringResponse = request.asString();
                System.out.println(stringResponse.getBody());
            } catch (UnirestException er) {
                System.out.println("wtf");
            }
        }

        System.out.println(response.getBody().toString());
    }

}
