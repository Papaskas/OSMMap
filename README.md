# <a id="urls">Полезные ссылки</a>

> [Dagger hilt - внедрение зависимостей](https://dagger.dev/hilt/)<br/>
> [Detekt - линтер](https://plugins.jetbrains.com/plugin/10761-detekt)<br/>
> [Безопасная маршрутизация](https://developer.android.com/guide/navigation/design/type-safety)<br/>
> [Store, ProtoStore - локальное хранилище](https://developer.android.com/topic/libraries/architecture/datastore)<br/>
> [SvgToCompose](https://www.composables.com/svgtocompose)<br/>
> [Room - БД](https://developer.android.com/jetpack/androidx/releases/room)

# Lint

> В проекте установлен `detekt` - `app/config/detekt/detekt.yml`<br/>
> Для его инициализации в android studio нужно скачать плагин [detekt](#urls)<br/>
> Его активация описана в описании плагина

# Логирование

> В `release` сборке логирования - нет.<br/>
> `Log.*`, `print` и `println` - в release сборке убираются в конфиге `proguard-rules.pro`<br/>
> Логирование сетевых запросов убирается проверкой `BuildConfig.DEBUG` в файле `api/network/HttpClient`

# Locale

> Все локале определены в стнадратном для андроид `xml`</br>
> Для добавления новых локалей необходимо добавить не только `xml`, но и в `gradle.android.defaultConfig.resourceConfigurations`</br>
> Язык по умолчанию обязательно [английский](https://habr.com/ru/companies/alconost/articles/581926/)

# Тема, цвета, значения, иконки к темам, типография

> Все файлы находятся в папке `providers/theme/Theme`<br/>
> По умолчанию стоит темная тема.<br/>
> Использование цветовой схемы:
>```kotlin
>  Text(color = Theme.colors.primary)
>```

# Routing

> Используются типобезопасные маршруты, смотри [официальную документацию](#urls)<br/>
> Основной и единственный файл роутинга - `navigation/Navigation`<br/>
> Для контроля роутинга необходимо пользоваться методом `LocalNavController.current` из
  `providers/LocalNavController`<br/>
> Для перехода по страницам нужно использовать не строки, а импортировать обьект Routes из
  `navigation/Routes`<br/>

# LocalStorage

### Для локального хранения используются две [библиотеки](#urls) - `DataStore` (ключ: значение) и `ProtoDataStore` (`data class`, `enum class`)

> Эти библиотеки интегрированы в проект с помощью [hilt](#urls) >> `module/(Proto)Store.kt`<br/>
> Они работают в IO потоке

---

### Как они работают в целом

> В проекте есть конструторы доступа к хранилищю `api/store/*.kt` - их трогать никогда не надо<br/>
> На вход они ожидают контекст и ключ доступа (обычный дата класс с заранее определенными параметрами)<br/>
> Смотря на этот ключ код будет определять куда смотреть и что отдавать

---

### Что такое ключ

> Ключ - представление в виде ключей, используемых для хранения и извлечения данных в хранилище.<br/>
> Каждый ключ связан с определенным типом данных, значением по умолчанию и сериализатором (Сериализатор только для `ProtoStore`).

> Ключи находятся в `repository/store/(Proto)Keys.kt`<br/>
> В этой папке есть файл `GenericSerializer` - нужен только для сериализации Proto данных (`data class`, `enum class`)<br/>
> Также есть папка `data` - интерфейсы хранимых данных в `ProtoStore`<br/>

---

#### Пример ключа в случае обычного `Store`(ключ: значение) - дополнительная документация о нем описана в JavaDoc в `store/Keys.kt`<br/>

```kotlin
// Коллекция обычных ключей
sealed class Keys<T>( 
    val key: Preferences.Key<T>,
    val defaultValue: T,
) {
    data object TOKEN : Keys<String>(
        key = stringPreferencesKey("TOKEN"), // Имя ключа хранимого в файле
        defaultValue = "", // Значение по умолчанию
    ) 
}
```
---

#### Пример ключа в случае `ProtoStore`(`data class`, `enum class`) - дополнительная документация о нем описана в JavaDoc в `store/ProtoKeys.kt`

```kotlin
sealed class ProtoKeys<T>(
  val key: Preferences.Key<String>,
  val defaultValue: T,
  val serializer: Serializer<T>,
) {
  data object USER : ProtoKeys<User>(
    key = stringPreferencesKey("user"),
    defaultValue = User(),
    serializer = GenericSerializer(serializer<User>(), User()), // Сериализатор прописывается один раз
  )

  data object THEME : ProtoKeys<CurrentTheme>(
    key = stringPreferencesKey("theme"),
    defaultValue = CurrentTheme.NULL,
    serializer = GenericSerializer(serializer<CurrentTheme>(), CurrentTheme.NULL),
  )
}

@kotlinx.serialization.Serializable
enum class CurrentTheme {
  DARK,
  LIGHT,
  NULL,
}

@kotlinx.serialization.Serializable
data class User(
  val id: String = "",
  val username: String = "",
  val surname: String = "",
  val email: String = "",
)
```

---

### Использование

1. В `HiltViewModel` получить нужный конструктор (`Store`, `ProtoStore`)
2. Использовать методы хранилища
```kotlin
@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeProtoStore: ProtoStore<CurrentTheme>, // 1 Получил экземляр хранилища, context и ключ ему передал hilt, о работе hilt смотри оф. доку
    @TokenStore private val tokenStore: Store<String>, // 1 Назначение аннотации описано в оф. документации hilt
) : ViewModel() {
    
    fun fetchTheme() {
        viewModelScope.launch {
            val currentTheme: CurrentTheme = themeProtoStore.get() // 2 Использование встроенных методов 
        }
    }
}
```

---

### Создание

> Для создания новых данных хранилища нужно сделать следующее<br/>
>> 1. Создать ключ в `(Proto)Keys.kt`<br/>
>> 2. Добавить новый ключ в `module/(Proto)StoreModule.kt`, согласно документации [Hilt](#urls)

---

# Бд

> В качестве базы данных предустановлен [Room](#urls) интегрированный в [Hilt](#urls)

# Сетевые запросы

> Основной конструктор запросов: `api/network/CreateRequest.kt` - его трогать нельзя. От него следует
  создать другие запросы<br/>
> Интерфейс полей ответа находится в `api/network/ApiResponse`<br/>

---

#### Создание запроса:

> В папке `repository/requests` создать файл с функцией такого типа:

```kotlin
@kotlinx.serialization.Serializable
data class SuccessResponse( // Возвращаемый тип в случае успеха
  val message: String
)

@kotlinx.serialization.Serializable
data class ErrorResponse( // Возвращаемый тип в случае ошибки
  val message: String
)

@kotlinx.serialization.Serializable
data class Request( // Тело запроса
  val _id: String
)

suspend fun getCurrentNews(context: Context, _id: String) =
  client.createRequest<SuccessResponse, ErrorResponse>(
    path = "api/news",
    method = HttpMethod.Post,
    body = Request(_id), // Необязательный аргумент
    context = context,
  )

//--- Обращение к этой фукнции ---//
viewModelScope.launch() {
  val res = getCurrentNews(
    context = context,
    _id = "id"
  )

  if (res is ApiResponse.Success) {
    val success: SuccessResponse = res.data
  } else if (res is ApiResponse.Error) {
    when (res.errorCode) {
      400, 401 -> {
        val error: ErrorResponse = res.errorData
      }
      500 -> {
        val error: ErrorResponse = res.errorData
      }
      else -> {
        val error: ErrorResponse = res.errorData
      }
    }
  }
}
```

# Разное

> В хранилище Bitrix24 находятся файлы signingConfigs 
