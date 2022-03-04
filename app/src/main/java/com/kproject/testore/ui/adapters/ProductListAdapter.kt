package com.kproject.testore.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kproject.testore.R
import com.kproject.testore.data.Product
import com.kproject.testore.databinding.RecyclerviewItemProductBinding

class ProductListAdapter(
    private val productList: List<Product>,
    private val itemClickListener: (product: Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RecyclerviewItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(
        private val binding: RecyclerviewItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val product = productList[position]
            with (binding) {
                ivImage.load(product.image)
                tvPrice.text = "$ ${product.price}"
                tvName.text = product.title
                tvRating.text = product.rating.rate.toString()
            }

            itemView.setOnClickListener {
                itemClickListener.invoke(product)
            }
        }
    }
}