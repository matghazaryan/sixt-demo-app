package com.sixt.car

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sixt.car.databinding.ItemCarBinding
import com.sixt.utils.recycler_view.adapter.SimpleViewHolder


class CarViewHolder(private val context: Context, view: View, private val handler: (ItemData) -> Unit) :
    SimpleViewHolder<ItemData>(view) {

    private val binding = ItemCarBinding.bind(view)

    override fun bind(data: ItemData) {
        binding.root.setOnClickListener {
            handler(data)
        }
        binding.modelName.text = "${data.make} ${data.modelName}"
        binding.otherInfo.text = context.getString(R.string.additions,data.licensePlate,data.fuelLevel.toString(),data.fuelType)

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_directions_car)
            .error(R.drawable.ic_directions_car_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(itemView)
            .load(data.carImageUrl)
            .apply(options)
            .into(binding.carImage)
    }
}