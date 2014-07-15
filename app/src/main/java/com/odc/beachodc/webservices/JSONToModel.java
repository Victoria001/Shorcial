package com.odc.beachodc.webservices;

import com.odc.beachodc.db.models.Checkin;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paco on 15/07/2014.
 */
public class JSONToModel {

    public static Playa toPlaya (JSONObject json){
        try {
            String idserver = json.optString("_id");
            String nombre = json.optString("nombre");
			String geo = json.optString("geo");
			geo = geo.replace("[", "");
			geo = geo.replace("]", "");
			String[] loc = geo.split(",");
			Double latitud = Double.valueOf(loc[1]);
			Double longitud = Double.valueOf(loc[0]);
            Boolean banderaazul = json.optBoolean("baderaAzul");
            String dificultadacceso = json.optString("dificultadAcceso");
            String limpieza = json.optString("limpieza");
            String tipoarena = json.optString("tipoArena");
            Double valoracion = json.getDouble("valoracion");
            Boolean rompeolas = json.optBoolean("rompeolas");
            Boolean hamacas = json.optBoolean("hamacas");
            Boolean sombrillas = json.optBoolean("sombrillas");
            Boolean chiringuitos = json.optBoolean("chiringuitos");
            Boolean duchas = json.optBoolean("duchas");
            Boolean socorrista = json.optBoolean("socorrista");

            return new Playa(idserver, nombre, latitud, longitud, banderaazul, dificultadacceso, limpieza, tipoarena, valoracion, rompeolas, hamacas, sombrillas, chiringuitos, duchas, socorrista);
        } catch (Exception e){
            return null;
        }
    }

    public static Checkin toCheckin (JSONObject json){
        try {
            String idplaya = json.optString("idPlaya");
            String idusuario = json.optString("idUsuario");
            String fechaStr = json.optString("fecha");
            Date fecha = null;
            try {
                if (fechaStr != null){
                    String[] parts = fechaStr.split("Z");
                    parts = parts[0].split("T");
                    String[] dias = parts[0].split("-");
                    String[] horas = parts[1].split(":");
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.YEAR,  Integer.valueOf(dias[0]));
                    date.set(Calendar.MONTH, Integer.valueOf(dias[1])-1);
                    date.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dias[2]));
                    date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(horas[0]));
                    date.set(Calendar.MINUTE, Integer.valueOf(horas[1]));
                    date.set(Calendar.SECOND, Double.valueOf(horas[2]).intValue());
                    fecha = date.getTime();
                }
            } catch (Exception e){}


            return new Checkin(idplaya, fecha, idusuario);
        } catch (Exception e){
            return null;
        }
    }

    public static Comentario toComentario (JSONObject json){
        try {
            String idplaya = json.optString("idPlaya");
            String idusuario = json.optString("idUsuario");
            String comentario = json.optString("comentario");
            Integer valoracion = json.optInt("valoracion");
            String fechaStr = json.optString("fecha");
            Date fecha = null;
            try {
                if (fechaStr != null){
                    String[] parts = fechaStr.split("Z");
                    parts = parts[0].split("T");
                    String[] dias = parts[0].split("-");
                    String[] horas = parts[1].split(":");
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.YEAR,  Integer.valueOf(dias[0]));
                    date.set(Calendar.MONTH, Integer.valueOf(dias[1])-1);
                    date.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dias[2]));
                    date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(horas[0]));
                    date.set(Calendar.MINUTE, Integer.valueOf(horas[1]));
                    date.set(Calendar.SECOND, Double.valueOf(horas[2]).intValue());
                    fecha = date.getTime();
                }
            } catch (Exception e){}


            return new Comentario(idusuario, idplaya, comentario, fecha, valoracion);
        } catch (Exception e){
            return null;
        }
    }

    public static MensajeBotella toMensajeEnBotella (JSONObject json){
        try {
            String idplayaorigen = json.optString("idPlayaOrigen");
            String idplayadestino = json.optString("idPlayaDestino");
            String nombreplayadestino = json.optString("nombrePlayaDestino");
            String idusuario = json.optString("idUsuario");
            String nombreusuario = json.optString("nombreUsuario");
            String mensaje = json.optString("mensaje");
            String fechaStr = json.optString("fecha");
            Date fecha = null;
            try {
                if (fechaStr != null){
                    String[] parts = fechaStr.split("Z");
                    parts = parts[0].split("T");
                    String[] dias = parts[0].split("-");
                    String[] horas = parts[1].split(":");
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.YEAR,  Integer.valueOf(dias[0]));
                    date.set(Calendar.MONTH, Integer.valueOf(dias[1])-1);
                    date.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dias[2]));
                    date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(horas[0]));
                    date.set(Calendar.MINUTE, Integer.valueOf(horas[1]));
                    date.set(Calendar.SECOND, Double.valueOf(horas[2]).intValue());
                    fecha = date.getTime();
                }
            } catch (Exception e){}


            return new MensajeBotella(idusuario, nombreusuario, idplayadestino, idplayaorigen, nombreplayadestino, fecha, mensaje);
        } catch (Exception e){
            return null;
        }
    }
}
