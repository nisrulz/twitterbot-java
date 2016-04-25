package github.nisrulz.bot;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterBot {

	static boolean debug = false;
	static Twitter twitter;

	// if something goes wrong, we might see a TwitterException
	public static void main(String... args) throws TwitterException {

		// access the twitter API using your twitter4j.properties file
		twitter = TwitterFactory.getSingleton();

		// send a tweet
		if (!debug) {
			sendTweet(twitter, "Hello World!");
		} else {
			// print a message so we know when it finishes
			System.out.println("Successfull");
		}
	}

	private static Status sendTweet(Twitter twitter, String text) {
		Status status = null;
		try {
			status = twitter.updateStatus(text);
			System.out.println("Tweet posted : " + text);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return status;
	}
}