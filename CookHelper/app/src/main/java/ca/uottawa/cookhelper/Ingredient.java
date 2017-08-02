package ca.uottawa.cookhelper;

/**
 * Created by Alek on 2016-12-06.
 */

public class Ingredient {


    Integer id;

    Integer recipe_id;

    String title;

    String quantity;


    public Ingredient (Integer id, Integer recipe_id, String name, String quantity){
        this.id = recipe_id;
        this.recipe_id = recipe_id;
        this.title = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public String getName() {
        return title;
    }

    public String getQuantity() {
        return quantity;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setName(String name) {
        this.title = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}