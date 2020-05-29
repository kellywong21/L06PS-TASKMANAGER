package sg.edu.rp.c346.l06ps_taskmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAddTasks;
    ListView lvTasks;
    ArrayList<Task> taskList;
    ArrayAdapter aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTasks = findViewById(R.id.lvTasks);
        btnAddTasks = findViewById(R.id.btnAdd);
        taskList = new ArrayList<>();

        DBHelper db = new DBHelper(MainActivity.this);
        taskList = db.getTasks();

        aa = new TaskAdapter(MainActivity.this,R.layout.row,taskList);
        lvTasks.setAdapter(aa);


        btnAddTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(i,9);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9){
            DBHelper db = new DBHelper(MainActivity.this);
            taskList.clear();
            taskList.addAll(db.getTasks());
            db.close();
            lvTasks.setAdapter(aa);
            aa.notifyDataSetChanged();
        }
    }
}
