# Inventorio... fixes?
A dirty collection of patches for special compatibility with other mods. Made specifically for the [Hardrock TerraFirmaCraft 3](https://www.curseforge.com/minecraft/modpacks/terrafirmacraft-hardrock) modpack.

This project exists as a separate mod due to the 1.18.2 version of Inventorio no longer receiving updates.
Please support Lizard of Oz, the original creator of Inventorio, and RubixDev, the current maintainer!
You can find the Inventorio github repository [here](https://github.com/RubixDev/Inventorio) and its modrinth page [here](https://modrinth.com/mod/inventorio).

## List of patches:
### Inventorio/General:
- Fixes an edge case regarding "set item slot" calls targeting the main hand when a tool is active (this should hopefully fix a good chunk of dupe exploits)
- Fixes incorrect mining speed being used when a tool is selected in the hotbar while another tool from a tool slot is active
- Fixes Inventorio slots forgetting data on client when changing dimensions, which required having to manually access the tool inventory to update them
- Adds convenience keybinds for explicitly opening a vanilla inventory, explicitly opening an Inventorio inventory, and changing the currently open inventory (effectively allowing you to set a keybind for the switch in the top right corner)

<br/>

Next come mod compatibility fixes! These will automatically load if the corresponding mod is present:
### TerraFirmaCraft:
- Fixes Inventorio slots disrespecting item stack sizes on item pickup, letting you overstack items up to 64 when you're not supposed to
- Makes Inventorio slots follow overburdening logic (e.g. having a Huge, Very Heavy item in your offhand or extended inventory corretcly applies Exhausted and Overburdened instead of being ignored)
- Makes TFC's Stack Food keybind work in the Inventorio inventory screen
### Tinkers' Construct:
- Fixes the Soulbound modifier not working if the item is in an Inventorio slot (making the item vanish on death!)
### Hardcore Revival:
- Makes the "Press E to die" prompt also work if you're opening the Inventorio inventory, instead of requiring you to open the vanilla inventory


##
Please report any issues or oversights! Suggestions are also welcome.
