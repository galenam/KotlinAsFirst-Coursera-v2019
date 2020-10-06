@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.math.sign

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val data = str.split(" ")
    if (data.size != 3) return ""
    val day = data[0].toIntOrNull() ?: return ""
    val monthStr = data[1]
    var monthDays = monthes[monthStr] ?: return ""
    val month = monthes.keys.toList().indexOf(monthStr) + 1
    val year = data[2].toIntOrNull() ?: return ""
    if (month == 2 && year % 4 == 0) monthDays += 1
    if (day < 1 || day > monthDays) return ""
    return String.format("%02d.%02d.%02d", day, month, year)
}

val monthes = mapOf(
    "января" to 31,
    "февраля" to 28,
    "марта" to 31,
    "апреля" to 30,
    "мая" to 31,
    "июня" to 30,
    "июля" to 31,
    "августа" to 31,
    "сентября" to 30,
    "октября" to 31,
    "ноября" to 30,
    "декабря" to 31
)

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val data = digital.split(".")
    if (data.size != 3) return ""

    val day = data[0].toIntOrNull() ?: return ""
    if (day < 1 || day > 31) return ""

    val monthInt = data[1].toIntOrNull() ?: return ""
    if (monthInt < 1 || monthInt > 12) return ""

    val year = data[2].toIntOrNull() ?: return ""
    val monthesKeys = monthes.keys.toList()
    if (monthesKeys.size > monthInt - 1) {
        var daysInCurrentMonth = monthes[monthesKeys[monthInt - 1]] ?: return ""
        if (year % 4 == 0 && monthInt == 2) {
            daysInCurrentMonth++
        }
        if (day > daysInCurrentMonth) return ""
    } else {
        return ""
    }
    return String.format("%s %s %s", day, monthesKeys[monthInt - 1], year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val sBuilder = StringBuilder()
    var i = 0
    while (i < phone.length) {
        if (phone[i] == '+') {
            if (i == 0) {
                sBuilder.append(phone[i])
                i++
                continue
            } else {
                return ""
            }
        }

        if (!isOkInnerPhoneString(phone[i], sBuilder)) {
            return ""
        }

        if (phone[i] == '(') {
            var j = i + 1
            val correctLength = sBuilder.length
            while (j < phone.length) {
                if (!isOkInnerPhoneString(phone[j], sBuilder)) {
                    return ""
                }
                if (phone[j] == ')') {
                    if (correctLength - sBuilder.length == 0) {
                        return ""
                    } else {
                        break
                    }
                }
                j++
            }
            i = j
        }
        i++
    }
    return sBuilder.toString()
}

