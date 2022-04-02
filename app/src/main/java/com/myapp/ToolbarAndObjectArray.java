package com.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToolbarAndObjectArray extends Activity {

    ArrayList<Note> noteList;
    Note oneNote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_and_list);


        Button clickButton = (Button) findViewById(R.id.openMenu);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Should open menu...");
                openOptionsMenu();
            }
        });


        final Activity thisActivity = this;
        noteList = new ArrayList<Note>();
        for(int i=1;i<=25;i++){
            oneNote = new Note();
            oneNote.name = "Nombre "+i;
            oneNote.text = "Texto "+i;
            noteList.add(oneNote);
        }

        final NotesAdapter notesAdapter = new NotesAdapter(this, noteList);

        ListView listView = (ListView) findViewById(R.id.notes_list);
        listView.setAdapter(notesAdapter);
//        registerForContextMenu(listView);   // ENABLE LONG CLICK HERE

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("Hello Javatpoint "+position+" - "+id);

//                Intent i = new Intent(thisActivity, NoteEditor.class);
//                startActivity(i);

                registerForContextMenu(parent);     // DISABLE LONG CLICK HERE
                openContextMenu(view);
                unregisterForContextMenu(parent);   // DISABLE LONG CLICK HERE
            }
        });


//        // PLAIN ALERT DIALOG
//        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setTitle("Alert");
//        alertDialog.setMessage("Alert message to be shown");
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();


        Button btnAlert1 = (Button) findViewById(R.id.btnAlert1);
        btnAlert1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCustomAlert();
            }
        });

        // open custom alert on load
        makeCustomAlert();
    }

    // recursive
    public static void resizeAllTextViews(View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    // recursively call this method
                    resizeAllTextViews(child);
                }
            } else if (v instanceof TextView) {
                //do whatever you want ...
                TextView tv1 = (TextView) v;
                tv1.setTextSize(30);
                Log.d("Resizing (1)", tv1.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void makeCustomAlert(){
        // CUSTOM ALERT DIALOG
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_ad, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Custom Alert");

        resizeAllTextViews(dialogView);

        TextView editText = (TextView) dialogView.findViewById(R.id.textView4);
        editText.setText("test label");

        final AlertDialog alertDialog = dialogBuilder.create();

        editText = (TextView) dialogView.findViewById(R.id.textView5);
        editText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }



    private void showToast(String message){
        Toast.makeText(getApplicationContext(),"MENU: "+message,Toast.LENGTH_SHORT).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menui00, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = "Invalid menu";
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newm:
                showToast("Creating new file");
                return true;
            case R.id.open:
                showToast("Opening existent file");
                return true;
            case R.id.save:
                showToast("Saving current file");
                return true;
            case R.id.close:
                showToast("Closing program");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        AdapterView.AdapterContextMenuInfo info =
//                (AdapterView.AdapterContextMenuInfo) menuInfo;
//
//        String itemTitle = ((TextView) info.targetView.findViewById(R.id.name)).getText().toString();
//        menu.setHeaderTitle(itemTitle);
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_floating, menu);
//
////        contextMenu.add(0, CONTEXT_MENU_EDIT_ITEM, 0, R.string.edit);
////        contextMenu.add(0, CONTEXT_MENU_DELETE_ITEM, 1, R.string.delete);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

        String itemTitle = ((TextView) info.targetView.findViewById(R.id.name)).getText().toString();
        contextMenu.setHeaderTitle(itemTitle);

        // GroupID, ItemID, OrderID, Title
        contextMenu.add(0,1,0,"Primero");
        contextMenu.add(0,2,0,"Segundo");
        SubMenu subMenu = contextMenu.addSubMenu(0,3,0,"itemTitle: Sub Menu");
        subMenu.add(0,4,0,"Sub Primero");
        subMenu.add(0,5,0,"Sub Segundo");

        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case 1:
                showToast("Uno Dinámico");
                break;
            case 2:
                showToast("Dos Dinámico");
                break;
            case 3:
                showToast("Tres Dinámico");
                break;
            case 4:
                showToast("Cuantro Dinámico");
                break;
            case 5:
                showToast("Cinco Dinámico");
                break;
            default:
                return super.onContextItemSelected(item);
        }


        switch (item.getItemId()) {
            case R.id.float1:
                showToast("In Float One");
                return true;
            case R.id.float2:
                showToast("In Float Two");
                return true;
            case R.id.float3:
                showToast("In Float Three");
                return true;
            case R.id.float4:
                showToast("In Float Four");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}