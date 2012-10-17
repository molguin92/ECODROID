// Desafio1 is a part of ECODROID.

/* 
Desafio1 es la actividad correspondiente al primer 
desafío presentado en MainActivity. Aquí se muestran
todos los detalles del desafío, junto con botones para
cancelar o marcar el desafío como completado. En el 
caso de la segunda opción, se analiza el puntaje 
asociado al desafío y se suma a un total.
*/

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
import android.widget.TextView;
import android.widget.Toast;

public class Desafio1 extends Activity {
	
	public String puntaje;
	String numdesafio;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desafio1);
		
		// A continuación se leen los datos guardados en las preferencias
		// de la app, y se carga el desafío correspondiente.
		// Luego, se actualizan los TextViews correspondientes con los 
		// datos guardados.

		TextView tviewtitulo = (TextView) findViewById(R.id.titulodes1);
		TextView tviewdesafio = (TextView) findViewById(R.id.textodes1);
		TextView tviewpuntaje = (TextView) findViewById(R.id.puntajedes1);
		
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


			SharedPreferences desafios = Desafio1.this.getApplicationContext()
					.getSharedPreferences("Prefs", 0);
			Random desafiosazar = new Random(); 
			
			int desafioint = desafios.getInt(numdesafio, desafiosazar.nextInt(50));
			
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
			tviewpuntaje.setText(puntaje + "pts");
			
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
		getMenuInflater().inflate(R.menu.activity_desafio1, menu);
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
		
		Context context = Desafio1.this.getApplicationContext();

		SharedPreferences desafios = context.getSharedPreferences("Prefs",
						0);
		
		int puntajetotal = Integer.parseInt(puntaje) + desafios.getInt("puntaje", 0);
		
		SharedPreferences.Editor editor = desafios.edit();
		editor.putInt(numdesafio, desafiosazar.nextInt(50));
		editor.putInt("puntaje", puntajetotal);

		editor.commit();
		
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, "Has ganado " + puntaje + " pts!", duration);
		
		toast.show();
		
		Desafio1.this.finish();
		
	}
	
	public void Cancelar (View view){
		
		Desafio1.this.finish();
		
	}

}
