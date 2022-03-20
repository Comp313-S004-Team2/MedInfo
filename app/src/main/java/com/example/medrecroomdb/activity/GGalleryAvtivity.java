package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Short3;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.StorageItem;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;
import com.example.medrecroomdb.Amplifyy;
import com.example.medrecroomdb.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class GGalleryAvtivity extends AppCompatActivity {

    TextView FileNameTV;
    public String path;
    public String key;
    public String origin;
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



    }

    private void downloadfiles(ArrayList<String> file){

        File downloadfolder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);


        ArrayList<S3file> filepaths = new ArrayList<S3file>();
        S3file fileobj = new S3file();
         file.size();
         Random rand = new Random();
         int num = rand.nextInt(9999);
        Amplify.Storage.downloadFile(
                "ExampleKey",
                new File(downloadfolder+"/download"+rand+".jpg"),
                StorageDownloadFileOptions.defaultInstance(),
                progress -> Log.i("MyAmplifyApp", "Fraction completed: " + progress.getFractionCompleted()),
                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName() + "Path" + result.getFile().getAbsolutePath()),

                      /*  "Name" + result.getFile().getName(), fileobj.path = result.getFile().getAbsolutePath(), fileobj.key = result.getFile().getName(),),*/
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)

        );




    }

    public void downloadprogress( String file){
        FileNameTV.setVisibility(View.VISIBLE);
        FileNameTV.setText(file+"Downloaded");

    }

    public class S3file{
        String path;
        String key;
        String origin;

    }
}

