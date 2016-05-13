package edu.stthomas.linklookup;

import org.apache.hadoop.io.Text;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class LinkLookUpReducer extends Reducer<Text, Text, Text, NullWritable> {

	IntWritable intWritable = new IntWritable();

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		context.write(key, NullWritable.get());

		for (Text value : values) {
			context.write(value, NullWritable.get());
		}
	}
}