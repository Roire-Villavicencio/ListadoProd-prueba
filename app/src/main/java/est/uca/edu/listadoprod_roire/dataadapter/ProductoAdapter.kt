package est.uca.edu.listadoprod_roire.dataadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import est.uca.edu.listadoprod_roire.databinding.ItemlistaBinding
import est.uca.edu.listadoprod_roire.dataclass.Producto

class ProductoAdapter(
    val listProd: List<Producto>,
    private val onclickerVista:(Producto)->Unit,
    private val onclickerBorrar: (Int) -> Unit,
    private val onclickerActualizar:(Int)->Unit
    ) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    inner class ProductoHolder(val binding: ItemlistaBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun cargar(
        producto: Producto, onClickListener:(Producto)->Unit,
        onclickerBorrar: (Int) -> Unit,
        onclickerActualizar: (Int) -> Unit
        )
        {
            with(binding) {
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = producto.precio.toString()
                itemView.setOnClickListener{onClickListener(producto)}
                binding.btnEliminar.setOnClickListener { onclickerBorrar(adapterPosition)}
                binding.btnEditar.setOnClickListener { onclickerActualizar(adapterPosition) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
        LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position],onclickerVista, onclickerBorrar, onclickerActualizar)

    }

    override fun getItemCount(): Int = listProd.size
}







