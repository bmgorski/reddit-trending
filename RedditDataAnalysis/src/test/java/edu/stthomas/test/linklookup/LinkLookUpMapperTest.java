package edu.stthomas.test.linklookup;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import edu.stthomas.linklookup.LookUpWords;
import edu.stthomas.test.wordcount.AdvancedWordCountMapperTest;
import edu.stthomas.util.Util;

public class LinkLookUpMapperTest {

	@Test
	public void test() throws JsonProcessingException {
		for (String line : AdvancedWordCountMapperTest.COMMENT_POST) {
			JsonNode jsonNode = Util.parseStringtoJsonNode(line);
			String contentString = Util.getPostOrCommentBody(jsonNode);
			String urlText = Util.getPostOrCommentURL(jsonNode);

			for (String word : Util.getValidStemmedWordsSet(contentString)) {
				if (LookUpWords.contains(word)) {
					System.out.println(word + " --- " + urlText);
				}
			}
		}
	}
}