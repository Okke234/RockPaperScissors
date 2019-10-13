package com.example.rockpaperscissors.app.ui

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.app.adapter.GameAdapter
import com.example.rockpaperscissors.app.database.GameRepository
import com.example.rockpaperscissors.app.model.Game
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private val gameList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gameList)
    private var historyCleared = false
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvGames.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
        rvGames.adapter = gameAdapter
        rvGames.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        getGamesFromDatabase()
    }

    private fun getGamesFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryActivity.gameList.clear()
            this@HistoryActivity.gameList.addAll(gameList)
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGameHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            historyCleared = true
            getGamesFromDatabase()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onBackPressed() {
        setResult(if (historyCleared) Activity.RESULT_OK else Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_delete_history -> {
                deleteGameHistory()
                true
            }
            android.R.id.home -> {
                setResult(if (historyCleared) Activity.RESULT_OK else Activity.RESULT_CANCELED)
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}