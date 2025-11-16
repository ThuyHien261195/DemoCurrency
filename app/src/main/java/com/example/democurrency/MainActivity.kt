package com.example.democurrency

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.democurrency.presentation.MainAction
import com.example.democurrency.presentation.MainViewModel
import com.example.democurrency.presentation.ui.StateEvent
import com.example.mediator.currencyinfo.presentation.CurrencyInfoFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var currencyInfoFragmentFactory: CurrencyInfoFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment())
                .commit()
        }
        registerObservers()
    }

    private fun registerObservers() {
        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiActionFlow.collect { action ->
                    when (action) {
                        is MainAction.ShowListAction -> {
                            openListFragment(action.groupId)
                        }

                        is MainAction.ShowAllAction -> {
                            openListFragment()
                        }

                        else -> {}
                    }
                }
            }
        }

        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.stateEventFlow.collect { event ->
                    when (event) {
                        is StateEvent.ClearSuccess ->
                            showToastMessage(getString(R.string.msg_clear_db_successfully))

                        is StateEvent.InitSuccess ->
                            showToastMessage(getString(R.string.msg_init_db_successfully))

                        is StateEvent.AddSuccess ->
                            showToastMessage(getString(R.string.msg_add_db_successfully))

                        is StateEvent.Error ->
                            showToastMessage(event.message)

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun openListFragment(groupId: Int? = null) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container, currencyInfoFragmentFactory.createFragment(
                    groupId = groupId,
                )
            )
            .addToBackStack(null)
            .commit()
    }
}