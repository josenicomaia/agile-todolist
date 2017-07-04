package com.agilesolutions.todolist.controller;

import com.agilesolutions.todolist.domain.TaskItem;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListTaskResponse {

    private Boolean success;
    private Set<TaskItem> result;
    private String message;

    public ListTaskResponse(Set<TaskItem> result) {
        this(result, null);
    }

    public ListTaskResponse(Set<TaskItem> result, String message) {
        success = true;
        this.result = result;
        this.message = message;
    }

    public ListTaskResponse(String message) {
        success = false;
        this.message = message;
    }

    public ListTaskResponse(Exception ex) {
        this(ex.getClass() + ": " + ex.getMessage());
    }

    @Override
    public String toString() {
        try {
            JSONObject json = new JSONObject();
            json.put("success", success);

            if (result != null) {
                JSONArray jsonResult = new JSONArray();

                for (TaskItem taskItem : result) {
                    JSONObject jsonTaskItem = new JSONObject();
                    jsonTaskItem.put("id", taskItem.getId().getValue().toString());
                    jsonTaskItem.put("description", taskItem.getDescription());
                    jsonTaskItem.put("finished", taskItem.isFinished());
                    jsonResult.put(jsonTaskItem);
                }

                json.put("result", jsonResult);
            }

            if (message != null) {
                json.put("message", message);
            }

            return json.toString();
        } catch (JSONException ex) {
            return null;
        }
    }
}
