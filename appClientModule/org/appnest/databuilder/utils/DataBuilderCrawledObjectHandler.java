package org.appnest.databuilder.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class DataBuilderCrawledObjectHandler {
	
	private static Logger logger = Logger.getLogger(DataBuilderCrawledObjectHandler.class);

	public static <T> void writeObjOnFileSystem(T obj, String filename) {
		try {
			// use buffering
			OutputStream file = new FileOutputStream(filename);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(obj);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			logger.error("some error during object serialization over filesystem", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readObjFromFileSystem(String filename) {
		T recoveredObj = null;
		try {
			// use buffering
			InputStream file = new FileInputStream(filename);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				recoveredObj = (T) input.readObject();
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			logger.error("some error during object deserialization from filesystem", ex);
		} catch (IOException ex) {
			logger.error("some error during object deserialization from filesystem", ex);
		}
		return recoveredObj;
	}
}
