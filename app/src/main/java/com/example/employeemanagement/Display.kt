package com.example.employeemanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.employeemanagement.databinding.ActivityDisplayBinding
import com.example.employeemanagement.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Display : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{
            val searchEmployeeNumber: String = binding.searchEmployeeNumber.text.toString()

            //check if the user have inputed the employee number or not
            if (searchEmployeeNumber.isNotEmpty()) {
                readData(searchEmployeeNumber)
            } else{
                Toast.makeText(this, "Please enter the employee number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(employeeNumber: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee Information") //Database name is Employee Information
        //checks if the data is successfully retrieves
        databaseReference.child(employeeNumber).get().addOnSuccessListener {
            //it checks if employee number exists
            if (it.exists()){
                val firstName = it.child("firstName").value
                val lastName = it.child("lastName").value
                val email = it.child("email").value
                val phoneNumber = it.child("phoneNumber").value
                val residentialAddress = it.child("residentialAddress").value
                val designation = it.child("designation").value
                val salary = it.child("salary").value
                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                binding.searchEmployeeNumber.text.clear()
                //convert all the data retrieved to string
                binding.readFirstName.text = firstName.toString()
                binding.readLastName.text = lastName.toString()
                binding.readEmail.text = email.toString()
                binding.readPhoneNumber.text = phoneNumber.toString()
                binding.readResidentialAddress.text = residentialAddress.toString()
                binding.readDesignation.text = designation.toString()
                binding.readSalary.text = salary.toString()
                //if the employee number is not found
            } else {
                Toast.makeText(this,"Employee number does not exists", Toast.LENGTH_SHORT).show()

            }
            //if data is not retrieved failure listener will be triggered
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}