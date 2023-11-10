from os import chdir

path = "src/main/resources/assets/justenoughcakez"

blockstate = """{
  "variants": {
    "bites=0": {
      "model": "minecraft:block/cake"
    },
    "bites=1": {
      "model": "minecraft:block/cake_slice1"
    },
    "bites=2": {
      "model": "minecraft:block/cake_slice2"
    },
    "bites=3": {
      "model": "minecraft:block/cake_slice3"
    },
    "bites=4": {
      "model": "minecraft:block/cake_slice4"
    },
    "bites=5": {
      "model": "minecraft:block/cake_slice5"
    },
    "bites=6": {
      "model": "minecraft:block/cake_slice6"
    }
  }
}"""

itemModel = """{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "minecraft:item/cake"
  },
  "tintindex": 0
}"""

itemModelCandle = """{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "minecraft:item/cake",
    "layer1": "justenoughcakez:item/candle_color",
    "layer2": "justenoughcakez:item/candle_flame"
  }
}"""

with open("./cakes.txt", "r") as cakes:
    split = cakes.read().split(";")
    for cake in split:
        iModels = path + "/models/item"
        bModels = path + "/models/block"
        bStates = path + "/blockstates"
        with open(bStates + "/" + cake + ".json", "w") as file:
            file.write(blockstate)
        if "candle" in cake:
            with open(iModels + "/" + cake + ".json", "w") as file:
              file.write(itemModelCandle)
        else:
          with open(iModels + "/" + cake + ".json", "w") as file:
            file.write(itemModel)