package com.example.icp9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);
        String summary = getIntent().getStringExtra("summary");
        TextView summaryText = (TextView) findViewById(R.id.summary);
        summaryText.setText(summary);
    }

    public void goBack(View view) {
        Intent redirect = new Intent(SummaryPage.this, MainActivity.class);
        startActivity(redirect);
    }
}