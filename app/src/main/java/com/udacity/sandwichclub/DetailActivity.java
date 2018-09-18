package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private TextView mAlsoKnownAsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionTextView;
    private ImageView ingredientsIv;
    private TextView mAlsoKnownAsLabelTextView;
    private TextView mPlaceOfOriginLabelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = findViewById(R.id.place_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mAlsoKnownAsLabelTextView = findViewById(R.id.als_known_label_tv);
        mPlaceOfOriginLabelTextView = findViewById(R.id.place_of_origin);

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

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            closeOnError();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // if there is no text in the placeOfOrigin attribute so there is no need to show the label
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())){
            mPlaceOfOriginTextView.setVisibility(View.GONE);
            mPlaceOfOriginLabelTextView.setVisibility(View.GONE);
        }

        // if there is no text in the alsoKnownAs  attribute so there is no need to show the label
        if (sandwich.getAlsoKnownAs().size()==0){
            mAlsoKnownAsLabelTextView.setVisibility(View.GONE);
            mAlsoKnownAsTextView.setVisibility(View.GONE);
        }

        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin()+"\n");
        mDescriptionTextView.setText(sandwich.getDescription());

        for(String ingredient : sandwich.getIngredients()){
            mIngredientsTextView.append(ingredient+"\n");
        }

        for (String knownAs : sandwich.getAlsoKnownAs()){
            mAlsoKnownAsTextView.append(knownAs+"\n");
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }
}
