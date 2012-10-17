// Desafio1 is a part of ECODROID.

/* 
Desafio1 es la actividad correspondiente al primer 
desaf�o presentado en MainActivity. Aqu� se muestran
todos los detalles del desaf�o, junto con botones para
cancelar o marcar el desaf�o como completado. En el 
caso de la segunda opci�n, se analiza el puntaje 
asociado al desaf�o y se suma a un total.
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desafio1);
		
		// A continuaci�n se leen los datos guardados en las preferencias
		// de la app, y se carga el desaf�o correspondiente.
		// Luego, se actualizan los TextViews correspondientes con los 
		// datos guardados.

		TextView tviewtitulo = (TextView) findViewById(R.id.titulodes1);
		TextView tviewdesafio = (TextView) findViewById(R.id.textodes1);
		TextView tviewpuntaje = (TextView) findViewById(R.id.puntajedes1);
		
		Intent intent = getIntent();
		int num = intent.getIntExtra("num", 1);
		
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
			
			int desafioint;
			
			if(1 == num){
				desafioint = desafios.getInt("desafio1", desafiosazar.nextInt(50));
			} else if(2 == num){
				desafioint = desafios.getInt("desafio2", desafiosazar.nextInt(50));
			} else {
				desafioint = desafios.getInt("desafio3", desafiosazar.nextInt(50));
			}
			
			// El siguiente comando "for" es para saltarse el n�mero
			// de l�neas necesario para llegar a la l�nea del desaf�o.
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
			
			if(1 == num){
				editor.putInt("desafio1", desafioint);
			} else if(2 == num){
				editor.putInt("desafio2", desafioint);
			} else {
				editor.putInt("desafio3", desafioint);
			}

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
	
		// Este m�todo describe el bot�n "Completado".
		// Aqu� se genera un nuevo desaf�o al completar
		// el anterior, y se actualiza la informaci�n 
		// guardada en SharedPreferences.
		// Tambi�n se actualiza el puntaje total del 
		// usuario (se suma el ptje anterior con el
		// puntaje asociado al desaf�o en cuesti�n.
		
		Intent intent = getIntent();
		int num = intent.getIntExtra("num", 1);
		
		Random desafiosazar = new Random();
		
		Context context = Desafio1.this.getApplicationContext();

		SharedPreferences desafios = context.getSharedPreferences("Prefs",
						0);
		
		int puntajetotal = Integer.parseInt(puntaje) + desafios.getInt("puntaje", 0);
		
		SharedPreferences.Editor editor = desafios.edit();

		if(1 == num){
			editor.putInt("desafio1", desafiosazar.nextInt(50));
		} else if(2 == num){
			editor.putInt("desafio2", desafiosazar.nextInt(50));
		} else {
			editor.putInt("desafio3", desafiosazar.nextInt(50));
		}
		
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
