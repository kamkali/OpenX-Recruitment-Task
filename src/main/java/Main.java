import models.Post;
        import models.User;

        import java.io.IOException;
        import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        RestClient restClient = new RestClient();

        List<Post> postDataFromApi = restClient.getPostDataFromApi();
        List<User> userInfoList = restClient.getUserInfoDataFromApi();

        UserPostUtilities userPostUtilities = new UserPostUtilities();

        Map<User, List<Post>> mergedApi = userPostUtilities.mergePostsWithUser(userInfoList, postDataFromApi);

        for (Map.Entry<User, List<Post>> u: mergedApi.entrySet()) {
            System.out.println(u.getValue().size());
        }

        List<String> posts = userPostUtilities.countUserPosts(mergedApi);
        System.out.println(posts);
    }
}
