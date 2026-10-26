package com.example.lab1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.lab1.databinding.FragmentViewTenantsBinding

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    // Safe Args: typed access to the arguments declared in nav_graph.xml
    private val args: ViewTenantsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newTenant = args.newTenant   // null when opened from Dashboard or the menus

        if (newTenant == null) {
            // Nothing was just added: hide "Just added" and the CALL button
            binding.newTenantGroup.visibility = View.GONE
        } else {
            binding.tenant = newTenant   // feeds @{tenant.summary()} in the layout

            // Implicit Intent: open the dialer with the number filled in (no permission needed)
            binding.callButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${newTenant.phone}"))
                startActivity(intent)
            }
        }

        val tenants = TenantRepository.tenants
        binding.allTenantsTextView.text = if (tenants.isEmpty()) {
            "No tenants yet. Add one from the Dashboard."
        } else {
            tenants.joinToString("\n\n") { it.summary() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}