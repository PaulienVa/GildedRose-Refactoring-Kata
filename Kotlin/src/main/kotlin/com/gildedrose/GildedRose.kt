package com.gildedrose

class GildedRose(var items: Array<Item>) {

    private val agedBrie = "Aged Brie"
    private val backStagePasses = "Backstage passes to a TAFKAL80ETC concert"
    private val sulfuras = "Sulfuras, Hand of Ragnaros"

    private val maximumQuality = 50
    private val minimalNormalSellInBackStage = 11
    private val minimalHighSellInBackStage = 6

    fun updateQuality() {
        for (item in items) {
            if (item.name != agedBrie && item.name != backStagePasses) {
                item.quality = decreaseQuality(item)
            } else {
                if (item.quality < maximumQuality) {
                    item.quality = item.quality + 1

                    if (item.name == backStagePasses) {
                        if (item.sellIn < minimalNormalSellInBackStage) {
                            item.quality = increaseQuality(item)
                        }

                        if (item.sellIn < minimalHighSellInBackStage) {
                            item.quality = increaseQuality(item)
                        }
                    }
                }
            }

            item.sellIn = updateSellIn(item)

            if (negativeSellIn(item)) {
                if (item.name != agedBrie) {
                    if (item.name != backStagePasses) {
                        item.quality = decreaseQuality(item)
                    } else {
                        item.quality = 0
                    }
                } else {
                    item.quality = increaseQuality(item)
                }
            }
        }
    }

    private fun increaseQuality(item: Item) : Int {
        return  if (item.quality < maximumQuality) {
            item.quality + 1
        } else   {
            item.quality
        }
    }


    private fun negativeSellIn(item: Item) = item.sellIn < 0

    private fun positiveQuality(item: Item) = item.quality > 0

    private fun updateSellIn(item: Item): Int {
        return if (item.name != sulfuras) {
             item.sellIn - 1
        } else  {
            item.sellIn
        }
    }

    private fun decreaseQuality(item: Item): Int {
        return if (positiveQuality(item) && item.name != sulfuras) {
            item.quality - 1
        } else {
            item.quality
        }
    }
}

