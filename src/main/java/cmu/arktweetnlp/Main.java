package cmu.arktweetnlp;

import java.io.IOException;
import java.util.Map;

import tuan.io.Commands;


/** This is the entry point to the program */
public class Main {

	public static void main(String[] args) {		
		Map<String, String> opts = Commands.parseCommandLineArguments(args);
		
		if ((opts.containsKey("ark") && opts.containsKey("hark")) ||
			(opts.containsKey("demo") && opts.containsKey("ark")) ||
			(opts.containsKey("demo") && opts.containsKey("hark"))) {
			usage("Can only run in one mode at a time");
		}
		
		// backward compatibility
		if (opts.containsKey("demo")) {
			RunTagger tagger = new RunTagger();
			try {
				tagger.finalizeOptions(opts);
				tagger.runTagger();
			} catch (OptionException e) {
				usage(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static void usage(String... msg) {
		if (msg != null) {
			System.err.print("ERROR: ");
			for (String m : msg) System.err.print(m);
		}
		System.err.println(
				"RunTagger [options] [ExamplesFilename]" +
				"\n  runs the CMU ARK Twitter tagger on tweets from ExamplesFilename, " +
				"\n  writing taggings to standard output. Listens on stdin if no input filename." +
				"\n\nOptions:" +
				"\n  -demo                    Run in demo, single-machine mode (old good API)" +
				"\n  -ark                     Run in single-machine mode" +
				"\n  -hark                    Run in hadoop mode"
		);
	}

}
