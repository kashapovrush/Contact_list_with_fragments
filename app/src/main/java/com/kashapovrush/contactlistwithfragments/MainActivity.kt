package com.kashapovrush.contactlistwithfragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.kashapovrush.contactlistwithfragments.EditProfileFragment.Companion.SAVE_DATA
import com.kashapovrush.contactlistwithfragments.UserListFragment.Companion.RESULT
import com.kashapovrush.contactlistwithfragments.UserListFragment.Companion.USER_KEY
import com.kashapovrush.contactlistwithfragments.databinding.ActivityMainBinding
import java.io.FileNotFoundException
import java.io.IOException


class MainActivity : AppCompatActivity(), UserListFragment.OnClickChangedUserInfo,
    EditProfileFragment.OnClickListenerEditProfile {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.container, UserListFragment.newInstance())
            }
        }
    }

    override fun onClickChangedUserInfo(user: User) {
        supportFragmentManager.setFragmentResult(RESULT, bundleOf(USER_KEY to user))
        supportFragmentManager.commit {
            replace(R.id.container, EditProfileFragment.newInstance())
            addToBackStack(null)
        }
    }

    override fun onClickSaveData(user: User) {
        supportFragmentManager.setFragmentResult(SAVE_DATA, bundleOf(USER_KEY to user))
        supportFragmentManager.popBackStack()
    }
}