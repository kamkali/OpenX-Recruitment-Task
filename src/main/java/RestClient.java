import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import models.Post;
import models.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * REST Client class responsible for collecting data from API and parsing them into model objects
 * @author Kamil Kaliś
 */

@Getter
@Setter
public class RestClient {
    private static final String POST_API_LINK = "https://jsonplaceholder.typicode.com/posts";
    private static final String USER_API_LINK = "https://jsonplaceholder.typicode.com/users";
    private URL postsURL;
    private URL usersURL;
    private Gson gson;

    /**
     * Rest Client constructor
     *
     * @throws MalformedURLException occurs when not legal protocol can be found using this URL
     *                               or string could not be parsed correctly
     */
    public RestClient() throws MalformedURLException {
        postsURL = new URL(POST_API_LINK);
        usersURL = new URL(USER_API_LINK);
        gson = new Gson();
    }

    /**
     * Function to collect data about posts from first endpoint and parse it to
     *
     * @return list of post data from API
     */
    public List<Post> getPostDataFromApi() {
        List<Post> postList = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(postsURL.openStream())) {
            postList = Arrays.asList(gson.fromJson(reader, Post[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postList;
    }

    /**
     * Function to collect data about users from second endpoint
     *
     * @return list of users data from API
     */
    public List<User> getUserInfoDataFromApi() {
        List<User> userList = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(usersURL.openStream())) {
            userList = Arrays.asList(gson.fromJson(reader, User[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
