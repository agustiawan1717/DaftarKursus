package com.agustiawan.daftarkursus.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import  com.agustiawan.daftarkursus.R
import  com.agustiawan.daftarkursus.model.User
import  com.agustiawan.daftarkursus.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembang tata letak untuk fragmen ini
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateNama_et.setText(args.currentUser.Nama)
        view.updateProdi_et.setText(args.currentUser.Prodi)
        view.updateNim_et.setText(args.currentUser.Nim.toString())

        view.update_btn.setOnClickListener {
            updateItem()
        }

        // Tambahkan menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val Nama = updateNama_et.text.toString()
        val Prodi = updateProdi_et.text.toString()
        val Nim = Integer.parseInt(updateNim_et.text.toString())

        if (inputCheck(Nama, Prodi, updateNim_et.text)) {
            // Buat Objek Pengguna
            val updatedUser = User(args.currentUser.id, Nama, Prodi, Nim )
            // Perbarui Pengguna Saat Ini
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigasi Kembali
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(Nama: String, Prodi: String, Nim: Editable): Boolean {
        return !(TextUtils.isEmpty(Nama) && TextUtils.isEmpty(Prodi) && Nim.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.Nama}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.Nama}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.Nama}?")
        builder.create().show()
    }
}