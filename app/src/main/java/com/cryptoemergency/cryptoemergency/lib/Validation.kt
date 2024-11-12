package com.cryptoemergency.cryptoemergency.lib

/**
 * Функция для валидации текста с использованием нескольких валидаторов.
 *
 * @param text Текст для валидации.
 * @param successMessage Сообщение об успешной валидации.
 * @param validators Набор валидаторов для проверки текста.
 * @return [Return], содержащий результат валидации.
 */
fun validation(
    text: String,
    successMessage: String,
    validators: Array<Validate>,
): Return {
    for (validator in validators) {
        if (!text.contains(validator.pattern)) {
            return Return(
                isError = true,
                errorMessage = validator.errorMessage,
                successMessage = null,
            )
        }
    }

    return Return(
        isError = false,
        errorMessage = null,
        successMessage = successMessage,
    )
}

/**
 * Класс для хранения результата валидации.
 *
 * @param isError Флаг, указывающий на наличие ошибки.
 * @param errorMessage Сообщение об ошибке, если есть.
 * @param successMessage Сообщение об успешной валидации, если есть.
 */
data class Return(
    val isError: Boolean,
    val errorMessage: String?,
    val successMessage: String?,
)

/**
 * Класс для хранения валидатора.
 *
 * @param pattern Регулярное выражение для валидации.
 * @param errorMessage Сообщение об ошибке, если валидация не пройдена.
 */
data class Validate(
    val pattern: Regex,
    val errorMessage: String,
)

/**
 * Объект, содержащий предопределенные валидаторы.
 */
object ValidatePattern {
    val isEmail = Validate(
        pattern = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)"),
        errorMessage = "Некорректная почта",
    )

    val hasUppercase = Validate(
        Regex("[A-Z]"),
        "Необходима хотя бы одна заглавная буква"
    )

    val hasLowercase = Validate(
        Regex("[a-z]"),
        "Необходима хотя бы одна строчная буква"
    )

    val hasDigit = Validate(
        Regex("\\d"),
        "Необходима хотя бы одна цифра"
    )

    val hasSpecialChar = Validate(
        Regex("[!@#\$%^&+=]"),
        "Необходим хотя бы один спец символ"
    )

    val onlyLetter = Validate(
        Regex("^[a-zA-Zа-яА-Я]*\$"),
        ""
    )

    val onlyNumber = Validate(
        Regex("^[0-9]+[0-9]*\$"),
        "Допустимы только цифры"
    )

    val isPhoneNumber = Validate(
        Regex("^+?((d{2,3}) ?d|d)(([ -]?d)|( ?(d{2,3}) ?)){5,12}d$"),
        ""
    )

    val isFullName = Validate(
        Regex("^[а-яА-ЯёЁa-zA-Z]+ [а-яА-ЯёЁa-zA-Z]+ ?[а-яА-ЯёЁa-zA-Z]+$"),
        ""
    )

    val isDomainName = Validate(
        Regex("^([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?.)+[a-zA-Z]{2,6}$"),
        ""
    )

    val isURL = Validate(
        Regex("(https?):((//)|(\\\\))+[wd:#@%/;$()~_?+-=.&]*"),
        ""
    )

    val withoutSpaces = Validate(
        Regex("^\\S*\$"),
        "Пробелы недопустимы"
    )

    val notSpecialSymbol = Validate(
        Regex("^[a-zA-Z0-9]*\$"),
        "Спец символы недопустимы"
    )

    val isIPv4 = Validate(
        Regex("((25[0-5]|2[0-4]d|[01]?dd?).){3}(25[0-5]|2[0-4]d|[01]?dd?)"),
        ""
    )

    val isIPv6 = Validate(
        Regex("((^|:)([0-9a-fA-F]{0,4})){1,8}$"),
        ""
    )

    val isUUID = Validate(
        Regex("^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$"),
        ""
    )

    val notEmpty = Validate(
        Regex(".+"),
        "Не должно быть пустым"
    )

    /**
     * Валидатор для проверки равенства строк.
     *
     * @param toText Текст, с которым нужно сравнить.
     * @param errorMessage Сообщение об ошибке, если строки не совпадают.
     * @return [Validate] для проверки равенства строк.
     *
     * @sample [ValidationSamples.isEquals]
     */
    fun isEquals(
        toText: String,
        errorMessage: String = "Строки не совпадают!"
    ) = Validate(
        Regex.escape(toText).toRegex(),
        errorMessage,
    )

    /**
     * Валидатор для проверки длины строки в заданном диапазоне.
     *
     * @param min Минимальная длина строки.
     * @param max Максимальная длина строки.
     * @param errorMessage Сообщение об ошибке, если длина строки не в диапазоне.
     * @return [Validate] для проверки длины строки.
     */
    fun inRange(
        min: Int,
        max: Int,
        errorMessage: String = "Некорректный диапазон символов, необходимо минимум $min до $max"
    ) = Validate(
        Regex("^.{$min,$max}$"),
        errorMessage,
    )
}

/**
 * Примеры использования функции валидации.
 */
private object ValidationSamples {
    /**
     * Пример использования валидатора для проверки равенства строк.
     */
    fun isEquals() {
        validation(
            "Что сравнивать",
            "Одинаковые",
            arrayOf(
                ValidatePattern.isEquals(
                    toText = "С чем сравнивать",
                    errorMessage = "Не совпадают!",
                )
            )
        )
    }


    /**
     * Пример использования валидатора для проверки длины строки в диапазоне.
     */
    fun inRange() {
        validation(
            "Пример текста",
            "В диапазоне",
            arrayOf(
                ValidatePattern.inRange(min = 5, max = 10),
            )
        )
    }
}

/**
 * Набор валидаторов для проверки пароля.
 */
val passwordPatterns = arrayOf(
    ValidatePattern.notEmpty,
    ValidatePattern.withoutSpaces,
    ValidatePattern.hasUppercase,
    ValidatePattern.hasLowercase,
    ValidatePattern.hasDigit,
    ValidatePattern.hasSpecialChar,
    ValidatePattern.inRange(min = 8, max = 25),
)

/**
 * Набор валидаторов для проверки email.
 */
val emailPatterns = arrayOf(
    ValidatePattern.notEmpty,
    ValidatePattern.withoutSpaces,
    ValidatePattern.isEmail,
    ValidatePattern.inRange(min = 7, max = 35)
)

