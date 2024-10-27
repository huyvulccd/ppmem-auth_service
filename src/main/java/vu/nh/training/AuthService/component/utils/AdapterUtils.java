package vu.nh.training.AuthService.component.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdapterUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(String jsonData, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonData, valueType);
        } catch (Exception e) {
            log.error("Error parsing JSON", e);
            throw new RuntimeException(String.format("Error parsing JSON: %s, JSON data: %s", e.getMessage(), jsonData));
        }
    }
}
