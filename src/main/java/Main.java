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

        List<String> posts = userPostUtilities.countUserPosts(mergedApi);
        System.out.println(posts);

        System.out.println(userPostUtilities.getNotUniquePostTitles(postDataFromApi));

        Map<User, User> neighbours = userPostUtilities.findNearestNeighbours(userInfoList);
        for (Map.Entry<User, User> n : neighbours.entrySet()) {
            System.out.println(n.getKey().getName() + " : " + n.getValue().getName());
        }

        System.out.println();
        int i = 0;
        for (User u : userInfoList) {
            System.out.println(++i + " " + u.getName() + u.getAddress().getGeo());
        }

        System.out.println();
        Map<User, Map<User, Double>> distanceMap = userPostUtilities.findEveryDistance(userInfoList);
        for (User u : distanceMap.keySet()) {
            System.out.println("\n" + u.getName() + " : " );
            for (Map.Entry<User, Double> map : distanceMap.get(u).entrySet())
                System.out.println(map.getKey().getName() + " : " + map.getValue());
            System.out.println("NEXT USER");
        }

    }
}
