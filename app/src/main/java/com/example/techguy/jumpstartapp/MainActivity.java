package com.example.techguy.jumpstartapp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button bjumpstart;
    TextToSpeech toSpeech;
    int result;
    FloatingActionButton speakproblem;
    static String[] texts = {"Hello Am JumpStart", "Car Expert System", "How May I Help U Fix Your Vehicle"};
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speakproblem = (FloatingActionButton) findViewById(R.id.speakproblem);
        bjumpstart = (Button) findViewById(R.id.button);
        toSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = toSpeech.setLanguage(Locale.UK);
                    toSpeech.speak(texts[0] + texts[1] + texts[2], TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        speakproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                microphone();
            }
        });
        bjumpstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpstart();
            }
        });
    }

    public void microphone() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the problem Of Your Vehicle");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Sorry Failed To Pick Vehicle Problem", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 100) && (data != null) && (resultCode == RESULT_OK)) {
            ArrayList<String> spokendata = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //toSpeech.speak(spokendata.get(0), TextToSpeech.QUEUE_FLUSH, null);

            if (spokendata.get(0).equalsIgnoreCase("Hello")) {
                toSpeech.speak("Tech Guy", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(getApplicationContext(), spokendata.get(0), Toast.LENGTH_LONG).show();
            } else if (spokendata.get(0).equalsIgnoreCase("Have a problem")) {
                toSpeech.speak("Yes am listening how may I help u", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(getApplicationContext(), spokendata.get(0), Toast.LENGTH_LONG).show();
            } else if (spokendata.get(0).equalsIgnoreCase("Hello JumpStart")) {
                toSpeech.speak("Hello...How May I Help You Fix Your Vehicle", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(getApplicationContext(), spokendata.get(0), Toast.LENGTH_LONG).show();
            } else if (spokendata.get(0).equalsIgnoreCase("Engine Failure")) {
                toSpeech.speak("Ok follow the procedures", TextToSpeech.QUEUE_FLUSH, null);
                procedures();
            } else {
                toSpeech.speak("Sorry...Never heard ur problem", TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(getApplicationContext(), "Sorry...Never heard ur problem", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void jumpstart() {
        editText = (EditText) findViewById(R.id.editText3);
        String text = editText.getText().toString();
        if (text.equalsIgnoreCase("Hello")) {
            toSpeech.speak("Tech Guy", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        } else if (text.equalsIgnoreCase("Have a problem")) {
            toSpeech.speak("Yes am listening how may I help u", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }else if (text.equalsIgnoreCase("Hello JumpStart")) {
            toSpeech.speak("Hello...How May I Help You Fix Your Vehicle", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        } else if (text.equalsIgnoreCase("Engine Failure")) {
            toSpeech.speak("Ok follow the procedures", TextToSpeech.QUEUE_FLUSH, null);
            procedures();
        } else if (text.equalsIgnoreCase("")) {
            toSpeech.speak("Sorry...Please Write Your Vehicle Problem", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), "Please Write Your Vehicle Problem", Toast.LENGTH_LONG).show();
        } else {
            toSpeech.speak("Sorry...We never got a solution to that", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(getApplicationContext(), "We never got a solution to that", Toast.LENGTH_LONG).show();
        }
    }

    public void procedures() {
        AlertDialog.Builder bud = new AlertDialog.Builder(MainActivity.this);
        bud.setMessage("How to troubleshoot Car Engine !!!" + "\n" + "\n" +
                "Check on your car battery charge voltage" + "\n" +
                "Check on your starter" + "\n" +
                "Check the fuel gauge" + "\n" +
                "Check for water temperatures")
                .setCancelable(false)
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder bud2 = new AlertDialog.Builder(MainActivity.this);
                        toSpeech.speak("Am I Helpful", TextToSpeech.QUEUE_FLUSH, null);
                        bud2.setMessage("Is jumpstart helpful")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        toSpeech.speak("Thank you For Using Jumpstart", TextToSpeech.QUEUE_FLUSH, null);
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        toSpeech.speak("Ok...I Will locate for you the nearest garage from your location for assistance", TextToSpeech.QUEUE_FLUSH, null);
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert = bud2.create();
                        alert.setTitle("JumpStart");
                        alert.show();
                    }
                });
        AlertDialog alert = bud.create();
        alert.setTitle("JumpStart");
        alert.show();
    }

    @Override
    protected void onDestroy() {
        if (toSpeech != null) {
            toSpeech.stop();
            toSpeech.shutdown();
        }
        super.onDestroy();
    }
}
