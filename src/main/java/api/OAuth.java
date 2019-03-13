package api;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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

    public String getToken() throws UnirestException, IOException {

        //Get path to config file at runtime.
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config.properties";
        config = new FileInputStream(rootPath);
        props.load(config);

        setVars();

        String body = String.format("{\"grant_type\": \"password\", \"username\": \"%s\", \"password\": \"%s\"}", REDDIT_USER, REDDIT_PASS);

        Unirest.clearDefaultHeaders();

        HttpResponse<JsonNode> response = Unirest.post(GET_TOKEN_URL).basicAuth(REDDIT_APP_ID, REDDIT_SECRET)
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "atkinty:atkinty.NeverMissReddit/0.2 (by u/R4nd0mnumbrz)")
                .body(body)
                .asJson();

        return response.getBody().toString();
    };

    private void setVars() {
        REDDIT_APP_ID = props.getProperty("REDDIT_APP_ID");
        REDDIT_SECRET = props.getProperty("REDDIT_SECRET");
        REDDIT_USER = props.getProperty("REDDIT_USER");
        REDDIT_PASS = props.getProperty("REDDIT_PASS");
    }
}
