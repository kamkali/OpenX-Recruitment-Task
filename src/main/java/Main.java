import models.Post;
import models.UserInfo;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        RestClient restClient = new RestClient();

        List<Post> postDataFromApi = restClient.getPostDataFromApi();
        List<UserInfo> userInfoList = restClient.getUserInfoDataFromApi();

        System.out.println(postDataFromApi.size());

        for (UserInfo u : userInfoList)
            System.out.println(u);
    }
}
