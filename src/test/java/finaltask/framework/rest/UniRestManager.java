package finaltask.framework.rest;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import finaltask.utils.ConfigFileReader;
import java.util.Map;

public class UniRestManager {

    public static <T> HttpResponse<T> POSTRequest(Map<String, String> parametersMap, String methodName, Class<T> clazz) {
        return Unirest.post(getURL(parametersMap, methodName)).asObject(clazz);
    }

    private static String getURL(Map<String, String> parametersMap, String nameMethod) {
        StringBuilder parameters = new StringBuilder();
        parameters.append("?");
        for (Map.Entry<String, String> pair : parametersMap.entrySet()) {
            parameters.append(pair.getKey()).append("=").append(pair.getValue()).append("&");
        }
        parameters.deleteCharAt(parameters.length() - 1);
        return ConfigFileReader.getUrlApi() + nameMethod + parameters;
    }
}