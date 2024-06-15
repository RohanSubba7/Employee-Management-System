package com.example.employeemanagement

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.employeemanagement.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //new added
    private lateinit var databaseReference: DatabaseReference
    private lateinit var tableLayout: TableLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //added recently
        tableLayout = binding.tableLayout

        databaseReference = FirebaseDatabase.getInstance().reference.child("Employee Information")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tableLayout.removeAllViews()

                for (snapshot in dataSnapshot.children) {
                    val employee = snapshot.getValue(EmployeeData::class.java)
                    if (employee != null) {
                        val row = TableRow(this@MainActivity)
                        val params = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
                        params.setMargins(0,0,0,10)
                        row.layoutParams = params

                        val employeeNumberTextView = TextView(this@MainActivity)
                        employeeNumberTextView.text = employee.employeeNumber
                        employeeNumberTextView.setPadding(20,10,20,10)
                        employeeNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        row.addView(employeeNumberTextView)

                        val firstNameTextView = TextView(this@MainActivity)
                        firstNameTextView.text = employee.firstName
                        firstNameTextView.setPadding(20,10,20,10)
                        firstNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        row.addView(firstNameTextView)

                        val lastNameTextView = TextView(this@MainActivity)
                        lastNameTextView.text = employee.lastName
                        lastNameTextView.setPadding(20,10,20,10)
                        lastNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        row.addView(lastNameTextView)

                        val initialsTextView = TextView(this@MainActivity)
                        val initials = generateInitials(employee.firstName, employee.lastName)
                        initialsTextView.text = initials
                        initialsTextView.setPadding(20, 10, 20, 10)
                        initialsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                        row.addView(initialsTextView)

                        tableLayout.addView(row)


                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to load data.", Toast.LENGTH_SHORT).show()
            }
        })


        //add button to send the user to Upload Activity
        binding.mainUpload.setOnClickListener {
            val intent = Intent (this@MainActivity, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Employee details button to send user to Display activity where user can view the employee details by employee number
        binding.employeeDetails.setOnClickListener {
            val intent = Intent (this@MainActivity, Display::class.java)
            startActivity(intent)
            finish()
        }

        //update button to send user to Update Activity where user can update the information by taking employee number as reference
        binding.mainUpdate.setOnClickListener {
            val intent = Intent (this@MainActivity, UpdateActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun addInitialsToExistingRecords() {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Employee Information")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val employee = snapshot.getValue(EmployeeData::class.java)
                    if (employee != null) {
                        val initials = generateInitials(employee.firstName, employee.lastName)
                        snapshot.ref.child("initials").setValue(initials)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to update data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun generateInitials(firstName: String?, lastName: String?): String {
        val firstInitial = firstName?.firstOrNull()?.toUpperCase() ?: ""
        val lastInitial = lastName?.firstOrNull()?.toUpperCase() ?: ""
        return "$firstInitial$lastInitial"
    }
}