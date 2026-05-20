# BudgetBuddy – Fix & Improvement Summary

## Overview

This document summarises the changes made to the BudgetBuddy Android app to address the issues raised in the marking feedback and to complete the outstanding Part 3 requirements.

---

## What Was Fixed

### 1. View List of Entries in a Period

**Problem:** The Reports screen existed visually but fetched no real data. The expense list was never populated.

**Fix:** `ReportsScreen.kt` was rewritten to:
- Accept a start and end date from the user (YYYY-MM-DD format with validation)
- Query the Room database using `getExpensesBetweenDates()` on button press
- Display every matching `ExpenseEntity` in a scrollable list of cards, showing description, amount, category, date, and time range
- Show a clear message when no entries are found for the selected period

---

### 2. View Category Totals in a Period

**Problem:** Category totals were hardcoded placeholder values and did not calculate from real expense data.

**Fix:** Within the same `ReportsScreen.kt`:
- After filtering expenses by date, totals are calculated per category using `groupBy { category }.sumOf { amount }`
- Each category is displayed with its total amount and a `LinearProgressIndicator` showing spend relative to the user's saved maximum budget goal
- The max goal is fetched from the logged-in user's record in the database

---

### 3. Categories Screen Persistence

**Problem:** Categories were stored in a plain in-memory `mutableStateListOf`, meaning they were lost every time the app was closed or navigated away from.

**Fix:** `CategoriesScreen.kt` was updated to:
- Load categories from Room via `dao.getAllCategories()` as a Flow, observed with `collectLatest` so the list stays live
- Insert new categories into the database on add
- Delete categories from the database via a delete icon on each card
- Validate for blank names and duplicate entries before inserting

---

### 4. ExpenseDao Updates

**File:** `ExpenseDao.kt`

- Added `deleteCategory(category: CategoryEntity)` — required by the updated Categories screen
- Cleaned up the `getExpensesBetweenDates` query to use `>=` and `<=` comparisons (works correctly because dates are stored as YYYY-MM-DD strings, where lexicographic and chronological order match)
- Added `ORDER BY name ASC` to `getAllCategories` for a consistent display order

---

### 5. AppDatabase Version Bump

**File:** `AppDatabase.kt`

- Version incremented from `2` to `3`
- A `Migration(2, 3)` stub was added (no DDL changes were needed; the bump ensures Room revalidates the schema and prevents crashes on existing installs)

---

### 6. UI Consistency

All screens now follow the same visual rules:

| Element | Standard applied |
|---|---|
| Top bars | `TopAppBarDefaults.topAppBarColors` with surface background |
| Back navigation | `ArrowBack` icon button on all secondary screens |
| Buttons | Full-width, 52 dp height, default Material 3 shape |
| Cards | `OutlinedCard` for list items, `ElevatedCard` for summary sections |
| Error text | `MaterialTheme.colorScheme.error` |
| Amount text | `MaterialTheme.colorScheme.primary`, bold weight |
| Spacing | 8 dp between minor elements, 16–24 dp between sections |

---

## Files Changed or Added

| File | Status |
|---|---|
| `ReportsScreen.kt` | Rewritten |
| `CategoriesScreen.kt` | Updated |
| `ExpenseDao.kt` | Updated |
| `AppDatabase.kt` | Updated (version 3 + migration) |
