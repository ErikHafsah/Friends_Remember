package com.example.friendsremember.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsremember.R
import com.example.friendsremember.data.room.Contact
import com.example.friendsremember.ui.add.AddFriendsActivity
import com.example.friendsremember.ui.main.ShowFriendsActivity.Companion.EXTRA_EMAIL
import com.example.friendsremember.ui.main.ShowFriendsActivity.Companion.EXTRA_GENDER
import com.example.friendsremember.ui.main.ShowFriendsActivity.Companion.EXTRA_ID
import com.example.friendsremember.ui.main.ShowFriendsActivity.Companion.EXTRA_NAME
import com.example.friendsremember.ui.main.ShowFriendsActivity.Companion.EXTRA_PHONE_NUMBER
import com.example.friendsremember.ui.main.ShowFriendsActivity.Companion.EXTRA_SURNAME


class MainActivity : AppCompatActivity() {

    companion object {
        private const val newContactActivityRequestCode = 1
    }

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var adapter: ContactAdapter

    private var clonedList: Array<Contact> = emptyArray<Contact>()
    val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
    val mainRecycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.mainRecycler)
    val fabAdd = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAdd)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        adapter = ContactAdapter(this)
        mainRecycler.adapter = adapter
        mainRecycler.layoutManager = LinearLayoutManager(this)


        //Observe list change
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, Observer { contacts ->
            contacts?.let {
                adapter.setList(it as ArrayList<Contact>)
                clonedList = adapter.getList().toTypedArray()
            }
        })

        fabAdd.setOnClickListener {
            val intent = Intent(this, AddFriendsActivity::class.java)
            startActivity(intent)
        }

        onSwipeRecycler()
        onClickRecycler()
    }

    private fun onClickRecycler() {
        adapter.setOnItemClickListener(object : OnClickInterface {
            override fun setOnClick(position: Int) {
                val intent = Intent(applicationContext, ShowFriendsActivity::class.java)
                intent.putExtra(EXTRA_ID, adapter.getItemAt(position).id)
                intent.putExtra(EXTRA_NAME, adapter.getItemAt(position).name)
                intent.putExtra(EXTRA_SURNAME, adapter.getItemAt(position).surname)
                intent.putExtra(EXTRA_EMAIL, adapter.getItemAt(position).email)
                intent.putExtra(EXTRA_PHONE_NUMBER, adapter.getItemAt(position).phoneNumber)
                intent.putExtra(EXTRA_GENDER, adapter.getItemAt(position).gender)
                startActivity(intent)
            }
        })

    }

    private fun onSwipeRecycler() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Usunięto: ${adapter.getItemAt(viewHolder.adapterPosition).name} ${adapter.getItemAt(viewHolder.adapterPosition).surname}",
                    Toast.LENGTH_SHORT
                ).show()
                contactViewModel.deleteContactWithId(adapter.getItemAt(viewHolder.adapterPosition).id)
            }
            
        }).attachToRecyclerView(mainRecycler)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        setSearchBar(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun setSearchBar(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Contact> = ArrayList<Contact>()
        for (contact in clonedList) {
            if (contact.name.toLowerCase().contains(text.toLowerCase()) ||
                contact.surname.toLowerCase().contains(text.toLowerCase()) ||
                contact.email.toLowerCase().contains(text.toLowerCase()) ||
                contact.phoneNumber.toString().toLowerCase().contains(text.toLowerCase())
            ) {
                filteredList.add(contact)
            }
        }
        if (filteredList.size == 0) {
            Toast.makeText(applicationContext, getString(R.string.nothing_found), Toast.LENGTH_SHORT).show()
        }
        adapter.setList(filteredList)
    }

}
