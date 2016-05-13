package edu.stthomas.wordcount;

import org.apache.hadoop.io.Text;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class AdvancedWordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	IntWritable intWritable = new IntWritable();

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		int sum = 0;

		for (IntWritable value : values) {
			sum = value.get() + sum;
		}

		context.write(key, intWritable);
	}
}