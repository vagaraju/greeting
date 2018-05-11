/**
 * to set the properties from the applicatio.properties file.
 */
package com.openedgepay.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author M1034465
 *
 */
public final class FileData {
    /**
     * to track logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileData.class);
    /**
     * unit test cases for input json files.
     */
    public static final String FILESPATH = "src\\test\\resources\\";
    /**
     * Creates instance of FileData.
     */
    private FileData() {
        // The explicit constructor is here.
    }
    /**
     * read data from the file.
     *
     * @param fileName
     *          .
     * @return JSONObject.
     */
    public static String readFileData(final String fileName) {
      String content;
      try {
        if (LOG.isInfoEnabled()) {
          LOG.info("file Path=" + Paths.get(FILESPATH + fileName + ".json"));
        }
        content = new String(Files.readAllBytes(Paths.get(FILESPATH + fileName + ".json")));
      } catch (Throwable e) {
          LOG.error("Exception @ ignore this error." + e.getMessage() + e.fillInStackTrace());
        content = "{\"error\":\"Please place the file @ desired location and try again.!! \"}";
      }
      if (LOG.isInfoEnabled()) {
        LOG.info("file Data=" + content);
      }
      return content;
    }
}
