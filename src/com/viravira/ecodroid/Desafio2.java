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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Desafio2 extends Activity {
	
	public String puntaje;
	public String numdesafio;
	public int dias;
	public String fechainicio;
	private ListView checkboxes;
	private String[] arr;
	
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
			BufferedReader archivodias = new BufferedReader(
					new InputStreamReader(this.getAssets().open("dias.txt")));

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
				archivodias.readLine();
			}
			
			String texto = archivodesafios.readLine();
			String titulo = archivotitulos.readLine();
			dias = Integer.parseInt(archivodias.readLine());
			puntaje = archivopuntajes.readLine();
			
			tviewdesafio.setText(texto);
			tviewtitulo.setText(titulo);
			tviewpuntaje.setText(puntaje + getString(R.string.pts));
			
			//Generar array con los textos de los Checkboxes
			
			arr = new String[dias];
			for (int i = 1; i <= dias; i++){
				arr[i-1] = "Día " + i;
			}
			
			// Agregar los checkboxes correspondientes al ListView
			
			checkboxes = (ListView) findViewById(R.id.ChkboxList);
			checkboxes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			checkboxes.setAdapter(new ArrayAdapter(this, 
					android.R.layout.simple_list_item_multiple_choice, arr));
		
			//Recuperar estado de los checkboxes 
			//TODO Bloquear checkboxes en el "futuro", que no pueden ser realizados todavia
			
			boolean state;
						
			for (int i=1;i<=dias;i++){
				state = desafios.getBoolean(desafioint + "_" + i, false);
				checkboxes.setItemChecked(i-1, state);
			}
			
//			
//			CheckBox check1 = (CheckBox) findViewById(R.id.checkbox1);
//			CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
//			
//			String desafiocheck1 = desafioint + "_" + "1";
//			String desafiocheck2 = desafioint + "_" + "2";
//			
//			boolean descheck1 = desafios.getBoolean(desafiocheck1, false);
//			boolean descheck2 = desafios.getBoolean(desafiocheck2, false);
//			
//			if(descheck1){
//				check1.setChecked(true);
//			}
//			if(descheck2){
//				check2.setChecked(true);
//			}
//			
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
			try {
				if (!archivodias.equals(null)) {
					archivodias.close();
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
		
//		CheckBox check1 = (CheckBox) findViewById(R.id.checkbox1);
//		CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
		
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
		SharedPreferences.Editor editor = desafios.edit();
				
		for (int i=1;i<=dias;i++){
			editor.remove(desafioint + "_" + i);
		}
		
//		String desafiocheck1 = desafioint + "_" + "1";
//		String desafiocheck2 = desafioint + "_" + "2";
		

		
//		if(check1.isChecked()){
//			editor.putBoolean(desafiocheck1, false);
//		}
//		if(check2.isChecked()){
//			editor.putBoolean(desafiocheck2, false);
//		}
		
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
		
//		CheckBox check1 = (CheckBox) findViewById(R.id.checkbox1);
//		CheckBox check2 = (CheckBox) findViewById(R.id.checkbox2);
		
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
		
//		String desafiocheck1 = desafioint + "_" + "1";
//		String desafiocheck2 = desafioint + "_" + "2";
		
		SharedPreferences.Editor editor = desafios.edit();
		
		for (int i=1;i<=dias;i++){
			if (checkboxes.isItemChecked(i-1)){
				editor.putBoolean(desafioint + "_" + i, true);
			}else{
				editor.putBoolean(desafioint + "_" + i, false);
			}
		}
		
//		if(check1.isChecked()){
//			editor.putBoolean(desafiocheck1, true);
//		}
//		if(check2.isChecked()){
//			editor.putBoolean(desafiocheck2, true);
//		}

		editor.commit();		
		
		Desafio2.this.finish();
		
	}
	
	protected void onStop(){
		super.onStop();
		Cancelar(findViewById(R.id.btncancelar2));
	}

}