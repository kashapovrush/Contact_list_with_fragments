package com.kashapovrush.contactlistwithfragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.kashapovrush.contactlistwithfragments.UserListFragment.Companion.RESULT
import com.kashapovrush.contactlistwithfragments.UserListFragment.Companion.USER_KEY
import com.kashapovrush.contactlistwithfragments.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    var user = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(RESULT) { _, bundle ->
            user = bundle.getParcelable(USER_KEY) ?: User()
            with(binding) {
                etFirstName.setText(user?.firstName)
                photoProfileEdit.setImageURI(user?.image)
                etLastName.setText(user?.lastName)
                etPhoneNumber.setText(user?.phoneNumber)
            }
        }

        binding.photoProfileEdit.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ), REQUEST_CODE
            )
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val edit = User(
                        id = user.id,
                        firstName = binding.etFirstName.text.toString(),
                        lastName = binding.etLastName.text.toString(),
                        image = user.image,
                        phoneNumber = binding.etPhoneNumber.text.toString()
                    )
                    (requireActivity() as OnClickListenerEditProfile).onClickSaveData(edit)
                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE || requestCode == RESULT_CODE) {
                var imageUri = data.data
                binding.photoProfileEdit.setImageURI(imageUri)
                user = user.copy(
                    image = imageUri
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    interface OnClickListenerEditProfile {

        fun onClickSaveData(user: User)
    }

    companion object {

        const val SAVE_DATA = "save_data"
        const val REQUEST_CODE = 3
        const val RESULT_CODE = 2

        fun newInstance() = EditProfileFragment()
    }


}