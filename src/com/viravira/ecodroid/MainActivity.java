package com.viravira.ecodroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void Desafio1(View view){
//    	Intent openrec = new Intent(this, RecAct.class);
//    	startActivity(openrec);
    	 showDialog(0);
    	
    }
    
    public void Desafio2(View view){
//    	Intent openplay = new Intent(this, PlayAct.class);
//    	startActivity(openplay);
    	showDialog(1);
    }
    public void Desafio3(View view){
    	showDialog(2);
    }   
    public void OpenPlay(View view){
    	Intent openplay = new Intent(this, PlayAct.class);
    	startActivity(openplay);

    }
   
    @Override
    protected Dialog onCreateDialog(int id){
          Dialog dialogo=crearAlerta(id);
          return dialogo; 
   }    
    
    private AlertDialog crearAlerta(int id) {
        
    	AlertDialog miAlerta=null;
        
        AlertDialog.Builder miConstructor=new AlertDialog.Builder(this);
        
        if (id == 0){
	        //Se establece el título de la alerta
	        miConstructor.setTitle(R.string.title_desafio1);
	        miConstructor.setMessage(R.string.desafio1);
	        //Agregar iconoc a la izquierda del título
	        miConstructor.setIcon(R.drawable.desafio1);
        }
        else if (id == 1){
	        //Se establece el título de la alerta
	        miConstructor.setTitle(R.string.title_desafio2);
	        miConstructor.setMessage(R.string.desafio2);
	        //Agregar iconoc a la izquierda del título
	        miConstructor.setIcon(R.drawable.desafio2);        	
        }
        else if (id == 2){
	        //Se establece el título de la alerta
	        miConstructor.setTitle(R.string.title_desafio3);
	        miConstructor.setMessage(R.string.desafio3);
	        //Agregar iconoc a la izquierda del título
	        miConstructor.setIcon(R.drawable.desafio3);         	
        }
        
        //La alerte será cancelable 
        miConstructor.setCancelable(true);
        
        //Se añada el botón "Si", junto con su listener
        miConstructor.setPositiveButton(R.string.si, new OnClickListener(){
           public void onClick(DialogInterface dialog, int which){
               // El dialogo se cierra
               dialog.dismiss();
            } 
          });
   
        //Se añade el botón "No", junto con su listener
        miConstructor.setNegativeButton(R.string.no, new OnClickListener(){
           public void onClick(DialogInterface dialog, int which) {
                  // El dialogo se cierra
                  dialog.dismiss();
           }
        });

        
        miAlerta=miConstructor.create();
        return miAlerta; 
}   
}
