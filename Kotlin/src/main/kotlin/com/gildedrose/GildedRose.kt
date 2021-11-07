package com.gildedrose

class GildedRose(var items: Array<Item>) {

    private val agedBrie = "Aged Brie"
    private val backStagePasses = "Backstage passes to a TAFKAL80ETC concert"
    private val sulfuras = "Sulfuras, Hand of Ragnaros"

    private val maximumQuality = 50

    private val minimalNormalSellInBackStage = 11

    private val minimalHighSellInBackStage = 6

    fun updateQuality() {
        for (i in items.indices) {
            if (items[i].name != agedBrie && items[i].name != backStagePasses) {
                if (items[i].quality > 0) {
                    if (items[i].name != sulfuras) {
                        items[i].quality = items[i].quality - 1
                    }
                }
            } else {
                if (items[i].quality < maximumQuality) {
                    items[i].quality = items[i].quality + 1

                    if (items[i].name == backStagePasses) {
                        if (items[i].sellIn < minimalNormalSellInBackStage) {
                            if (items[i].quality < maximumQuality) {
                                items[i].quality = items[i].quality + 1
                            }
                        }

                        if (items[i].sellIn < minimalHighSellInBackStage) {
                            if (items[i].quality < maximumQuality) {
                                items[i].quality = items[i].quality + 1
                            }
                        }
                    }
                }
            }

            if (items[i].name != sulfuras) {
                items[i].sellIn = items[i].sellIn - 1
            }

            if (items[i].sellIn < 0) {
                if (items[i].name != agedBrie) {
                    if (items[i].name != backStagePasses) {
                        if (items[i].quality > 0) {
                            if (items[i].name != sulfuras) {
                                items[i].quality = items[i].quality - 1
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality
                    }
                } else {
                    if (items[i].quality < maximumQuality) {
                        items[i].quality = items[i].quality + 1
                    }
                }
            }
        }
    }

}

