package net.pupunha.smartagent.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ClassConfiguration {

    private Map<String, MethodConfiguration> methods;

}
