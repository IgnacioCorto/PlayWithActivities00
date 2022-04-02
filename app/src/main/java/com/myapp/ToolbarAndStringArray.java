package com.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ToolbarAndStringArray extends Activity {

    String[] mobileArray = {
            "Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X",
            "Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X",
            "Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_and_list);

        final Activity thisActivity = this;

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.item_note_string, mobileArray);

        ListView listView = (ListView) findViewById(R.id.notes_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),"Hello Javatpoint "+position+" - "+id,Toast.LENGTH_LONG).show();

                Intent i = new Intent(thisActivity, NoteEditor.class);
                startActivity(i);
            }
        });

    }

}