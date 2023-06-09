package com.example.projectFinal.activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.databinding.FragmentHomeBinding
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }

      lifecycleScope.launch {
          RequestListUsersWithinAnOrganization.sendRequest("7483d89f-3c28-4f1f-aabf-42d7c36f4735")
      }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}