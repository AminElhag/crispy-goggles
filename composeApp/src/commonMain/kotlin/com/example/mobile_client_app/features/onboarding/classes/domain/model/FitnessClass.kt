package com.example.mobile_client_app.features.onboarding.classes.domain.model

data class FitnessClass(
    val id: Int,
    val name: String,
    val time: String,
    val trainer: String,
    val imageUrl: String,
    val date: ClassDate
) {
    enum class ClassDate(val id: Int) {
        TODAY(0), TOMORROW(1);
        companion object {
            fun getById(id: Int): ClassDate? = entries.find { it.id == id }
        }
    }
}