package discusion.forum.activiy;


import com.forum.main.DiscussionForum;
import com.forum.units.Question;
import com.forum.units.Reply;
import com.forum.units.User;
import com.forum.units.UserRole;
import com.forum.util.Utility;
import discussion.forum.units.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class UserActivity {
	public UserService userService;
	public QuestionService questionService;
	public ReplyService replyService;
	public UpvoteService upvoteService;

	public UserActivity() {
		userService = new UserServiceImpl();
		questionService = new QuestionServiceImpl();
		replyService = new ReplyServiceImpl();
		upvoteService = new UpvoteServiceImpl();
	}

	public User loginActivity() throws IOException {
		System.out.println("Welcome to discussion forum login");
		System.out.println("Enter your username : ");
		String username = Utility.inputFromUser();
		System.out.println("Enter your password : ");
		String password = Utility.inputFromUser();
		User user = userService.getUser(username, password);
		if (user != null) {
			return user;
		}
		System.out.println("You do not have the account. Request admin to get an account in the discussion forum");
		return null;
	}
	public void sort(ArrayList<Question> questions) {
		questions.sort(Comparator.comparingInt(Question::getUpvoteCount));
	}
	public void upvoteQuestion(User user) throws NumberFormatException, IOException {
		System.out.println("Enter question number you want to upvote : ");
		Question question = getQuestion();
		upvoteService.addUpvote(question, user); // Assuming upvoteService has an addUpvote method
	}

	public void replyToQuestion(User user) throws IOException {
		System.out.println("Enter the question number you want to reply to: ");
		Question question = getQuestion();

		if (question != null) {
			System.out.println("Enter your reply:");
			String replyMessage = Utility.inputFromUser();

			// Create and add the reply to the question
			replyService.addReply(replyMessage, question, user);

			System.out.println("Reply posted successfully!");
		} else {
			System.out.println("Invalid question number.");
		}
	}

	public void upvoteReply(User user) throws IOException {
		System.out.println("Enter the reply number you want to upvote: ");
		Reply reply = getReply();

		if (reply != null) {
			// Add an upvote for the specified reply
			upvoteService.addUpvote(reply, user);

			System.out.println("Upvoted successfully!");
		} else {
			System.out.println("Invalid reply number.");
		}
	}

	public void deleteQuestion(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("Enter the question number you want to delete: ");
		Question question = getQuestion();

		if (question != null) {
			if (canDeleteQuestion(user, question)) {
				questionService.deleteQuestion(question);
				System.out.println("Question deleted successfully.");
			} else {
				System.out.println("You are not authorized to delete this question.");
			}
		} else {
			System.out.println("Invalid question number.");
		}

		// Return to the main menu
		DiscussionForum.menu(user, userActivity);
	}

	private boolean canDeleteQuestion(User user, Question question) {
		UserRole userRole = user.getUserRole();
		User questionUser = question.getUser();

		// Admins can delete any question
		if (userRole == UserRole.ADMIN) {
			return true;
		}

		// Moderators can delete questions from regular users or their own questions

        // Regular users can only delete their own questions
		return user == questionUser;
	}


	public void seeAllReplies(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("Enter the question number for which you want to see replies: ");
		Question question = getQuestion();

		if (question != null) {
			ArrayList<Reply> replies = replyService.getReplies(question);

			if (replies.isEmpty()) {
				System.out.println("No replies posted for this question yet.");
			} else {
				for (Reply reply : replies) {
					System.out.println(reply.getId() + ". Comment - " + reply.getMessage());
					System.out.println("Upvote - " + upvoteService.upvoteCount(reply));
				}
				DiscussionForum.replyMenu(userActivity, user, question);
			}
		} else {
			System.out.println("Invalid question number.");
		}
	}

	public void deleteReply(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("Enter the reply number you want to delete: ");
		Reply reply = getReply();

		if (reply != null) {
			if (canDeleteReply(user, reply)) {
				replyService.deleteReply(reply);
				System.out.println("Reply deleted successfully.");
			} else {
				System.out.println("You are not authorized to delete this reply.");
			}
		} else {
			System.out.println("Invalid reply number.");
		}

		// Return to the question menu
		DiscussionForum.questionMenu(userActivity, user);
	}
	private boolean canDeleteReply(User user, Reply reply) {
		UserRole userRole = user.getUserRole();
		User replyUser = reply.getUser();

		// Admins can delete any reply
		if (userRole == UserRole.ADMIN) {
			return true;
		}

		// Moderators can delete replies from regular users or their own replies

        // Regular users can only delete their own replies
		return user == replyUser;
	}



	public void createNewUser() throws IOException {
		System.out.println("Enter username : ");
		String username = Utility.inputFromUser();
		System.out.println("Enter password : ");
		String password = Utility.inputFromUser();
		System.out.println("Enter email : ");
		String email = Utility.inputFromUser();
		System.out.println("What role : ");
		UserRole role = DiscussionForum.roleFromMenu();
		userService.createUser(username, password, email, role);
	}

	public void postNewQuestion(User user) throws IOException {
		System.out.println("Enter question title : ");
		String title = Utility.inputFromUser();
		System.out.println("Enter question : ");
		String message = Utility.inputFromUser(); // Added message input
		questionService.createQuestion(title, message, user);
	}

	public void seeAllQuestions(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		ArrayList<Question> questions = QuestionServiceImpl.questions;

		if (questions.isEmpty()) { // Check if the questions list is empty
			System.out.println("No question posted yet");
		} else {
			sort(questions);
			for (Question question : questions) {
				System.out.println(question.getId() + ". Question Title - " + question.getTitle());
				System.out.println("Question - " + question.getMessage());
				System.out.println("Upvote - " + question.getUpvoteCount());
			}
			DiscussionForum.questionMenu(userActivity, user);
		}
	}

	// ... (Other methods remain unchanged)

	private Question getQuestion() throws NumberFormatException, IOException {
		Question question;
		while (true) {
			question = questionService.getQuestionById(Long.parseLong(Utility.inputFromUser()));
			if (question != null)
				break;
			System.out.println("Enter the correct question number from the displayed questions");
		}
		return question;
	}

	private Reply getReply() throws NumberFormatException, IOException {
		Reply reply;
		while (true) {
			reply = replyService.getReply(Long.parseLong(Utility.inputFromUser()));
			if (reply != null)
				break;
			System.out.println("Enter the correct reply number from the displayed replies");
		}
		return reply;
	}
}
