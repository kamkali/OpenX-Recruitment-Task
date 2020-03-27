import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUtilitiesTest {

    private List<Post> postList = new ArrayList<>();
    private List<User> userInfoList = new ArrayList<>();

    @BeforeEach
    void setUpData() {
        postList.add(new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        postList.add(new Post(1, 2, "qui est esse",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"));
        postList.add(new Post(2, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        postList.add(new Post(2, 2, "qui est esse",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"));


        userInfoList.add(new User(1, "name", "username", "email@email.com",
                new Address("Street", "Suite", "City", "30-303",
                        new Geo("lat", "Ing")),
                "+481234567", "https://website.net",
                new Company("Name", "Phrase", "bs")));
        userInfoList.add(new User(2, "name", "username", "email@email.com",
                new Address("Street", "Suite", "City", "30-303",
                        new Geo("lat", "Ing")),
                "+481234567", "https://website.net",
                new Company("Name", "Phrase", "bs")));
    }

    @Test
    void should_merge_post_and_user_data() {
        UserUtilities tool = new UserUtilities();
        assertEquals(2, tool.mergePostsWithUser(userInfoList, postList).size());
    }

    @Test
    void should_count_user_posts() {
        UserUtilities userUtilities = new UserUtilities();
        List<String> output = new ArrayList<>();
        Map<User, List<Post>> mergedApi = userUtilities.mergePostsWithUser(userInfoList, postList);

        for (Map.Entry<User, List<Post>> m : mergedApi.entrySet()) {
            output.add(m.getKey().getName() + " napisał(a) " + m.getValue().size() + " postów");
        }

        assertEquals(output, userUtilities.countUserPosts(mergedApi));
    }
}
