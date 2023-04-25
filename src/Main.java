import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static User loggedInUser;
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Please enter a command");
            String cmd = in.nextLine();

            if(cmd == "signup") {
                System.out.println("Please enter arguments");
                String argString = in.nextLine();
                String[] arguments = argString.split("\\s");

                try {
                    User user = User.getBuilder()
                            .setUserName(arguments[0])
                            .setPassword(arguments[1])
                            .build();

                    MemoryDB.users.put(user.id, user);
                } catch (IllegalArgumentException e) {
                    System.out.println("User with this username already exists");
                }
            } else if(cmd == "login") {
                System.out.println("Please enter arguments");
                String argString = in.nextLine();
                String[] arguments = argString.split("\\s");

                try {
                    User user = User.login(arguments[0], arguments[1]);
                    loggedInUser = user;
                    System.out.println("Logged in successfully");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid username or password.");
                }
            } else if(cmd == "post") {
                if(loggedInUser == null) {
                    System.out.println("Login to perform this action.");
                    continue;
                }

                System.out.println("Please enter content:");
                String content = in.nextLine();

                Post post = new Post(content, loggedInUser);
                MemoryDB.posts.put(post.id, post);

                System.out.println("Post added.");
            } else if(cmd == "reply") {
                if(loggedInUser == null) {
                    System.out.println("Login to perform this action.");
                    continue;
                }

                System.out.println("Please enter arguments");
                String argString = in.nextLine();
                String[] arguments = argString.split("\\s");
                String type = arguments[0];
                Integer id = Integer.parseInt(arguments[1]);
                String content = String.join(" ", Arrays.copyOfRange(arguments, 2, arguments.length));
                Comment comment = null;

                if(type == "p") {
                    Post replyOf = MemoryDB.posts.get(id);
                    comment = new Comment(content, loggedInUser, replyOf);
                } else if(type == "c") {
                    Comment replyOf = MemoryDB.comments.get(id);
                    comment = new Comment(content, loggedInUser, replyOf);
                }

                MemoryDB.comments.put(comment.id, comment);
                System.out.println("Comment added.");
            } else if(cmd == "follow") {
                if(loggedInUser == null) {
                    System.out.println("Login to perform this action.");
                    continue;
                }

                System.out.println("Please enter user_id to follow");
                Integer userId = in.nextInt();
                loggedInUser.follow(userId);
            } else if(cmd == "upvote") {
                if(loggedInUser == null) {
                    System.out.println("Login to perform this action.");
                    continue;
                }

                System.out.println("Please enter arguments");
                String argString = in.nextLine();
                String[] arguments = argString.split("\\s");
                String type = arguments[0];
                Integer id = Integer.parseInt(arguments[1]);

                if(type == "p") {
                    MemoryDB.posts.get(id).upvote();
                } else if(type == "c") {
                    MemoryDB.comments.get(id).upvote();
                }
            } else if(cmd == "downvote") {
                if(loggedInUser == null) {
                    System.out.println("Login to perform this action.");
                    continue;
                }

                System.out.println("Please enter arguments");
                String argString = in.nextLine();
                String[] arguments = argString.split("\\s");
                String type = arguments[0];
                Integer id = Integer.parseInt(arguments[1]);

                if(type == "p") {
                    MemoryDB.posts.get(id).downvote();
                } else if(type == "c") {
                    MemoryDB.comments.get(id).downvote();
                }
            } else if(cmd == "shownewsfeed") {
                if(loggedInUser == null) {
                    System.out.println("Login to perform this action.");
                    continue;
                }

                loggedInUser.showNewsFeed();
            }
        } while (true);
    }
}
