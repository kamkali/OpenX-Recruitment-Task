import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.internal.matchers.Equality.areEqual;

public class UserPostUtilitiesTest {

    private List<Post> postList = new ArrayList<>();
    private List<Post> uniquePostList = new ArrayList<>();
    private List<User> userInfoList = new ArrayList<>();

    @BeforeEach
    void setUpTestData() {
        postList.add(new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        postList.add(new Post(1, 2, "qui est esse",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"));
        postList.add(new Post(2, 1, "!!!UniqueTitle!!! sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        postList.add(new Post(2, 2, "qui est esse",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"));

        uniquePostList.add(new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        uniquePostList.add(new Post(1, 2, "qui est esse",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"));
        uniquePostList.add(new Post(2, 1, "!!!UniqueTitle!!! sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));

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
        assertEquals(2, UserPostUtilities.mergePostsWithUsers(userInfoList, postList).size());
    }

    @Test
    void should_count_user_posts() {
        List<String> output = new ArrayList<>();
        Map<User, List<Post>> mergedApi = UserPostUtilities.mergePostsWithUsers(userInfoList, postList);

        for (Map.Entry<User, List<Post>> m : mergedApi.entrySet()) {
            output.add(m.getKey().getName() + " napisał(a) " + m.getValue().size() + " postów");
        }

        assertEquals(output, UserPostUtilities.countUserPosts(mergedApi));
    }

    @Test
    void should_return_not_unique_post_titles() {
        List<String> notUniqueTitles = new ArrayList<>();
        notUniqueTitles.add("qui est esse");

        assertEquals(notUniqueTitles, UserPostUtilities.getNotUniquePostTitles(postList));
    }

    @Test
    void should_return_empty_list_when_unique() {
        assertEquals(0, UserPostUtilities.getNotUniquePostTitles(uniquePostList).size());
    }

    @Test
    void should_calculate_distance_properly() {
        User user1 = new User(1, null, null, null, new Address(null, null, null, null,
                new Geo("10", "10")), null, null, null);

        User user2 = new User(2, null, null, null, new Address(null, null, null, null,
                new Geo("-10", "10")), null, null, null);

        User user3 = new User(3, null, null, null, new Address(null, null, null, null,
                new Geo("10", "-10")), null, null, null);
         /*
            User1 : User3
            User2 : User1
            User3 : User1
         */
        Map<User, User> expectedMap = new HashMap<>();
        expectedMap.put(user1, user3);
        expectedMap.put(user2, user1);
        expectedMap.put(user3, user1);

        Map<User, User> resultMap = UserPostUtilities.findNearestNeighbours(Arrays.asList(user1, user2, user3));

        assertTrue(areEqual(expectedMap, resultMap));
    }
}
