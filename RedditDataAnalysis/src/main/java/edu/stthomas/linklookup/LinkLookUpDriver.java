package edu.stthomas.linklookup;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class LinkLookUpDriver extends Configured implements Tool {

	@Override
	@SuppressWarnings("deprecation")
	public int run(String[] args) throws Exception {
		Job job = new Job(getConf());
		job.setJarByClass(LinkLookUpDriver.class);
		job.setJobName("Word URLs");

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(LinkLookUpMapper.class);
		job.setReducerClass(LinkLookUpReducer.class);
		// job.setCombinerClass(AverageReducer.class);

		// job.setNumReduceTasks(26);
		// job.setPartitionerClass(LetterPartitioner.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		// for reducer
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		return (job.waitForCompletion(true) ? 0 : 1);
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new LinkLookUpDriver(), args);
		System.exit(exitCode);
	}
}
