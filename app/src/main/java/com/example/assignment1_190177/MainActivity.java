package com.example.assignment1_190177;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ItemClickListener {

    public static final String RESTAURANT_OBJECT = "RestaurantObject";
    private List<Restaurant> restaurantlist = new ArrayList<>();
    private RestaurantAdapter restaurantAdapter;

    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        restaurantAdapter = new RestaurantAdapter();



        FirebaseDatabase database = FirebaseDatabase.getInstance();
       database.setPersistenceEnabled(true);

        myRef = database.getReference("Restaurant");

        setSampleRestaurantData();


        writeRestaurantDataToFirebase();
        readRestaurantDataToFirebase();


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(restaurantAdapter);

        restaurantAdapter.setItemClickListener(this);

    }

    private void readRestaurantDataToFirebase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "Snapshot does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                restaurantlist.clear();
                for (DataSnapshot  restaurantSnapShot: dataSnapshot.getChildren()) {
                    Restaurant restaurant = restaurantSnapShot.getValue(Restaurant.class);
                    restaurantlist.add(restaurant);

                }

                restaurantAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "DB Error: ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void writeRestaurantDataToFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Restaurant");

        myRef.setValue(restaurantlist).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "restaurant data saved to database", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "restaurant data failed to save to database", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void OnClick(View view, int position) {
        //Toast.makeText(this, restaurantlist.get(position).getRestaurantname(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,DetailActivity.class);
        Restaurant restaurant = restaurantlist.get(position);
        intent.putExtra(RESTAURANT_OBJECT, restaurant);

        startActivity(intent);


    }


    private class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
        private ItemClickListener itemClickListener;

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.restaurant_list_row, parent, false);

            return new RestaurantViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            Restaurant restaurant = restaurantlist.get(position);
            holder.tvrestaurantname.setText(restaurant.getRestaurantname());
            holder.tvcuisine.setText(restaurant.getCuisine());
            holder.tvrating.setText(restaurant.getRating() + "");
            holder.tvlocation.setText(restaurant.getLocation());
            holder.tvdelivery.setText(restaurant.getDelivery());
            int resID = getResources().getIdentifier(restaurant.getRestaurantImageName(), "drawable", getPackageName());
            holder.ivrestaurantImageName.setImageResource(resID);


        }

        @Override
        public int getItemCount() {
            if (restaurantlist.size() > 0)
                return restaurantlist.size();
            else
                return 0;
        }

        private void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvrestaurantname;
            private TextView tvcuisine;
            private TextView tvrating;
            private TextView tvlocation;
            private TextView tvdelivery;

            private ImageView ivrestaurantImageName;

            public RestaurantViewHolder(@NonNull View itemView) {
                super(itemView);

                tvrestaurantname = itemView.findViewById(R.id.tvrestaurantname);
                tvcuisine = itemView.findViewById(R.id.tvcuisine);
                tvrating = itemView.findViewById(R.id.tvrating);
                tvlocation = itemView.findViewById(R.id.tvlocation);
                tvdelivery = itemView.findViewById(R.id.tvdelivery);
                ivrestaurantImageName = itemView.findViewById(R.id.ivrestaurantimage);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.OnClick(view, getAdapterPosition());
                }
            }
        }
        }





    private void setSampleRestaurantData() {
        Restaurant restaurant = new Restaurant(
                "Shack Shack",
                "Cuisine: American",
                "5 star",
                "Location: Mall of Muscat",
                "Delivery? yes",
                "burger", 1.5, "Delivery Fee","Shake Shack is a modern day “roadside” burger stand serving a classic American menu of premium burgers, hot dogs, crinkle-cut fries, shakes, frozen custard, beer and wine. With its fresh and simple, high-quality food at a great value, Shake Shack is a fun and lively community-gathering place with widespread appeal."


        );
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("Mcdonalds", "Cuisine:American", " 4 Star", "Location: Muscat Grand Mall", "Delivery? yes", "mcdonalds", 2.0,"Delivery Fee: ",  "McDonald's Corp (McDonald's) is a foodservice retail chain operator. The company operates and franchises McDonald's restaurants that serve a locally-relevant menu of food and beverages.");
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("KFC", "Cuisine: American", " 4 Star", "Location: City Center", "Delivery? yes","kfc", 1.5,"Delivery Fee: ", "KFC is a global chicken restaurant brand with a rich, decades-long history of success and innovation. It all started with one cook, Colonel Harland Sanders, who created a finger lickin' good recipe more than 75 years ago—a list of 11 secret herbs and spices scratched out on the back of his kitchen door." );
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("Pizza Hut", "Cuisine: American", " 3 Star", "Location: City Center", "Delivery? yes","pizza", 1.2,"Delivery Fee: ", "Pizza Hut is an American multinational restaurant chain and international franchise founded in 1958 in Wichita, Kansas by Dan and Frank Carney. They serve their signature pan pizza and other dishes including pasta, breadsticks and dessert at dine-in, take-out and delivery chain locations." );
        restaurantlist.add(restaurant);



        restaurant = new Restaurant("Muay Thai", "Cuisine: Thai", " 2 Star", "Location: Mall of Oman", "Delivery? yes","muaythai",2.0,"Delivery Fee: ", "Street food in Thailand brings together various offerings of ready-to -eat meals,snacks,fruits and drinks sold by hawkers or vendors" );
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("Nandos", "Cuisine: American", " 4 Star", "Location: CCC", "Delivery? yes","nandos", 1.0,"Delivery Fee: ",  "Nando's (/ˈnændoʊz/; Afrikaans: [ˈnandœs]) is a South African multinational fast casual chain that specialises in Portuguese flame-grilled peri-peri style chicken. Founded in Johannesburg in 1987, Nando's operates over 1,200 outlets in 30 countries.");
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("Chilis", "Cuisine: American", " 4 Star", "Location: CCC", "Delivery? yes","chilis", 1.5,"Delivery Fee: ",  "Chili's serves American food, Tex-Mex cuisine and dishes influenced by Mexican cuisine, such as spicy shrimp tacos, quesadillas, fajitas. In addition to their regular menu, the company offers a nutritional menu, allergen menu, and vegetarian menu.");
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("Burger King", "Cuisine: American", " 3 Star", "Location: Mall of Muscat", "Delivery? yes","burgerking", 2.5,"Delivery Fee: ",  "Founded in 1954, BURGER KING® is the second largest fast food hamburger chain in the world. The original HOME OF THE WHOPPER®, our commitment to premium ingredients, signature recipes, and family-friendly dining experiences is what has defined our brand for more than 50 successful years.");
        restaurantlist.add(restaurant);

        restaurant = new Restaurant("PF Changs", "Cuisine: Thai", " 4 Star", "Location: Mall of Muscat", "Delivery? yes","pfchangs", 3.0,"Delivery Fee", "P.F. Chang's is an Asian restaurant concept founded on making food from scratch every day in every restaurant. Created in 1993 by Philip Chiang and Paul Fleming, P.F.");
        restaurantlist.add(restaurant);



        restaurantAdapter.notifyDataSetChanged();

    }


}



