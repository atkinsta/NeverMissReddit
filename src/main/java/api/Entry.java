package api;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class Entry {

    public static void main (String[] args) {
        try {
            OAuth.init();
            OAuth.getToken();
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }

        RedditAPI.getRequest("/hot");
    }
}

