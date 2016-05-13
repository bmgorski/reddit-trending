package edu.stthomas.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;

import edu.stthomas.wordcount.StopWordHashSet;
import net.dean.jraw.JrawUtils;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.SimpleTokenizer;

public class Util {
	private static Pattern pattern = Pattern.compile("[a-z]");
	private static Matcher matcher;
	private static PorterStemmer porterStemmer = new PorterStemmer();

	public static JsonNode parseStringtoJsonNode(String json) {
		return JrawUtils.fromString(json);
	}

	public static String getPostOrCommentBody(JsonNode jsonNode) {
		if (jsonNode.get("selftext") != null) {
			return jsonNode.get("selftext").asText().toLowerCase();
		} else if (jsonNode.get("body") != null) {
			return jsonNode.get("body").asText().toLowerCase();
		}

		return null;
	}

	public static List<String> getValidStemmedWords(String words) {
		List<String> list = new ArrayList<String>();
		for (String word : SimpleTokenizer.INSTANCE.tokenize(words)) {
			// don't count if stop word
			if (word.length() > 1 && !StopWordHashSet.contains(word)) {
				// count only if contains letter
				matcher = pattern.matcher(word);
				if (matcher.find()) {
					list.add(porterStemmer.stem(word));
				}
			}
		}

		return list;
	}
	
	public static Set<String> getValidStemmedWordsSet(String words) {
		Set<String> list = new HashSet<String>();
		for (String word : SimpleTokenizer.INSTANCE.tokenize(words)) {
			// don't count if stop word
			if (word.length() > 1 && !StopWordHashSet.contains(word)) {
				// count only if contains letter
				matcher = pattern.matcher(word);
				if (matcher.find()) {
					list.add(porterStemmer.stem(word));
				}
			}
		}

		return list;
	}

	public static String getPostOrCommentURL(JsonNode jsonNode) {
		if (jsonNode.get("selftext") != null) {
			return jsonNode.get("url").asText();
		} else {
			return "https://www.reddit.com/api/info.json?id=" + jsonNode.get("name").asText();
		}
	}
}
