package ikakus.com.drink.history

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ikakus.com.drink.R
import kotlinx.android.synthetic.main.list_layout.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ikakus on 4/5/17.
 */
class HistoryAdapter constructor(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list : List<Long> = ArrayList<Long>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    constructor(context: Context, list : List<Long>) : this(context){
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        var v = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        return Item(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as Item).bindData(list[position])
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(time: Long) {
            val dateString = SimpleDateFormat("hh:mm").format(Date(time))
            itemView.textView.text = dateString
        }
    }
}