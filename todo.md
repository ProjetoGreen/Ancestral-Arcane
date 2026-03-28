# Ancestral Arcane Mod - Alignment TODO

This document tracks the status of mod features compared to the project Wiki. All Core (P1) and mid-to-late game (P2, P3) mechanical features are now implemented.

---

## ✅ Completed (Mechanical Alignment Phase)
- [x] **Leather Grip Properties**: implemented -10% cast time and +1 reuse tolerance.
- [x] **Cast Hold/Collapse Mechanic**: implemented 1s Stage D window with auto-fizzle and penalties.
- [x] **Affinity Bonus Expansion**: implemented -15% cast time, -20% costs, and elemental particles.
- [x] **Home Anchor Registration**: wands link to anchors with visual portal effects.
- [x] **Heartstone Logic**: teleports to linked anchors with 0.5 wear cost.
- [x] **Summon Wolves Scaling**: Tier-based counts and Raw Beef charge discount.
- [x] **Fragment of All Knowledge**: implemented sneak+right-click rapid rune swapping in the field.
- [x] **Translatable UX**: all wands/grimoires now use translatable tooltips and Roman numeral tiers.
- [x] **Impurity Enforcement**: spells now fail if the "Dirty" meter reaches 100%.
- [x] **Dirty Side-Effects**: implemented Slowness I and smoke particles when > 75% dirty.
- [x] **Advanced Modular Wand (Multi-Slot)**:
  - [x] Expanded `WandItem` NBT to support up to 3 `runes` in a ListTag.
  - [x] Implemented NBT Migration for legacy items.
  - [x] Implemented Tier V "Socketed Grimoire" expansion logic in Smithing Table.
  - [x] Implemented Shift + Right-click air selector mechanism to cycle active runes.
  - [x] Updated Tooltips to display current selection and all equipped slots.

---

## 🟡 Priority 2: Mid-Game Utility
- [ ] **Advanced Home Anchor Visuals**: Implement a visible "Beam" or visual tether between linked anchors and the player when holding the wand.

---

## 🟢 Priority 3: Advanced Progression
- [ ] **Advanced World Interaction**: (Planned) Additional complex spell interactions.

---

## ⚪ Priority 4: UX & Polish
- [x] **Recipe Alignment**: Synchronized all crafting recipes with the official Wiki, including new Catalysts and Fragments.
- [x] **Nesting/Creative Tab Consistency**:
  - [x] Pre-initialized all Registry/Creative Tab items with correct NBT.
- [x] **Code Polish (Technical Debt)**:
  - [x] Resolved major "Null type safety" warnings in registry, UI, and spell execution classes.
