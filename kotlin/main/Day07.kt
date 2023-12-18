import Card.JACK
import Card.JOKER
import Day07.part1
import Day07.part2
import Strength.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    println(part1(input)) // 253313241
    println(part2(input)) // 253362743
}

object Day07 {
    fun part1(input: List<String>): Int {
        return input
            .map { line ->
                val tmp = line.split(" ")
                val bid = tmp.last().toInt()
                val cards = tmp
                    .first()
                    .chunked(1)
                    .map { Card(it) }

                Hand(cards, cards.sortedDescending(), bid)
            }
            .groupBy { it.getStrength() }
            .mapValues { (_, hands) ->
                hands.sortedWith(handComparator)
            }
            .toSortedMap()
            .flatMap { it.value }
            .mapIndexed { rang, hand -> hand.bid * (rang + 1) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line ->
                val tmp = line.split(" ")
                val bid = tmp.last().toInt()
                val cards = tmp
                    .first()
                    .chunked(1)
                    .map { Card(it) }
                    .map { if (it == JACK) JOKER else it }

                val sorted = cards
                    .sortedDescending()
                    .toMutableList()

                when (sorted.count { it == JOKER }) {
                    1 -> {
                        when {
                            (sorted[0] == sorted[1] && sorted[1] == sorted[2])
                                    || (sorted[1] == sorted[2] && sorted[2] == sorted[3]) -> { // three of a kind
                                sorted[4] = sorted[1]
                            }

                            sorted[0] == sorted[1] && sorted[2] == sorted[3] -> { // two pairs
                                sorted[4] = sorted[0]
                            }

                            sorted[0] == sorted[1] || sorted[1] == sorted[2] || sorted[2] == sorted[3] -> { // one pair
                                sorted[4] = if (sorted[0] == sorted[1]) sorted[0] else sorted[2]
                            }

                            else -> { // four of a kind and high card
                                sorted[4] = sorted[0]
                            }
                        }
                    }

                    2 -> {
                        // all three are different
                        if (sorted[0] == sorted[1] && sorted[1] != sorted[2]) {
                            sorted[3] = sorted[0]
                            sorted[4] = sorted[0]
                        } else { // one pair or thee of a kind, take card 2
                            sorted[3] = sorted[1]
                            sorted[4] = sorted[1]
                        }
                    }

                    3 -> { // one pair or high card
                        sorted[2] = sorted[0]
                        sorted[3] = sorted[0]
                        sorted[4] = sorted[0]
                    }

                    4 -> { // all jokers are card 1
                        sorted.replaceAll { sorted[0] }
                    }

                    else -> {} // if 0 or 5, nothing to do
                }

                Hand(cards, sorted.sortedDescending(), bid)
            }
            .groupBy { it.getStrength() }
            .mapValues { (_, hands) ->
                hands.sortedWith(handComparator)
            }
            .toSortedMap()
            .flatMap { it.value }
            .mapIndexed { rang, hand -> hand.bid * (rang + 1) }
            .sum()
    }
}

enum class Card(private val text: String) {
    JOKER("J"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("T"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    override fun toString(): String = text

    companion object {
        operator fun invoke(s: String): Card {
            return when (s) {
                "2" -> TWO
                "3" -> THREE
                "4" -> FOUR
                "5" -> FIVE
                "6" -> SIX
                "7" -> SEVEN
                "8" -> EIGHT
                "9" -> NINE
                "T" -> TEN
                "J" -> JACK
                "Q" -> QUEEN
                "K" -> KING
                "A" -> ACE
                else -> throw Exception("Something bad happen!")
            }
        }
    }
}

enum class Strength {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND;
}

data class Hand(
    val originalCards: List<Card>,
    val mappedCards: List<Card>,
    val bid: Int,
)

val handComparator = compareBy<Hand>(
    { it.originalCards[0] },
    { it.originalCards[1] },
    { it.originalCards[2] },
    { it.originalCards[3] },
    { it.originalCards[4] },
)

fun Hand.getStrength(): Strength = when {
    isFiveOfAKind() -> FIVE_OF_A_KIND
    isFourOfAKind() -> FOUR_OF_A_KIND
    isFullHouse() -> FULL_HOUSE
    isThreeOfAKind() -> THREE_OF_A_KIND
    isTwoPair() -> TWO_PAIR
    isOnePair() -> ONE_PAIR
    else -> HIGH_CARD
}

fun Hand.isFiveOfAKind(): Boolean = mappedCards.all { it == mappedCards[0] }

fun Hand.isFourOfAKind(): Boolean = mappedCards.count { it == mappedCards[0] } == 4
        || mappedCards.count { it == mappedCards[4] } == 4

fun Hand.isFullHouse(): Boolean =
    (mappedCards.count { it == mappedCards[0] } == 3 && mappedCards.count { it == mappedCards[4] } == 2)
            || (mappedCards.count { it == mappedCards[4] } == 3 && mappedCards.count { it == mappedCards[0] } == 2)

fun Hand.isThreeOfAKind(): Boolean = (mappedCards.count { it == mappedCards[0] } == 3)
        || (mappedCards.count { it == mappedCards[1] } == 3)
        || (mappedCards.count { it == mappedCards[2] } == 3)

fun Hand.isTwoPair(): Boolean = (mappedCards[0] == mappedCards[1] && mappedCards[2] == mappedCards[3])
        || (mappedCards[0] == mappedCards[1] && mappedCards[3] == mappedCards[4])
        || (mappedCards[1] == mappedCards[2] && mappedCards[3] == mappedCards[4])

fun Hand.isOnePair(): Boolean = (mappedCards[0] == mappedCards[1])
        || (mappedCards[1] == mappedCards[2])
        || (mappedCards[2] == mappedCards[3])
        || (mappedCards[3] == mappedCards[4])
