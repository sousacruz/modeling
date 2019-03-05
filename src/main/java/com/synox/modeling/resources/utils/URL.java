package com.synox.modeling.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeStrParam(String param) {
		try {
			return URLDecoder.decode(param,"UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}

	}
	
	public static List<Integer> decodeIntList(String args) {
		return Arrays.asList(args.split(","))
				.stream()
				.map(i -> Integer.parseInt(i))
				.collect(Collectors.toList());
	}

}
