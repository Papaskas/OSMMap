package com.cryptoemergency.cryptoemergency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoemergency.cryptoemergency.api.store.ProtoStore
import com.cryptoemergency.cryptoemergency.providers.theme.currentTheme
import com.cryptoemergency.cryptoemergency.repository.store.data.CurrentTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeProtoStore: ProtoStore<CurrentTheme>,
) : ViewModel() {

    suspend fun getThemeFromStorage(): CurrentTheme {
        return themeProtoStore.get()
    }

    fun toggleTheme() {
        currentTheme = when (currentTheme) {
            CurrentTheme.DARK -> CurrentTheme.LIGHT
            CurrentTheme.LIGHT -> CurrentTheme.DARK
            CurrentTheme.NULL -> CurrentTheme.LIGHT
        }

        viewModelScope.launch(Dispatchers.IO) {
            themeProtoStore.put(currentTheme)
        }
    }

    fun changeTheme(theme: CurrentTheme) {
        currentTheme = theme

        viewModelScope.launch(Dispatchers.IO) {
            themeProtoStore.put(currentTheme)
        }
    }
}
