package com.example.employeemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeemanagement.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.updateButton.setOnClickListener{
            val referenceEmployeeNumber = binding.referenceEmployeeNumber.text.toString()
            val updateFirstName = binding.updateFirstName.text.toString()
            val updateLastName = binding.updateLastName.text.toString()
            val updateEmail = binding.updateEmail.text.toString()
            val updatePhoneNumber = binding.updatePhoneNumber.text.toString()
            val updateResidentialAddress = binding.updateResidentialAddress.text.toString()
            val updateDesignation = binding.updateDesignation.text.toString()
            val updateSalary = binding.updateSalary.text.toString()

            updateData(referenceEmployeeNumber, updateFirstName, updateLastName, updateEmail, updatePhoneNumber, updateResidentialAddress, updateDesignation, updateSalary)
        }

        binding.deleteButton.setOnClickListener{
            val employeeNumber = binding.referenceEmployeeNumber.text.toString()
            if (employeeNumber.isNotEmpty()){
                deleteData(employeeNumber)
            } else {
                Toast.makeText(this, "Please enter Employee Number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener{
            val intent = Intent (this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        }

    private fun updateData(employeeNumber: String, firstName: String, lastName: String, email: String, phoneNumber: String, residentialAddress: String, designation: String, salary: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee Information")
        val employeeData = mapOf("firstName" to firstName, "lastname" to lastName, "email" to email, "phoneNumber" to phoneNumber, "residentialAddress" to residentialAddress, "designation" to designation, "salary" to salary)
        databaseReference.child(employeeNumber).updateChildren(employeeData).addOnSuccessListener {
            binding.referenceEmployeeNumber.text.clear()
            binding.updateFirstName.text.clear()
            binding.updateLastName.text.clear()
            binding.updateEmail.text.clear()
            binding.updatePhoneNumber.text.clear()
            binding.updateResidentialAddress.text.clear()
            binding.updateDesignation.text.clear()
            binding.updateSalary.text.clear()
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{
            Toast.makeText(this, "Unable to update", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteData(employeeNumber: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee Information")
        databaseReference.child(employeeNumber).removeValue().addOnSuccessListener {
            binding.referenceEmployeeNumber.text.clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Unable to Delete", Toast.LENGTH_SHORT).show()
        }

    }
}