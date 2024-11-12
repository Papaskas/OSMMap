package com.cryptoemergency.cryptoemergency.providers.localNavController

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("No localNavController provided") }

/**
 * @return Возвращает класс типа: package com.cryptoemergency.cryptoemergency.navigation.Routes.Home.Home
 *
 * Пример использования есть в ui/common/bottomBar
 * */
@Composable
fun getCurrentRoute(): String? {
    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun NavControllerProvider(content: @Composable () -> Unit) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        content()
    }
}
