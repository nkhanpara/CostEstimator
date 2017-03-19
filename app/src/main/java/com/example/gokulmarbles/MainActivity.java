package com.example.gokulmarbles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    double grandTotal = 0;
    private static boolean isCurrentModeMarble = true;
    private static boolean isPasswordSet = false;
    final String[] header = {"Inch1",
            "Inch2",
            "Piece",
            "Feet1",
            "Feet2",
            "Feet",
            "Total"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!isPasswordSet)
            validatePassword();
        addTableRow(header, "#A35507");
    }
    private void addTableRow(String[] dataString, String bgColor) {

        TableLayout tableview = (TableLayout) findViewById(R.id.maintable);
        tableview.setPadding(15, 3, 15, 3);

        TableRow row = new TableRow(this);

        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        row.setPadding(15, 3, 15, 3);

        row.setBackgroundColor(Color.parseColor(bgColor));
        for (int j = 0; j < 7; j++) {
            TextView Values = new TextView(this);
            Values.setPadding(5, 0, 5, 0);
            Values.setGravity(Gravity.CENTER);
            //Values.setTextSize(25.0f);
            Values.setTextColor(Color.parseColor("#FFFFFF"));
            Values.setTypeface(null, Typeface.BOLD);
            Values.setText(dataString[j]);
            row.addView(Values);
        }
        tableview.addView(row);
        EditText inch1 = (EditText) findViewById(R.id.inch1EditText);
        inch1.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.data:
                showData();
                return true;
            case R.id.about:
                showAbout();
                return true;
            case R.id.clearTable:
                clearTable();
                return true;
            case R.id.menu_toogle:
                changeMode(item);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeMode(MenuItem item) {
        if(isCurrentModeMarble){
            item.setTitle("Selected: KOTA");
            isCurrentModeMarble=false;
        }else{
            item.setTitle("Selected: Marble");
            isCurrentModeMarble=true;
        }
        clearTable();
    }

    private void clearTable() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.maintable);
        tableLayout.removeAllViews();
        grandTotal = 0;
        TextView grandtotalTextView = (TextView) findViewById(R.id.grandTotalMenu);
        grandtotalTextView.setText("Total:0");
        addTableRow(header, "#A35507");
        EditText inch1 = (EditText) findViewById(R.id.inch1EditText);
        inch1.requestFocus();
    }

    private void showData() {
        Intent intent = new Intent(this, DisplayDataActivity.class);
        startActivity(intent);
    }

    private void showAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onAdd2ButtonClicked(View view) {
        EditText inch1 = (EditText) findViewById(R.id.inch1EditText);
        EditText inch2 = (EditText) findViewById(R.id.inch2EditText);
        EditText piece = (EditText) findViewById(R.id.pieceEditText);
        int inch1N, inch2N, pieceN = 1;
        String[] trowcontent = new String[7];
        if (inch1.getText().length() != 0) {
            inch1N = Integer.parseInt(inch1.getText().toString());
            if (inch2.getText().length() != 0) {
                inch2N = Integer.parseInt(inch2.getText().toString());
                if (piece.getText().length() != 0) {
                    pieceN = Integer.parseInt(piece.getText().toString());
                }
                double feet1, feet2, feet, rtotal;
                if (isCurrentModeMarble) {
                    if (inch1N > DBDefaultValues.MarbleData.length || inch2N > DBDefaultValues.MarbleData.length) {
                        Toast.makeText(MainActivity.this, "Inch values vadhu nakhi didhi bhai", Toast.LENGTH_LONG).show();
                        return;
                    }
                    feet1 = DBDefaultValues.MarbleData[inch1N - 1];
                    feet2 = DBDefaultValues.MarbleData[inch2N - 1];
                } else {
                    if (inch1N > DBDefaultValues.KotaData.length || inch2N > DBDefaultValues.KotaData.length) {
                        Toast.makeText(MainActivity.this, "Inch values vadhu nakhi didhi bhai", Toast.LENGTH_LONG).show();
                        return;
                    }
                    feet1 = DBDefaultValues.KotaData[inch1N - 1];
                    feet2 = DBDefaultValues.KotaData[inch2N - 1];
                }
                feet = feet1 * feet2;
                rtotal = feet * pieceN;
                trowcontent[0] = "" + inch1N;
                trowcontent[1] = "" + inch2N;
                trowcontent[2] = "" + pieceN;
                trowcontent[3] = "" + feet1;
                trowcontent[4] = "" + feet2;
                trowcontent[5] = String.format("%.2f", feet);
                trowcontent[6] = String.format("%.2f", rtotal);
                grandTotal += rtotal;

            } else {
                inch2.requestFocus();
                return;
            }
        } else {
            inch1.requestFocus();
            return;
        }
        addTableRow(trowcontent, "#F18519");
        TextView grandtotalTextView = (TextView) findViewById(R.id.grandTotalMenu);
        String text = "Total:" + String.valueOf(grandTotal);
        grandtotalTextView.setText(text);
        inch2.setText("");
        inch1.setText("");
        piece.setText("");
    }

    private void validatePassword() {
        final String pass = "qwaszx";
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setCancelable(false);
        //alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle("Enter PASSWORD");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
        input.requestFocus();

        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = input.getText().toString();

                        if (pass.equals(password)) {
                            Toast.makeText(getApplicationContext(),
                                    "Password Matched", Toast.LENGTH_SHORT).show();
                            isPasswordSet = true;
                            dialog.cancel();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong Password!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });

        alertDialog.show();


    }

}
