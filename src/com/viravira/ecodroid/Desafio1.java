package com.viravira.ecodroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Desafio1 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desafio1);

		TextView tviewtitulo = (TextView) findViewById(R.id.titulodes1);
		TextView tviewdesafio = (TextView) findViewById(R.id.textodes1);
		TextView tviewpuntaje = (TextView) findViewById(R.id.puntajedes1);
		
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
			
			int desafioint = desafios.getInt("desafio1", desafiosazar.nextInt(50));
			
			for(int i = 0; i < desafioint; i++){
				archivodesafios.readLine();
				archivopuntajes.readLine();
				archivotitulos.readLine();
			}
			
			String texto = archivodesafios.readLine();
			String titulo = archivotitulos.readLine();
			String puntaje = archivopuntajes.readLine();
			
			tviewdesafio.setText(texto);
			tviewtitulo.setText(titulo);
			tviewpuntaje.setText(puntaje);
			
			SharedPreferences.Editor editor = desafios.edit();

			editor.putInt("desafio1", desafioint);

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
		
		Random desafiosazar = new Random();

		SharedPreferences desafios = Desafio1.this
				.getApplicationContext().getSharedPreferences("Prefs",
						0);
		SharedPreferences.Editor editor = desafios.edit();

		editor.putInt("desafio1", desafiosazar.nextInt(50));

		editor.commit();
		
		Desafio1.this.finish();
		
	}
	
	public void Cancelar (View view){
		
		Desafio1.this.finish();
		
	}

}
