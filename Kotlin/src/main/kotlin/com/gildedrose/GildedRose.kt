package com.gildedrose

class GildedRose(var items: Array<Item>) {

    private val agedBrie = "Aged Brie"
    private val backStagePasses = "Backstage passes to a TAFKAL80ETC concert"
    private val sulfuras = "Sulfuras, Hand of Ragnaros"
    private val conjured = "Conjured"

    private val maximumQuality = 50
    private val minimalNormalSellInBackStage = 11
    private val minimalHighSellInBackStage = 6

    fun updateQuality() {
        for (item in items) {
            if (item.isConjured()) {
               item.quality = decreaseQuality(item)
               item.quality = decreaseQuality(item)
            } else if (!item.isAgedBrie() && !item.isBackStagePasses()) {
                item.quality = decreaseQuality(item)
            } else if (item.isBackStagePasses()) {
                item.quality =  increaseQualityForBackStagePasses(item)
            } else {
                item.quality = increaseQuality(item)
            }

            item.sellIn = updateSellIn(item)

            if (negativeSellIn(item)) {
                if (item.isAgedBrie()) {
                    item.quality = increaseQuality(item)
                } else if (!item.isBackStagePasses()) {
                    item.quality = decreaseQuality(item)
                } else {
                    item.quality = 0
                }
            }
        }
    }

    private fun Item.isConjured() = this.name == conjured
    private fun Item.isAgedBrie() = this.name == agedBrie
    private fun Item.isBackStagePasses() = this.name == backStagePasses
    private fun Item.isSulfuras() = this.name == sulfuras
    private fun Item.hasValidQuality() = this.quality in 1 until maximumQuality
    private fun Item.hasNoQuality() = this.quality == 0

    private fun negativeSellIn(item: Item) = item.sellIn < 0

    private fun increaseQualityForBackStagePasses(item: Item) : Int {
        return when (item.sellIn) {
            in minimalHighSellInBackStage .. minimalNormalSellInBackStage -> {
                increaseQuality(item, 2)
            }
            in 1 .. minimalHighSellInBackStage -> {
                increaseQuality(item, 3)
            }
            else -> {
                increaseQuality(item)
            }
        }
    }

    private fun increaseQuality(item: Item,  step : Int = 1) : Int {
        return  if (item.hasValidQuality()) {
            item.quality + step
        } else {
            maximumQuality
        }
    }

    private fun updateSellIn(item: Item): Int {
        return if (!item.isSulfuras()) {
             item.sellIn - 1
        } else  {
            item.sellIn
        }
    }

    private fun decreaseQuality(item: Item): Int {
        return if (( item.hasValidQuality()) && !item.isSulfuras()) {
            item.quality - 1
        } else if (item.isSulfuras() || item.hasNoQuality()) {
            item.quality
        } else {
            maximumQuality
        }
    }
}
