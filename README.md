# Inventorio... fixes?
A dirty collection of patches for special compatibility with other mods. Made specifically for the [Hardrock TerraFirmaCraft 3](https://www.curseforge.com/minecraft/modpacks/terrafirmacraft-hardrock) modpack.

This project exists as a separate mod due to the 1.18.2 version of Inventorio no longer receiving updates.
Please support Lizard of Oz, the original creator of Inventorio, and RubixDev, the current maintainer!
You can find the Inventorio github repository [here](https://github.com/RubixDev/Inventorio) and its modrinth page [here](https://modrinth.com/mod/inventorio).

## List of patches:
### Inventorio/General:
- Fix an edge case regarding "set item slot" calls targeting the main hand when a tool is active (this should hopefully fix a good chunk of dupe exploits)
- Fix incorrect mining speed being used when a tool is selected in the hotbar while another tool from a tool slot is active
- Fix Inventorio slots losing data on client when changing dimensions
- Add extra keybinds for explicitly opening a vanilla inventory or an Inventorio inventory, as well as one for changing the currently open inventory (equivalent to hitting the switch in the top right corner)

Everything below is optional and will automatically load if the corresponding mod is present:
### TerraFirmaCraft:
- Fix Inventorio slots disrespecting item stack sizes on item pickup
- Make Inventorio slots do overburdening logic
- Make the Inventorio inventory screen valid for the Stack Food keybind
### Tinkers' Construct:
- Make Inventorio slots do Soulbound modifier logic
### Hardcore Revival:
- Make the "Press E to die" prompt also recognize the Inventorio inventory as an inventory


##
Please report any issues or oversights! Suggestions are also welcome.
