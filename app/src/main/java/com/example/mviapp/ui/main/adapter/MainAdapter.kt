package com.example.mviapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mviapp.R
import com.example.mviapp.data.model.UniversityDataModel

class MainAdapter(private var universityData: List<UniversityDataModel>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryTV: TextView
        val uniNameTV: TextView
        val stateProvinceTV: TextView

        init {
            countryTV = view.findViewById(R.id.countryTV)
            uniNameTV = view.findViewById(R.id.uniNameTV)
            stateProvinceTV = view.findViewById(R.id.stateProvinceTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.countryTV.text = universityData[position].country
        holder.stateProvinceTV.text = universityData[position].state_province
        holder.uniNameTV.text = universityData[position].name
    }

    override fun getItemCount(): Int = universityData.size

    fun addData(list: List<UniversityDataModel>) {
        universityData = list
    }
}