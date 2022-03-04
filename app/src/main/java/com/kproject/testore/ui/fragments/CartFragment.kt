package com.kproject.testore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.kproject.testore.data.Product
import com.kproject.testore.data.Rating
import com.kproject.testore.databinding.FragmentCartBinding
import com.kproject.testore.ui.adapters.CartAdapter
import com.kproject.testore.ui.adapters.SwipeHelperCallback
import com.kproject.testore.ui.viewmodels.MainViewModel
import com.kproject.testore.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.tbToolbar.setupWithNavController(navController, appBarConfiguration)

        initializeReciclerView()
        mainViewModel.allCartProduct.observe(viewLifecycleOwner) { cartProducts ->
            cartProducts?.let { products ->
                adapter.setNewList(products)

                if (products.isEmpty()) {
                    binding.rvProductList.visibility = View.GONE
                    binding.llEmptyList.visibility = View.VISIBLE
                } else {
                    binding.rvProductList.visibility = View.VISIBLE
                    binding.llEmptyList.visibility = View.GONE
                }
            }
        }

        binding.btCheckout.setOnClickListener {
            mainViewModel.deleteAllProducts()
        }

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initializeReciclerView() {
        adapter = CartAdapter(onSwiped = { position, product ->
            // Remove o produto do banco de dados
            mainViewModel.deleteProduct(product)
            Utils.showSnackbar(
                view = binding.btCheckout,
                anchorView = binding.btCheckout,
                text = "Product removed",
                actionText = "Undo",
                duration = Snackbar.LENGTH_LONG,
                onAction = {
                    adapter.restoreDeleteProduct(position, product)
                    // Adiciona novamente caso o usuário mude de ideia
                    mainViewModel.addProduct(product)
                }
            )
        })
        binding.rvProductList.adapter = adapter

        val callback: ItemTouchHelper.Callback = SwipeHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper?.attachToRecyclerView(binding.rvProductList)
    }
}