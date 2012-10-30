// Desafio2 is a part of ECODROID.

package com.viravira.ecodroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Desafio2 extends Activity {
	
	public String puntaje;
	public String numdesafio;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desafio2);
		
		// A continuación se leen los datos guardados en las preferencias
		// de la app, y se carga el desafío correspondiente.
		// Luego, se actualizan los TextViews correspondientes con los 
		// datos guardados.

		TextView tviewtitulo = (TextView) findViewById(R.id.titulodes2);
		TextView tviewdesafio = (TextView) findViewById(R.id.textodes2);
		TextView tviewpuntaje = (TextView) findViewById(R.id.puntajedes2);
		
		Intent intent = getIntent();
		int num = intent.getIntExtra("num", 1);
		if (num==1){
			numdesafio = "desafio1";
		}else if(num==2){
			numdesafio = "desafio2";
		}else{
			numdesafio = "desafio3";
		}
		
		try {

			BufferedReader archivodesafios = new BufferedReader(
					new InputStreamReader(this.getAssets().open("desafios.txt")));
			BufferedReader archivopuntajes = new BufferedReader(
					new InputStreamReader(this.getAssets().open("puntajes.txt")));
			BufferedReader archivotitulos = new BufferedReader(
					new InputStreamReader(this.getAssets().open("titulos.txt")));


			SharedPreferences desafios = Desafio2.this.getApplicationContext()
					.getSharedPreferences("Prefs", 0);
			Random desafiosazar = new Random(); 
			
			int desafioint = desafios.getInt(numdesafio, desafiosazar.nextInt(40));
			
			// El siguiente comando "for" es para saltarse el número
			// de líneas necesario para llegar a la línea del desafío.
			for(int i = 0; i < desafioint; i++){
				archivodesafios.readLine();
				archivopuntajes.readLine();
				archivotitulos.readLine();
			}
			
			String texto = archivodesafios.readLine();
			String titulo = archivotitulos.readLine();
			puntaje = archivopuntajes.readLine();
			
			tviewdesafio.setText(texto);
			tviewtitulo.setText(titulo);
			tviewpuntaje.setText(puntaje + getString(R.string.pts));
			
			CheckBox check1 = (CheckBox) findViewById(R.id.checkbox1);
			CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
			
			String desafiocheck1 = desafioint + "_" + "1";
			String desafiocheck2 = desafioint + "_" + "2";
			
			boolean descheck1 = desafios.getBoolean(desafiocheck1, false);
			boolean descheck2 = desafios.getBoolean(desafiocheck2, false);
			
			if(descheck1){
				check1.setChecked(true);
			}
			if(descheck2){
				check2.setChecked(true);
			}
			
			SharedPreferences.Editor editor = desafios.edit();
			editor.putInt(numdesafio, desafioint);
			editor.commit();

			try {
				if (!archivodesafios.equals(null)) {
					archivodesafios.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			try {
				if (!archivopuntajes.equals(null)) {
					archivopuntajes.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			try {
				if (!archivotitulos.equals(null)) {
					archivotitulos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_desafio2, menu);
		return true;
	}
	
	public void Completado (View view){
	
		// Este método describe el botón "Completado".
		// Aquí se genera un nuevo desafío al completar
		// el anterior, y se actualiza la información 
		// guardada en SharedPreferences.
		// También se actualiza el puntaje total del 
		// usuario (se suma el ptje anterior con el
		// puntaje asociado al desafío en cuestión.
		
		CheckBox check1 = (CheckBox) findViewById(R.id.checkbox1);
		CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
		
		Intent intent = getIntent();
		int num = intent.getIntExtra("num", 1);
		
		if (num==1){
			numdesafio = "desafio1";
		}else if(num==2){
			numdesafio = "desafio2";
		}else{
			numdesafio = "desafio3";
		}
		
		Random desafiosazar = new Random();
		
		Context context = Desafio2.this.getApplicationContext();

		SharedPreferences desafios = context.getSharedPreferences("Prefs",
						0);
		
		int desafioint = desafios.getInt(numdesafio, desafiosazar.nextInt(40));
		int puntajetotal = Integer.parseInt(puntaje) + desafios.getInt("puntaje", 0);
		
		String desafiocheck1 = desafioint + "_" + "1";
		String desafiocheck2 = desafioint + "_" + "2";
		
		SharedPreferences.Editor editor = desafios.edit();
		
		if(check1.isChecked()){
			editor.putBoolean(desafiocheck1, false);
		}
		if(check2.isChecked()){
			editor.putBoolean(desafiocheck2, false);
		}
		
		editor.putInt(numdesafio, desafiosazar.nextInt(40));
		editor.putInt("puntaje", puntajetotal);

		editor.commit();
		
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, "Has ganado " + puntaje + " "+getString(R.string.pts)+
				"!", duration);
		
		toast.show();
		
		Desafio2.this.finish();
		
	}
	
	public void Cancelar (View view){
		
		CheckBox check1 = (CheckBox) findViewById(R.id.checkbox1);
		CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
		
		Intent intent = getIntent();
		
		int num = intent.getIntExtra("num", 1);
		
		if (num==1){
			numdesafio = "desafio1";
		}else if(num==2){
			numdesafio = "desafio2";
		}else{
			numdesafio = "desafio3";
		}
		
		Context context = Desafio2.this.getApplicationContext();

		SharedPreferences desafios = context.getSharedPreferences("Prefs",
						0);
		
		Random desafiosazar = new Random(); 
		
		int desafioint = desafios.getInt(numdesafio, desafiosazar.nextInt(40));
		
		String desafiocheck1 = desafioint + "_" + "1";
		String desafiocheck2 = desafioint + "_" + "2";
		
		SharedPreferences.Editor editor = desafios.edit();
		
		if(check1.isChecked()){
			editor.putBoolean(desafiocheck1, true);
		}
		if(check2.isChecked()){
			editor.putBoolean(desafiocheck2, true);
		}

		editor.commit();		
		
		Desafio2.this.finish();
		
	}

}