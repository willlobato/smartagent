package net.pupunha.smartagent.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Configuration {

    private Map<String, ClassConfiguration> classFullName;
    private String logFile;

}
