package com.odc.beachodc.webservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.odc.beachodc.Home;
import com.odc.beachodc.Playas;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.ResultadoBusquedaPlaya;
import com.odc.beachodc.db.BBDD;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 15/07/2014.
 */
public class Response {

    public static void responseGetPlaya(Context ctx, JSONArray response){
        Playa playa = null;
        boolean BBDDborrada = false;
        for (int i=0; i<response.length(); i++){
            try {
                playa = JSONToModel.toPlaya(response.getJSONObject(i));
                if (!BBDDborrada){
                    BBDD.getApplicationDataContext(ctx).playasDao.removeAll();
                    BBDD.getApplicationDataContext(ctx).playasDao.save();
                    BBDDborrada = true;
                }
                playa.setStatus(Entity.STATUS_NEW);
                BBDD.getApplicationDataContext(ctx).playasDao.add(playa);
                BBDD.getApplicationDataContext(ctx).playasDao.save();
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETPLAYA: "+e.getMessage());
                playa = null;
            }
        }
    }

    public static void responseNuevaPlaya(Activity activity, JSONObject response){
        try {
            Playa playa = JSONToModel.toPlaya(response);

            playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();

            Intent intent = new Intent(activity, Home.class);
            intent.putExtra("creaplaya", true);

            // Para eliminar el historial de activities visitadas ya que volvemos al HOME y asi el boton ATRAS no tenga ningun comportamiento, se resetee.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }

    public static void responseEditarPlaya(Activity activity, JSONObject response){
        try {
            Playa playa = JSONToModel.toPlaya(response);

            // TODO: Para futuras versiones cuando haya cache local
            /*try {
                Playa aActualizar = BBDD.getApplicationDataContext(activity).playasDao.search(false, "idserver = ?", new String[]{playa.idserver}, null, null, null, null, null).get(0);
                aActualizar.setStatus(Entity.STATUS_DELETED);
                BBDD.getApplicationDataContext(activity).playasDao.remove(aActualizar);
                BBDD.getApplicationDataContext(activity).playasDao.save(aActualizar);
            } catch (Exception e){
                return;
            }

            playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();*/

            if ((playa.idserver != null) && (!playa.idserver.equals(""))) {

                Intent intent = new Intent(activity, Home.class);
                intent.putExtra("editaplaya", true);

                // Para eliminar el historial de activities visitadas ya que volvemos al HOME y asi el boton ATRAS no tenga ningun comportamiento, se resetee.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                activity.finish();
            } else {
                Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            }
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }

    public static void responseValorarPlaya(Activity activity, JSONObject response){
        try {
            Playa playa = JSONToModel.toPlaya(response);

            // TODO: Para futuras versiones cuando haya cache local
            /* try {
                Playa aActualizar = BBDD.getApplicationDataContext(activity).playasDao.search(false, "idserver = ?", new String[]{playa.idserver}, null, null, null, null, null).get(0);
                aActualizar.setStatus(Entity.STATUS_DELETED);
                BBDD.getApplicationDataContext(activity).playasDao.remove(aActualizar);
                BBDD.getApplicationDataContext(activity).playasDao.save(aActualizar);
            } catch (Exception e){
                return;
            }

            playa.setStatus(Entity.STATUS_NEW);
            BBDD.getApplicationDataContext(activity).playasDao.add(playa);
            BBDD.getApplicationDataContext(activity).playasDao.save();*/

            if (playa != null) {

                Intent intent = new Intent(activity, Playas.class);
                ValidacionPlaya.playa = playa;
                //TODO: Enviar algun boolean en la activity para que aparezca un mensajito como que la valoracion se ha realizado correctamente

                activity.startActivity(intent);
                activity.finish();
            } else {
                Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            }
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }

    public static void responseMensajeBotellaPlaya(Activity activity, JSONObject response){
        try {
            if ((response.optString("res") != null) && (response.optString("res").equals("ok"))) {
                Intent intent = new Intent(activity, Playas.class);
                //TODO: Enviar algun boolean en la activity para que aparezca un mensajito como que el mensaje se ha lanzado correctamente

                activity.startActivity(intent);
                activity.finish();
            } else {
                Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            }
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            return;
        }
    }

    public static void responseGetPlayasCercanas(Context ctx, JSONArray response, ProgressDialog pd){
        Playa playa = null;
        ValidacionPlaya.playas = new ArrayList<Playa>();
        for (int i=0; i<response.length(); i++){
            try {
                playa = JSONToModel.toPlaya(response.getJSONObject(i));
                ValidacionPlaya.playas.add(playa);
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETPLAYA: "+e.getMessage());
            }
        }
        ValidacionPlaya.cargadaPlayas=true;
        if ((ValidacionPlaya.cargadosUltimosCheckins) && (pd.isShowing())) {
            pd.dismiss();
        }
    }

    public static void responseGetUltimosCheckins(Context ctx, JSONArray response, ProgressDialog pd){
        Playa playa = null;
        ValidacionPlaya.playasCheckins = new ArrayList<Playa>();
        for (int i=0; i<response.length(); i++){
            try {
                playa = JSONToModel.toPlaya(response.getJSONObject(i));
                ValidacionPlaya.playasCheckins.add(playa);
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETPLAYA: "+e.getMessage());
            }
        }
        ValidacionPlaya.cargadosUltimosCheckins=true;
        if ((ValidacionPlaya.cargadaPlayas) && (pd.isShowing())) {
            pd.dismiss();
        }
    }

    public static void responseCheckinPlaya(Activity activity, JSONObject response, ProgressDialog pd){
        try {
            if ((response.optString("res") != null) && (response.optString("res").equals("ok"))){
                pd.dismiss();
                Crouton.makeText(activity, R.string.checkinexito, Style.CONFIRM).show();
            } else if ((response.optString("res") != null) && (response.optString("res").equals("existe"))) {
                Crouton.makeText(activity, R.string.checkinrepetido, Style.INFO).show();
                pd.dismiss();
            } else {
                Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
                pd.dismiss();
            }
        } catch (Exception e) {
            Crouton.makeText(activity, R.string.error_bbdd, Style.ALERT).show();
            System.out.println("FALLO RESPONSENUEVAPLAYA: "+e.getMessage());
            pd.dismiss();
            return;
        }
    }

    public static void responseGetPlayasCercanasTo(Context ctx, JSONArray response, ProgressDialog pd, String direccion, Double latitud, Double longitud){
        Playa playa = null;
        ValidacionPlaya.playas = new ArrayList<Playa>();
        for (int i=0; i<response.length(); i++){
            try {
                playa = JSONToModel.toPlaya(response.getJSONObject(i));
                ValidacionPlaya.playas.add(playa);
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETPLAYA: "+e.getMessage());
            }
        }
        Intent intentS = new Intent(ctx, ResultadoBusquedaPlaya.class);
        intentS.putExtra("search", direccion);
        intentS.putExtra("isSearch", true);
        intentS.putExtra("latitud", latitud);
        intentS.putExtra("longitud", longitud);
        pd.dismiss();
        ctx.startActivity(intentS);
    }

    public static void responseGetPlayasByName(Context ctx, JSONArray response, ProgressDialog pd, String name){
        Playa playa = null;
        ValidacionPlaya.playas = new ArrayList<Playa>();
        for (int i=0; i<response.length(); i++){
            try {
                playa = JSONToModel.toPlaya(response.getJSONObject(i));
                ValidacionPlaya.playas.add(playa);
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETPLAYA: "+e.getMessage());
            }
        }
        Intent intentS = new Intent(ctx, ResultadoBusquedaPlaya.class);
        intentS.putExtra("search", name);
        pd.dismiss();
        ctx.startActivity(intentS);
    }

    public static void responseGetMensajesBotella(Context ctx, JSONArray response, ProgressDialog pd){
        MensajeBotella mensaje;
        for (int i=0; i<response.length(); i++){
            try {
                mensaje = JSONToModel.toMensajeEnBotella(response.getJSONObject(i));
                ValidacionPlaya.mensajesBotella.add(mensaje);
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETMENSAJEBOTELLA: "+e.getMessage());
            }
        }
        ValidacionPlaya.cargadosMensajesPlaya=true;
        if (ValidacionPlaya.comprobarCargaPlaya()){
            pd.dismiss();
        }
    }

    public static void responseGetComentariosPlaya(Context ctx, JSONArray response, ProgressDialog pd){
        Comentario comentario;
        for (int i=0; i<response.length(); i++){
            try {
                comentario = JSONToModel.toComentario(response.getJSONObject(i));
                ValidacionPlaya.comentariosPlaya.add(comentario);
            } catch (Exception e){
                System.out.println("FALLO RESPONSEGETCOMENTARIOSPLAYA: "+e.getMessage());
            }
        }
        ValidacionPlaya.cargadosComentarios=true;
        if (ValidacionPlaya.comprobarCargaPlaya()){
            pd.dismiss();
        }
    }


    public static void responseGetTemp(Context ctx, JSONObject response, ProgressDialog pd){
        Double temp;

        try {
            temp = Double.parseDouble(response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp"));
            ValidacionPlaya.temperatura = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ValidacionPlaya.cargadaTemperatura=true;
        if (ValidacionPlaya.comprobarCargaPlaya()){
            pd.dismiss();
        }
    }


}
