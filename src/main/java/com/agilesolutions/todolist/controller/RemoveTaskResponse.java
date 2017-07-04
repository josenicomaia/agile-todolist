package com.agilesolutions.todolist.controller;

import org.json.JSONException;
import org.json.JSONObject;

public class RemoveTaskResponse {

    private Boolean success;
    private String message;

    public RemoveTaskResponse() {

    }

    public RemoveTaskResponse(String message) {
        success = false;
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            JSONObject responseJson = new JSONObject();

            responseJson.put("success", success);

            if (message != null) {
                responseJson.put("message", message);
            }

            return responseJson.toString();
        } catch (JSONException ex) {
            return null;
        }
    }
}
