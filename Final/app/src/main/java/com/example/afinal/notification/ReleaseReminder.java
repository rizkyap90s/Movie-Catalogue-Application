package com.example.afinal.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.afinal.BuildConfig;
import com.example.afinal.R;
import com.example.afinal.api.Client;
import com.example.afinal.api.Service;
import com.example.afinal.detail.DetailMovie;
import com.example.afinal.model.Movie;
import com.example.afinal.respone.MovieRespone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReminder extends BroadcastReceiver {
    public final static int NOTIFICATION_ID = 1;
    private static final String RELEASE_MESSAGE = "release_message";
    public static final String RELEASE_TYPE = "release_type";
    public List<Movie> movieList = new ArrayList<>();

    public ReleaseReminder() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            Client client = new Client();
            Service service = client.getClient().create(Service.class);
            Call<MovieRespone> call = service.getUpComingMovie(BuildConfig.API_KEY);
            call.enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    movieList = response.body().getMovieResult();
                    for(Movie movie : movieList){
                            String title =movie.getMovie_title();
                            String message = movie.getMovie_overview();
                            String poster = movie.getMovie_poster();
                            Double rate = movie.getMovie_rating();
                            String lang = movie.getMovie_language();
                            String mid = movie.getMovie_id();
                            int notifId  = 1;
                            sendNotification(context, title, message, poster, rate, lang, mid, notifId);
                    }
                }
                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {
                    Toast.makeText((context), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendNotification(Context context, String title, String description, String poster, Double rate, String language, String mid, int id) {
        String CHANNEL_ID = "ID";
        String CHANNEL_NAME = "NAME";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Movie movie = new Movie();
        movie.setMovie_id(mid);
        movie.setMovie_title(title);
        movie.setMovie_overview(description);
        movie.setMovie_language(language);
        movie.setMovie_poster(poster);
        movie.setMovie_rating(rate);
        Intent intent = new Intent(context, DetailMovie.class);
        intent.putExtra(DetailMovie.KEY_MOVIE, movie);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(title)
                .setContentText(description)
                .setColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(id, notification);
        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseReminder.class);
        intent.putExtra(RELEASE_MESSAGE, message);
        intent.putExtra(RELEASE_TYPE, type);
        String timeArray[] = time.split(":");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt((timeArray[0])));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, R.string.release_reminder_active, Toast.LENGTH_SHORT).show();

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.release_reminder_noactive, Toast.LENGTH_SHORT).show();
    }
}
