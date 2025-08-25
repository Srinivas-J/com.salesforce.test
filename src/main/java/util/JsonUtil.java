package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Utility class for JSON file operations using Jackson.
 */
public class JsonUtil {

    // Singleton ObjectMapper instance for reuse
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Reads a JSON file and converts it to a Map.
     *
     * @param filePath the path to the JSON file
     * @return a Map representing the JSON structure
     * @throws RuntimeException if reading fails
     */
    public static Map<String, Object> readJsonFileToMap(String filePath) {
        try {
            return OBJECT_MAPPER.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }

    /**
     * Writes a Map<String, String> to a JSON file.
     *
     * @param filePath the path to the output JSON file
     * @param data the Map data to write
     * @throws RuntimeException if writing fails
     */
    public static void writeMapToJsonFile(String filePath, Map<String, String> data) {
        try {
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write JSON file: " + filePath, e);
        }
    }

    /**
     * Reads a single value from a JSON file by key.
     *
     * @param filePath the path to the JSON file
     * @param key the key to retrieve
     * @return the value as a String, or null if the key does not exist
     * @throws RuntimeException if reading fails
     */
    public static String readValueByKeyFromJsonFile(String filePath, String key) {
        try {
            Map<String, String> data = OBJECT_MAPPER.readValue(new File(filePath), new TypeReference<Map<String, String>>() {});
            return data.get(key);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read key '" + key + "' from JSON file: " + filePath, e);
        }
    }
}