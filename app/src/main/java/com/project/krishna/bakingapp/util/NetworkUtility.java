package com.project.krishna.bakingapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Krishna on 1/11/18.
 */

public class NetworkUtility {

    public static final String RECIPE_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static String getRespsonseFromHttpUrl(URL url)throws IOException {
        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
        InputStream inputStream=null;

        try {
            inputStream=urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert inputStream != null;
        Scanner scanner=new Scanner(inputStream);
        scanner.useDelimiter("\\A");
        boolean hasInput=scanner.hasNext();
        if(hasInput){
            return scanner.next();
        }
        else {
            return null;
        }
    }
}
