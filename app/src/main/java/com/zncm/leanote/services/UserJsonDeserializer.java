package com.zncm.leanote.services;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.zncm.leanote.data.User;

import java.lang.reflect.Type;


public class UserJsonDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        User user = new User();
        user.UserId = jsonObject.get("UserId").getAsString();
        user.Username = jsonObject.get("Username").getAsString();
        user.Email = jsonObject.get("Email").getAsString();
        user.Token = jsonObject.get("Token").getAsString();
        return user;
    }


}
