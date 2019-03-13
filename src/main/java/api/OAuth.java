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
    private final String GET_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    private String REDDIT_APP_ID;
    private String REDDIT_SECRET;
    private String REDDIT_USER;
    private String REDDIT_PASS;
    private Properties props = new Properties();
    private FileInputStream config;
    private String token;

    public void getToken() throws UnirestException, IOException {
        //Get path to config file at runtime.
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config.properties";
        config = new FileInputStream(rootPath);
        props.load(config);

        setVars();

        HttpResponse<JsonNode> response = Unirest.post(GET_TOKEN_URL).basicAuth(REDDIT_APP_ID, REDDIT_SECRET)
                .header("Content-Type", "x-www-form-urlencoded")
                .header("User-Agent", "desktop:atkinty.java.NeverMissReddit/0.2 (by u/R4nd0mnumbrz)")
                .queryString("grant_type", "password") //Wow this took forever to find out...
                .queryString("username", REDDIT_USER)
                .queryString("password", REDDIT_PASS)
                .asJson();

        token = response.getBody().getObject().getString("access_token");
        setToken(token);
    };

    private void setVars() {
        REDDIT_APP_ID = props.getProperty("REDDIT_APP_ID");
        REDDIT_SECRET = props.getProperty("REDDIT_SECRET");
        REDDIT_USER = props.getProperty("REDDIT_USER");
        REDDIT_PASS = props.getProperty("REDDIT_PASS");
    }

    private void setToken(String token) {
        RedditAPI.token = token;
    }

    private void refreshToken(String token) throws IOException, UnirestException {
        getToken();

    }

    private void setRefreshTimer(int timer) {

    }
}
