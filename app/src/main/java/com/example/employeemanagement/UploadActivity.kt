package com.example.employeemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.employeemanagement.databinding.ActivityMainBinding
import com.example.employeemanagement.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {

    //setting up binding
    private lateinit var binding: ActivityUploadBinding
    //declare database reference. It is to refer the database
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener{
            val firstName = binding.FirstName.text.toString()
            val lastName = binding.LastName.text.toString()
            val email = binding.Email.text.toString()
            val phoneNumber = binding.PhoneNumber.text.toString()
            val residentialAddress = binding.ResidentialAddress.text.toString()
            val designation = binding.Designation.text.toString()
            val salary = binding.Salary.text.toString()
            val employeenumber = binding.EmployeeNumber.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Employee Information")

            val employeeData = EmployeeData(firstName, lastName, email, phoneNumber, residentialAddress, designation, salary)
            //inside employee number all the other data will be stored since employee number is always unique
            databaseReference.child(employeenumber).setValue(employeeData).addOnSuccessListener {
                binding.FirstName.text.clear()
                binding.LastName.text.clear()
                binding.Email.text.clear()
                binding.PhoneNumber.text.clear()
                binding.ResidentialAddress.text.clear()
                binding.Designation.text.clear()
                binding.Salary.text.clear()
                binding.EmployeeNumber.text.clear()

                Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

    }
    }
}