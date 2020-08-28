@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import java.lang.StringBuilder
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.text.repeat

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt((v.fold(0.0, { previous, next -> previous + next * next })))

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    if (list.count() == 0) return 0.0
    return list.fold(0.0, { previous, next -> previous + next }) / list.count()
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mean = mean(list)
    for (i in 0 until list.count()) {
        list[i] -= mean
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    if (a.count() == 0 || b.count() == 0) return 0
    val c = a.zip(b) { v1, v2 -> v1 * v2 }
    return c.fold(0, { prev, next -> prev + next })
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isEmpty()) return 0
    return p.foldIndexed(
        0,
        { index, previous, element -> previous + element * x.toDouble().pow((index).toDouble()).toInt() })
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isEmpty()) return list
    var sum = 0
    for (i in list.indices) {
        sum += list[i]
        list[i] = sum
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val multipliers = mutableListOf<Int>()
    var value = n

    value = getValAndMultipliers(value, multipliers, 2)

    for (i in 2..sqrt(n.toDouble()).toInt() + 1) {
        if (i % 2 == 0) continue
        value = getValAndMultipliers(value, multipliers, i)
    }
    if (multipliers.isEmpty()) multipliers.add(n)
    return multipliers.toList()
}

fun getValAndMultipliers(n: Int, multipliers: MutableList<Int>, divisor: Int): Int {
    var value = n
    while (value % divisor == 0) {
        value /= divisor
        multipliers.add(divisor)
    }
    return value

}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val mList = factorize(n)
    if (mList.size == 1) return mList[0].toString()
    return mList.joinToString("*")
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var value = n
    val mList = mutableListOf<Int>()
    while (value / base > 0) {
        val reminder = value % base
        mList.add(reminder)
        value /= base
    }
    if (value % base > 0) {
        mList.add(value)
    }
    return mList.asReversed().toList()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val list = convert(n, base)
    return list.fold(
        "",
        { previous, element -> if (element < 10) previous + element else previous + ('a' + (element - 10)).toString() })
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = digits.foldIndexed(
    0,
    { index, previous, next -> previous + next * base.toDouble().pow(digits.size - index - 1).toInt() })


/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val decimal =
        str.toCharArray().map { element ->
            if (element < 'a') (element - '0') else (element - 'a' + 10)
        }
    return decimal(decimal, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val ints = n.toString().toCharArray().map { it - '0' }
    var power = ints.size - 1
    var value = n
    val sb = StringBuilder()
    for (i in ints.indices) {
        sb.append(getRomanDigit(ints[i], Rank.values().first { it.value == ints.size - i }))
        value -= ints[i] * 10.toDouble().pow(power).toInt()
        power--
        if (value == 0) break
    }
    return sb.toString()
}

enum class Rank(val value: Int) {
    UNITY(1), TEN(2), HUNDRED(3), THOUSAND(4),
    TENTHOUSAND(5), HUNDREDTHOUSAND(6)
}

fun getRomanDigit(value: Int, rank: Rank): String =
    when {
        rank == Rank.THOUSAND -> "M".repeat(value)
        rank == Rank.HUNDRED && value == 9 -> "CM"
        rank == Rank.HUNDRED && value >= 5 && value < 9 -> "D" + "C".repeat(value - 5)
        rank == Rank.HUNDRED && value == 4 -> "CD"
        rank == Rank.HUNDRED && value >= 1 && value < 4 -> "C".repeat(value)
        rank == Rank.TEN && value == 9 -> "XC"
        rank == Rank.TEN && value >= 5 && value < 8 -> "L" + "X".repeat(value - 5)
        rank == Rank.TEN && value == 4 -> "XL"
        rank == Rank.TEN && value >= 1 && value < 4 -> "X".repeat(value)
        rank == Rank.UNITY && value == 9 -> "IX"
        rank == Rank.UNITY && value >= 5 && value < 9 -> "V" + "I".repeat(value - 5)
        rank == Rank.UNITY && value == 4 -> "IV"
        rank == Rank.UNITY && value >= 1 && value < 4 -> "I".repeat(value)
        else -> "0"
    }


/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val ints = n.toString().toCharArray().map { it - '0' }
    val sb = StringBuilder()
    for (i in ints.indices) {
        val rank = Rank.values().first { it.value == ints.size - i }
        val previousSymbol = if (i == 0) -1 else ints[i - 1]
        if ((rank == Rank.THOUSAND || rank == Rank.UNITY) && previousSymbol == 1) {
            continue
        }
        val nextSymbol = if (i + 1 == ints.size) -1 else ints[i + 1]
        val symbol = getRussianDigit(ints[i], nextSymbol, rank)
        if (symbol.isEmpty()) {
            continue
        }
        sb.append("$symbol ")
    }
    return sb.toString().trim()
}

fun getRussianDigit(value: Int, nextSymbol: Int, rank: Rank): String {
    var res = when {
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 9 -> "девятьсот"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 8 -> "восемьсот"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 7 -> "семьсот"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 6 -> "шестьсот"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 5 -> "пятьсот"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 4 -> "четыреста"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 3 -> "триста"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 2 -> "двести"
        (rank == Rank.HUNDREDTHOUSAND || rank == Rank.HUNDRED) && value == 1 -> "сто"

        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 9 -> "девяносто"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 8 -> "восемьдесят"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 7 -> "семьдесят"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 6 -> "шестьдесят"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 5 -> "пятьдесят"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 4 -> "сорок"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 3 -> "тридцать"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 2 -> "двадцать"
        (rank == Rank.TENTHOUSAND || rank == Rank.TEN) && value == 1 -> getNear10(nextSymbol)

        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 9 -> "девять"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 8 -> "восемь"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 7 -> "семь"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 6 -> "шесть"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 5 -> "пять"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 4 -> "четыре"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 3 -> "три"
        rank == Rank.THOUSAND && value == 2 -> "две"
        (rank == Rank.THOUSAND || rank == Rank.UNITY) && value == 1 -> "один"

        (rank == Rank.UNITY) && value == 2 -> "два"

        else -> ""
    }
    res += when {
        (rank == Rank.THOUSAND && value == 0) -> "тысяч"
        (rank == Rank.THOUSAND && (value in 5..9)) || (rank == Rank.TENTHOUSAND && value == 1) -> " тысяч"
        rank == Rank.THOUSAND && value <= 4 && value >= 2 -> " тысячи"
        rank == Rank.THOUSAND && value == 1 -> " тысяча"
        else -> ""
    }
    return res
}

fun getNear10(nextSymbol: Int): String =
    when (nextSymbol) {
        0 -> "десять"
        1 -> "одинадцать"
        2 -> "двенадцать"
        3 -> "тринадцать"
        4 -> "четырнадцать"
        5 -> "пятьнадцать"
        6 -> "шестнадацать"
        7 -> "семнадцать"
        8 -> "восемнадцать"
        else -> "девятнадцать"
    }
