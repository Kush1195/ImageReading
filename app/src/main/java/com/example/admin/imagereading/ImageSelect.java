package com.example.admin.imagereading;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;

public class ImageSelect extends AppCompatActivity
{
    ImageView image_select;
    EditText text;
    TextView Name,path,width,height,size;
    Button submit;
    Uri uri;
    String x,get;

    private final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        init();

        image_select.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ImageSelect.this,ImageDisplay.class);
                get = text.getText().toString();

                if (x == null)
                {
                    Toast.makeText(ImageSelect.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (get.matches(""))
                {
                    Toast.makeText(ImageSelect.this, "Please enter text", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    intent.putExtra("x",x);
                    intent.putExtra("detail",get);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && data != null && data.getData() != null )
        {
            uri = data.getData();
            image_select.setImageURI(uri);
            x = uri.toString();
            size(x);
        }
    }

    public String size(String uri)
    {
        String pathh = getRealPathFromURI(Uri.parse(uri));

        File file = new File(pathh);
        String name = file.getName();
        String path1 = file.getAbsolutePath();

        File file1 = new File(path1);
        long length = file1.length();
        length = length / 1024 ;

        Uri uri1 = Uri.parse(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        try
        {
            BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri1),null, options);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        Name.setText(name);
        path.setText(path1);
        width.setText(Integer.toString(imageWidth));
        height.setText(Integer.toString(imageHeight));
        size.setText(Long.toString(length)+"KB");

        return null;
    }
    public String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void init()
    {
        image_select = findViewById(R.id.image_select);
        text = findViewById(R.id.text);
        submit = findViewById(R.id.submit);

        Name = findViewById(R.id.Name);
        path = findViewById(R.id.path);

        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        size = findViewById(R.id.Size);
    }
}
