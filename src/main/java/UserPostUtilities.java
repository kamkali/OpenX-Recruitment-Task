import models.Post;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserPostUtilities {
    public static final double R = 6371.8; // mean Earth radius in km

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

    public Map<User, User> findNearestNeighbours(List<User> userList) {
        Map<User, User> userMap = new HashMap<>();
        for (User user : userList) {
            userMap.put(user,
                    userList.stream()
                            .filter(u -> !u.equals(user))
                            .min(Comparator.comparing(u -> calculateDistance(user, u)))
                            .get());
        }
        return userMap;
    }

    public Map<User, Map<User, Double>> findEveryDistance(List<User> userList) {
        Map<User, Map<User, Double>> resultMap = new HashMap<>();
        Map<User, Double> distanceMap = new HashMap<>();

        for (User user : userList){
            for (User u : userList){
                Double distance = calculateDistance(user, u);
                distanceMap.put(u, distance);
            }
            resultMap.put(user,distanceMap);
        }
        return resultMap;
    }

    /**
     * Calculate the 'great circle' distance between two users
     * using Haversine formula
     * a = sin²(ΔlatDifference/2) + cos(lat1).cos(lt2).sin²(ΔlonDifference/2)
     * c = 2*atan2(√a, √(1−a))
     * d = R*c
     *
     * @return
     */
    private double calculateDistance(User user1, User user2) {
        double latU1 = Double.parseDouble(user1.getAddress().getGeo().getLat());
        double latU2 = Double.parseDouble(user2.getAddress().getGeo().getLat());

        double lngU1 = Double.parseDouble(user1.getAddress().getGeo().getLng());
        double lngU2 = Double.parseDouble(user2.getAddress().getGeo().getLng());


        double deltaLat = Math.toRadians(latU2 - latU1);
        double deltaLng = Math.toRadians(lngU2 - lngU1);

        double a = Math.pow(Math.sin(deltaLat / 2), 2) * Math.cos(latU1) * Math.cos(latU2) * Math.pow(Math.sin(deltaLng/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
