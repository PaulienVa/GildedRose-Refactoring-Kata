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
               item.quality = item.decreaseQuality()
               item.quality = item.decreaseQuality()
            } else if (!item.isAgedBrie() && !item.isBackStagePasses()) {
                item.quality = item.decreaseQuality()
            } else if (item.isBackStagePasses()) {
                item.quality =  item.increaseQualityForBackStagePasses()
            } else {
                item.quality = item.increaseQuality()
            }

            item.sellIn = updateSellIn(item)

            if (item.hasNegativeSellIn()) {
                if (item.isAgedBrie()) {
                    item.quality = item.increaseQuality()
                } else if (!item.isBackStagePasses()) {
                    item.quality = item.decreaseQuality()
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
    private fun Item.hasNegativeSellIn() = this.sellIn < 0

    private fun Item.increaseQualityForBackStagePasses() : Int {
        return when (this.sellIn) {
            in minimalHighSellInBackStage .. minimalNormalSellInBackStage -> {
                this.increaseQuality(2)
            }
            in 1 .. minimalHighSellInBackStage -> {
                this.increaseQuality(3)
            }
            else -> {
                this.increaseQuality()
            }
        }
    }

    private fun Item.increaseQuality(step : Int = 1) : Int {
        return  if (this.hasValidQuality()) {
            this.quality + step
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

    private fun Item.decreaseQuality(): Int {
        return if (this.hasValidQuality() && !this.isSulfuras()) {
            this.quality - 1
        } else if (this.isSulfuras() || this.hasNoQuality()) {
            this.quality
        } else {
            maximumQuality
        }
    }
}
