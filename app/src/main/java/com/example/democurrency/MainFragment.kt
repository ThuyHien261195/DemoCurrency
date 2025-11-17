package com.example.democurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.democurrency.presentation.MainAction
import com.example.democurrency.presentation.MainViewModel
import com.example.democurrency.presentation.ui.ActionButtonsGroupView
import com.example.democurrency.presentation.ui.StateEvent
import com.example.feature.currencyinfo.presentation.dialog.AddCurrencyDialog
import com.example.feature.currencyinfo.presentation.ui.LoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val groupDataState by mainViewModel.groupsFlow.collectAsState()
            val loadingState by mainViewModel.stateEventFlow.collectAsState(initial = null)
            var showAddDialog by remember { mutableStateOf(false) }

            ActionButtonsGroupView(
                currencyGroupUiModels = groupDataState,
                onInitDb = {
                    mainViewModel.initDb()
                },
                onClearDb = {
                    mainViewModel.clearDb()
                },
                onInsertDb = {
                    showAddDialog = true
                },
                onGroupClick = { groupId ->
                    mainViewModel.navigateAction(MainAction.ShowListAction(groupId))
                },
                onDisplayAlls = {
                    mainViewModel.navigateAction(MainAction.ShowAllAction)
                },
            )

            if (showAddDialog) {
                AddCurrencyDialog(
                    currencyGroupUiModels = groupDataState,
                    onSave = { groupId, currencyInfoUiModel ->
                        mainViewModel.addCurrencyInfo(groupId, currencyInfoUiModel)
                        showAddDialog = false
                    },
                    onDismiss = { showAddDialog = false },
                )
            }

            if (loadingState is StateEvent.Loading) {
                LoadingView()
            }
        }
    }
}
