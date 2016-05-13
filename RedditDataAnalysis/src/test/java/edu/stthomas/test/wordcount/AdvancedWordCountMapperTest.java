package edu.stthomas.test.wordcount;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import edu.stthomas.util.Util;
import net.dean.jraw.JrawUtils;


public class AdvancedWordCountMapperTest {
	public static final String POST = "{\"domain\":\"self.scala\",\"banned_by\":null,\"media_embed\":{},\"subreddit\":\"scala\",\"selftext_html\":\"&lt;!-- SC_OFF --&gt;&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Hello &lt;a href=\\\"/r/Scala\\\"&gt;/r/Scala&lt;/a&gt;,  &lt;/p&gt;\\n\\n&lt;p&gt;This is a weekly thread where you can ask any question, no matter if you are just starting, or are a long-time contributor to the compiler.  &lt;/p&gt;\\n\\n&lt;p&gt;Also feel free to post general discussion, or tell us what you&amp;#39;re working on (or would like help with). &lt;/p&gt;\\n\\n&lt;p&gt;&lt;a href=\\\"https://www.reddit.com/r/scala/search?q=weekly+scala+ask+anything&amp;amp;sort=new&amp;amp;restrict_sr=on&amp;amp;t=all\\\"&gt;Previous discussions&lt;/a&gt;  &lt;/p&gt;\\n\\n&lt;p&gt;Thanks!&lt;/p&gt;\\n&lt;/div&gt;&lt;!-- SC_ON --&gt;\",\"selftext\":\"Hello /r/Scala,  \\n\\nThis is a weekly thread where you can ask any question, no matter if you are just starting, or are a long-time contributor to the compiler.  \\n\\nAlso feel free to post general discussion, or tell us what you're working on (or would like help with). \\n\\n[Previous discussions](https://www.reddit.com/r/scala/search?q=weekly+scala+ask+anything&amp;sort=new&amp;restrict_sr=on&amp;t=all)  \\n\\nThanks!\",\"likes\":null,\"suggested_sort\":null,\"user_reports\":[],\"secure_media\":null,\"link_flair_text\":null,\"id\":\"4ij98u\",\"from_kind\":null,\"gilded\":0,\"archived\":false,\"clicked\":false,\"report_reasons\":null,\"author\":\"AutoModerator\",\"media\":null,\"name\":\"t3_4ij98u\",\"score\":7,\"approved_by\":null,\"over_18\":false,\"hidden\":false,\"thumbnail\":\"\",\"subreddit_id\":\"t5_2qh37\",\"edited\":false,\"link_flair_css_class\":null,\"author_flair_css_class\":null,\"downs\":0,\"mod_reports\":[],\"secure_media_embed\":{},\"saved\":false,\"removal_reason\":null,\"stickied\":true,\"from\":null,\"is_self\":true,\"from_id\":null,\"permalink\":\"/r/scala/comments/4ij98u/weekly_scala_ask_anything_and_discussion_thread/\",\"locked\":false,\"hide_score\":false,\"created\":1.46282447E9,\"url\":\"https://www.reddit.com/r/scala/comments/4ij98u/weekly_scala_ask_anything_and_discussion_thread/\",\"author_flair_text\":null,\"quarantine\":false,\"title\":\"Weekly Scala Ask Anything and Discussion Thread - May 09, 2016\",\"created_utc\":1.46279567E9,\"ups\":7,\"upvote_ratio\":0.9,\"num_comments\":8,\"visited\":false,\"num_reports\":null,\"distinguished\":\"moderator\"}";
	public static final String COMMENT = "{\"subreddit_id\":\"t5_2qh37\",\"banned_by\":null,\"removal_reason\":null,\"link_id\":\"t3_4ij98u\",\"likes\":null,\"replies\":\"\",\"user_reports\":[],\"saved\":false,\"id\":\"d2ylx56\",\"gilded\":0,\"archived\":false,\"report_reasons\":null,\"author\":\"pellets\",\"parent_id\":\"t1_d2yi36h\",\"score\":1,\"approved_by\":null,\"controversiality\":0,\"body\":\"Case classes can have setters if you put \\\"var\\\" before the argument.\\n\\n\\n   case class C(var v: Int)\",\"edited\":false,\"author_flair_css_class\":null,\"downs\":0,\"body_html\":\"&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Case classes can have setters if you put &amp;quot;var&amp;quot; before the argument.&lt;/p&gt;\\n\\n&lt;p&gt;case class C(var v: Int)&lt;/p&gt;\\n&lt;/div&gt;\",\"subreddit\":\"scala\",\"name\":\"t1_d2ylx56\",\"score_hidden\":false,\"stickied\":false,\"created\":1.462833005E9,\"author_flair_text\":null,\"created_utc\":1.462804205E9,\"distinguished\":null,\"mod_reports\":[],\"num_reports\":null,\"ups\":1}\r\n";
	public static final String[] COMMENT_POST = { POST, COMMENT };

	@Test
	public void test() throws JsonProcessingException {

		for (String line : COMMENT_POST) {
			JsonNode jsonNode = Util.parseStringtoJsonNode(line);
			
			System.out.println(JrawUtils.objectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(jsonNode));
			
			String contentString = Util.getPostOrCommentBody(jsonNode);


			for(String word : Util.getValidStemmedWords(contentString)){
				assertNotNull(word);
				System.out.println(word);
			}
		}
	}

}
