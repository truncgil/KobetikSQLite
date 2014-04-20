package com.example.kobetikdb;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final KobetikSQLite kobetik = new KobetikSQLite(this);
		
		kobetik.baglan();
		
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		final EditText t = (EditText) findViewById(R.id.editText1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] dizi = t.getText().toString().split(" ");
				
				kobetik.sorgu2("INSERT INTO deneme (isim, soyisim) VALUES ('"+dizi[0].toString()+"','"+dizi[1].toString()+"')");
				Listele();
			}
		});
		
		
		Listele();
		
	}
	public void Listele() {
		final KobetikSQLite kobetik = new KobetikSQLite(this);
		ListView lv = (ListView) findViewById(R.id.listView1);
		Cursor c = kobetik.sorgu("Select * from deneme ORDER BY id DESC");
		
		ArrayAdapter<String> dizi = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		lv.setAdapter(dizi);
		while(c.moveToNext()) {
			dizi.add(kobetik.sutun(c, 1) +" "+ kobetik.sutun(c, 2));
			System.out.println(c.getString(2).toString());
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
