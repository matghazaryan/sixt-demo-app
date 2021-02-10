package com.sixt.car

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sixt.car.databinding.ActivityCarListBinding
import com.sixt.carDetails.CarDetailsFragment
import com.sixt.utils.launchWhenStarted
import com.sixt.utils.recycler_view.adapter.SimpleRecyclerViewAdapter
import com.sixt.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CarActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityCarListBinding::inflate)
    private val viewModel by viewModels<CarViewModel>()

    private val recyclerViewAdapter = SimpleRecyclerViewAdapter(R.layout.item_car) {
        CarViewHolder(this, it, viewModel::onItemClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        with(viewBinding.recyclerView) {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@CarActivity)
        }
    }

    private fun observeViewModel(){
        launchWhenStarted {
            viewModel.command.collect {
                when (it) {
                    CarViewModel.Command.GeneralError -> showGeneralErrorToast()
                    is CarViewModel.Command.CarDetail -> {
                        navigateToCarDetail(it.latitude, it.longitude,it.name)
                    }
                }
            }
        }

        launchWhenStarted {
            viewModel.data.collect{
                updateRecyclerView(it)
            }
        }
    }

    private fun updateRecyclerView(data: List<ItemData>) {
        recyclerViewAdapter.setData(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    private fun showGeneralErrorToast() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCarDetail(latitude: Double, longitude: Double, name: String) {
        // Using fragmentManager directly only for test purpose
        supportFragmentManager.beginTransaction()
            .add(
                viewBinding.fragmentContainer.id,
                CarDetailsFragment.createInstance(latitude,longitude,name)
            )
            .addToBackStack(null)
            .commit()
    }
}