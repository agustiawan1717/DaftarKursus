package com.agustiawan.daftarkursus.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.agustiawan.daftarkursus.R
import com.agustiawan.daftarkursus.model.User
import com.agustiawan.daftarkursus.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembang tata letak untuk fragmen ini
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val Nama = addNama_et.text.toString()
        val Prodi = addProdi_et.text.toString()
        val Nim = addNim_et.text

        if(inputCheck(Nama, Prodi, Nim)){
            // Buat Objek Pengguna
            val user = User(
                0,
                Nama,
                Prodi,
                Integer.parseInt(Nim.toString())
            )
            // Tambahkan Data ke Basis Data
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigasi Kembali
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(Nama: String, Prodi: String, Nim: Editable): Boolean{
        return !(TextUtils.isEmpty(Nama) && TextUtils.isEmpty(Prodi) && Nim.isEmpty())
    }

}