package api;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class Entry {
    public static void main (String[] args) throws IOException {
        String token = null;
        OAuth auth = new OAuth();
        try {
            token = auth.getToken();
        } catch (UnirestException err) {
            err.printStackTrace();
        }

        System.out.println(token);
    }
}

