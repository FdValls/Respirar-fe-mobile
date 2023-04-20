package com.example.projectFinal.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.AdapterProperty
import com.example.projectFinal.entities.Property
import com.example.projectFinal.entities.User
import java.io.Serializable

class ListProperty : Fragment(), Serializable {

    lateinit var v: View
    lateinit var recProduct: RecyclerView
    var products: MutableList<Property> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productListAdapter: AdapterProperty

    companion object {
        fun newInstance() = ListExpensas()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_expensas, container, false)

        recProduct = v.findViewById(R.id.rec_list_product)

        return v
    }

    override fun onStart() {
        super.onStart()

        var user = User("fernandodanielvalls@gmail.com")
        var user1 = User("danielalvarez@gmail.com")
        var user2 = User("camiloclub@gmail.com")
        var user3 = User("federacionpatronal@gmail.com")


        for (i in 1..5) {
            products.add(
                Property(
                    "Propiedades Buen Camino",
                    "Suipacha 207, CABA",
                    user,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY4VQY3qSim_ZpG4saJnrw5NAUJLleOfiHpg&usqp=CAU"
                )
            )
            products.add(
                Property(
                    "Magistral",
                    "Alem 1247, Brandsen",
                    user1,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY4VQY3qSim_ZpG4saJnrw5NAUJLleOfiHpg&usqp=CAU"
                )
            )
            products.add(
                Property(
                    "Remax S.A",
                    "Lugones 4321, Adogue",
                    user2,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY4VQY3qSim_ZpG4saJnrw5NAUJLleOfiHpg&usqp=CAU"
                )
            )
            products.add(
                Property(
                    "Construmax S.R.L",
                    "Luis Guiñoz 140, Cañuelas",
                    user3,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY4VQY3qSim_ZpG4saJnrw5NAUJLleOfiHpg&usqp=CAU"
                )
            )

        }

        recProduct.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        recProduct.layoutManager = linearLayoutManager

        productListAdapter = AdapterProperty(products) { x ->
            onItemClick(x)
        }

        recProduct.adapter = productListAdapter
    }

    fun onItemClick(position: Int): Boolean {
        Toast.makeText(activity, "Detalles del producto", Toast.LENGTH_SHORT)
            .show()
        var a = ListPropertyDirections.actionListPropertyToExpensasDetailFragment2()
        v.findNavController().navigate(a)

        return true
    }
}