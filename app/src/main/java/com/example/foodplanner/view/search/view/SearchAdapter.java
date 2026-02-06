    package com.example.foodplanner.view.search.view;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.navigation.NavDirections;
    import androidx.navigation.Navigation;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.example.foodplanner.R;
    import com.example.foodplanner.data.model.Meal;

    import java.util.ArrayList;
    import java.util.List;

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        private List<Meal> meals = new ArrayList<>();
        private Context context;
        private boolean isGuest = false;
        private OnFavClickListener favListener;

        public interface OnFavClickListener {
            void onFavClick(Meal meal);
        }

        public SearchAdapter(Context context, OnFavClickListener favListener,boolean isGuest) {
            this.context = context;
            this.favListener = favListener;
            this.isGuest = isGuest;
        }

        public void setMeals(List<Meal> meals) {
            this.meals = meals;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.meal_card, parent, false);
            return new SearchAdapter.SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            Meal meal = meals.get(position);
            holder.mealName.setText(meals.get(position).getName());
            Glide.with(context).load(meals.get(position).getImageUrl()).into(holder.mealImage);

            holder.bind(meal);
            holder.itemView.setOnClickListener(v -> {
                NavDirections action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal.getId());

                Navigation.findNavController(v).navigate(action);
            });

            if (isGuest) {
                holder.addToFavBtn.setVisibility(View.GONE);
            } else {
                holder.addToFavBtn.setVisibility(View.VISIBLE);

                holder.addToFavBtn.setOnClickListener(v -> {
                    if (meal.isFav()) {
                        meal.setFav(false);
                        favListener.onFavClick(meal);
                    } else {
                        meal.setFav(true);
                        favListener.onFavClick(meal);
                    }

                    notifyItemChanged(position);
                });
            }
        }

        @Override
        public int getItemCount() {
            return meals.size();
        }

        class SearchViewHolder extends RecyclerView.ViewHolder {
            private ImageView mealImage;
            private TextView mealName;
            private ImageView addToFavBtn;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                mealImage = itemView.findViewById(R.id.mealImage);
                mealName = itemView.findViewById(R.id.mealName);
                addToFavBtn = itemView.findViewById(R.id.addToFavBtn);
            }

            public void bind(Meal meal) {
                mealName.setText(meal.getName());
                addToFavBtn.setImageResource(
                        meal.isFav()
                                ? R.drawable.baseline_favorite_24
                                : R.drawable.baseline_favorite_border_24
                );

            }
        }

    }