fun isOkInnerPhoneString(ch: Char, sBuilder: StringBuilder): Boolean {
    if (Regex("\\d").containsMatchIn(ch.toString())) {
        sBuilder.append(ch)
        return true
    }
    if (ch == ' ' || ch == '-') {
        return true
    }
    if (ch != '(' && ch != ')') {
        return false
    }
    return true
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var i = 0
    val digitInStrings = mutableListOf<String>()
    while (i < jumps.length) {
        if (jumps[i] != ' ' && jumps[i] != '%' && jumps[i] != '-' && (jumps[i] < '0' || jumps[i] > '9')) {
            return -1
        }
        if (jumps[i] in '0'..'9') {
            val sBuilder = StringBuilder()
            while (i < jumps.length && jumps[i] in '0'..'9') {
                sBuilder.append(jumps[i])
                i++
            }
            digitInStrings.add(sBuilder.toString())
        } else {
            i++
        }
    }
    var max = -1
    for (digitStr in digitInStrings) {
        val value = digitStr.toInt()
        if (max < value) max = value
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var i = 0
    val digitInStrings = mutableListOf<String>()
    while (i < jumps.length) {
        if (jumps[i] != ' ' && jumps[i] != '%' && jumps[i] != '-' && jumps[i] != '+' && (jumps[i] < '0' || jumps[i] > '9')) {
            return -1
        }
        if (jumps[i] in '0'..'9') {
            val sBuilder = StringBuilder()
            while (i < jumps.length && jumps[i] in '0'..'9') {
                sBuilder.append(jumps[i])
                i++
            }
            while (i < jumps.length) {
                if (jumps[i] == '%') {
                    i++
                    break
                }
                if (jumps[i] == '+') {
                    digitInStrings.add(sBuilder.toString())
                    i++
                    break
                }
                i++
            }
        } else {
            i++
        }
    }
    var max = -1
    for (digitStr in digitInStrings) {
        val value = digitStr.toInt()
        if (max < value) max = value
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val data = expression.split(" ")
    if (data.isEmpty()) {
        throw IllegalArgumentException()
    }
    if (!Regex("^\\d+$").containsMatchIn(data[0])) {
        throw IllegalArgumentException()
    }
    var result = 0
    var sign = true
    for (i in data.indices) {
        if (i % 2 == 1) {
            sign = when {
                data[i] == "+" -> {
                    true
                }
                data[i] == "-" -> {
                    false
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        } else {
            if (!Regex("^\\d+$").containsMatchIn(data[i])) {
                throw IllegalArgumentException()
            }
            val value = data[i].toIntOrNull() ?: throw IllegalArgumentException()
            if (sign) {
                result += value
            } else {
                result -= value
            }
        }
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var i = 0
    val map = mutableMapOf<String, Int>()
    while (i < str.length) {
        val sBuilder = StringBuilder()
        while (i < str.length && str[i] != ' ') {
            sBuilder.append(str[i])
            i++
        }
        i++
        val data = sBuilder.toString().toLowerCase()
        if (map.containsKey(data)) {
            val index = map[data]!!
            if (i - 1 - data.length == index + 1 + data.length) {
                return map[data]!!
            }
        }
        map[data] = i - 1 - data.length
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val data = description.split(";")
    val map = mutableMapOf<String, Double>()
    for (namePrice in data) {
        var pair = namePrice.trim().split(" ")
        if (pair.isEmpty() || pair.size != 2) {
            return ""
        }
        map[pair[0]] = pair[1].toDoubleOrNull() ?: return ""
    }
    return map.maxBy{ it.value }?.key ?: ""
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val map = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)
    var i = 0
    var result = 0
    while (i < roman.length) {
        val cur = map[roman[i]] ?: return -1
        if (i + 1 < roman.length) {
            val next = map[roman[i + 1]] ?: return -1
            if (cur < next) {
                result -= cur
            } else {
                result += cur
            }
        } else {
            result += cur
        }
        i++
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var isOpen = 0
    for (ch in commands) {
        if (ch != '>' && ch != '<' && ch != '>' && ch != '+' && ch != '-' && ch != '[' && ch != ']' && ch != ' ') {
            throw IllegalArgumentException()
        }
        if (ch == ']') {
            if (isOpen > 0) {
                isOpen--
            } else {
                throw IllegalArgumentException()
            }
        }
        if (ch == '[') {
            isOpen++
        }
    }
    if (isOpen != 0) {
        throw IllegalArgumentException()
    }
    val result = MutableList(cells) { 0 }
    var i = cells / 2
    var j = 0
    var countCommands = 0
    while (j < commands.length && countCommands < limit) {
        if (i >= result.size || i < 0) {
            throw IllegalStateException()
        }
        countCommands++

        if (commands[j] == ' ') {
            j++
            continue
        }

        if (commands[j] == '>') {
            i++
            j++
            continue
        }

        if (commands[j] == '<') {
            i--
            j++
            continue
        }

        if (commands[j] == '+') {
            result[i]++
            j++
            continue
        }

        if (commands[j] == '-') {
            result[i]--
            j++
            continue
        }

        if (commands[j] == '[') {
            if (result[i] == 0) {
                var nesting = 0
                while (j < commands.length) {
                    j++
                    if (commands[j] == '[') {
                        nesting++
                    }
                    if (commands[j] == ']') {
                        if (nesting > 0) {
                            nesting--
                        } else {
                            j++
                            break
                        }
                    }
                }
                continue
            } else {
                j++
                continue
            }
        }

        if (commands[j] == ']') {
            if (result[i] != 0) {
                var nesting = 0
                while (j >= 0) {
                    j--
                    if (commands[j] == ']') {
                        nesting++
                    }
                    if (commands[j] == '[') {
                        if (nesting > 0) {
                            nesting--
                        } else {
                            j++
                            break
                        }
                    }
                }
                continue
            } else {
                j++
                continue
            }
        }
    }
    return result
}
