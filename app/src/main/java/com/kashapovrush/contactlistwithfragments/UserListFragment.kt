package com.kashapovrush.contactlistwithfragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.kashapovrush.contactlistwithfragments.EditProfileFragment.Companion.SAVE_DATA
import com.kashapovrush.contactlistwithfragments.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: Adapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        setOnClickListener()
        setFragmentResultListener(SAVE_DATA) { _, bundle ->
            val user = bundle.getParcelable<User>(USER_KEY)
            listUsers.removeIf {
                it.id == user?.id
            }
            listUsers.add(user ?: User())
        }

    }

    private fun setOnClickListener() {
        adapter.onClickChangedUserInfoListener = {
            (requireActivity() as OnClickChangedUserInfo).onClickChangedUserInfo(it)
        }
    }

    private fun initRV() {
        val rvList = binding.userListRv
        adapter = Adapter()
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = adapter
        adapter.submitList(listUsers)
    }


    interface OnClickChangedUserInfo {

        fun onClickChangedUserInfo(user: User)
    }

    companion object {

        const val RESULT = "result"
        const val USER_KEY = "user_key"

        fun newInstance(): UserListFragment {

            return UserListFragment()
        }


        var listUsers = arrayListOf(
            User(1, Uri.parse("android.resource://com.kashapovrush.contactlistwithfragments/" + R.drawable.ic_alan), "Alan", "Smith", "+79991234567"),
            User(2, Uri.parse("android.resource://com.kashapovrush.contactlistwithfragments/" + R.drawable.ic_john), "John", "Anderson", "+79997654321"),
            User(3, Uri.parse("android.resource://com.kashapovrush.contactlistwithfragments/" + R.drawable.ic_thomas), "Thomas", "Davis", "+79991112233"),
            User(4, Uri.parse("android.resource://com.kashapovrush.contactlistwithfragments/" + R.drawable.ic_william), "William", "Johnson", "+79993213211")
        )
    }
}