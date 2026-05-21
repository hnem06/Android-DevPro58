package com.hnem06.day07.model;

import java.util.ArrayList;

public class ProductModel {
    int id;
    String name;
    String description;
    int cookingTime;
    DifficultyEnum difficulty;
    int servings;

    ArrayList<Integer> ingredients;

    int rate;
    boolean liked;

    public ProductModel(int id, String name, String description, int cookingTime, DifficultyEnum difficulty, int servings, ArrayList<Integer> ingredients, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cookingTime = cookingTime;
        this.difficulty = difficulty;
        this.servings = servings;
        this.ingredients = ingredients;
        this.rate = rate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCookingTime() { return cookingTime; }
    public DifficultyEnum getDifficulty() { return difficulty; }
    public int getServings() { return servings; }
    public ArrayList<Integer> getIngredients() { return ingredients; }
    public int getRate() { return rate; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
}
