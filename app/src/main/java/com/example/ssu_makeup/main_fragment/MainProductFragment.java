package com.example.ssu_makeup.main_fragment;

import static com.bumptech.glide.Glide.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssu_makeup.adaptor.ProductReviewAdaptor;
import com.example.ssu_makeup.custom_class.Product;
import com.example.ssu_makeup.R;
import com.example.ssu_makeup.custom_class.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainProductFragment extends Fragment implements View.OnClickListener {
    ImageView starButton1;
    ImageView starButton2;
    ImageView starButton3;
    ImageView starButton4;
    ImageView starButton5;
    Button submitReviewButton;
    int reviewScore = 0;
    Product selectedProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_product, container, false);

        assert getArguments() != null;
        Product selectedProduct = (Product) getArguments().getSerializable("selected_product");
        with(requireContext()).load(selectedProduct.getProductImageURL()).into((ImageView) root.findViewById(R.id.product_item_image));
        root.findViewById(R.id.product_item_image).setClipToOutline(true);
        ((TextView) root.findViewById(R.id.product_item_brand)).setText(selectedProduct.getProductBrand());
        ((TextView) root.findViewById(R.id.product_item_name)).setText(selectedProduct.getProductName());
        ((TextView) root.findViewById(R.id.product_item_ingredient)).setText(selectedProduct.getProductIngredient());

        starButton1 = root.findViewById(R.id.review_star_button_1);
        starButton2 = root.findViewById(R.id.review_star_button_2);
        starButton3 = root.findViewById(R.id.review_star_button_3);
        starButton4 = root.findViewById(R.id.review_star_button_4);
        starButton5 = root.findViewById(R.id.review_star_button_5);
        submitReviewButton = root.findViewById(R.id.review_submit_button);
        starButton1.setOnClickListener(this);
        starButton2.setOnClickListener(this);
        starButton3.setOnClickListener(this);
        starButton4.setOnClickListener(this);
        starButton5.setOnClickListener(this);
        submitReviewButton.setOnClickListener(this);


        //TODO: 리뷰 불러오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CosmeticData").child(selectedProduct.getProductCategory()).child(selectedProduct.getProductIndex()).child("reviewList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Review> reviewArrayList = new ArrayList<>();
                reviewArrayList= new ArrayList<>();
                reviewArrayList.add(new Review(4, "김숭실", "추천해요!"));
                int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Review review = new Review(Integer.parseInt(dataSnapshot.child(Integer.toString(i)).child("reviewScore").getValue(String.class)), dataSnapshot.child(Integer.toString(i)).child("reviewer").getValue(String.class), dataSnapshot.child(Integer.toString(i)).child("review").getValue(String.class));
                    reviewArrayList.add(review);
                    i++;
                }
                RecyclerView recyclerView = root.findViewById(R.id.product_review_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                ProductReviewAdaptor productReviewAdaptor = new ProductReviewAdaptor(reviewArrayList);
                recyclerView.setAdapter(productReviewAdaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
    }

    @Override
    public void onClick(View view) {
        setStarButton();

        if (view == starButton1) {
            starButton1.setImageResource(R.drawable.ic_review_star_red);
            reviewScore = 1;
        } else if (view == starButton2) {
            starButton1.setImageResource(R.drawable.ic_review_star_red);
            starButton2.setImageResource(R.drawable.ic_review_star_red);
            reviewScore = 2;
        } else if (view == starButton3) {
            starButton1.setImageResource(R.drawable.ic_review_star_red);
            starButton2.setImageResource(R.drawable.ic_review_star_red);
            starButton3.setImageResource(R.drawable.ic_review_star_red);
            reviewScore = 3;
        } else if (view == starButton4) {
            starButton1.setImageResource(R.drawable.ic_review_star_red);
            starButton2.setImageResource(R.drawable.ic_review_star_red);
            starButton3.setImageResource(R.drawable.ic_review_star_red);
            starButton4.setImageResource(R.drawable.ic_review_star_red);
            reviewScore = 4;
        } else if (view == starButton5) {
            starButton1.setImageResource(R.drawable.ic_review_star_red);
            starButton2.setImageResource(R.drawable.ic_review_star_red);
            starButton3.setImageResource(R.drawable.ic_review_star_red);
            starButton4.setImageResource(R.drawable.ic_review_star_red);
            starButton5.setImageResource(R.drawable.ic_review_star_red);
            reviewScore = 5;
        } else if (view == submitReviewButton) {
            if (reviewScore == 0)
                Toast.makeText(requireContext(), "별점을 선택해 주세요", Toast.LENGTH_SHORT).show();
            else {
                //TODO: 리뷰 등록 구현
                FirebaseAuth mfirebase = FirebaseAuth.getInstance();
                String uid = mfirebase.getCurrentUser().getUid();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = databaseReference.child("CosmeticData").child(selectedProduct.getProductCategory()).child(selectedProduct.getProductIndex()).child("reviewList").push().getKey();
                        Review review = new Review(reviewScore, snapshot.child(uid).child("firstName").getValue(String.class) + snapshot.child(uid).child("lastName").getValue(String.class), "제품이 좋아요!");
                        Map<String, Object> reviewValues = review.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/reviewList/" + key, reviewValues);

                        databaseReference.updateChildren(childUpdates);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }
        void setStarButton () {
            starButton1.setImageResource(R.drawable.ic_review_star_grey);
            starButton2.setImageResource(R.drawable.ic_review_star_grey);
            starButton3.setImageResource(R.drawable.ic_review_star_grey);
            starButton4.setImageResource(R.drawable.ic_review_star_grey);
            starButton5.setImageResource(R.drawable.ic_review_star_grey);
        }

}