package com.project.krishna.bakingapp.data;

/**
 * Created by Krishna on 1/11/18.
 */

public class Recipes {
    String id;
    String name;
    String servings;



    String thumnailURL;

    public String getThumnailURL() {
        return thumnailURL;
    }

    public void setThumnailURL(String thumnailURL) {
        this.thumnailURL = thumnailURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }
}
