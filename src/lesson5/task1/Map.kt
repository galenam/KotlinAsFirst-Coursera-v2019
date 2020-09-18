@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import java.util.*
import kotlin.math.max

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val res = mutableMapOf<Int, MutableList<String>>()
    for ((key, value) in grades) {
        if (res.containsKey(value)) {
            val names = res[value]
            names!!.add(key)
            res[value] = names
        } else {
            res[value] = mutableListOf(key)
        }
    }
    return res
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, _) in a) {
        if (!b.containsKey(key) || b[key] != a[key]) {
            return false
        }
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    val keys = mutableListOf<String>()
    for ((key, _) in b) {
        if (a.containsKey(key) && a[key] == b[key]) {
            keys.add(key)
        }
    }
    for (key in keys) {
        a.remove(key)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val list = mutableSetOf<String>()
    for (key in a) {
        if (b.contains(key)) {
            list.add(key)
        }
    }
    return list.toList()
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for ((key, _) in mapA) {
        if (mapB.containsKey(key) && mapA[key] != mapB[key]) {
            result[key] = mapA[key] + ", " + mapB[key]
        } else {
            result[key] = mapA[key]!!
        }
    }
    val diff = mapB - mapA.keys
    for ((key, value) in diff) {
        result.put(key, value)
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val map = mutableMapOf<String, Pair<Double, Int>>()
    for ((key, value) in stockPrices) {
        if (map.containsKey(key)) {
            val pair = map[key]!!
            map[key] = Pair(pair.first + value, pair.second + 1)
        } else {
            map[key] = Pair(value, 1)
        }
    }
    val result = mutableMapOf<String, Double>()
    for ((key, value) in map) {
        result[key] = value.first / value.second
    }
    return result
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? =
    stuff.filter { element -> element.value.first == kind }
        .minBy { element -> element.value.second }
        ?.key


/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val setWord = mutableSetOf<Char>()
    for (symbol in word) {
        setWord.add(symbol)
    }
    val setChars = mutableSetOf<Char>()
    for (symbol in chars) {
        setChars.add(symbol)
    }
    return (setWord - setChars).isEmpty()
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    for (str in list) {
        if (map.containsKey(str)) {
            val value = map[str]
            map[str] = value!! + 1
        } else {
            map[str] = 1
        }
    }
    val result = mutableMapOf<String, Int>()
    for ((key, value) in map) {
        if (value > 1) {
            result[key] = value
        }
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val structure = mutableListOf<SortedMap<Char, Int>>()
    var result = false
    for (word in words) {
        val map = mutableMapOf<Char, Int>()
        for (symbol in word) {
            var count = 1
            if (map.containsKey(symbol)) {
                count = map[symbol]!! + 1
            }
            map[symbol] = count
        }
        structure.add(map.toSortedMap())
    }
    for (i in structure.indices) {
        for (j in i + 1 until structure.size) {
            if (structure[i].size != structure[j].size) {
                continue
            }
            var isOk = true
            for ((key, value) in structure[i]) {
                if (!structure[j].containsKey(key)) {
                    isOk = false
                    break
                }
                if (structure[j][key]!! != value) {
                    isOk = false
                    break
                }
            }
            if (isOk) {
                result = true
            }
        }
    }
    return result
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val fullFriendsSet = mutableSetOf<String>()
    for ((name, friendSet) in friends) {
        fullFriendsSet.add(name)
        fullFriendsSet.addAll(friendSet)
    }
    val result = mutableMapOf<String, Set<String>>()
    for (name in fullFriendsSet) {
        val value = friends[name]
        if (value == null) {
            result[name] = setOf()
            continue
        }
        val addedFriends = value.toMutableSet()
        val intermediate = recursionNames(value, friends, addedFriends)
        addedFriends.addAll(intermediate - name)
        result[name] = addedFriends
    }
    return result
}

fun recursionNames(
    value: Set<String>,
    friends: Map<String, Set<String>>,
    addedFriends: MutableSet<String>
): MutableSet<String> {
    val intermediate = mutableSetOf<String>()
    for (name in value) {
        if (friends.containsKey(name)) {
            intermediate.addAll(friends.getValue(name).toMutableSet())
        }
        val diff = intermediate - addedFriends
        if (diff.isNotEmpty()) {
            intermediate.addAll(recursionNames(diff, friends, addedFriends))
        }
    }
    return intermediate
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    for (i in list.indices) {
        map[list[i]] = i
    }
    for ((key, value) in map) {
        if (number - key != key && map.containsKey(number - key)) {
            return Pair(value, map[number - key]!!)
        }
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val result = mutableListOf<MutableList<Pair<Int, MutableSet<String>>>>()
    val zeroLine = mutableListOf<Pair<Int, MutableSet<String>>>()
    for (j in 0 until capacity) {
        zeroLine.add(0 to mutableSetOf())
    }
    result.add(zeroLine)
    for (i in 1..treasures.size) {
        result.add(mutableListOf())
    }
    var i = 1
    for ((key, value) in treasures) {
        for (j in 0 until capacity) {
            if (value.first > j) {
                result[i].add(result[i - 1][j])
            } else {
                val set = result[i - 1][j].second
                val weightPrevWithoutThis = result[i - 1][j].first
                val weightThisWithoutPrev = result[i - 1][j - value.first].first + value.second
                if (weightPrevWithoutThis >= weightThisWithoutPrev) {
                    result[i][j] = Pair(weightPrevWithoutThis, set)
                } else {
                    set.add(key)
                    result[i].add(Pair(weightThisWithoutPrev, set))
                }
            }
        }
        i++
    }
    var max = result[0][0].first
    val length = result.size - 1
    var maxIndex = 0
    for (k in 0 until result[length].size) {
        if (result[length][k].first > max) {
            max = result[length][k].first
            maxIndex = k
        }
    }
    return result[length][maxIndex].second
}
