package com.example.practice.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun CharacterFilterForm(filterTypes: Map<String,CharacterFilterType>, onFilterChange : (CharacterFilterType) -> Unit = {}) {
    Column {
        filterTypes.values.forEach { filterType ->
            when (filterType) {
                is CharacterFilterType.Gender -> {
                    Text("Gender")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Male")
                        Checkbox(checked = filterType.male, onCheckedChange = {
                            onFilterChange(filterType.copy(male = !filterType.male)) })
                        Text("Female")
                        Checkbox(filterType.female, {
                            onFilterChange(filterType.copy(female = !filterType.female))})
                    }
                }

                is CharacterFilterType.Status -> {
                    Text("Status")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Alive")
                        Checkbox(filterType.alive, {
                            onFilterChange( filterType.copy(alive = !filterType.alive))})
                        Text("Dead")
                        Checkbox(filterType.dead, {
                            onFilterChange(filterType.copy(dead = !filterType.dead))})
                        Text("Unknown")
                        Checkbox(filterType.unknown, {
                            onFilterChange( filterType.copy(unknown = !filterType.unknown))})
                    }

                }
            }
        }
    }
}