package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Booking
import com.example.myapplication.viewmodel.ArticleListViewModel
import com.example.myapplication.viewmodel.TransactionListViewModel


class TransactionListFragment : Fragment() {
   private lateinit var viewModel: TransactionListViewModel
   private val transactionAdapter = TransactionListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TransactionListViewModel::class.java]
        viewModel.refresh()
        val recViewTransaction = view.findViewById<RecyclerView>(R.id.recViewHistory)
        recViewTransaction.layoutManager = LinearLayoutManager(context)
        recViewTransaction.adapter = transactionAdapter
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.transactionLD.observe(viewLifecycleOwner){
            transactionAdapter.UpdateList(it as ArrayList<Booking>)
        }
    }
}