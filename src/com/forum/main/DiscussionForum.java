package com.forum.main;

import java.io.IOException;

import com.forum.units.Question;
import com.forum.units.User;
import com.forum.units.UserRole;
import com.forum.util.Utility;

import discusion.forum.activiy.UserActivity;

public class DiscussionForum {

	public static void main(String[] args) throws IOException {
		User user;
		UserActivity userActivity = new UserActivity();

		// Creating an initial admin user for the forum
		userActivity.userService.createUser("admin", "admin", "admin", UserRole.ADMIN);

		while (true) {
			// User login
			user = userActivity.loginActivity();
			if (user == null)
				continue;
			System.out.println("Welcome " + user.getUsername());
			// Displaying the main menu for the user
			menu(user, userActivity);
		}
	}

	// Main menu for the user
	public static void menu(User user, UserActivity userActivity) throws NumberFormatException, IOException {
        do {
            int menuIndex = 0;
            if (user.getUserRole() == UserRole.ADMIN) {
                System.out.println(++menuIndex + " Create new user");
            }
            System.out.println(++menuIndex + " Ask a question");
            System.out.println(++menuIndex + " See all questions");
            System.out.println(++menuIndex + " Log Out");
            System.out.println("\n Enter your choice");

            // Processing user's choice from the menu
        } while (classifyMenuChoice(Integer.parseInt(Utility.inputFromUser()), userActivity, user));
	}

	// Handling user's choice from the main menu
	public static boolean classifyMenuChoice(int choice, UserActivity userActivity, User user) throws IOException {
		if (UserRole.ADMIN != user.getUserRole()) {
			choice++; // Shift choice index for non-admin users
		}
		while (true) {
			switch (choice) {
				case 1:
					userActivity.createNewUser();
					return true;
				case 2:
					userActivity.postNewQuestion(user);
					return true;
				case 3:
					userActivity.seeAllQuestions(userActivity, user);
					return true;
				case 4:
					return false; // Log out
				default:
					System.out.println("Wrong choice. Try again");
			}
			System.out.println("\n Enter your choice");
			choice = Integer.parseInt(Utility.inputFromUser());
		}
	}

	// Helper function to select user roles
	public static UserRole roleFromMenu() throws NumberFormatException, IOException {
		int menuIndex = 0;
		System.out.println(++menuIndex + UserRole.ADMIN.toString());
		System.out.println(++menuIndex + UserRole.MODERATOR.toString());
		System.out.println(++menuIndex + UserRole.USER.toString());
		while (true) {
			System.out.println("\n Enter your choice");
			int choice = Integer.parseInt(Utility.inputFromUser());
			switch (choice) {
				case 1:
					return UserRole.ADMIN;
				case 2:
					return UserRole.MODERATOR;
				case 3:
					return UserRole.USER;
				default:
					System.out.println("Wrong choice. Try again");
			}
		}
	}

	// Menu for interacting with questions
	public static void questionMenu(UserActivity userActivity, User user) throws NumberFormatException, IOException {
        do {
            int menuIndex = 0;
            System.out.println(++menuIndex + " Upvote a question");
            System.out.println(++menuIndex + " Reply to a question");
            System.out.println(++menuIndex + " See replies for a question");
            System.out.println(++menuIndex + " Delete a question");
            System.out.println(++menuIndex + " Return to main menu");
            System.out.println("\n Enter your choice");

            // Processing user's choice for question interactions
        } while (processQuestionChoice(Integer.parseInt(Utility.inputFromUser()), userActivity, user));
	}

	// Handling user's choice for question interactions
	public static boolean processQuestionChoice(int choice, UserActivity userActivity, User user) throws NumberFormatException, IOException {
		while (true) {
			switch (choice) {
				case 1:
					userActivity.upvoteQuestion(user);
					return true;
				case 2:
					userActivity.replyToQuestion(user);
					return true;
				case 3:
					userActivity.seeAllReplies(userActivity, user);
					return true;
				case 4:
					userActivity.deleteQuestion(userActivity, user);
					return true;
				case 5:
					return false; // Return to main menu
				default:
					System.out.println("Wrong choice. Try again");
			}
			System.out.println("Enter your choice");
			choice = Integer.parseInt(Utility.inputFromUser());
		}
	}

	// Menu for interacting with replies
	public static void replyMenu(UserActivity userActivity, User user, Question question) throws NumberFormatException, IOException {
        do {
            int menuIndex = 0;
            System.out.println(++menuIndex + " Upvote a reply");
            System.out.println(++menuIndex + " Delete a reply");
            System.out.println(++menuIndex + " Return to question menu");
            System.out.println("\n Enter your choice");

            // Processing user's choice for reply interactions
        } while (processReplyChoice(Integer.parseInt(Utility.inputFromUser()), userActivity, user, question));
	}

	// Handling user's choice for reply interactions
	public static boolean processReplyChoice(int choice, UserActivity userActivity, User user, Question question) throws NumberFormatException, IOException {
		while (true) {
			switch (choice) {
				case 1:
					userActivity.upvoteReply(user);
					return true;
				case 2:
					userActivity.deleteReply(userActivity, user);
					return true;
				case 3:
					return false; // Return to question menu
				default:
					System.out.println("Wrong choice. Try again");
			}
		}
	}
}
