package net.pupunha.smartagent.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MethodConfiguration {

    private String[] parameters;

    public String getParameterFullName() {
        StringBuilder param = new StringBuilder("(");
        if (parameters != null) {
            for (int i=0; i<parameters.length; i++) {
                param.append(parameters[i]);
                param.append("/");
            }
            param.delete(param.length()-1, param.length());
        }
        param.append(")");
        return param.toString();
    }

}
