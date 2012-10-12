package com.viravira.ecodroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	// Variables públicas para compartir información entre distintos métodos.
	public int desafioint1;
	public int desafioint2;
	public int desafioint3;
	public String desafio1;
	public String desafio2;
	public String desafio3;
	public String puntaje1;
	public String puntaje2;
	public String puntaje3;
	public String titulo1;
	public String titulo2;
	public String titulo3;
	public String[] Desafios = new String[50];
	public String[] Puntajes = new String[50];
	public String[] Titulos = new String[50];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// El siguiente bloque de Try/Catch lee los desafíos desde un archivo
		// "desafios.txt",
		// los puntajes asociados a cada uno de los desafíos desde un archivo
		// "puntajes.txt",
		// los titulos asociados a cada uno de los desafíos desde un archivo
		// "titulos.txt".
		// Luego almacena los datos en dos arreglos, Desafios y Puntajes. Se
		// asume que el
		// número de desafíos será 50.

		try {

			BufferedReader archivodesafios = new BufferedReader(
					new InputStreamReader(this.getAssets().open("desafios.txt")));
			BufferedReader archivopuntajes = new BufferedReader(
					new InputStreamReader(this.getAssets().open("puntajes.txt")));
			BufferedReader archivotitulos = new BufferedReader(
					new InputStreamReader(this.getAssets().open("titulos.txt")));

			for (int i = 0; i < 50; i++) {
				Desafios[i] = archivodesafios.readLine();
				Puntajes[i] = archivopuntajes.readLine();
				Titulos[i] = archivotitulos.readLine();
			}

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

		// A continuación se incluye el código para cargar los desafíos activos.
		// Si alguno de los desafíos no tiene un valor guardado, se genera uno
		// al azar.
		// Los desafíos se mantienen activos hasta que el usuario presione
		// "Completado" en el dialog.

		SharedPreferences desafios = MainActivity.this.getApplicationContext()
				.getSharedPreferences("Prefs", 0);
		Random desafiosazar = new Random(); // Generador Azaroso

		// Cargar valores de los desafíos (cada valor es un int, que luego se
		// busca en los vectores creados anteriormente.
		desafioint1 = desafios.getInt("desafio1", desafiosazar.nextInt(50));
		desafioint2 = desafios.getInt("desafio2", desafiosazar.nextInt(50));
		desafioint3 = desafios.getInt("desafio3", desafiosazar.nextInt(50));

		desafio1 = Desafios[desafioint1];
		desafio2 = Desafios[desafioint2];
		desafio3 = Desafios[desafioint3];

		puntaje1 = Puntajes[desafioint1];
		puntaje2 = Puntajes[desafioint2];
		puntaje3 = Puntajes[desafioint3];

		titulo1 = Titulos[desafioint1];
		titulo2 = Titulos[desafioint2];
		titulo3 = Titulos[desafioint3];

		// A continuación, se guardan los valores hasta que se pulse
		// "Completado" en el dialog.

		SharedPreferences.Editor editor = desafios.edit();

		editor.putInt("desafio1", desafioint1);
		editor.putInt("desafio2", desafioint2);
		editor.putInt("desafio3", desafioint3);

		editor.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void Desafio1(View view) {
		Intent intent = new Intent(this, Desafio1.class);
		startActivity(intent);
	}

	public void Desafio2(View view) {
		Intent intent = new Intent(this, Desafio2.class);
		startActivity(intent);
	}

	public void Desafio3(View view) {
		Intent intent = new Intent(this, Desafio3.class);
		startActivity(intent);
	}
}
