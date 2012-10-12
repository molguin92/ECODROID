/*
ECODROID es un juego cuya misión es ayudar a aprender cómo cuidar
el medioambiente.

ECODROID is a game which aims to teach the user how to take care of
the environment.

Copyright (C) 2012  Manuel Olguín

-- ESPAÑOL --

Este programa es software libre: puede ser redistribuído y/o modificado
bajo los términos de la GNU General Public License, tal cual ha sido 
dictada por la Free Software Foundation, ya sea la versión 3 de dicha 
licencia o (a elección) cualquier versión posterior.

Éste programa es distribuído con la esperanza de que sea útil, pero 
SIN GARANTÍA ALGUNA; nisiquiera la garantía implícita de MERCANTIBILIDAD
o APTITUD DE USO PARA UN PROPÓSITO PARTICULAR. Para mayor detalle, vea
la GNU General Public License.

Debió haber recibido una copia de la GNU General Public License con
éste programa. De no ser así, vea <http://www.gnu.org/licenses/licenses.es.html>

-- ENGLISH --

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
	
*/

/* 
Éste archivo contiene la actividad principal de ECODROID.
Aquí se leen los estados guardados en sesiones anteriores,
se generan nuevos desafíos en caso de ser necesario, y se 
presentan en botones, los cuales abren actividades con 
detalles sobre los respectivos desafíos. También presenta
una opción para resetear la app.
*/
 com.viravira.ecodroid;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
	public int puntajetotal;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initDesafios();

	}

	public void initDesafios(){

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
		// "Completado" en la actividad correspondiente.

		SharedPreferences desafios = MainActivity.this.getApplicationContext()
				.getSharedPreferences("Prefs", 0);
		Random desafiosazar = new Random(); // Generador Azaroso

		// Cargar valores de los desafíos (cada valor es un int, que luego se
		// busca en los vectores creados anteriormente.
		desafioint1 = desafios.getInt("desafio1", desafiosazar.nextInt(50));
		desafioint2 = desafios.getInt("desafio2", desafiosazar.nextInt(50));
		desafioint3 = desafios.getInt("desafio3", desafiosazar.nextInt(50));

		puntajetotal = desafios.getInt("puntaje", 0);

		desafio1 = Desafios[desafioint1];
		desafio2 = Desafios[desafioint2];
		desafio3 = Desafios[desafioint3];

		puntaje1 = Puntajes[desafioint1];
		puntaje2 = Puntajes[desafioint2];
		puntaje3 = Puntajes[desafioint3];

		titulo1 = Titulos[desafioint1];
		titulo2 = Titulos[desafioint2];
		titulo3 = Titulos[desafioint3];
		
		TextView puntaje = (TextView) findViewById(R.id.score);
		Button btn1 = (Button) findViewById(R.id.Desafio1);
		Button btn2 = (Button) findViewById(R.id.Desafio2);
		Button btn3 = (Button) findViewById(R.id.Desafio3);
		
		// Se carga el puntaje total del usuario. Valor por defecto es 0.
		puntajetotal = desafios.getInt("puntaje", 0);
		
		// Se actualizan los títulos de los botones y el valor
		// del contador de puntaje.
		btn1.setText(titulo1);
		btn2.setText(titulo2);
		btn3.setText(titulo3);
				
		String pts = getString(R.string.pts);
		puntaje.setText(" "+puntajetotal+" "+pts);

		// A continuación, se guardan los valores hasta que se pulse
		// "Completado" en la actividad.

		SharedPreferences.Editor editor = desafios.edit();

		editor.putInt("desafio1", desafioint1);
		editor.putInt("desafio2", desafioint2);
		editor.putInt("desafio3", desafioint3);

		editor.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
	// Aquí se crea un menú con una opción de reseteo
	// del contador de puntaje y los desafíos.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.cleanprefs:
			// Elemento del menú para resetar la 
			// aplicación en su totalidad.
			cleanPrefs();
			initDesafios();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void cleanPrefs(){
	
		// Éste método es llamado por el botón de reseteo
		// para borrar todos los datos de la app.

		SharedPreferences desafios = MainActivity.this.getApplicationContext()
				.getSharedPreferences("Prefs", 0);

		SharedPreferences.Editor editor = desafios.edit();

		editor.clear();

		editor.commit();

	}

	public void onResume(){
	
		// Actualiza la actividad al volver desde un desafío.
		
		super.onResume();

		initDesafios();

	}
	
	// Los siguientes tres métodos están asociados a cada uno 
	// de los tres botones, e inician la actividad correspondiente
	// a cada desafío.
	
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
