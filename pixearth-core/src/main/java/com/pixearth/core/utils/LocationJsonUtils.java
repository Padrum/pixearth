package com.pixearth.core.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;
import com.google.gson.JsonParser;

/**
 * Classe utilitaire qui permet de transformer un objet Location en un json et vise versa
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class LocationJsonUtils {

    /**
     * Permet de transformer une location en un objet json
     *
     * @param location  Location
     * @return  Retourne un objet json de la location
     */
    public static JSONObject toJson(Location location) {

        JSONObject json = new JSONObject();

        json.put("x", location.getX());
        json.put("y", location.getY());
        json.put("z", location.getZ());
        json.put("world", location.getWorld().getName());
        json.put("pitch", location.getPitch());
        json.put("yaw", location.getYaw());

        return json;
    }

    /**
     * Transforme une chaine de caratere en un objet Location
     *
     * @param jsonStr   Json
     * @return  Retourne un objet Location si le json est valide
     */
    public static Location formJson(String jsonStr) {

        if(!jsonStr.isEmpty()) {

            JsonElement element = new JsonParser().parse(jsonStr);

            if(element.isJsonObject()) {

                JsonObject json = element.getAsJsonObject();

                long x = json.get("x").getAsLong();
                long y = json.get("y").getAsLong();
                long z = json.get("z").getAsLong();
                String worldName = json.get("world").getAsString();

                if (worldName != null) {
                    World worldObject = Bukkit.getWorld(worldName);
                    if (worldObject != null) {

                        Location location = new Location(worldObject, x, y, z);
                            location.setPitch(json.get("pitch").getAsFloat());
                            location.setYaw(json.get("yaw").getAsFloat());

                        return location;
                    }
                }
            }
        }

        return null;
    }

}
