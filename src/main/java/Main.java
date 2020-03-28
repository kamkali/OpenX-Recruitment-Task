import models.Post;
import models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Solution for OpenX recruitment task variant 2
 * @author Kamil Kaliś
 */

public class Main {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {

            // 1. Get data from API
            RestClient restClient = new RestClient();
            List<Post> postList = restClient.getPostDataFromApi();
            List<User> userList = restClient.getUserInfoDataFromApi();

            // Merge data
            Map<User, List<Post>> mergedApi = UserPostUtilities.mergePostsWithUsers(userList, postList);

            System.out.println("======================Section 1======================");
            writer.printf("======================Section 1======================%n");
            // 2. Count how many posts each user posted and return it as list
            List<String> posts = UserPostUtilities.countUserPosts(mergedApi);
            for (String s : posts) {
                System.out.println(s);
                writer.printf("%s%n", s);
            }

            System.out.println("======================Section 2======================");
            writer.printf("======================Section 2======================%n");

            // 3. Check for unique posts
            List<String> notUniquePosts = UserPostUtilities.getNotUniquePostTitles(postList);
            int numberOfNotUnique = notUniquePosts.size();
            if (numberOfNotUnique == 0) {
                System.out.println("All post titles are unique");
                writer.printf("All post titles are unique%n");
            }
            else {
                for (String s : notUniquePosts) {
                    System.out.println(s);
                    writer.printf("%s%n", s);
                }
            }

            System.out.println("======================Section 3======================");
            writer.printf("======================Section 3======================%n");

            // 4. Find closest user
            Map<User, User> neighbours = UserPostUtilities.findNearestNeighbours(userList);
            for (Map.Entry<User, User> n : neighbours.entrySet()) {
                System.out.println(n.getKey().getName() + " has nearest neighbor " + n.getValue().getName());
                writer.printf("%s has nearest neighbor %s%n", n.getKey().getName(), n.getValue().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
