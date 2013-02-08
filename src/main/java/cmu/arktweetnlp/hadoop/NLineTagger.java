package cmu.arktweetnlp.hadoop;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Tagger.TaggedToken;

/** One hadoop job for reading big tweet data from text file, where
 * each single line corresponds to one tweet. The output is one plain-text
 * line per annotated tweet, with the format specified by the user. This
 * job does not load model files dynamically at runtime */

public class NLineTagger extends Configured implements Tool {

	private static final Logger LOG = Logger.getLogger(NLineTagger.class);
		
	// Mapper reads from a chunk in input file line by line
	private static class TaggerMapper extends 
			Mapper<LongWritable, Text, IntWritable, Text> {

		private Tagger tagger = new Tagger();
		private String format = null;
		private static final IntWritable ONE = new IntWritable(1);
		private Text annot = new Text();
		private StringBuilder sb = new StringBuilder();
		
		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			
			Configuration conf = context.getConfiguration();
			String modelFile = conf.get("modelFile", "/cmu/arktweetnlp/model.20120919");
			format = conf.get("format", "%s %s\t");
			tagger.loadModel(modelFile);
			LOG.debug("module file loaded");
		}

		@Override
		protected void map(LongWritable key, Text value, 
				Mapper<LongWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			String tweet = value.toString();
			List<TaggedToken> tokens = null;
			try {
				tokens = tagger.tokenizeAndTag(tweet);
			} finally {
				if (tokens != null && !tokens.isEmpty()) {
					sb.delete(0, sb.length());
					for (TaggedToken t : tokens) {
						sb.append(String.format(format, t.token, t.tag));
					}
					annot.set(sb.toString());
					context.write(ONE, annot);
				}
			}
		}
	}
	
	private static final class TaggerReducer extends 
			Reducer<IntWritable, Text, Text, NullWritable> {
		
		@Override
		protected void reduce(IntWritable k, Iterable<Text> vals, 
				Reducer<IntWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			for (Text val : vals) {
				context.write(val, NullWritable.get());
			}
		}
	}
		
	@Override
	public int run(String[] args) throws Exception {
		String inputFile = null;
		String modelFile = null;
		String format = null;
		String outputFile = null;
		int reduceNo = 0;
		int linesPerMap = 15771;
		
		if (args.length == 1) {
			inputFile = args[0]; 
		}
		else if (args.length == 2) {
			inputFile = args[0];
			outputFile = args[1];
		}
		else if (args.length == 3) {
			inputFile = args[0];
			outputFile = args[1];
			linesPerMap = Integer.parseInt(args[2]);
		}
		else if (args.length == 4) {
			inputFile = args[0];
			outputFile = args[1];
			linesPerMap = Integer.parseInt(args[2]);
			modelFile = args[3];
		}
		else if (args.length == 5) {
			inputFile = args[0];
			outputFile = args[1];
			linesPerMap = Integer.parseInt(args[2]);
			modelFile = args[3];
			format = args[4];
		}
		else if (args.length == 6) {
			inputFile = args[0];
			outputFile = args[1];
			linesPerMap = Integer.parseInt(args[2]);
			modelFile = args[3];
			format = args[4];
			reduceNo = Integer.parseInt(args[5]);
		}
		else throw new Exception("Bad arguments");
		
		Job job = new Job(getConf(), "Tagging Twitter from " + inputFile);
		if (modelFile != null) job.getConfiguration().set("modelFile", modelFile);
		if (format != null) job.getConfiguration().set("format", format);
		
		job.setJarByClass(NLineTagger.class);
		job.setNumReduceTasks(reduceNo);
		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", linesPerMap);
		
		FileInputFormat.setInputPaths(job, new Path(inputFile));
		FileOutputFormat.setOutputPath(job, new Path(outputFile));
		
		job.setInputFormatClass(NLineInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setMapperClass(TaggerMapper.class);
		job.setReducerClass(TaggerReducer.class);
		
		job.waitForCompletion(true);
		return 0;
	}
	
	public static void main(String[] args) {
		int res = 0;
		try {
			res = ToolRunner.run(new Configuration(), new NLineTagger(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(res);
		}
	}
}
