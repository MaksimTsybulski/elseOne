package finaltask.framework.rest;

import org.json.simple.JSONArray;
import java.util.HashMap;
import java.util.Map;

public class UniRestController {

    public static String getToken(String variant) {
        Map<String, String> map = new HashMap();
        map.put("variant", variant);
        return UniRestManager.POSTRequest(map, "/token/get", String.class).getBody();
    }

    private static JSONArray getJsonTests(String projectId) {
        Map<String, String> map = new HashMap();
        map.put("projectId", projectId);
        return UniRestManager.POSTRequest(map, "/test/get/json", JSONArray.class).getBody();
    }

    public static JSONArray getJsonTests(String projectId, int numberAttempts) {
        JSONArray jsonArray;
        for (int i = 0; i < numberAttempts; i++) {
            jsonArray = getJsonTests(projectId);
            if (jsonArray != null) return jsonArray;
        }
        throw new RuntimeException("The list of tests is not cast into JSON format");
    }
}