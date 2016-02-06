package net.iyouqu.bruceretrofit.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.iyouqu.bruceretrofit.Service.SampleIntentService;


public class AlarmManagerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, SampleIntentService.class);
        context.startService(intentService);
    }
}
