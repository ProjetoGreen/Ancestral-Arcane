import json
import os
import glob

models_dir = "/Users/dan/Documents/mine mod/dntr/src/main/resources/assets/ancestral_arcane/models/item/"

wands = [
    "copper_wand.json", "diamond_wand.json", "emerald_wand.json", 
    "flint_wand.json", "golden_wand.json", "iron_wand.json", "netherite_wand.json",
    "copper_wand_leather_grip.json", "diamond_wand_leather_grip.json", "emerald_wand_leather_grip.json",
    "flint_wand_leather_grip.json", "golden_wand_leather_grip.json", "iron_wand_leather_grip.json", "netherite_wand_leather_grip.json"
]

for w_file in wands:
    filepath = os.path.join(models_dir, w_file)
    if os.path.exists(filepath):
        with open(filepath, "r") as f:
            data = json.load(f)
            
        if "overrides" in data:
            # Keep only the overrides that do NOT have "ancestral_arcane:in_hand"
            new_overrides = []
            for over in data["overrides"]:
                if "ancestral_arcane:in_hand" not in over.get("predicate", {}):
                    new_overrides.append(over)
            data["overrides"] = new_overrides
            
        with open(filepath, "w") as f:
            json.dump(data, f, indent=4)
            
print("Removed in_hand overrides from base wand json models!")
