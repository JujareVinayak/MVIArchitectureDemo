package com.vinayak.mviarchitecturedemo.util

import com.vinayak.mviarchitecturedemo.base.UiEffect
import com.vinayak.mviarchitecturedemo.base.UiEvent
import com.vinayak.mviarchitecturedemo.base.UiState

/**
 * Contract of [MainActivity]
 */
class MainContract {

    sealed class Event : UiEvent {
        data object OnRandomNumberClicked : Event()
        data object OnShowToastClicked : Event()
    }

    data class State(
        val randomNumberState: RandomNumberState
    ) : UiState

    sealed class RandomNumberState {
        data object Idle : RandomNumberState()
        data object Loading : RandomNumberState()
        data class Success(val number : Int) : RandomNumberState()
    }

    sealed class Effect : UiEffect {

        data object ShowToast : Effect()

    }

}