package com.example.androidexam.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidexam.R
import com.example.androidexam.data.local.PersonEntity
import com.example.androidexam.databinding.ActivityPersonDetailsBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId


class PersonDetails : AppCompatActivity() {
    private lateinit var binding: ActivityPersonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val person = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("person", PersonEntity::class.java)
        } else {
            intent.getParcelableExtra<PersonEntity>("person")
        }

        if (person != null) {
            // set Person details value
            val defaultOptions = RequestOptions().error(R.drawable.ic_launcher_background)
            Glide.with(this).setDefaultRequestOptions(defaultOptions)
                .load(person.picture.large)
                .centerCrop()
                .into(binding.imageDetailIv)
            binding.nameValueTv.text = "${person.name.first} ${person.name.last}"
            binding.ageValueTv.text = "${getAge(person.dob.date)}"
            binding.bdayValueTv.text = "${getDate(person.dob.date)}"
            binding.emailValueTv.text = "${person.email}"
            binding.numberValueTv.text = "${person.cell}"
            binding.addressValueTv.text = "${person.location.street.name} ${person.location.street.number} ${person.location.city} ${person.location.country}"
        }
    }
}

// transform BirthDate to readable date
fun getDate(dateString: String): String {
    var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val newDate = spf.parse(dateString)
    spf = SimpleDateFormat("MMM dd','yyyy")
    return spf.format(newDate)
}

// get Age from BirthDate
fun getAge(dateString: String): Int {
    var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val newDate = spf.parse(dateString)
    val localDate = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val year = localDate.year
    val month = localDate.monthValue
    val day = localDate.dayOfMonth
    return Period.between(
        LocalDate.of(year, month, day),
        LocalDate.now()
    ).years
}