package com.example.diceroller.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiceRollerViewModel() : ViewModel() {
    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean>
        get() = _isDarkModeEnabled

    fun setDarkModeEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            _isDarkModeEnabled.value = isEnabled
        }
    }
}

