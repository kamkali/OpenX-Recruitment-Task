import models.Post;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserPostUtilities {
    public static final double R = 6371.8; // mean Earth radius in km

    private UserPostUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<User, List<Post>> mergePostsWithUser(List<User> users, List<Post> posts) {
        Map<User, List<Post>> usersWithPosts = new HashMap<>();
        for(User user : users) {
            usersWithPosts.put(user,
                    posts.stream()
                    .filter(p -> p.getUserId().equals(user.getId()))
                    .collect(Collectors.toList()));
        }
        return usersWithPosts;
    }

    public static List<String> countUserPosts(Map<User, List<Post>> userPosts) {
        List<String> userPostsCount = new ArrayList<>();
        for (Map.Entry<User, List<Post>> m : userPosts.entrySet()) {
            userPostsCount.add(m.getKey().getName() + " napisał(a) " + m.getValue().size() + " postów");
        }
        return userPostsCount;
    }

    private static boolean isPostTitlesUnique(List<Post> postList) {
        return (postList.stream()
                .map(Post::getTitle)
                .distinct().count() == postList.size());
    }

    public static List<String> getNotUniquePostTitles(List<Post> postList) {
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

    public static Map<User, User> findNearestNeighbours(List<User> userList) {
        Map<User, User> userMap = new HashMap<>();
        for (User user : userList) {
            Optional<User> closest = userList.stream()
                    .filter(u -> !u.equals(user))
                    .min(Comparator.comparing(u -> calculateDistance(user, u)));

            userMap.put(user, closest.orElse(null));
        }
        return userMap;
    }

    /**
     * Calculate the 'great circle' distance between two users using Haversine formula
     *
     * a = sin²(ΔlatDifference/2) + cos(lat1).cos(lt2).sin²(ΔlonDifference/2)
     * c = 2*asin(√a)
     * d = R*c
     *
     * @return distance between user1 and user2
     */
    private static double calculateDistance(User user1, User user2) {
        double dLatDiff = Math.toRadians(
                Double.parseDouble(user2.getAddress().getGeo().getLat()) -
                        Double.parseDouble(user1.getAddress().getGeo().getLat()));
        double dLngDiff = Math.toRadians(
                Double.parseDouble(user2.getAddress().getGeo().getLng()) -
                        Double.parseDouble(user1.getAddress().getGeo().getLng()));

        double lat1 = Math.toRadians(Double.parseDouble(user1.getAddress().getGeo().getLat()));
        double lat2 = Math.toRadians(Double.parseDouble(user2.getAddress().getGeo().getLat()));

        double a = Math.pow(Math.sin(dLatDiff / 2), 2) +
                Math.pow(Math.sin(dLngDiff / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
