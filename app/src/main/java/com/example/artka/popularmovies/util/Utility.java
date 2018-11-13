package com.example.artka.popularmovies.util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.example.artka.popularmovies.MovieJobService;

public final class Utility {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, MovieJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPeriodic(1000 * 60 * 120);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    private Utility() {
    }

}
