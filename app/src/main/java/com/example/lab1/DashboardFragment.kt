package com.example.lab1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab1.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "DashboardFragment: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TENANT_LIFECYCLE", "DashboardFragment: onCreateView")
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "DashboardFragment: onViewCreated")

        // Runs every time the view is rebuilt (e.g. after Back), so the metrics refresh
        binding.tenantCountTextView.text = "Tenants\n${TenantRepository.tenants.size}"
        binding.totalRentTextView.text = "Total rent\nKSh %,.0f".format(TenantRepository.totalRent())

        // Safe Args generates DashboardFragmentDirections after a build
        binding.addTenantButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddTenantFragment()
            )
        }
        binding.viewTenantsButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToViewTenantsFragment()
            )
        }
    }

    override fun onStart() { super.onStart(); Log.d("TENANT_LIFECYCLE", "DashboardFragment: onStart") }
    override fun onResume() { super.onResume(); Log.d("TENANT_LIFECYCLE", "DashboardFragment: onResume") }
    override fun onPause() { super.onPause(); Log.d("TENANT_LIFECYCLE", "DashboardFragment: onPause") }
    override fun onStop() { super.onStop(); Log.d("TENANT_LIFECYCLE", "DashboardFragment: onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TENANT_LIFECYCLE", "DashboardFragment: onDestroyView")
        _binding = null   // avoid leaking the old view
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "DashboardFragment: onDestroy")
    }
}