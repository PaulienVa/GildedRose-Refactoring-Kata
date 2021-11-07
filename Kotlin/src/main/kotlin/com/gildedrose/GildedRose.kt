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
                            if (item.quality < maximumQuality) {
                                item.quality = item.quality + 1
                            }
                        }

                        if (item.sellIn < minimalHighSellInBackStage) {
                            if (item.quality < maximumQuality) {
                                item.quality = item.quality + 1
                            }
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
                        item.quality = item.quality - item.quality
                    }
                } else {
                    if (item.quality < maximumQuality) {
                        item.quality = item.quality + 1
                    }
                }
            }
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

