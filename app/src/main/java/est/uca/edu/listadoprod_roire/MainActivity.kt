package est.uca.edu.listadoprod_roire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import est.uca.edu.listadoprod_roire.dataadapter.ProductoAdapter
import est.uca.edu.listadoprod_roire.databinding.ActivityMainBinding
import est.uca.edu.listadoprod_roire.dataclass.Producto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listaProd =ArrayList<Producto>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun limpiar(){
        with(binding){
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd(){
        with(binding){
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            }catch (ex: Exception){
                Toast.makeText(
                    this@MainActivity, "Error: ${ex.toString()} ",
                    Toast.LENGTH_LONG
                ).show()
            }

            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(
                listaProd,
                { producto -> seleccionarProducto(producto) },
                { position -> borrarProducto(position) },
                { position -> actualizarProducto(position) }
            )

        }
        limpiar()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    private fun actualizarProducto(position: Int) {
        with(binding) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Usted ha editado correctamente este producto")
                .setCancelable(false)

            val id: Int = etID.text.toString().toInt()
            val nombre: String = etNombreProd.text.toString()
            val precio: Double = etPrecio.text.toString().toDouble()
            val prod = Producto(id, nombre, precio)
            listaProd.set(position, prod)
            rcvLista.adapter?.notifyItemRemoved(position)
        }
    }

    private fun seleccionarProducto(producto: Producto) {
        with(binding) {

            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun borrarProducto(position: Int) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("¿Usted esta seguro de llevar a cabo esta accion? Le recordamos que esto no se puede deshacer.")
            .setCancelable(false)
            .setPositiveButton("Si, si quiero") { dialog, id ->
                with(binding) {
                    listaProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                    limpiar()
                }

            }.setNegativeButton("No, me rehuso a hacerlo") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun iniciar(){
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnDespegar.setOnClickListener {
            limpiar()
        }
    }
}

