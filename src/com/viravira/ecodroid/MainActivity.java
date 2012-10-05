package com.viravira.ecodroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String[] Desafios = new String[50];
		String[] Puntajes = new String[50];
		String[] Titulos = new String[50];

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
		// Intent openrec = new Intent(this, RecAct.class);
		// startActivity(openrec);
		showDialog(0);

	}

	public void Desafio2(View view) {
		// Intent openplay = new Intent(this, PlayAct.class);
		// startActivity(openplay);
		showDialog(1);
	}

	public void Desafio3(View view) {
		showDialog(2);
	}

	public void OpenPlay(View view) {
		Intent openplay = new Intent(this, PlayAct.class);
		startActivity(openplay);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialogo = crearAlerta(id);
		return dialogo;
	}

	private AlertDialog crearAlerta(final int id) {

		// Aquí se crean los dialogs correspondientes a cada desafío.
		// Los textos, titulos y puntajes se establecen a partir de los
		// strings generados anteriormente (a partir de los archivos
		// desafios.txt, titulos.txt y puntajes.txt.

		AlertDialog miAlerta;

		AlertDialog.Builder miConstructor = new AlertDialog.Builder(this);

		if (id == 0) {
			miConstructor.setTitle(titulo1 + " - " + puntaje1);
			miConstructor.setMessage(desafio1);
			miConstructor.setIcon(R.drawable.desafio1);
		} else if (id == 1) {
			miConstructor.setTitle(titulo2 + " - " + puntaje2);
			miConstructor.setMessage(desafio2);
			miConstructor.setIcon(R.drawable.desafio2);
		} else if (id == 2) {
			miConstructor.setTitle(titulo3 + " - " + puntaje3);
			miConstructor.setMessage(desafio3);
			miConstructor.setIcon(R.drawable.desafio3);
		}

		// La alerta será cancelable
		miConstructor.setCancelable(true);

		// Se añade el botón "Completado", junto con su listener
		miConstructor.setPositiveButton(R.string.si, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				// Aquí se genera un nuevo desafío cuando el usuario completa el
				// desafío anterior.
				// Por el momento, este cambio sólo se ve reflejado cuando se
				// reinicia la app.

				Random desafiosazar = new Random();

				SharedPreferences desafios = MainActivity.this
						.getApplicationContext().getSharedPreferences("Prefs",
								0);
				SharedPreferences.Editor editor = desafios.edit();

				if (id == 0) {
					editor.putInt("desafio1", desafiosazar.nextInt(50));
				} else if (id == 1) {
					editor.putInt("desafio2", desafiosazar.nextInt(50));
				} else {
					editor.putInt("desafio3", desafiosazar.nextInt(50));
				}

				editor.commit();

				// El dialogo se cierra
				dialog.dismiss();
			}
		});

		// Se añade el botón "No", junto con su listener
		miConstructor.setNegativeButton(R.string.no, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// El dialogo se cierra
				dialog.dismiss();
			}
		});

		miAlerta = miConstructor.create();
		return miAlerta;
	}
}
