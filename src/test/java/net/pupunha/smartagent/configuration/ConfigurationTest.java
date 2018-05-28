package net.pupunha.smartagent.configuration;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConfigurationTest {


    @Test
    public void testReadConfiguration() throws IOException {

        String fileConfiguration = "configuration.yaml";

        ReaderConfiguration reader = new ReaderConfiguration();
        Configuration configuration = reader.read(fileConfiguration);
        assertTrue(configuration != null);
        assertNotNull(configuration.getClassFullName().get("net/pupunha/compl/Complemento"));
        assertNotNull(configuration.getLogFile());
        assertTrue(configuration.getLogFile().equals("/Users/willlobato/Development/logando.log"));

    }

}