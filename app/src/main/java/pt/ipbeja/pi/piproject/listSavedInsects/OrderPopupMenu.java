//package pt.ipbeja.pi.piproject.listSavedInsects;
//
//import android.content.Context;
//import android.content.Intent;
//import androidx.core.widget.PopupMenuCompat;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.PopupMenu;
//
//import java.io.IOException;
//import java.util.List;
//
//import pt.ipbeja.pi.piproject.R;
//import pt.ipbeja.pi.piproject.idkey.IdentificationKey;
//import pt.ipbeja.pi.piproject.persistence.Identification;
//import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb;
//
//// Popup menu to change order of insect in "My insects" list
//// Date: 2018/06/15
//public class OrderPopupMenu extends PopupMenu {
//
//    public OrderPopupMenu(final Context context, View view, final Identification identification, final MyIdentificationsDb db,
//                          final Identification[] identificationsArray, final ArrayAdapter adapter, final View identsLst
//                          ) {
//        super(context, view);
//
//        // https://developer.android.com/guide/topics/ui/menus#PopupMenu
//        try {
//            this.getMenuInflater().inflate(R.menu.insect_order_popup_menu, this.getMenu());
//
//            final List<String> orders = IdentificationKey.getInstance(context).getOrders();
//            for(int i = 0; i < orders.size(); i++)
//            {
//                //Log.e("$$$$$$$$$$$$->", orders.get(i));
//                this.getMenu().getItem(i).setTitle(orders.get(i));
//            }
//
//            this.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    identification.setOrder(item.toString());
//                    // O problema de não estar a dar update á informação erá devido a só estar alterar a ordem e nao o Id da Identification
//                    try {
//                        String id = IdentificationKey.getInstance(context).getResultByOrder(item.toString()).getId();
//                        identification.setKeyId(id);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            db.identificationDao().updateOrderInIdentification(identification);
//                            //adapter.notifyDataSetChanged(); //Estava a causar a aplicação crashar...
//                            Intent bookingIntent = new Intent(context, MyIdentifications.class);
//                            context.startActivity(bookingIntent);
//                            identsLst.invalidate();
//                        }
//                    }.start();
//
//                    return true;
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
