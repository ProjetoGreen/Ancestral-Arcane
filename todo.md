# Ancestral Arcane Mod - Alignment TODO

This document tracks the status of mod features compared to the project Wiki. Many Core (P1) and Exploration (P2) features are now implemented.

---

## ✅ Completed (Recent Alignment Phase)
- [x] **Leather Grip Properties**: implemented -10% cast time and +1 reuse tolerance.
- [x] **Cast Hold/Collapse Mechanic**: implemented 1s Stage D window with fizzle penalty.
- [x] **Affinity Bonus Expansion**: implemented -15% cast time and -20% charge cost/dirty gain for matching catalyst/spell pairs.
- [x] **Home Anchor Registration**: wands can now be linked to a Home Anchor (Block).
- [x] **Heartstone Logic**: now teleports to the linked Home Anchor or respawn point.
- [x] **Summon Wolves Expansion**: Tier-based scaling (3, 4, 5 wolves) and Raw Beef cost reduction (1 charge total).
- [x] **Wand Durability Tiers**: All wands in registry updated with Wiki-accurate durability values.

---

## 🔴 Priority 1: High Impact Mechanics
- [ ] **Affinity Visuals**: Add particle or sound cues when casting with affinity to provide player feedback.
- [ ] **Cast Failures (Impurity)**: Ensure the "Dirty" (Impurity) meter correctly triggers negative effects or prevents casting when reaching 100% of capacity.

---

## 🟡 Priority 2: Mid-Game Utility
- [ ] **Fragment of All Knowledge (Rapid Swapping)**:
  - Implement a field-swapping GUI or mechanic when the wand contains the `Fragment of All Knowledge`.
  - Goal: Allow basic rune swapping without an Arcane Smithing Table.
- [ ] **Home Anchor Visual Sync**: Add a "linking" particle effect between the Wand and the Anchor when right-clicked.

---

## 🟢 Priority 3: Advanced Progression
- [ ] **Advanced Modular Wand (Multi-Slot)**:
  - Expand `WandItem` NBT structure to support up to 3 `equipped_runes`.
  - Implement Tier V "Socketed Grimoire" expansion logic.
  - Add a selector mechanism (keybind or scroll) to switch active runes in the field.

---

## ⚪ Priority 4: UX & Polish
- [ ] **Translatable & Dynamic Tooltips**:
  - Replace `Component.literal` with `Component.translatable` for all dynamic data.
  - Implement `toRoman(tier)` converter for tooltips.
- [ ] **NBT Default Consistency**:
  - Pre-initialize all Registry/Creative Tab items with correct NBT.
- [ ] **Code Polish (Technical Debt)**:
  - Resolve remaining 100+ "Null type safety" warnings in registry and UI classes.
