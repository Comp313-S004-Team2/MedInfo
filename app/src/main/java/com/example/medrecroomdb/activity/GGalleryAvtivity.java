package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.StorageItem;
import com.example.medrecroomdb.Amplifyy;
import com.example.medrecroomdb.R;

import java.util.ArrayList;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class GGalleryAvtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggallery_avtivity);


        /*Amplify.Storage.list("",
                result -> {

                    String filesTxt = new String();
                    String files = new String();

                    for (StorageItem item : result.getItems()) {
                        Log.i("MyAmplifyApp", "Item: " + item.getKey());
                        filesTxt += item.getKey() + " ${(item.size).div(1000)}KB";
                        files += item.getKey();
                    }
                    Dispatchers.getMain(){
                },
                error -> Log.e("MyAmplifyApp", "List failure", error)
        );*/


        //private void downloadfiles(String file)
    }
}