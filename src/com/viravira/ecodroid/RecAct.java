package com.viravira.ecodroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecAct extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rec);

		ListView ListRecCat = (ListView) findViewById(R.id.ListRecCat); //Lista para mostrar elementos.
		
		//Elementos a mostrar en la lista.
		final String[] Categorias = { "Botellas de Plastico",
				"Botellas de Vidrio", "Latas", "Electronica",
				"Materia Organica" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Categorias);

		ListRecCat.setAdapter(adapter);

		ListRecCat.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int id,
					long position) {

				// Toast.makeText(getApplicationContext(),
				// "ClickListener number " + position,
				// Toast.LENGTH_LONG).show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_rec, menu);
		return true;
	}

}
