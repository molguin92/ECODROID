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

public class Desafio2 extends Activity {
	
	public String puntaje;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desafio2);

		TextView tviewtitulo = (TextView) findViewById(R.id.titulodes2);
		TextView tviewdesafio = (TextView) findViewById(R.id.textodes2);
		TextView tviewpuntaje = (TextView) findViewById(R.id.puntajedes2);
		
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
			
			int desafioint = desafios.getInt("desafio2", desafiosazar.nextInt(50));
			
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

			editor.putInt("desafio2", desafioint);

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
		
		Random desafiosazar = new Random();

		SharedPreferences desafios = Desafio2.this
				.getApplicationContext().getSharedPreferences("Prefs",
						0);
		
		int puntajetotal = Integer.parseInt(puntaje) + desafios.getInt("puntaje", 0);
		
		SharedPreferences.Editor editor = desafios.edit();

		editor.putInt("desafio2", desafiosazar.nextInt(50));
		editor.putInt("puntaje", puntajetotal);

		editor.commit();
		
		Desafio2.this.finish();
		
	}
	
	public void Cancelar (View view){
		
		Desafio2.this.finish();
		
	}

}