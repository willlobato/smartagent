package net.pupunha.smartagent.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ReaderConfiguration {

    public Configuration read(String fileConfiguration) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileConfiguration);
        File file;
        if (url != null) {
            file = new File(Objects.requireNonNull(url).getFile());
        } else {
            file = new File(fileConfiguration);
        }
        return mapper.readValue(file, Configuration.class);
    }

}
