package ir.sanaitgroup.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "ir.sanaitgroup.mvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "ir.sanaitgroup.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "ir.sanaitgroup.mvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "ir.sanaitgroup.mvvm.EXTRA_PRIORITY";

    private EditText editTextTitle, editTextDescription, editTextSearch;
    private NumberPicker numberPickerPriority;
    private Button btn_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        editTextSearch = findViewById(R.id.edit_text_search);
        btn_find = findViewById(R.id.btn_find);

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_note();
            }
        });

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else {
            setTitle("Add Note");
        }
    }

    private void save_note(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "لطفا عنوان و توضیحات را تکمیل کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK,data);
        finish();
    }

    private void find_note(){
        if (editTextSearch.getText().toString().trim().length()==0)
            return;

        int id = Integer.parseInt(editTextSearch.getText().toString());
        NoteViewModel  noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        Note note = noteViewModel.getNoteById(id);
        Toast.makeText(getApplicationContext(), "id: " + note.getId()
                + " ,title: " + note.getTitle()
                + " ,description: " + note.getDescription()
                + " ,priority: " + note.getPriority(),Toast.LENGTH_SHORT).show();
        editTextSearch.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                save_note();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
