import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private ArrayList<User> follows;
    public Integer id;

    public static class UserBuilder {
        private String userName;
        private String password;
        private ArrayList<User> follows;

        public User build() {
            return new User(this);
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }
    }

    private User(UserBuilder builder) {
        // Validation
        for(User user: MemoryDB.users.values()) {
            if(user.userName == builder.userName) {
                throw new IllegalArgumentException();
            }
        }

        // Set attr.
        this.userName = builder.userName;
        this.password = builder.password;

        this.id = MemoryDB.maxUserId + 1;
        MemoryDB.maxUserId++;
    }

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public static User login(String userName, String password) {
        for(User user: MemoryDB.users.values()) {
            if(user.userName == userName) {
                if(user.password == password) {
                    return user;
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }

        throw new IllegalArgumentException();
    }

    public void follow(Integer userId) {
        User userToFollow = MemoryDB.users.get(userId);
        this.follows.add(userToFollow);
    }

    public boolean doesFollow(Integer userId) {
        for(User user: follows) {
            if(user.id == userId) {
                return true;
            }
        }

        return false;
    }

    public void showNewsFeed() {
        List<Post> posts = MemoryDB.posts.values().stream().toList();;
        Collections.sort(posts, new Post.PostComparator());

        for(Post post: posts) {
            System.out.println(post.id);
            System.out.println(post.getContent());
            System.out.println(post.getFormattedTimeElapsed());
        }
    }
}
