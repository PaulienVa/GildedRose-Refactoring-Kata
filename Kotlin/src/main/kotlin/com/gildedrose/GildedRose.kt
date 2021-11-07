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
                if (item.quality > 0) {
                    if (item.name != sulfuras) {
                        item.quality = item.quality - 1
                    }
                }
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

            updateSellIn(item)

            if (item.sellIn < 0) {
                if (item.name != agedBrie) {
                    if (item.name != backStagePasses) {
                        if (item.quality > 0) {
                            if (item.name != sulfuras) {
                                item.quality = item.quality - 1
                            }
                        }
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

    private fun updateSellIn(item: Item) {
        if (item.name != sulfuras) {
            item.sellIn = item.sellIn - 1
        }
    }

}

