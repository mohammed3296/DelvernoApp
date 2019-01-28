package com.codecaique3296.delivrenoapp.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.adapters.ReportAdapter;
import com.codecaique3296.delivrenoapp.interfaces.ReportClick;
import com.codecaique3296.delivrenoapp.models.object.Report;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.models.response.ReportsJson;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.codecaique3296.delivrenoapp.utils.Sesstion;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class ReportsActivity extends AppCompatActivity implements ReportClick {
    private ArrayList<Report> reports;
    private RecyclerView mRecyclerView;
    private ReportAdapter adapter;
    private ProgressBar mLoadingIndicator;
    private Button mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_patients);
        adapter = new ReportAdapter(this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setHasFixedSize(true);
        getReports();

        mDisplayDate = findViewById(R.id.select_date);
        findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReports();
            }
        });
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReportsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("[][][]", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

             String   dateFromDatePicker = day + "/" + month + "/" + year;
                mDisplayDate.setText(dateFromDatePicker);
                getReportsByDate(dateFromDatePicker);
            }
        };
    }

    private void getReportsByDate(String asd) {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        reports = new ArrayList<>();
        Call<ReportsJson> call1 = apiService1.getReportByDate("1",
                Sesstion.getInstance(ReportsActivity.this).getTayar().getId() , asd);
        call1.enqueue(new Callback<ReportsJson>() {
            @Override
            public void onResponse(Call<ReportsJson> call, retrofit2.Response<ReportsJson> response) {
                int statusCode = response.code();
                mLoadingIndicator.setVisibility(View.GONE);
                reports = (ArrayList<Report>) response.body().getReports();

                adapter.setReporttData(reports, ReportsActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReportsJson> call, Throwable t) {
                Toast.makeText(ReportsActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void delete(View v, int position) {

        Report report = reports.get(position);
        String id = report.getId();
        deleteReport(id);
    }

    public void deleteReport(final String report_id) {
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiInterface apiService1 =
                                ApiClient.getClient().create(ApiInterface.class);
                        Call<PublicResponse> call1 = apiService1.deleteReport("1", report_id,
                                Sesstion.getInstance(ReportsActivity.this).getTayar().getId());

                        call1.enqueue(new Callback<PublicResponse>() {
                            @Override
                            public void onResponse(Call<PublicResponse> call, retrofit2.Response<PublicResponse> response) {
                                int statusCode = response.code();
                                Toast.makeText(ReportsActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
                                getReports();
                            }

                            @Override
                            public void onFailure(Call<PublicResponse> call, Throwable t) {
                                Toast.makeText(ReportsActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                                Log.e(">>>>>>>", t.getMessage());
                            }
                        });
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Report");
        builder.setPositiveButton("OK", discardButtonClickListener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                    getReports();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    public void getReports() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        reports = new ArrayList<>();
        Call<ReportsJson> call1 = apiService1.getReport("1",
                Sesstion.getInstance(ReportsActivity.this).getTayar().getId());
        call1.enqueue(new Callback<ReportsJson>() {
            @Override
            public void onResponse(Call<ReportsJson> call, retrofit2.Response<ReportsJson> response) {
                int statusCode = response.code();
                mLoadingIndicator.setVisibility(View.GONE);
                reports = (ArrayList<Report>) response.body().getReports();

                adapter.setReporttData(reports, ReportsActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReportsJson> call, Throwable t) {
                Toast.makeText(ReportsActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(this , ADashboardActivity.class));
    }
}
