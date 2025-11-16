package com.example.currencyinfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currencyinfo.presentation.CurrencyInfoFragment.Companion.TAG
import com.example.currencyinfo.presentation.ui.CurrencyListView
import com.example.currencyinfo.presentation.ui.CurrencySearchBar
import com.example.mediator.currencyinfo.presentation.CurrencyInfoFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyInfoFragment : Fragment() {

    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val currencies by viewModel.currenciesFlow.collectAsState()
                val groupInfo by viewModel.groupInfoFlow.collectAsState()
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CurrencySearchBar(
                        groupName = groupInfo?.groupName,
                        onSearchKeywordChanged = { keyword ->
                            viewModel.searchCurrencies(keyword)
                        },
                        onBack = {
                            activity?.onBackPressedDispatcher?.onBackPressed()
                        },
                        onClear = {
                            viewModel.clearSearch()
                        }
                    )
                    CurrencyListView(currencies)
                }
            }
        }
    }

    companion object {
        const val TAG = "CurrencyInfoFragment"
        const val ARG_GROUP_ID = "ARG_GROUP_ID"

        fun newInstance(groupId: Int?) = CurrencyInfoFragment().apply {
            arguments = bundleOf(ARG_GROUP_ID to groupId)
        }
    }
}

class CurrencyInfoFragmentFactoryImpl @Inject constructor() : CurrencyInfoFragmentFactory {

    override val tag: String = TAG

    override fun createFragment(groupId: Int?): Fragment {
        return CurrencyInfoFragment.newInstance(groupId)
    }
}