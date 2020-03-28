import models.Post;
import models.User;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // 1. Get data from API
        RestClient restClient = new RestClient();
        List<Post> postList = restClient.getPostDataFromApi();
        List<User> userList = restClient.getUserInfoDataFromApi();

        // Merge data
        Map<User, List<Post>> mergedApi = UserPostUtilities.mergePostsWithUsers(userList, postList);

        System.out.println("======================Zadanie 1======================");
        // 2. Count how many posts each user posted and return it as list
        List<String> posts = UserPostUtilities.countUserPosts(mergedApi);
        for (String s : posts)
            System.out.println(s);

        System.out.println("======================Zadanie 2======================");

        // 3. Check for unique posts
        List<String> notUniquePosts = UserPostUtilities.getNotUniquePostTitles(postList);
        int numberOfNotUnique = notUniquePosts.size();
        if (numberOfNotUnique == 0)
            System.out.println("All post titles are unique");
        else {
            for (String s : notUniquePosts)
                System.out.println(s);
        }

        System.out.println("======================Zadanie 3======================");

        // 4. Find closest user
        Map<User, User> neighbours = UserPostUtilities.findNearestNeighbours(userList);
        for (Map.Entry<User, User> n : neighbours.entrySet()) {
            System.out.println(n.getKey().getName() + " has nearest neighbor " + n.getValue().getName());
        }
    }
}
