package com.example.foodplanner.categorey.presenter;

import com.example.foodplanner.datasource.remote.CategoriesNetworkResponse;
import com.example.foodplanner.datasource.remote.MealsRemoteDataSource;
import com.example.foodplanner.categorey.*;
import com.example.foodplanner.model.Category;

import java.util.List;

public class CategoryPresenterImp implements CategoryPresenter{ //TODO

    private CategoryView view;
    private MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();

    public CategoryPresenterImp(CategoryView view) {
        this.view = view;
    }

    @Override
    public void getCategories() {
        remoteDataSource.getCategories(new CategoriesNetworkResponse() {
            @Override
            public void onSuccess(List<Category> categories) {
                if(view !=null && categories !=null){
                    view.showCategories(categories);
                }
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }

            @Override
            public void onInternetError(String message) {
                view.showError(message);
            }
        });
    }
}
