package edu.stthomas.linklookup;

import java.util.HashSet;
import java.util.Set;

public class LookUpWords {
	private static Set<String> lookup = new HashSet<String>();

	public static boolean contains(String word) {
		return lookup.contains(word);
	}

	static {
		lookup.add("setter");
		lookup.add("scala");
	}
}
