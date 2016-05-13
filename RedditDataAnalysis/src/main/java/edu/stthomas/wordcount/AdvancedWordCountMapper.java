package edu.stthomas.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;

import edu.stthomas.util.Util;

import net.dean.jraw.JrawUtils;

public class AdvancedWordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private Text text = new Text();
	private IntWritable intWritable = new IntWritable(1);

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		try {

			JsonNode jsonNode = Util.parseStringtoJsonNode(line);

			System.out.println(JrawUtils.objectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));

			String contentString = Util.getPostOrCommentBody(jsonNode);

			for (String word : Util.getValidStemmedWords(contentString)) {
				text.set(word);
				context.write(text, intWritable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}