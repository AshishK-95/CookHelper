package ca.uottawa.cookhelper;

/**
 * Created by Alek on 2016-12-06.
 */

public class Step {


    Integer id;

    Integer recipe_id;

    String description;


    public Step (Integer id, Integer recipe_id, String desc){
        this.id = id;
        this.recipe_id = recipe_id;
        this.description = desc;
    }


    public int getId() {
        return id;
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public String getDescription() {
        return description;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}