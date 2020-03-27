import models.Post;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserPostUtilities {

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

    private boolean isPostTitlesUnique(List<Post> postList) {
        return (postList.stream()
                .map(Post::getTitle)
                .distinct().count() == postList.size());
    }

    public List<String> getNotUniquePostTitles(List<Post> postList) {
        if (isPostTitlesUnique(postList))
            return Collections.emptyList();

        return postList.stream()
                .map(Post::getTitle)
                .filter(p -> Collections.frequency(
                        postList.stream()
                        .map(Post::getTitle)
                        .collect(Collectors.toList()), p) > 1)
                .distinct()
                .collect(Collectors.toList());
    }
}
