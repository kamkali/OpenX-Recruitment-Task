import models.Post;
import models.User;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RestClient restClient = new RestClient();

        List<Post> postDataFromApi = restClient.getPostDataFromApi();
        List<User> userInfoList = restClient.getUserInfoDataFromApi();

        Map<User, List<Post>> mergedApi = UserPostUtilities.mergePostsWithUser(userInfoList, postDataFromApi);

        List<String> posts = UserPostUtilities.countUserPosts(mergedApi);
        System.out.println(posts);

        System.out.println(UserPostUtilities.getNotUniquePostTitles(postDataFromApi));

        Map<User, User> neighbours = UserPostUtilities.findNearestNeighbours(userInfoList);
        for (Map.Entry<User, User> n : neighbours.entrySet()) {
            System.out.println(n.getKey().getName() + " : " + n.getValue().getName());
        }
    }
}
