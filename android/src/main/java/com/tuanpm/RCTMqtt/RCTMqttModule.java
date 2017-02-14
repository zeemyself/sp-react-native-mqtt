/**
 * Created by TuanPM (tuanpm@live.com) on 1/4/16.
 */

package com.tuanpm.RCTMqtt;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Random;

public class RCTMqttModule
        extends ReactContextBaseJavaModule
{

    private static final String TAG = "RCTMqttModule";
    private final ReactApplicationContext _reactContext;
    private HashMap<Integer, RCTMqtt> clients;

    public RCTMqttModule(ReactApplicationContext reactContext)
    {
        super(reactContext);
        _reactContext = reactContext;
        clients = new HashMap<>();
    }

    public static int randInt(int min,
                              int max)
    {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public @NonNull String getName()
    {
        return "Mqtt";
    }

    @ReactMethod
    public void createClient(final ReadableMap _options,
                             Promise promise)
    {
        int clientRef = randInt(1000, 9999);
        RCTMqtt client = new RCTMqtt(clientRef, _reactContext, _options);
        client.setCallback();
        clients.put(clientRef, client);
        promise.resolve(clientRef);
        Log.d(TAG, new StringBuilder("ClientRef:").append(clientRef).toString());
    }

    @ReactMethod
    public void connect(final int clientRef)
    {
        clients.get(clientRef).connect();
    }

    @ReactMethod
    public void disconnect(final int clientRef)
    {
        clients.get(clientRef).disconnect();
    }

    @ReactMethod
    public void subscribe(final int clientRef,
                          final @NonNull String topic,
                          final int qos)
    {
        clients.get(clientRef).subscribe(topic, qos);
    }

    @ReactMethod
    public void unsubscribe(final int clientRef,
                            final @NonNull String topic)
    {
        clients.get(clientRef).unsubscribe(topic);
    }

    @ReactMethod
    public void publish(final int clientRef,
                        final String topic,
                        final String payload,
                        final int qos,
                        final boolean retain)
    {
        clients.get(clientRef).publish(topic, payload, qos, retain);
    }
}