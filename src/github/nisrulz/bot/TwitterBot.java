package github.nisrulz.bot;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterBot {

	static boolean debug = false;

	// if something goes wrong, we might see a TwitterException
	public static void main(String... args) {
		// send a tweet
		if (!debug) {
			try {
				// Send Tweet
				// sendTweet("Hello World!");

				// Get tweets from Home Timeline
				// getHomeTimeLine();

				// Seach for tweets
				// searchForTweets("@gdg_nd");

				// Reply to a tweet
				String query_text = "\"your welcome\"";
				String reply = "I believe you meant \"you're\" here?";
				replyToTweet(query_text, reply);

				// Reply with variety
				List<String> searches = new ArrayList<>();
				searches.add("\"your welcome\"");
				searches.add("\"your the\"");
				searches.add("\"your a \"");

				List<String> replies = new ArrayList<>();
				replies.add("I believe you meant \"you're\" here?");
				replies.add(" I've detected the wrong \"you're\". Destroy!");
				replies.add(" No, you are! Seriously. You are. \"You're\".");

				replyToTweetWithVariety(searches, replies);

			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {
			// print a message so we know when it finishes
			System.out.println("Debug Mode Enabled!");
		}
	}

	private static Status sendTweet(String text) throws TwitterException {
		// access the twitter API using your twitter4j.properties file
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();

		Status status = null;

		status = twitter.updateStatus(text);
		System.out.println("Successfully updated the status to ["
				+ status.getText() + "].");

		return status;
	}

	private static void getHomeTimeLine() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = null;
		statuses = twitter.getHomeTimeline();

		System.out.println("Showing home timeline.");
		if (statuses != null) {
			for (Status status : statuses) {
				System.out.println(status.getUser().getName() + ":"
						+ status.getText());
			}
		}
	}

	private static void searchForTweets(String query_text)
			throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(query_text);
		QueryResult result;
		result = twitter.search(query);
		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":"
					+ status.getText());
		}
	}

	private static void replyToTweet(String query_text, String reply)
			throws TwitterException {
		// access the twitter API using your twitter4j.properties file
		Twitter twitter = TwitterFactory.getSingleton();

		// create a new search
		Query query = new Query(query_text);

		// get the results from that search
		QueryResult result = twitter.search(query);

		// get the first tweet from those results
		Status tweetResult = result.getTweets().get(0);

		// reply to that tweet
		StatusUpdate statusUpdate = new StatusUpdate(".@"
				+ tweetResult.getUser().getScreenName() + " " + reply);
		statusUpdate.inReplyToStatusId(tweetResult.getId());
		Status status = twitter.updateStatus(statusUpdate);
	}

	private static void replyToTweetWithVariety(List<String> searches,
			List<String> replies) throws TwitterException {
		// access the twitter API using your twitter4j.properties file
		Twitter twitter = TwitterFactory.getSingleton();

		// keep tweeting forever
		while (true) {
			// create a new search, chosoe from random searches
			Query query = new Query(searches.get((int) (searches.size() * Math
					.random())));

			// get the results from that search
			QueryResult result = twitter.search(query);

			// get the first tweet from those results
			Status tweetResult = result.getTweets().get(0);

			// reply to that tweet, choose from random replies
			StatusUpdate statusUpdate = new StatusUpdate(".@"
					+ tweetResult.getUser().getScreenName()
					+ replies.get((int) (replies.size() * Math.random())));
			statusUpdate.inReplyToStatusId(tweetResult.getId());
			Status status = twitter.updateStatus(statusUpdate);
			System.out.println("Sleeping.");

			// go to sleep for an hour
			try {
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}