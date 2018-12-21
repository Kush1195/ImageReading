package com.example.admin.imagereading;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageDisplay extends AppCompatActivity
{
    ImageView image_display;
    TextView textView;
    Bundle b;
    String image,detail;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        init();

        b = getIntent().getExtras();

        image = b.getString("x");
        detail = b.getString("detail");

        uri = Uri.parse(image);

        image_display.setImageURI(uri);
        textView.setText(detail);
    }

    private void init()
    {
        image_display = findViewById(R.id.image_display);
        textView = findViewById(R.id.textView);
    }
}
