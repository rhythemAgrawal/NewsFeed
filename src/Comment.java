public class Comment extends AbstractTextEntry{
    private AbstractTextEntry replyOf;
    public Integer id;

    public Comment(String content, User user, AbstractTextEntry replyOf) {
        super(content, user);

        this.replyOf = replyOf;

        this.id = MemoryDB.maxCommentId + 1;
        MemoryDB.maxCommentId++;
    }

    public AbstractTextEntry getReplyOf() {
        return replyOf;
    }
}
