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
import com.example.projectFinal.adapter.AdapterExpensas
import com.example.projectFinal.entities.Expensas
import java.io.Serializable

class ListExpensas : Fragment(), Serializable {

    lateinit var v: View
    lateinit var recProduct: RecyclerView
    var products: MutableList<Expensas> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productListAdapter: AdapterExpensas

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


        for (i in 1..5) {
            products.add(
                Expensas(
                    "Enero",
                    7400.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Febrero",
                    7400.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Marzo",
                    7400.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Abril",
                    7400.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Mayo",
                    7400.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Junio",
                    7400.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Julio",
                    9100.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Agosto",
                    9100.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Septiembre",
                    9100.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Octubre",
                    9100.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Noviembre",
                    9100.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
            products.add(
                Expensas(
                    "Diciembre",
                    9100.00,
                    "https://play-lh.googleusercontent.com/9XKD5S7rwQ6FiPXSyp9SzLXfIue88ntf9sJ9K250IuHTL7pmn2-ZB0sngAX4A2Bw4w=s256"
                )
            )
        }

        recProduct.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        recProduct.layoutManager = linearLayoutManager

        productListAdapter = AdapterExpensas(products) { x ->
            onItemClick(x)
        }

        recProduct.adapter = productListAdapter
    }

    fun onItemClick(position: Int): Boolean {
        Toast.makeText(activity, "Detalles del producto", Toast.LENGTH_SHORT)
            .show()
        var a = ListExpensasDirections.actionListExpensasToExpensasDetailFragment()
        v.findNavController().navigate(a)

        return true
    }

}