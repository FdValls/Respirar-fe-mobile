package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.ContactListOrgUsersSwitchAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.utils.UserDto

class SwitchOwnerMemberFragment : Fragment() {

    lateinit var v: View
    lateinit var usersOrg : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var usersOrgListAdapter: ContactListOrgUsersSwitchAdapter
    private lateinit var orgId: String
    private lateinit var myUser: UserDto
    private lateinit var users: MutableList<UserDto>
    private var test1: MutableList<String> = mutableListOf()
    private lateinit var ids: Ids

    companion object {
        fun newInstance() = SwitchOwnerMemberFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: SwitchOwnerMemberFragmentArgs by navArgs()
        orgId = args.idOrg.toString()

        v = inflater.inflate(R.layout.item_switch_org_user, container, false)

        usersOrg = v.findViewById(R.id.id_orgSwitch_contactos)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        usersOrg.addItemDecoration(itemDecoration)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
    }

    override fun onStart() {
        super.onStart()

        users = GlobalVariables.getInstance().listUsers

        usersOrg.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        usersOrg.layoutManager = linearLayoutManager

        if (!::usersOrgListAdapter.isInitialized) {
            usersOrgListAdapter = ContactListOrgUsersSwitchAdapter(users) { x ->
                onItemClick(x)
            }
            usersOrg.adapter = usersOrgListAdapter
        } else {
            usersOrgListAdapter.notifyDataSetChanged()
        }
    }

    fun onItemClick ( position : Int ) : Ids{
        myUser = users[position]
        ids = Ids(orgId, myUser.id, position, test1)
        return ids
    }
}

data class Ids(val id_org: String, val id_user: String, val position: Int, val test1: MutableList<String>)
