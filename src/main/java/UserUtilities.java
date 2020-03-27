import models.Post;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserUtilities {

    public Map<User, List<Post>> mergePostsWithUser(List<User> users, List<Post> posts) {
        Map<User, List<Post>> usersWithPosts = new HashMap<>();
        for(User u : users) {
            usersWithPosts.put(u,
                    posts.stream()
                    .filter(p -> p.getUserId().equals(u.getId()))
                    .collect(Collectors.toList()));
        }
        return usersWithPosts;
    }

    public List<String> countUserPosts(Map<User, List<Post>> userPosts) {
        List<String> userPostsCount = new ArrayList<>();
        for (Map.Entry<User, List<Post>> m : userPosts.entrySet()) {
            userPostsCount.add(m.getKey().getName() + " napisał(a) " + m.getValue().size() + " postów");
        }

        return userPostsCount;
    }
}
