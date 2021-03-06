package api;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.client.HttpClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class OAuth {
    private static final String GET_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    private static String REDDIT_AGENT;
    private static String REDDIT_APP_ID;
    private static String REDDIT_SECRET;
    private static String REDDIT_USER;
    private static String REDDIT_PASS;
    private static Properties props = new Properties();
    private static FileInputStream config;

    public static void init() throws FileNotFoundException, IOException {
        //Get path to config file at runtime.
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config.properties";
        config = new FileInputStream(rootPath);
        props.load(config);

        setVars();
        Unirest.setDefaultHeader("User-Agent", REDDIT_AGENT);
    }

    public static void getToken() throws UnirestException, IOException {
        HttpRequestWithBody request = Unirest.post(GET_TOKEN_URL).basicAuth(REDDIT_APP_ID, REDDIT_SECRET);
        request.header("Content-Type", "x-www-form-urlencoded");

        System.out.println(request.getHeaders());

        HttpResponse<JsonNode> response = Unirest.post(GET_TOKEN_URL).basicAuth(REDDIT_APP_ID, REDDIT_SECRET)
                .header("Content-Type", "x-www-form-urlencoded")
                .queryString("grant_type", "password") //Wow this took forever to find out...
                .queryString("username", REDDIT_USER)
                .queryString("password", REDDIT_PASS)
                .asJson();

        setToken(response.getBody().getObject().getString("access_token"));
    };

    private static void setVars() {
        REDDIT_APP_ID = props.getProperty("REDDIT_APP_ID");
        REDDIT_SECRET = props.getProperty("REDDIT_SECRET");
        REDDIT_USER = props.getProperty("REDDIT_USER");
        REDDIT_PASS = props.getProperty("REDDIT_PASS");
        REDDIT_AGENT = props.getProperty("REDDIT_AGENT");
    }

    private static void setToken(String token) {
        if (token != null)
            RedditAPI.token = token;
        else
            System.out.println("Unable to get a token");
    }

    private static void refreshToken(String token) throws IOException, UnirestException {
        getToken();

    }

    private static void setRefreshTimer(int timer) {

    }
}
