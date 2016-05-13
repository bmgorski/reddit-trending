package edu.stthomas.wordcount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AdvancedWordCountDriver extends Configured implements Tool {

	@Override
	@SuppressWarnings("deprecation")
	public int run(String[] args) throws Exception {
		Job job = new Job(getConf());
		job.setJarByClass(AdvancedWordCountDriver.class);
		job.setJobName("Advanced Word Count");

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(AdvancedWordCountMapper.class);
		job.setReducerClass(AdvancedWordCountReducer.class);
		// job.setCombinerClass(AverageReducer.class);

		// job.setNumReduceTasks(26);
		// job.setPartitionerClass(LetterPartitioner.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// for reducer
		// job.setOutputKeyClass(Text.class);
		// job.setOutputValueClass(FloatWritable.class);

		return (job.waitForCompletion(true) ? 0 : 1);
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new AdvancedWordCountDriver(), args);
		System.exit(exitCode);
	}
}
