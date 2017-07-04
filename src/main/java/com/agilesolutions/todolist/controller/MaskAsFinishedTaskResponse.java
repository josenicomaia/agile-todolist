package com.agilesolutions.todolist.controller;

import com.agilesolutions.todolist.domain.TaskItem;
import org.json.JSONException;
import org.json.JSONObject;

public class MaskAsFinishedTaskResponse {

    private Boolean success;
    private TaskItem result;
    private String message;

    public MaskAsFinishedTaskResponse(TaskItem result) {
        this(result, null);
    }

    public MaskAsFinishedTaskResponse(TaskItem result, String message) {
        success = true;
        this.result = result;
        this.message = message;
    }

    public MaskAsFinishedTaskResponse(String message) {
        success = false;
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            JSONObject responseJson = new JSONObject();

            responseJson.put("success", success);

            if (result != null) {
                JSONObject resultJson = new JSONObject();
                resultJson.put("id", result.getId().getValue().toString());
                resultJson.put("description", result.getDescription());
                resultJson.put("finished", result.isFinished());

                responseJson.put("result", resultJson);
            }

            if (message != null) {
                responseJson.put("message", message);
            }

            return responseJson.toString();
        } catch (JSONException ex) {
            return null;
        }
    }
}
