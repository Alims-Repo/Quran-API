package com.nelu.license

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class LicenseCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_check)

        val purchaseLink = findViewById<TextView>(R.id.purchase_link)

        purchaseLink.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    intent.getStringExtra("URL")
                )
            )
            startActivity(browserIntent)
        }

        findViewById<ImageView>(R.id.email_button).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("nelucode@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Support Request (${packageName})")
            }

            if (emailIntent.resolveActivity(packageManager) != null)
                startActivity(emailIntent)
        }

        findViewById<ImageView>(R.id.whatsapp_button).setOnClickListener {
            val phoneNumber = "+880xxxxxxxxx"
            val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://wa.me/$phoneNumber")
            }
            startActivity(whatsappIntent)
        }

        findViewById<ImageView>(R.id.facebook_button).setOnClickListener {
            val facebookPageUrl = "https://www.facebook.com/yourpage"
            val facebookIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl))
            startActivity(facebookIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finishAffinity()
        exitProcess(0)
    }
}