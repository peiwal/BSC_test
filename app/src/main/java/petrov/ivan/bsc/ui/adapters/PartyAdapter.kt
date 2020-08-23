package petrov.ivan.bsc.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.person.view.*
import petrov.ivan.bsc.R
import petrov.ivan.bsc.data.Party
import petrov.ivan.bsc.databinding.ListPartyAdapterItemBinding
import petrov.ivan.bsc.utils.loadPersonImage
import java.util.*


class PartyAdapter(val clickListener: PartyListener) : RecyclerView.Adapter<PartyAdapter.ViewHolder>() {
    var items = ArrayList<Party>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListPartyAdapterItemBinding, val layoutInflater: LayoutInflater) : RecyclerView.ViewHolder(binding.root) {
        val llInvitees: LinearLayout = itemView.findViewById(R.id.llInvitees)

        fun bind(party: Party, clickListener: PartyListener) {
            binding.apply {
                this.party = party
                val btnMap = itemView.findViewById<Button>(R.id.btnMap)
                btnMap.setOnClickListener { clickListener.onClick(party) }
                executePendingBindings()
            }

            llInvitees.removeAllViews()
            party.listOfInvitees.forEach { person ->
                val view = layoutInflater.inflate(R.layout.person, null)

                view.tvName.text = person.name
                view.ivPerson.loadPersonImage(person.imgUri)

                llInvitees.addView(view)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListPartyAdapterItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, layoutInflater)
            }
        }
    }

    class PartyListener(val clickListener: (party: Party) -> Unit) {
        fun onClick(party: Party) = clickListener(party)
    }
}
