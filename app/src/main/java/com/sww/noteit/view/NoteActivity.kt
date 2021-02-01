package com.sww.noteit.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.ActivityNoteBinding
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.model.Photo
import com.sww.noteit.view_model.NoteViewModel
import com.sww.noteit.view_model.NoteViewModelFactory
import com.sww.noteit.view_model.adapters.PhotosListAdapter
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity(), PhotosListAdapter.OnClickListener {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private lateinit var noteViewModel: NoteViewModel
    private var noteID: Int? = null

    private lateinit var adapter: PhotosListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNoteBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_note)

        noteID = intent.extras?.getInt(NotesFragment.NOTE_ID)

        setSupportActionBar(toolbar_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO: get title of the note and set supportActionBar title with it
        supportActionBar?.title = DataContainer.currentNote.Title

        val noteViewModelFactory = NoteViewModelFactory(noteID, application)
        noteViewModel =
            ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
        binding.lifecycleOwner = this
        binding.noteViewModel = noteViewModel
        et_note_content.setText(DataContainer.currentNote.Note)

        // set up the RecyclerView

        //TODO sztuczna lista

//        var image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_camera);

//        val photosTMPList = mutableListOf(
//            Photo(1, image)
//        )
//
//        val photosListAdapter = PhotosListAdapter(mutableListOf<Photo>(), this)
//        binding.photosRecyclerView.adapter = photosListAdapter

//        photosListAdapter.setPhotos(photosTMPList)
    }

    override fun onSupportNavigateUp(): Boolean {
        //TODO: Update note in db
        DataContainer.currentNote.Note=et_note_content.getText().toString()
        DatabaseHttpRequests.sendUpdateNotesRequest(DataContainer.currentNote)
        Toast.makeText(this, "Note Saved 💾", Toast.LENGTH_SHORT).show()

        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        DataContainer.currentNote.Note=et_note_content.getText().toString()
        menuInflater.inflate(R.menu.toolbar_edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit_note_menu_item) {
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra(NotesFragment.NOTE_ID, noteID)
            startActivity(intent)
            return true
        } else if (item.itemId == R.id.take_picture_menu_item) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(this, "oops", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //TODO change image View to something different and save picture
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val thumbNail: Bitmap = data!!.extras!!.get("data") as Bitmap
                DataContainer.currentNote.ImageURL="wykop.pl"
                //imageView.setImageBitmap(thumbNail)

                //Todo add item to recycler view
                // TODO: Create LiveData allNotes and fetch the data from db
//              notesViewModel.allNotes.observe(viewLifecycleOwner, { notes ->
//              notes?.let { notesListAdapter.setNotes(it.toMutableList()) }
//                 })


            }
        }
    }

    override fun onClick(position: Int, model: Photo) {
        TODO("Not yet implemented")
    }
}