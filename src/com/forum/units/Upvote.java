package com.forum.units;

public class Upvote extends AbstractEntity {
	// Question for which the user has upvoted
	private Question question;
	// Reply for which the user has upvoted
	private Reply reply;
	// User who has upvoted for either a question or a reply
	private User user;
	// ID for the last upvote on the discussion forum
	private static Long lastEntry = 0L;

	public void autoGenerateId() {
		lastEntry = lastEntry + 1L;
		super.setId(lastEntry);
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
