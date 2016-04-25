package github.nisrulz.bot;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterBot {

	static boolean debug = false;

	// if something goes wrong, we might see a TwitterException
	public static void main(String... args) throws TwitterException {
		// send a tweet
		if (!debug) {
			// Send Tweet
			// sendTweet("Hello World!");
			
			// Get tweets from Home Timeline
			getHomeTimeLine();

		} else {
			// print a message so we know when it finishes
			System.out.println("Successfull");
		}
	}

	private static Status sendTweet(String text) {
		// access the twitter API using your twitter4j.properties file
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();

		Status status = null;
		try {
			status = twitter.updateStatus(text);
			System.out.println("Successfully updated the status to ["
					+ status.getText() + "].");
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return status;
	}

	private static void getHomeTimeLine() {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = null;
		try {
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		System.out.println("Showing home timeline.");
		if (statuses != null) {
			for (Status status : statuses) {
				System.out.println(status.getUser().getName() + ":"
						+ status.getText());
			}
		}
	}
}