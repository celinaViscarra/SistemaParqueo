package com.grupo13.parqueo.comments;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.grupo13.parqueo.R;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //vars
    private ArrayList<String> nNames = new ArrayList<>();
    private ArrayList<String> nImagesUrls = new ArrayList<>();
    private ArrayList<String> nComments = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    String name = account.getDisplayName();
    String email = account.getEmail();
    String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_tool);

        initImageBitmas();
    }

    private void initImageBitmas(){
        //for add the images

        nComments.add("FEO");
        nImagesUrls.add("https://pm1.narvii.com/7093/84196c693eba950461690eb36ad46bf1e7cb1ae1r1-332-363v2_uhq.jpg");
        nNames.add("LUCIA ZAMORANO");

        nComments.add("HERMOSO");
        nImagesUrls.add("https://st-listas.20minutos.es/images/2015-03/394837/4670373_640px.jpg");
        nNames.add("MARIA DE LOS ANGELES");

        nComments.add("HORRIBLE");
        nImagesUrls.add("https://pm1.narvii.com/6354/1ab557c7209ee79663a32dc26cd888f9e67e6fa9_hq.jpg");
        nNames.add("CASIMIRA DEL SOL");

        nComments.add("PRECIOSOOOOOOOO");
        nImagesUrls.add("https://pm1.narvii.com/6354/5019d49dc977fbadaf59164597b590014ee882b0_hq.jpg");
        nNames.add("CATALINA VEGANA");

        nComments.add("NO, JAMAS REGRESARE");
        nImagesUrls.add("https://cellularnews.com/wp-content/uploads/2020/04/spongebob-and-patrick-yellow-hearts-325x485.jpg");
        nNames.add("PATRICIO ESTRELLA");

        nComments.add("BIEN");
        nImagesUrls.add("https://www.thqnordic.com/sites/default/files/games/gallery/SpongeBob_BfBB_00.jpg");
        nNames.add("TORONJA NARANJA");
        //calling the recyclerview
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nNames, nImagesUrls, nComments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
