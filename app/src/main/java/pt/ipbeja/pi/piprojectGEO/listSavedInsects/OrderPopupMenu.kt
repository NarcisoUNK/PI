package pt.ipbeja.pi.piprojectGEO.listSavedInsects

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.idkey.IdentificationKey
import pt.ipbeja.pi.piproject.persistence.Identification
import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb
import java.io.IOException

// Popup menu to change order of insect in "My insects" list
// Date: 2018/06/15
class OrderPopupMenu(
    context: Context, view: View?, identification: Identification, db: MyIdentificationsDb,
    identificationsArray: Array<Identification?>?, adapter: ArrayAdapter<*>?, identsLst: View
) : PopupMenu(context, view) {
    init {
        // https://developer.android.com/guide/topics/ui/menus#PopupMenu
        try {
            this.menuInflater.inflate(R.menu.insect_order_popup_menu, this.menu)

            val orders = IdentificationKey.getInstance(context)!!
                .getOrders()
            for (i in orders.indices) {
                //Log.e("$$$$$$$$$$$$->", orders.get(i));
                this.menu.getItem(i).setTitle(orders[i])
            }

            this.setOnMenuItemClickListener { item ->
                identification.order = item.toString()
                // O problema de não estar a dar update á informação erá devido a só estar alterar a ordem e nao o Id da Identification
                try {
                    val id: String =
                        IdentificationKey.getInstance(context)!!.getResultByOrder(item.toString())?.id.toString()
                    identification.keyId = id
                } catch (e: IOException) {
                    e.printStackTrace()
                }


                object : Thread() {
                    override fun run() {
                        db.identificationDao()?.updateOrderInIdentification(identification)
                        //adapter.notifyDataSetChanged(); //Estava a causar a aplicação crashar...
                        val bookingIntent = Intent(context, MyIdentifications::class.java)
                        context.startActivity(bookingIntent)
                        identsLst.invalidate()
                    }
                }.start()
                true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}