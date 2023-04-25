import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

abstract class AbstractTextEntry {
    private String content;
    private User user;
    private Integer score = 0;
    private LocalDateTime postedAt = LocalDateTime.now();

    public synchronized void upvote() {
        this.score += 1;
    }

    public synchronized void downvote() {
        this.score -= 1;
    }

    public AbstractTextEntry(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Integer getScore() {
        return score;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public String getContent() {
        return content;
    }

    public String getFormattedTimeElapsed() {
        Period period = Period.between(this.postedAt.toLocalDate(), LocalDateTime.now().toLocalDate());
        Duration duration = Duration.between(this.postedAt, LocalDateTime.now());

        if (period.getYears() != 0) {
            return String.format("Posted %d years ago", period.getYears());
        } else if (period.getMonths() != 0) {
            return String.format("Posted %d months ago", period.getMonths());
        } else if (period.getDays() != 0) {
            return String.format("Posted %d days ago", period.getDays());
        } else if (duration.toHours() != 0) {
            return String.format("Posted %d hours ago", duration.toHours());
        } else if (duration.toMinutes() != 0) {
            return String.format("Posted %d minutes ago", duration.toMinutes());
        } else if (duration.getSeconds() != 0) {
            return String.format("Posted %d seconds ago", duration.getSeconds());
        } else {
            return "Posted moments ago";
        }
    }
}
