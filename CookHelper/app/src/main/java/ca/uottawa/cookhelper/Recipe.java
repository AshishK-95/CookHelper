package ca.uottawa.cookhelper;

import java.io.Serializable;
import java.util.ArrayList;

// Data storage class, stores a single recipe
public class Recipe implements Serializable {
    Integer id;
    String imgUrl;
    private ArrayList<Integer> ingredients = new ArrayList(0);
    private ArrayList<Integer> steps = new ArrayList(0);
    String title;
    private String cuisine, course;
    private String prep_time_unit, cook_time_unit;
    private int calories, serving_size;
    private float prep_time, cook_time;


    public String getTitle() {
        return title;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getCourse() {
        return course;
    }

    public int getCalories() {
        return calories;
    }

    public float getPrepTime() {
        return prep_time;
    }

    public String getPrepTimeUnit() {
        return prep_time_unit;
    }

    public float getCookTime() {
        return cook_time;
    }

    public int getId() { return id;}

    public String getCookTimeUnit() {
        return cook_time_unit;
    }

    public int getServingSize() {
        return serving_size;
    }

    public ArrayList getIngredients() { return ingredients; }

    public ArrayList getDirections() { return steps; }


    //Setter methods
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setCuisine(String newCuisine) {
        cuisine = newCuisine;
    }

    public void setCourse(String newCourse) {
        course = newCourse;
    }

    public void setCalories(int newCalories) {
        calories = newCalories;
    }

    public void setPrepTime(int newPrepTime) {
        prep_time = newPrepTime;
    }

    public void setCookTime(int newCookTime) {
        cook_time = newCookTime;
    }

    public void setServingSize(int newServingSize) {
        serving_size = newServingSize;
    }

    public void setPrepTimeUnit (String newPrep) { prep_time_unit = newPrep; }

    public void setCookTimeUnit (String newCook) { cook_time_unit = newCook; }

    public void setIngredients(ArrayList<Integer> newIngredients){
        ingredients = newIngredients;
    }

    public void setSteps(ArrayList<Integer> newSteps){ steps = newSteps; }

    public void setId(int id) {
        this.id = id;
    }

}