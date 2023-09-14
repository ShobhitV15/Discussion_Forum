package com.forum.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

// Utility class is used to implement basic utilities that are used at multiple places in your program
// And you don't need multiple instances of these utilities; one instance is enough to be used at multiple places
public class Utility {
	public static Date getCurrentDate() {
		return Calendar.getInstance(TimeZone.getTimeZone("IST")).getTime();
	}

	/**
	 * Check if a string is not null and not empty.
	 *
	 * @param arg: The string to check
	 * @return true if the string is not null and not empty, false otherwise
	 */
	public static boolean isNotNullAndEmpty(String arg) {
		return arg != null && !arg.isEmpty();
	}

	/**
	 * Get input from the user via standard input.
	 *
	 * @return the user's input as a string
	 * @throws IOException if there is an error reading input
	 */
	public static String inputFromUser() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}
}
