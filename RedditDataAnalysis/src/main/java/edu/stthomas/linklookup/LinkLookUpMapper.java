package edu.stthomas.linklookup;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;

import edu.stthomas.util.Util;
import opennlp.tools.stemmer.PorterStemmer;

public class LinkLookUpMapper extends Mapper<LongWritable, Text, Text, Text> {

	private Text urlText = new Text();
	private Text wordKey = new Text();

	PorterStemmer porterStemmer = new PorterStemmer();

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		try {
			JsonNode jsonNode = Util.parseStringtoJsonNode(line);
			String contentString = Util.getPostOrCommentBody(jsonNode);
			urlText.set(Util.getPostOrCommentURL(jsonNode));

			for (String word : Util.getValidStemmedWordsSet(contentString)) {
				if (LookUpWords.contains(word)) {
					wordKey.set(word);
					context.write(wordKey, urlText);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}