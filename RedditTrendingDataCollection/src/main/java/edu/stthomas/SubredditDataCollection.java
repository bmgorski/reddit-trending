package edu.stthomas;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.models.CommentNode;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;

@Controller
public class SubredditDataCollection {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${rdc.username}")
	private String username;

	@Value("${rdc.password}")
	private String password;

	@Value("${rdc.client-id}")
	private String clientId;

	@Value("${rdc.client-secret}")
	private String clientSecret;

	@RequestMapping(method = RequestMethod.GET, value = "/reddit/collect/{subreddit}/{limit}")
	public String getHotSubmissions(@PathVariable String subreddit, @PathVariable int limit, Model model) throws NetworkException, OAuthException, IOException {
		final String fileName = Application.FILE_FOLDER + File.separator
				+ UUID.randomUUID().toString().replaceAll("-", "") + "-" + subreddit + "-" + limit;
		final File file = new File(fileName);
		RedditClient reddit = new RedditClient(UserAgent.of("Script", "MetaTrends", ".2", "JavaCodesYou"));

		logger.debug("username: {}  password: {}  clientId: {}  clientSecret: {}\n", username, password, clientId,
				clientSecret);

		Credentials credentials = Credentials.script(username, password, clientId, clientSecret);
		reddit.authenticate(reddit.getOAuthHelper().easyAuth(credentials));

		SubredditPaginator paginator = new SubredditPaginator(reddit);
		paginator.setSubreddit(subreddit);
		paginator.setSorting(Sorting.HOT);

		List<Listing<Submission>> submissions = paginator.accumulate(limit);
		
		for (Listing<Submission> submissions2 : submissions) {
			for (Submission submission : submissions2) {
				submission = reddit.getSubmission(submission.getId());

				FileUtils.writeStringToFile(file, submission.getDataNode().toString() + "\n", true);

				if (submission.getCommentCount() > 0 && submission.getComments() != null) {

					List<CommentNode> commentNodes = submission.getComments().getChildren();

					if (!commentNodes.isEmpty()) {
						printComments(commentNodes, file);
					}
				}
			}
		}
		return "collectionStats";
	}


	private void printComments(List<CommentNode> commentNodes, File file) throws IOException {
		for (CommentNode commentNode : commentNodes) {
			FileUtils.writeStringToFile(file, commentNode.getComment().getDataNode().toString() + "\n", true);
			List<CommentNode> children = commentNode.getChildren();
			if (!children.isEmpty()) {
				printComments(children, file);
			}
		}
	}
}
