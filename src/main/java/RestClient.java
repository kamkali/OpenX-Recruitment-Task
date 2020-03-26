import com.google.gson.Gson;
import models.Post;
import models.UserInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RestClient {
    private final URL postsURL;
    private final URL usersURL;
    private Gson gson;

    public RestClient() throws MalformedURLException {
        usersURL = new URL("https://jsonplaceholder.typicode.com/users");
        postsURL = new URL("https://jsonplaceholder.typicode.com/posts");
        gson = new Gson();
    }

    public List<Post> getPostDataFromApi() throws IOException {
        InputStreamReader reader = new InputStreamReader(postsURL.openStream());
        return Arrays.asList(gson.fromJson(reader, Post[].class));
    }

    public List<UserInfo> getUserInfoDataFromApi() throws IOException {
        InputStreamReader reader = new InputStreamReader(usersURL.openStream());
        return Arrays.asList(gson.fromJson(reader, UserInfo[].class));
    }


}
