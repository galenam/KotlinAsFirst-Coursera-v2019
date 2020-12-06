@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.BufferedWriter
import java.io.File
import java.util.*
import kotlin.text.StringBuilder

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val map = substrings.map { it to 0 }.toMap().toMutableMap()
    for (line in File(inputName).readLines()) {
        for ((key) in map) {
            var begin = 0
            do {
                val index = line.indexOf(key, begin, true)
                if (index >= 0) {
                    map[key] = map[key]!! + 1
                    begin = index + 1
                }
            } while (index >= 0)
        }
    }
    return map

}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    for (line in File(inputName).readLines()) {
        var correctLine = line
        val regex = Regex("чя|щя|чю|щю|жы|шы|жю|шю|жя|шя", RegexOption.IGNORE_CASE)
        if (regex.containsMatchIn(line)) {
            correctLine = regex.replace(line) { m ->
                m.value[0] +
                        when (m.value[1]) {
                            'я' -> "а"
                            'Я' -> "А"
                            'ю' -> "у"
                            'Ю' -> "У"
                            'ы' -> "и"
                            'Ы' -> "И"
                            else -> ""
                        }
            }
        }
        outputStream.write(correctLine)
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val sBuilder = StringBuilder()
    var maxLength = 0
    var isBegin = true

    for (line in File(inputName).readLines()) {
        if (isBegin) {
            isBegin = false
        } else {
            sBuilder.appendln()
        }
        val curLength = line.trim().length
        if (curLength > maxLength) {
            maxLength = curLength
        }
        sBuilder.append(line.trim())
    }
    isBegin = true
    val outputStream = File(outputName).bufferedWriter()
    for (line in sBuilder.lines()) {
        if (isBegin) {
            isBegin = false
        } else {
            outputStream.newLine()
        }
        if (line.length < maxLength) {
            val diff = (maxLength - line.length) / 2
            outputStream.write(" ".repeat(diff))
        }
        outputStream.write(line)
    }

    outputStream.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    var maxLength = 0
    val listOfList = mutableListOf<List<String>>()

    for (line in File(inputName).readLines()) {
        val list = line.trim().split(" ")
        val length = list.sumBy { it.length } + list.size - 1
        if (maxLength < length) {
            maxLength = length
        }
        listOfList.add(list)
    }
    val outputStream = File(outputName).bufferedWriter()
    var isBegin = true
    for (list in listOfList) {
        if (isBegin) {
            isBegin = false
        } else {
            outputStream.newLine()
        }
        if (list.isEmpty()) {
            continue
        }

        val length = list.sumBy { it.length }

        val quotient = if (maxLength == length) 1 else if (list.size > 1) (maxLength - length) / (list.size - 1) else 0
        var reminder = if (list.size > 1) (maxLength - length) % (list.size - 1) else 0
        for (i in list.indices) {
            var count = quotient
            if (reminder > 0) {
                reminder--
                count++
            }
            outputStream.write(list[i])
            if (i != list.size - 1) {
                outputStream.write(" ".repeat(count))
            }
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val lowerCaseInput = File(inputName).readText().trim().toLowerCase()
    val regex = Regex("[^а-яёa-z]+")
    val list = lowerCaseInput.split(regex)
    val map = mutableMapOf<String, Int>()
    if (list.isEmpty()) {
        return map
    }
    for (str in list) {
        if (str.isEmpty()) {
            break
        }
        if (map.containsKey(str)) {
            map[str] = map[str]!! + 1
        } else {
            map[str] = 1
        }
    }
    val sortedValues = map.values.sortedDescending().take(20)
    val result = mutableMapOf<String, Int>()
    for (sValue in sortedValues) {
        for ((key, value) in map) {
            if (value == sValue) {
                result[key] = sValue
                map.remove(key)
                break
            }
        }
    }
    return result
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        val sBuilder = StringBuilder()
        for (symbol in line) {
            val value = dictionary[symbol.toLowerCase()] ?: dictionary[symbol.toUpperCase()]
            if (!value.isNullOrEmpty()) {
                if (symbol.isLowerCase()) {
                    sBuilder.append(value.toLowerCase())
                } else {
                    sBuilder.append(value.toLowerCase().capitalize())
                }
            } else {
                sBuilder.append(symbol)
            }
        }
        sBuilder.appendln()
        outputStream.write(sBuilder.toString())
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val dict = mutableMapOf<String, Int>()
    var maxLength = 0
    for (line in File(inputName).readLines()) {
        val map = mutableSetOf<Char>()
        var isMatched = true
        for (symbol in line.toLowerCase()) {
            if (map.contains(symbol)) {
                isMatched = false
                break
            } else {
                map.add(symbol)
            }
        }
        if (isMatched) {
            dict[line] = line.length
            if (maxLength < line.length) {
                maxLength = line.length
            }
        }
    }
    val outputStream = File(outputName).bufferedWriter()
    var isBegin = true
    for ((str) in dict.filter { (_, value) -> value == maxLength }) {
        if (isBegin) {
            isBegin = false
        } else {
            outputStream.append(", ")
        }
        outputStream.append(str)
    }
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val stack = ArrayDeque<String>()

    outputStream.append("<html><body><p>")
    stack.addFirst("</p>")

    for (line in File(inputName).readLines()) {

        if (isNeedAddIndention(line, stack, outputStream)) {
            continue
        }
        innerHtml(line, stack, outputStream)
    }
    appendOutputStream(stack, outputStream)

    outputStream.append("</body></html>")
    outputStream.close()
}

fun editStackStream(stack: ArrayDeque<String>, outputStream: BufferedWriter, symbol: String) {
    if (stack.peekFirst() == "</$symbol>") {
        outputStream.append(stack.removeFirst())
    } else {
        outputStream.append("<$symbol>")
        stack.addFirst("</$symbol>")
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val stack = ArrayDeque<String>()
    val regex = Regex("^\\d+\\.")

    outputStream.append("<html><body>")
    var countOfBlanks = -1

    for (line in File(inputName).readLines()) {

        var currentCountOfBlanks = 0
        if (line.startsWith(" ")) {
            currentCountOfBlanks = line.takeWhile { ch -> ch == ' ' }.length
        }
        val currentLine = line.trim()

        if (currentCountOfBlanks == countOfBlanks) {
            if (currentLine.isEmpty()) {
                continue
            }
            if (!stack.isEmpty()) {
                outputStream.append(stack.removeFirst())
            }
            appendStreamStackFullTag(stack, outputStream, "li", currentLine, regex)
        } else if (currentCountOfBlanks > countOfBlanks) {
            if (currentLine[0] == '*') {
                appendStreamStackTag(stack, outputStream, "ul")
            } else if (regex.containsMatchIn(currentLine)) {
                appendStreamStackTag(stack, outputStream, "ol")
            }
            appendStreamStackFullTag(stack, outputStream, "li", currentLine, regex)
        } else {
            append3StackElement(stack, outputStream)
            appendStreamStackFullTag(stack, outputStream, "li", currentLine, regex)
        }
        countOfBlanks = currentCountOfBlanks

    }

    appendOutputStream(stack, outputStream)

    outputStream.append("</body></html>")
    outputStream.close()
}

fun append3StackElement(stack: ArrayDeque<String>, outputStream: BufferedWriter) {
    var i = 0
    while (!stack.isEmpty()) {
        outputStream.append(stack.removeFirst())
        i++
        if (i == 3) {
            break
        }
    }

}

fun appendStreamStackFullTag(
    stack: ArrayDeque<String>,
    outputStream: BufferedWriter,
    tag: String,
    currentLine: String,
    regex: Regex
) {
    appendStreamStackTag(stack, outputStream, tag, currentLine.replace("* ", "").replace(regex, "").trim())
}


fun appendStreamStackTag(
    stack: ArrayDeque<String>,
    outputStream: BufferedWriter,
    tag: String,
    addition: String = ""
) {
    outputStream.append("<$tag>$addition")
    stack.addFirst("</$tag>")
}


/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val stackIndention = ArrayDeque<String>()
    val stackList = ArrayDeque<String>()
    val regex = Regex("^\\d+\\.")

    outputStream.append("<html><body>")
    var countOfBlanks = -1

    for (line in File(inputName).readLines()) {
        if (isNeedAddIndention(line, stackIndention, outputStream)) {
            continue
        }

        var currentCountOfBlanks = 0
        if (line.startsWith(" ")) {
            currentCountOfBlanks = line.takeWhile { ch -> ch == ' ' }.length
        }
        val currentLine = line.trim()

        if (currentCountOfBlanks == countOfBlanks) {
            if (currentLine.isEmpty()) {
                continue
            }
            if (!stackList.isEmpty()) {
                outputStream.append(stackList.removeFirst())
            }
            appendStreamStackFullTagInnerEdit(stackIndention, stackList, outputStream, "li", currentLine, regex)
        } else if (currentCountOfBlanks > countOfBlanks) {
            when {
                currentLine[0] == '*' -> {
                    appendStreamStackTag(stackList, outputStream, "ul")
                }
                regex.containsMatchIn(currentLine) -> {
                    appendStreamStackTag(stackList, outputStream, "ol")
                }
                else -> {
                    outputStream.append("<p>")
                    stackIndention.addFirst("</p>")
                }
            }
            appendStreamStackFullTagInnerEdit(stackIndention, stackList, outputStream, "li", currentLine, regex)
        } else {
            append3StackElement(stackList, outputStream)
            appendStreamStackFullTagInnerEdit(stackIndention, stackList, outputStream, "li", currentLine, regex)
        }
        countOfBlanks = currentCountOfBlanks

    }
    appendOutputStream(stackList, outputStream)
    appendOutputStream(stackIndention, outputStream)

    outputStream.append("</body></html>")
    outputStream.close()
}

fun appendOutputStream(stack: ArrayDeque<String>, outputStream: BufferedWriter) {
    for (value in stack) {
        outputStream.append(value)
    }
}

fun isNeedAddIndention(line: String, stackIndention: ArrayDeque<String>, outputStream: BufferedWriter): Boolean {
    if (line.isEmpty()) {
        if (stackIndention.peekFirst() == "</p>") {
            outputStream.append(stackIndention.removeFirst())
        }
        outputStream.append("<p>")
        stackIndention.addFirst("</p>")
        return true
    }
    return false
}

fun appendStreamStackFullTagInnerEdit(
    stackIndetion: ArrayDeque<String>,
    stackList: ArrayDeque<String>,
    outputStream: BufferedWriter,
    tag: String,
    currentLine: String,
    regex: Regex
) {
    var line = currentLine
    if (isList(currentLine, regex)) {
        outputStream.append("<$tag>")
        line = line.replace("* ", "").replace(regex, "").trim()
    }
    innerHtml(line, stackIndetion, outputStream)
    if (isList(currentLine, regex)) {
        stackList.addFirst("</$tag>")
    }
}

fun isList(currentLine: String, regex: Regex): Boolean = currentLine[0] == '*' || regex.containsMatchIn(currentLine)

fun innerHtml(line: String, stack: ArrayDeque<String>, outputStream: BufferedWriter) {
    val set = setOf('*', '~')
    var i = 0
    while (i < line.length) {
        if (!set.contains(line[i])) {
            outputStream.append(line[i])
            i++
            continue
        }
        if (line[i] == '*' || line[i] == '~') {
            if (i != line.length) {
                if (line[i + 1] == line[i]) {
                    editStackStream(stack, outputStream, if (line[i] == '*') "b" else "s")
                    i += 2
                    continue
                } else {
                    if (line[i] == '*') {
                        editStackStream(stack, outputStream, "i")
                    } else {
                        outputStream.append(line[i])
                    }
                }
            }
        }
        i++
    }
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val list = mutableListOf<String>()
    var quotient = rhv
    while (quotient > 0) {
        val reminder = quotient % 10
        list.add((reminder * lhv).toString())
        quotient /= 10
    }
    val maxLength = list.maxBy { it.length }?.length ?: return
    val totalLength = maxLength + list.size
    val outputStream = File(outputName).bufferedWriter()
    outputStream.appendln(lhv.toString().padStart(totalLength))
    outputStream.appendln("*" + rhv.toString().padStart(totalLength - 1))
    outputStream.appendln("-".repeat(totalLength))
    for (i in list.indices) {
        var count = totalLength - i
        if (i > 0) {
            outputStream.append("+")
            count -= 1
        }
        outputStream.appendln(list[i].padStart(count))
    }
    outputStream.appendln("-".repeat(totalLength))
    outputStream.appendln((lhv * rhv).toString().padStart(totalLength))
    outputStream.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val lhvStr = lhv.toString()
    val quotient = lhv / rhv
    val quotientList = quotient.toString().chunked(1).map { it.toInt() }
    val outputStream = File(outputName).bufferedWriter()

    var countBlanks = 0
    var difference = 0

    var indexBegin = 0
    var indexEnd = (quotientList[0] * rhv).toString().length

    for (i in quotientList.indices) {
        val product = quotientList[i] * rhv
        var part = lhvStr.substring(indexBegin, indexEnd)
        var minuendStr = if (i == 0) part else "$difference$part"
        var minuend = minuendStr.toInt()

        if (product > minuend) {
            indexEnd += 1
            part = lhvStr.substring(indexBegin, indexEnd)
            minuendStr = if (i == 0) part else "$difference$part"
            minuend = minuendStr.toInt()
        }
        if (i == 0) {
            if (isPartStringLengthEqualProductStringLength(minuendStr, product)) {
                outputStream.append(" ")
            }
            outputStream.appendln("$lhv | $rhv")
        } else {
            outputStream.append(" ".repeat(countBlanks))
            outputStream.appendln(minuendStr)
        }

        addBlanks(minuendStr, product, i, outputStream, countBlanks)
        outputStream.append("-$product")

        if (i == 0) {
            val count =
                lhvStr.length + 2 + (if (isPartStringLengthEqualProductStringLength(
                        minuendStr,
                        product
                    )
                ) 1 else 0) - product.toString().length
            outputStream.appendln(" ".repeat(count) + quotient)
        } else {
            outputStream.appendln()
        }

        addBlanks(minuendStr, product, i, outputStream, countBlanks)
        outputStream.appendln("-".repeat(product.toString().length + 1))

        difference = minuend - product
        indexBegin = indexEnd
        indexEnd += 1
        countBlanks += product.toString().length + 1 - difference.toString().length
        if (i == quotientList.size - 1) {
            if (isPartStringLengthEqualProductStringLength(minuendStr, product) && quotientList.size > 1) {
                outputStream.append(" ".repeat(countBlanks - 1))
            } else {
                outputStream.append(" ".repeat(countBlanks))
            }


            outputStream.appendln(difference.toString())
        }
    }
    outputStream.close()
}

fun isPartStringLengthEqualProductStringLength(partStr: String, product: Int): Boolean =
    partStr.length == product.toString().length

fun addBlanks(partStr: String, product: Int, i: Int, outputStream: BufferedWriter, countBlanks: Int) {
    if (i > 0) {
        val blanks =
            if (isPartStringLengthEqualProductStringLength(partStr, product)) countBlanks - 1 else countBlanks
        outputStream.append(" ".repeat(blanks))
    }
}

