package com.pethoalpar.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private FileUtil() {

	}

	public static String getFileContent(String fileName) {
		ClassPathResource resource = new ClassPathResource(fileName);
		InputStream is;
		try {
			is = resource.getInputStream();
			return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.warn("Can not read file:" + fileName);
		}
		return "[]";
	}

	public static <T> List<T> processFile(String jsonFile, Class<T> clazz) {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement parsed = parser.parse(jsonFile);
		List<T> retList = new ArrayList<>();
		if (parsed.isJsonArray()) {
			JsonArray array = parsed.getAsJsonArray();
			for (int i = 0; i < array.size(); ++i) {
				JsonElement json = array.get(i);
				T element = gson.fromJson(json, clazz);
				retList.add(element);
			}
		}
		return retList;
	}

}
