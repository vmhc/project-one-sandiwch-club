package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String UNKNOWN = "unknown";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvAlsoKnownAsDescription;
    private TextView tvIngredientsDescription;
    private TextView tvPlaceOfOriginDescription;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvAlsoKnownAsDescription = findViewById(R.id.tv_alsoKnownAsDescription);
        tvIngredientsDescription = findViewById(R.id.tv_ingredientsDescription);
        tvPlaceOfOriginDescription = findViewById(R.id.tv_placeOfOriginDescription);

        tvDescription = findViewById(R.id.tv_description);

        ImageView ivIngredients = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        // Set ui data here.
        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ivIngredients);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().size() > 0) {
            for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
                tvAlsoKnownAsDescription.append(alsoKnownAs + "\n");
            }
        } else {
            tvAlsoKnownAsDescription.append(UNKNOWN + "\n");
        }

        if (sandwich.getIngredients().size() > 0) {
            for (String ingredients : sandwich.getIngredients()) {
                tvIngredientsDescription.append(ingredients + "\n");
            }
        } else {
            tvIngredientsDescription.append(UNKNOWN + "\n");
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            tvPlaceOfOriginDescription.append(sandwich.getPlaceOfOrigin() + "\n");
        } else {
            tvPlaceOfOriginDescription.append(UNKNOWN + "\n");
        }

        if (!sandwich.getDescription().isEmpty()) {
            tvDescription.append(sandwich.getDescription() + "\n");
        } else {
            tvDescription.append(UNKNOWN + "\n");
        }
    }
}
