import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestClientTest {

    private List<Post> postList = new ArrayList<>();
    private List<UserInfo> userInfoList = new ArrayList<>();

    @BeforeEach
    void setUpData() {
        postList.add(new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        postList.add(new Post(1,2, "qui est esse",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"));
        userInfoList.add(new UserInfo(1, "name", "username", "email@email.com",
                new Address("Street", "Suite", "City", "30-303",
                        new Geo("lat", "Ing")),
                "+481234567", "https://website.net",
                new Company("Name", "Phrase", "bs")));
    }

    @Test
    void should_return_posts_data_from_api() throws IOException {
        RestClient restClient = Mockito.mock(RestClient.class);
        List<Post> result;

        Mockito.when(restClient.getPostDataFromApi()).thenReturn(postList);

        result = restClient.getPostDataFromApi();
        assertEquals(postList, result);
    }

    @Test
    void should_return_user_data_from_api() throws IOException {
        RestClient restClient = Mockito.mock(RestClient.class);
        List<UserInfo> result;

        Mockito.when(restClient.getUserInfoDataFromApi()).thenReturn(userInfoList);

        result = restClient.getUserInfoDataFromApi();
        assertEquals(userInfoList, result);
    }

    @Test
    void should_merge_post_and_user_data() {

    }
}