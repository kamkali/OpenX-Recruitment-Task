import com.google.gson.Gson;
import models.Post;
import models.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestClient {
    private URL postsURL;
    private URL usersURL;
    private Gson gson;

    public RestClient() {
        try {
            usersURL = new URL("https://jsonplaceholder.typicode.com/users");
            postsURL = new URL("https://jsonplaceholder.typicode.com/posts");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to API. Exiting...");
            System.exit(1);
        }
        gson = new Gson();
    }

    public List<Post> getPostDataFromApi() {
        List<Post> postList = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(postsURL.openStream())) {
            postList = Arrays.asList(gson.fromJson(reader, Post[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<User> getUserInfoDataFromApi() {
        List<User> userInfoList = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(usersURL.openStream())) {
            userInfoList = Arrays.asList(gson.fromJson(reader, User[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfoList;
    }
}
