package com.kproject.testore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kproject.testore.R
import com.kproject.testore.data.Product
import com.kproject.testore.databinding.BottomSheetProductDetailsBinding

private const val ARG_PRODUCT_OBJ = "arg_product"

class ProductDetailsBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetProductDetailsBinding? = null
    private val binding get() = _binding!!
    private var listener: ClickListener? = null

    interface ClickListener {
        fun onAddProductToCart(product: Product)
    }

    companion object {
        fun newInstance(product: Product): ProductDetailsBottomSheetDialogFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARG_PRODUCT_OBJ, product)
            val dialog = ProductDetailsBottomSheetDialogFragment()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetProductDetailsBinding.inflate(inflater, container, false)
        val product: Product = arguments?.getSerializable(ARG_PRODUCT_OBJ) as Product
        with (binding) {
            ivImage.load(product.image)
            tvPrice.text = "$ ${product.price}"
            tvName.text = product.title
            tvDescription.text = product.description
            tvRating.text = "${product.rating.rate} (${product.rating.count} reviews)"

            btAddToCart.setOnClickListener {
                listener?.onAddProductToCart(product)
                this@ProductDetailsBottomSheetDialogFragment.dismiss()
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }
}