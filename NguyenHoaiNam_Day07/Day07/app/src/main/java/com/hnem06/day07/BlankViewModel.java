package com.hnem06.day07;

import androidx.lifecycle.ViewModel;

import com.hnem06.day07.model.DifficultyEnum;
import com.hnem06.day07.model.ProductModel;

import java.util.ArrayList;

public class BlankViewModel extends ViewModel {

    private ArrayList<ProductModel> productList;

    public ArrayList<ProductModel> getProductList() {
        if (productList == null) {
            // Read from pref
            productList = new ArrayList<>();
            productList.add(new ProductModel(1, "Toast With Egg", "Delicious toast with egg", 5, DifficultyEnum.EASY, 2,
                    new ArrayList<>(), 4));
            productList.add(new ProductModel(2, "Grilled Chicken", "Tasty grilled chicken", 10, DifficultyEnum.MEDIUM, 4,
                    new ArrayList<>(), 5));
            productList.add(new ProductModel(3, "Caesar Salad", "Fresh caesar salad", 15, DifficultyEnum.EASY, 2,
                    new ArrayList<>(), 3));
            productList.add(new ProductModel(4, "Beef Steak", "Premium beef steak", 20, DifficultyEnum.HARD, 1,
                    new ArrayList<>(), 5));
            productList.add(new ProductModel(5, "Pasta Carbonara", "Classic Italian pasta", 25, DifficultyEnum.MEDIUM, 3,
                    new ArrayList<>(), 4));
            productList.add(new ProductModel(6, "Sushi Roll", "Japanese sushi roll", 30, DifficultyEnum.HARD, 2,
                    new ArrayList<>(), 5));
            productList.add(new ProductModel(7, "Toast With Egg", "Delicious toast with egg", 30, DifficultyEnum.EASY, 2,
                    new ArrayList<>(), 4));
            productList.add(new ProductModel(8, "Grilled Chicken", "Tasty grilled chicken", 45, DifficultyEnum.MEDIUM, 4,
                    new ArrayList<>(), 5));
            productList.add(new ProductModel(9, "Caesar Salad", "Fresh caesar salad", 15, DifficultyEnum.EASY, 2,
                    new ArrayList<>(), 3));
            productList.add(new ProductModel(10, "Beef Steak", "Premium beef steak", 60, DifficultyEnum.HARD, 1,
                    new ArrayList<>(), 5));
            productList.add(new ProductModel(15, "Pasta Carbonara", "Classic Italian pasta", 25, DifficultyEnum.MEDIUM, 3,
                    new ArrayList<>(), 4));
            productList.add(new ProductModel(16, "Sushi Roll", "Japanese sushi roll", 40, DifficultyEnum.HARD, 2,
                    new ArrayList<>(), 5));
        }
        return productList;
    }
}