package com.example.myapplication.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ProfileViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userIdPreferences: SharedPreferences
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Change text in toolbar
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "My Profile"

        sharedPreferences = requireContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        userIdPreferences = requireContext().getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userId = userIdPreferences.getInt("userId", 0)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.fetch(userId)
        observeViewModel()


        val txtIdUser = view.findViewById<TextView>(R.id.txtIdUserLogin)
        txtIdUser.text = "ID User " + userId.toString()


        //Logout
        val btnLogout = view.findViewById<Button>(R.id.btnLogOut)
        btnLogout.setOnClickListener{
            // Call the manuallyLogout() function to log out the user
            userLogout()
            // Navigate to the login fragment
            findNavController().navigate(R.id.loginFragment)
        }

        //Edit profile
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)
        btnEditProfile.setOnClickListener{
            val action = MyProfileFragmentDirections.actionProfileDetail()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        userIdPreferences = requireContext().getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userId = userIdPreferences.getInt("userId", 0)

        viewModel.userLiveData.observe(viewLifecycleOwner){
            val txtIdUser = view?.findViewById<TextView>(R.id.txtIdUserLogin)
            val txtFullnameProfile = view?.findViewById<TextView>(R.id.txtFullnameProfile)
            val txtUsernameProfile = view?.findViewById<TextView>(R.id.txtUsernameProfile)
            val txtPhoneProfile = view?.findViewById<TextView>(R.id.txtPhoneProfile)
            val txtGenderProfile = view?.findViewById<TextView>(R.id.txtGenderProfile)

            txtIdUser?.text = "User ID Anda: " + userId.toString()
            txtFullnameProfile?.text = it.fullname
            txtUsernameProfile?.text = it.username
            txtPhoneProfile?.text = it.phone

            if(it.gender == "1"){
                txtGenderProfile?.setText("Male")
            }
            else{
                txtGenderProfile?.setText("Female")
            }

        }
    }

    fun userLogout() {
        // Manually set the login state to false
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

        userIdPreferences.edit().putInt("userId", 0).apply()

    }

}