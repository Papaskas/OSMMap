package com.cryptoemergency.cryptoemergency.navigation

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Закрытый класс, представляющий различные маршруты в приложении. Этот класс гарантирует,
 * что в нем определены все возможные маршруты, обеспечивая безопасность типов.
 * Внутри определяются интрефейсы для блокировки редиректа на них.
 *
 * @Keep В release сборке имена классов минифицируется, Keep игнорит это. Исправить когда для
 * безопасных маршрутов сделают нормальное апи для справнения маршрутов
 **/
@Keep
@Serializable
sealed class Destination {

    @Keep
    interface Home {
        @Keep
        @Serializable
        data object Map : Destination()
    }
}
