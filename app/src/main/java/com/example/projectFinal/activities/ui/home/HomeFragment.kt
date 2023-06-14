package com.example.projectFinal.activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.OrganizationFragmentDirections
import com.example.projectFinal.adapter.OrgListAdapter
import com.example.projectFinal.data.GlobalVariables
//import com.example.projectFinal.databinding.FragmentHomeBinding
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.RequestListAllOrganization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeFragment : Fragment() {

    lateinit var v: View
//        private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    private lateinit var btnCreate: Button
    private lateinit var btnViews: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        v = inflater.inflate(R.layout.fragment_home, container, false)

        btnCreate = v.findViewById(R.id.buttonAgregar)
        btnViews = v.findViewById(R.id.buttonVerTodas)

        // Obtener referencia del RecyclerView
        recyclerView = v.findViewById(R.id.recyclerView)

        // Configurar el RecyclerView con un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Crear y establecer el adaptador del RecyclerView
        adapter = ItemAdapter(getItems())
        recyclerView.adapter = adapter

        return v
    }

    override fun onStart() {btnViews
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()
        }

        btnCreate.setOnClickListener {
            val action2 = HomeFragmentDirections.actionNavHomeToCreateOrgFragment()
            v.findNavController().navigate(action2)
        }

        btnViews.setOnClickListener {
            val action3 = HomeFragmentDirections.actionNavHomeToNavOrganization()
            v.findNavController().navigate(action3)
        }
    }

    private fun getItems(): List<Item> {

        val items = mutableListOf<Item>()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()
        }

        var orgs = GlobalVariables.getInstance().listOrganizationsForUpdate
        println("PRUEBA DE ORGANIZACIONES" + orgs)

        orgs.forEach { elemento ->
            items.add(Item(R.drawable.group_icon, "Nombre: " + elemento.name.toString(), "Descripcion: " + elemento.description.toString()))
        }

        return items
    }

    data class Item(val imageResId: Int, val title: String, val description: String)

    inner class ItemAdapter(private val items: List<Item>) :
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_org_home, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = items[position]

            // Asignar los valores a los elementos de la interfaz de usuario en el ViewHolder
            holder.imageView.setImageResource(item.imageResId)
            holder.titleTextView.text = item.title
            holder.descriptionTextView.text = item.description
        }

        override fun getItemCount(): Int {
            return items.size
        }

        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}