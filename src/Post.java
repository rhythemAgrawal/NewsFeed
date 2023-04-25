import java.util.Comparator;

public class Post extends AbstractTextEntry {
    public Integer id;
    public Post(String content, User user) {
        super(content, user);

        this.id = MemoryDB.maxPostId + 1;
        MemoryDB.maxPostId++;
    }

    public Integer commentCount() {
        int count = 0;

        for(Comment comment: MemoryDB.comments.values()) {
            if(comment.getReplyOf() == this) {
                count++;
            }
        }

        return count;
    }

    static class PostComparator implements Comparator<Post>
    {
        public int compare(Post p1, Post p2)
        {
            if(p1.getUser().doesFollow(Main.loggedInUser.id) && !p2.getUser().doesFollow(Main.loggedInUser.id)) {
                return -1;
            } else if (p2.getUser().doesFollow(Main.loggedInUser.id) && !p1.getUser().doesFollow(Main.loggedInUser.id)) {
                return 1;
            }

            if(p1.getScore() > p2.getScore()) {
                return -1;
            } else if (p1.getScore() < p2.getScore()) {
                return 1;
            }

            if(p1.commentCount() > p2.commentCount()) {
                return -1;
            } else if (p1.commentCount() < p2.commentCount()) {
                return 1;
            }

            return p2.getPostedAt().compareTo(p1.getPostedAt());
        }
    }

}
