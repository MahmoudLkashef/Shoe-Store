package com.example.shoestore.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shoestore.R
import com.example.shoestore.databinding.FragmentShoeListBinding
import com.example.shoestore.model.Shoe
import org.w3c.dom.Text


class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private val viewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoe_list, container, false
        )

        addMenu()

        binding.fab.setOnClickListener(View.OnClickListener {
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
        })

        viewModel.shoeList.observe(viewLifecycleOwner, Observer { shoeList ->
            Log.i("testt", shoeList.size.toString())
            addView(shoeList)

        })

        return binding.root
    }

    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.option_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.logout -> {
                            viewModel.removeList()
                            findNavController()
                                .navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }


    private fun addView(shoeList: List<Shoe>) {

        for (shoe in shoeList) {
            var inflater = LayoutInflater.from(context).inflate(R.layout.shoe_list_item, null) as View
            var company: TextView = inflater.findViewById(R.id.tv_company)
            var shoeName: TextView = inflater.findViewById(R.id.tv_shoe_name)
            var shoeSize: TextView = inflater.findViewById(R.id.tv_size)
            var shoeDescription: TextView = inflater.findViewById(R.id.tv_description)
            company.text = shoe.company
            shoeName.text = shoe.shoeName
            shoeSize.text = shoe.shoeSize
            shoeDescription.text = shoe.description

            binding.LinearLayout.addView(inflater)
        }
    }

}