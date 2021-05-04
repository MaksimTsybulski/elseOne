package finaltask.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import finaltask.project.model.TestQA;
import kong.unirest.json.JSONArray;
import finaltask.framework.rest.UniRestController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static TestQA getTestFromJson(String json){
        return GSON.fromJson(json, TestQA.class);
    }

    public static List<TestQA> getListTestFromJson(String projectId, int numberAttempts){
        JSONArray array = new JSONArray(UniRestController.getJsonTests(projectId,numberAttempts).toJSONString());
        List<TestQA> testQAList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String obj = array.get(i).toString();
            testQAList.add(getTestFromJson(obj));
        }
        Collections.sort(testQAList);
        return testQAList;
    }

}

