##ShushBroadcaster

This is an example app demonstrating how to send a broadcast that Shush will receive and handle
by ignoring ringer mode changes made in the following 10 seconds.

####Background

Shush is an app that pops up a dialog when the ringer mode changes to silent or vibrate, offering
to restore the volume after a specified duration.

Android does not provide a way for an app like Shush to distinguish between ringer mode changes
performed by direct user action (e.g. using the volume buttons) vs. those made programmatically
by another app.
 
Thus, it is common to find unwanted interactions between Shush and other apps; when another app
silences the ringer (based on time, location, or other trigger), the Shush dialog appears.

####Workaround

To workaround this issue, I have defined a custom intent action named
`com.androidintents.PRE_RINGER_MODE_CHANGE`, and an intent extra named 
`com.androidintents.EXTRA_SENDER`.  The extra value is optional, but its purpose is to tell 
the receiver the package name of the app sending the intent.

Shush contains a BroadcastReceiver that listens for this action, and if a ringer mode change is
made in the following 10 seconds, Shush will ignore it.


You can test Shush's behavior by sending intents via adb commands:

First, the custom action:

    adb -d shell am broadcast -a com.androidintents.PRE_RINGER_MODE_CHANGE 
    --es com.androidintents.EXTRA_SENDER com.yourapp

...followed within 10 seconds by:

    adb -d shell am broadcast -a android.media.RINGER_MODE_CHANGED 
    --ei android.media.EXTRA_RINGER_MODE 0

Note that in these examples I used "adb -d" to send to the connected device; you would use "adb -e"
to send to an emulator.

#### Implementing the integration

Just 3 lines of code does the job:

    Intent intent = new Intent("com.androidintents.PRE_RINGER_MODE_CHANGE");
    intent.putExtra("com.androidintents.EXTRA_SENDER", "com.yourapp");
    getApplicationContext().sendBroadcast(intent);
    
If you implement this integration, please let me know at team@shushapp.com, including the version
of your app that includes it.  I thank you, but more importantly, so do our mutual users :-)

####P.S.
If your app has similar undesired interactions with other apps like Shush, obviously those other apps could implement a
BroadcastReciever that handles the same intent action.
