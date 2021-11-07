package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class GildedRoseTest {

    private val foo = "foo"
    private val agedBrie = "Aged Brie"
    private val sulfuras = "Sulfuras, Hand of Ragnaros"
    private val backStagePasses = "Backstage passes to a TAFKAL80ETC concert"
    private val conjured = "Conjured"

    private val defaultSellIn = 3

    @Test
    fun foo() {
        val updatedItem = updateQuality(quality = 0, sellIn = 0)

        updatedItem hasName foo
        updatedItem hasSellIn -1
        updatedItem hasQuality 0
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        val updatedItem = updateQuality(sellIn = 0, quality = 10)

        updatedItem hasName foo
        updatedItem hasSellIn -1
        updatedItem hasQuality 8
    }

    @ParameterizedTest
    @MethodSource("negativeQualityAndSellIn") // some will fail  initialy
    fun `The Quality of an item is never negative`(quality: Int, sellIn: Int) {
        val updatedItem = updateQuality(quality = quality, sellIn = sellIn)

        updatedItem hasName foo
        updatedItem.hasNotNegativeQuality()
        updatedItem hasSellIn sellIn - 1
    }

    @Test
    fun  `"Aged Brie" actually increases in Quality the older it gets`(){
        val updatedItem = updateQuality(name = agedBrie, quality =  10)

        updatedItem hasName agedBrie
        updatedItem hasSellIn 2
        updatedItem hasQuality 11
    }

    @Test
    fun `The Quality of an item is never more than 50`() {
        val updatedItem = updateQuality(quality = 50)

        updatedItem hasName foo
        updatedItem hasSellIn 2
        updatedItem hasQuality 49
    }

    @Test // will fail initialy
    fun `The Quality of an item is never more than 50 - although higher than 50`() {
        val updatedItem = updateQuality(quality = 65)

        updatedItem hasName foo
        updatedItem hasSellIn 2
        updatedItem hasQuality 50
    }


    @Test
    fun `"Sulfuras", being a legendary item, never has to be sold or decreases in Quality`() {
        val updatedItem = updateQuality(name = sulfuras, quality = 10)

        updatedItem hasName sulfuras
        updatedItem hasSellIn defaultSellIn
        updatedItem hasQuality 10
    }

    @Test
    fun `"Sulfuras", as a legendary item and as such its Quality is 80 and it never alters`() {
        val updatedItem = updateQuality(name = sulfuras, quality = 80)

        updatedItem hasName sulfuras
        updatedItem hasSellIn defaultSellIn
        updatedItem hasQuality 80
    }

    @ParameterizedTest
    @ValueSource(ints = [6, 7, 8, 9, 10])
    fun `"Backstage passes", like aged brie, increases in Quality as its SellIn value approaches -  Quality increases by 2 when there are 10 days or less`(sellIn: Int) {
        val updatedItem = updateQuality(name = backStagePasses, sellIn = sellIn, quality = 10)

        updatedItem hasName backStagePasses
        updatedItem hasSellIn sellIn - 1
        updatedItem hasQuality 12 // quality + 2 = 10 + 2
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5])
    fun `"Backstage passes", like aged brie, increases in Quality as its SellIn value approaches -  Quality increases by 3 when there are 5 days or less`(sellIn: Int) {
        val updatedItem = updateQuality(name = backStagePasses, sellIn = sellIn, quality = 10)

        updatedItem hasName backStagePasses
        updatedItem hasSellIn sellIn - 1
        updatedItem hasQuality 13 // quality + 3 = 10 + 3
    }

    @Test
    fun `"Backstage passes", like aged brie, increases in Quality as its SellIn value approaches - Quality drops to 0 after the concert`() {
        val updatedItem = updateQuality(name = backStagePasses, sellIn = 0, quality = 10)

        updatedItem hasName backStagePasses
        updatedItem hasSellIn -1
        updatedItem hasQuality 0
    }

    @Test // fail initialy
    fun `"Conjured" items degrade in Quality twice as fast as normal items`()  {
        val updatedItem = updateQuality(name = conjured, quality = 10)

        updatedItem hasName conjured
        updatedItem hasSellIn defaultSellIn
        updatedItem hasQuality 8
    }

    private fun updateQuality(name: String = foo, quality: Int, sellIn: Int = defaultSellIn): Item {
        val item = arrayOf(Item(name = name, sellIn = sellIn, quality = quality))
        val app = GildedRose(item)
        app.updateQuality()

        return app.items[0]
    }

    companion object {
        @JvmStatic
        fun negativeQualityAndSellIn() = Stream.of(
            Arguments.of(0, 0),
            Arguments.of(0, -10),
            Arguments.of(-1, -10),
            Arguments.of(-1, 0),
        )
    }
}

private infix fun Item.hasName(name: String) =  assertEquals(name, this.name)
private infix fun Item.hasSellIn(sellIn: Int) =  assertEquals(sellIn, this.sellIn)
private infix fun Item.hasQuality(quality: Int) =  assertEquals(quality, this.quality)
private fun Item.hasNotNegativeQuality() =  assertTrue(this.quality >= 0)

