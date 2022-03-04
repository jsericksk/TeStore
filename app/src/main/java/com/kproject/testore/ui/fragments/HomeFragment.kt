package com.kproject.testore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kproject.testore.R
import com.kproject.testore.data.CartProduct
import com.kproject.testore.data.Product
import com.kproject.testore.data.Resource
import com.kproject.testore.databinding.FragmentHomeBinding
import com.kproject.testore.ui.adapters.ProductListAdapter
import com.kproject.testore.ui.viewmodels.MainViewModel
import com.kproject.testore.utils.ProductCategory
import com.kproject.testore.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductDetailsBottomSheetDialogFragment.ClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Busca pelos produtos apenas na primeira vez
        if (savedInstanceState == null) {
            mainViewModel.getProducts(ProductCategory.ALL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainViewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            dataState?.let { state ->
                when (state) {
                    is Resource.Loading -> {
                        setContentVisibility(State.LOADING)
                    }
                    is Resource.Success -> {
                        setContentVisibility(State.SUCCESS)
                        initializeRecyclerView(dataState.data!!)
                    }
                    is Resource.Error -> {
                        setContentVisibility(State.ERROR)
                    }
                }
            }
        }

        with (binding) {
            ibShoppingCart.setOnClickListener {
                findNavController().navigate(R.id.navigateToCartFragment)
            }

            /**
             * Usado setOnClickListener() em cada um dos Chips ao invés de
             * setOnCheckedChangeListener() no ChipGroup() porque ele estava sendo chamado várias
             * vezes com a mudança do ciclo de vida do Fragment (navegar ao CartFragment e voltar,
             * por exemplo). Resolver o problema me pareceu menos eficiente e limpo do que simplesmente
             * definir setOnClickListener() em cada um dos Chips.
             */
            cAll.setOnClickListener {
                mainViewModel.getProducts(ProductCategory.ALL)
            }
            cElectronics.setOnClickListener {
                mainViewModel.getProducts(ProductCategory.ELECTRONICS)
            }
            cJewelery.setOnClickListener {
                mainViewModel.getProducts(ProductCategory.JEWELERY)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onAddProductToCart(product: Product) {
        val cartProduct = CartProduct(
            id = product.id,
            title =  product.title,
            price = product.price,
            description = product.description,
            imageUrl = product.image,
            rate = product.rating.rate,
            reviews = product.rating.count
        )
        mainViewModel.addProduct(cartProduct)
        Utils.showSnackbar(view = binding.root, text = "Product added to cart.")
    }

    private fun setContentVisibility(state: State) {
        with (binding) {
            when (state) {
                State.LOADING -> {
                    pbLoading.visibility = View.VISIBLE
                    rvProductList.visibility = View.GONE
                    llError.visibility = View.GONE
                }
                State.SUCCESS -> {
                    pbLoading.visibility = View.GONE
                    rvProductList.visibility = View.VISIBLE
                    llError.visibility = View.GONE
                }
                State.ERROR -> {
                    pbLoading.visibility = View.GONE
                    rvProductList.visibility = View.GONE
                    llError.visibility = View.VISIBLE
                }
            }
        }
     }

    private fun initializeRecyclerView(products: List<Product>) {
        with (binding) {
            val adapter = ProductListAdapter(products, itemClickListener = { product ->
                val productDetailsBottomSheet =
                        ProductDetailsBottomSheetDialogFragment.newInstance(product)
                productDetailsBottomSheet.setClickListener(this@HomeFragment)
                productDetailsBottomSheet.show(requireActivity().supportFragmentManager, tag)
            })

            rvProductList.adapter = adapter
            rvProductList.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }
}