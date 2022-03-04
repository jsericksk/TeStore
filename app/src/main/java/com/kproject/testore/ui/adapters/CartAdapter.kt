package com.kproject.testore.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kproject.testore.data.CartProduct
import com.kproject.testore.databinding.RecyclerviewItemCartBinding

class CartAdapter(
    val onSwiped: (position: Int, product: CartProduct) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(), ItemTouchHelperAdapter {
    private var cartProducts: MutableList<CartProduct> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = RecyclerviewItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartProducts.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(cartProducts: List<CartProduct>){
        this.cartProducts = cartProducts.toMutableList()
        notifyDataSetChanged()
    }

    fun restoreDeleteProduct(position: Int, product: CartProduct) {
        this.cartProducts.add(position, product)
        notifyItemInserted(position)
    }

    inner class CartViewHolder(
        private val binding: RecyclerviewItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val cartProduct = cartProducts[position]
            with (binding) {
                ivImage.load(cartProduct.imageUrl)
                tvPrice.text = "$ ${cartProduct.price}"
                tvName.text = cartProduct.title
                tvRating.text = "${cartProduct.rate} (${cartProduct.reviews} reviews)"
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    override fun onItemDismiss(position: Int) {
        val product = cartProducts[position]
        cartProducts.remove(product)
        notifyItemRemoved(position)
        onSwiped(position, product)
    }
}