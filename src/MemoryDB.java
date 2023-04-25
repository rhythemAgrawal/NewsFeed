import java.util.HashMap;
import java.util.Map;

public class MemoryDB {
    public static Map<Integer, User> users = new HashMap<Integer, User>();
    public static Integer maxUserId = 0;
    public static Map<Integer, Post> posts = new HashMap<Integer, Post>();
    public static Integer maxPostId = 0;
    public static Map<Integer, Comment> comments = new HashMap<Integer, Comment>();
    public static Integer maxCommentId = 0;
}
