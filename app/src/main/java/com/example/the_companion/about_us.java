package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.CalendarContract;
=======

import android.annotation.SuppressLint;
import android.os.Bundle;
>>>>>>> a03c22cd8a96bddbf1fa1527a5354e0c846d4f33
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

<<<<<<< HEAD
import android.os.Bundle;

=======
>>>>>>> a03c22cd8a96bddbf1fa1527a5354e0c846d4f33
public class about_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_us);


        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(" An app to combat daily recurrent thoughts and activities")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("CONNECT WITH US!")
                .addEmail("rashidulikram406@gmail.com")
                .addPlayStore("com.example.yourprojectname")
                .addGroup(" Follow us on social media")
                .addFacebook("https://www.facebook.com/The-Companion-102081092054983")
                .addInstagram("jarves.usaram")
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }
<<<<<<< HEAD
=======

>>>>>>> a03c22cd8a96bddbf1fa1527a5354e0c846d4f33
    private Element createCopyright()
    {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by The Companion", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        // copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(about_us.this,copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> a03c22cd8a96bddbf1fa1527a5354e0c846d4f33
